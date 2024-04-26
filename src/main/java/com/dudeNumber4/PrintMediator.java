package com.dudeNumber4;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

/*
Started with just Towers class.  Added ConsolePrinter (and supporting).  This forwarded or mediated between towers and the printer.
 */
public class PrintMediator
{

    public void printTower(Stack<Integer> start, Stack<Integer> temp, Stack<Integer> target, int towerHeight) throws InterruptedException
    {
        // "bottom" rings are at head of lists.
        var startTower = getListForTower(start, towerHeight);
        var tempTower = getListForTower(temp, towerHeight);
        var targetTower = getListForTower(target, towerHeight);
        ConsolePrinter printer = new ConsolePrinter(startTower, tempTower, targetTower);
        print(printer);
        System.out.println();
    }

    private static void print(ConsolePrinter printer) throws InterruptedException
    {
        // Just added virtual thread to tinker.  I thought it would actually slow down print (creating virtual thread for every move just to print),
        // but it did speed up printing (which is very slow).
        Thread.ofVirtual().name("Print move to console").start(printer::Print).join();
    }

    private List<Integer> getListForTower(Stack<Integer> s, int towerHeight)
    {
        // printer wants lists of equal size "padded" with 0
        var result = new ArrayList<>(s);
        IntStream.rangeClosed(s.size() + 1, towerHeight).forEach((i) -> result.add(0));
        return result;
    }

}
