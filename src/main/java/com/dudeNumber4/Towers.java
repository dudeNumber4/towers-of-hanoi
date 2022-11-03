package com.dudeNumber4;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;

import wtf.g4s8.tuples.Triplet;

// Unchecked: no recovery
public class IllegalMove extends Error
{
    public IllegalMove(String message) 
    {
        super(message);
    }
}

public class Towers
{

    private int ringCount = 0;
    private boolean ringCountEven;
    private Stack<Integer> src;
    private Stack<Integer> temp;
    private Stack<Integer> target;

    public void start()
    {
        while (ringCount <= 0)
        {
            System.out.println("Enter Ring Count of 3 or greater.");
            try
            {
                Scanner sc = new Scanner(System.in);
                this.ringCount = Integer.parseInt(sc.nextLine());
            } 
            catch (NumberFormatException e)
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
            } catch (IllegalMove e) 
            {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(String.format("===========================================%1$sGame complete.", System.lineSeparator()));
    }

    private void executeMove() throws IllegalMove
    {
        Triplet<Boolean, Stack<Integer>, Stack<Integer>> srcMove = planMove(src);
        Triplet<Boolean, Stack<Integer>, Stack<Integer>> tempMove = planMove(temp);
        Triplet<Boolean, Stack<Integer>, Stack<Integer>> targetMove = planMove(target);

        // Find largest ring which can legally move
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
            throw new IllegalMove("expected to find pain to move, but it was null");    
        }

        triplettToMove.accept((moveLeft, towerToMoveFrom, towerToMoveTo) ->
        {
            move(towerToMoveFrom, towerToMoveTo);
        });
    }

    /**
     * @param tower Tower (stack) we're wanting to move from
     * @return Tuple of (bool (move left?), tower to move from, tower to move to) or null if can't move at all
     */
    private Triplet<Boolean, Stack<Integer>, Stack<Integer>> planMove(Stack<Integer> tower)
    {
        // get tower's direction of movement
        // for that direction, try immediate adjacent tower
        // see if it can move legally
        if (peek(tower) == 0) 
        {
            // Can't move from this tower
            return null;
        }
        boolean shouldMoveLeft = shouldMoveLeft(tower);
        Stack<Integer> immediateAdjacent = getTowerToMoveTo(tower == src, tower == temp, tower == target, shouldMoveLeft);
        boolean mayMoveToAdjacent = mayLegallyMove(tower, immediateAdjacent);
        if (mayMoveToAdjacent) 
        {
            return Triplet.of(shouldMoveLeft, tower, immediateAdjacent);    
        }
        return null;
    }

    private boolean mayLegallyMove(Stack<Integer> from, Stack<Integer> to)
    {
        // If to ring is empty, we can move there
        return (to.size() == 0) || (peek(from) < peek(to));
    }

    private boolean shouldMoveLeft(Stack<Integer> s)
    {
        int ring = peek(s);
        if (ring == 0) 
        {
            return false; // If we're trying to move a null ring, IllegalMove will be thrown downstream.
        }
        // Direction to move switches based on odd/even total ring count.
        return ringCountEven ? ring % 2 == 0 : ring % 2 != 0;
    }

    /**
     * @param towerIsSrc
     * @param towerIsTemp
     * @param towerIsTarget
     * @param left True if we're wanting to move left
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
            return left ? src : target;
        }
        return left ? temp : src;
    }

    private void move(Stack<Integer> from, Stack<Integer> to) throws IllegalMove
    {
        Integer fromValue = peek(from);
        Integer toValue = peek(to);
        if (fromValue == 0) 
        {
            throw new IllegalMove(String.format("Attempting to move from %1$s is illegal because it contains no rings.", getTowerName(from)));
        }
        if ((to.size() == 0) || (toValue > fromValue))
        {
            System.out.println(String.format("Move ring %1$d from %2$s to %3$s", fromValue, getTowerName(from), getTowerName(to)));
            from.pop();
            to.push(fromValue);
        }
        else
        {
            throw new IllegalMove(String.format("Moving %1$d from %2$s to %3$s is illegal because %4$d is at the top of the tower moving to.", fromValue, getTowerName(from), getTowerName(to), toValue));
        }
    }

    private String getTowerName(Stack<Integer> tower)
    {
        if (tower == src) 
        {
            return "Source";     
        }
        if (tower == temp) 
        {
            return "Temp";     
        }
        return "Target";
    }
    
    private void prepareGame(int ringCount)
    {
        src = new Stack<Integer>();
        temp = new Stack<Integer>();
        target = new Stack<Integer>();

        for (int i = ringCount; i > 0; i--) 
        {
            src.push(i);
        }

        ringCountEven = ringCount % 2 == 0;

        System.out.println(String.format("%1$sStart game.  Smallest ring is ring 1; largest ring is ring %2$d%1$s===========================================", System.lineSeparator(), ringCount)); 
    }

    private boolean gameIsComplete()
    {
        return target.size() == ringCount;
    }

    // Stacks blow up on peek if empty
    private int peek(Stack<Integer> s)
    {
        if (s.size() == 0) 
        {
            return 0;
        }
        return s.peek();
    }

}
