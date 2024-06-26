package com.dudeNumber4;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import wtf.g4s8.tuples.Triplet;

public class Towers
{

    private int ringCount = 0;
    private boolean ringCountEven;
    private Stack<Integer> start;
    private Stack<Integer> temp;
    private Stack<Integer> target;
    private final PrintMediator mediator = new PrintMediator();
    private boolean quit = false;
    private final Scanner consoleScanner = new Scanner(System.in);

    public void start() throws InterruptedException
    {
        while (ringCount < 3 || ringCount > 8)
        {
            System.out.print("Enter Ring Count between 3 and 8: ");
            try
            {
                this.ringCount = Integer.parseInt(consoleScanner.nextLine());
            }
            catch (NumberFormatException ignored)
            {
            }
        }
        this.prepareGame(ringCount);
        play();
    }

    private void play()
    {
        while (!gameIsComplete()) 
        {
            try
            {
                executeMove();
                if (quit)
                    break;
            } catch (IllegalMove e) 
            {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(String.format("%1$sGame complete.", System.lineSeparator()));
    }

    private void executeMove() throws IllegalMove
    {
        Triplet<Boolean, Stack<Integer>, Stack<Integer>> srcMove = planMove(start);
        Triplet<Boolean, Stack<Integer>, Stack<Integer>> tempMove = planMove(temp);
        Triplet<Boolean, Stack<Integer>, Stack<Integer>> targetMove = planMove(target);

        // Find largest ring which can legally printTower
        Triplet<Boolean, Stack<Integer>, Stack<Integer>> triplettToMove = Collections.unmodifiableList(Arrays.asList(srcMove, tempMove, targetMove)).stream().filter(p -> p != null).max((p1, p2) -> 
        {
            final AtomicReference<Stack<Integer>> r1 = new AtomicReference<>();
            final AtomicReference<Stack<Integer>> r2 = new AtomicReference<>();
            p1.accept((b, i, __) -> r1.set(i));
            p2.accept((b, i, __) -> r2.set(i));
            return peek(r1.get()) > peek(r2.get()) ? 1 : -1;
        }
        ).get();

        if (triplettToMove == null) 
        {
            throw new IllegalMove("expected to find ring to printTower, but it was null");
        }

        triplettToMove.accept((moveLeft, towerToMoveFrom, towerToMoveTo) ->
        {
            try
            {
                move(towerToMoveFrom, towerToMoveTo);
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * @param tower Tower (stack) we're wanting to printTower from
     * @return Tuple of (bool (printTower left?), tower to printTower from, tower to printTower to) or null if can't printTower at all
     */
    private Triplet<Boolean, Stack<Integer>, Stack<Integer>> planMove(Stack<Integer> tower)
    {
        // get tower's direction of movement
        // for that direction, try immediate adjacent tower
        // see if it can printTower legally
        if (peek(tower) == 0) 
        {
            // Can't printTower from this tower
            return null;
        }
        boolean shouldMoveLeft = shouldMoveLeft(tower);
        Stack<Integer> immediateAdjacent = getTowerToMoveTo(tower == start, tower == temp, tower == target, shouldMoveLeft);
        boolean mayMoveToAdjacent = mayLegallyMove(tower, immediateAdjacent);
        if (mayMoveToAdjacent) 
        {
            return Triplet.of(shouldMoveLeft, tower, immediateAdjacent);    
        }
        return null;
    }

    private boolean mayLegallyMove(Stack<Integer> from, Stack<Integer> to)
    {
        // If to ring is empty, we can printTower there
        return (to.isEmpty()) || (peek(from) < peek(to));
    }

    private boolean shouldMoveLeft(Stack<Integer> s)
    {
        int ring = peek(s);
        if (ring == 0) 
        {
            return false; // If we're trying to printTower a null ring, IllegalMove will be thrown downstream.
        }
        // Direction to printTower switches based on odd/even total ring count.
        return ringCountEven == (ring % 2 == 0);
    }

    /**
     * @param towerIsSrc
     * @param towerIsTemp
     * @param towerIsTarget
     * @param left True if we're wanting to printTower left
     * @return The reference to the tower that is "left" or "right" of a given tower
     */
    private Stack<Integer> getTowerToMoveTo(Boolean towerIsSrc, Boolean towerIsTemp, Boolean towerIsTarget, boolean left)
    {
        if (towerIsSrc)
        {
            return left ? target : temp;    
        }
        if (towerIsTemp)
        {
            return left ? start : target;
        }
        return left ? temp : start;
    }

    private void move(Stack<Integer> from, Stack<Integer> to) throws IllegalMove, InterruptedException
    {
        Integer fromValue = peek(from);
        Integer toValue = peek(to);
        if (fromValue == 0) 
        {
            throw new IllegalMove(String.format("Attempting to printTower from %1$s is illegal because it contains no rings.", getTowerName(from)));
        }
        if ((to.isEmpty()) || (toValue > fromValue))
        {
            from.pop();
            to.push(fromValue);
            printMove(from, to, fromValue);
        }
        else
        {
            throw new IllegalMove(String.format("Moving %1$d from %2$s to %3$s is illegal because %4$d is at the top of the tower moving to.", fromValue, getTowerName(from), getTowerName(to), toValue));
        }
    }

    private void printMove(Stack<Integer> from, Stack<Integer> to, Integer fromValue) throws InterruptedException
    {
        System.out.printf("Move ring %1$d from %2$s to %3$s:%n", fromValue, getTowerName(from), getTowerName(to));
        mediator.printTower(start, temp, target, ringCount);
        System.out.print("Enter q to quit or any key to continue: ");
        // Here I discovered that you can't create/close a new Scanner 2 different times in the same (process?).  I had to re-use an existing one.
        // If you try, it'll never halt awaiting next line.
        quit = consoleScanner.nextLine().equals("q");
    }

    private String getTowerName(Stack<Integer> tower)
    {
        if (tower == start)
        {
            return "Start";
        }
        if (tower == temp) 
        {
            return "Temp";     
        }
        return "Target";
    }
    
    private void prepareGame(int ringCount) throws InterruptedException
    {
        start = new Stack<>();
        temp = new Stack<>();
        target = new Stack<>();

        for (int i = ringCount; i > 0; i--) 
        {
            start.push(i);
        }

        ringCountEven = ringCount % 2 == 0;

        System.out.printf("%1$sStart positions:%1$s%n", System.lineSeparator());
        mediator.printTower(start, temp, target, ringCount);
    }

    private boolean gameIsComplete()
    {
        return target.size() == ringCount;
    }

    // Stacks blow up on peek if empty
    private int peek(Stack<Integer> s)
    {
        if (s.isEmpty())
        {
            return 0;
        }
        return s.peek();
    }

}
