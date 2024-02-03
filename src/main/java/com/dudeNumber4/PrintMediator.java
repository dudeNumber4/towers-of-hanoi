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

    public void printTower(Stack<Integer> start, Stack<Integer> temp, Stack<Integer> target, int towerHeight)
    {
        // "bottom" rings are at head of lists.
        var startTower = new CanvasTower(getListForTower(start, towerHeight), TowerType.start);
        var tempTower = new CanvasTower(getListForTower(temp, towerHeight), TowerType.temp);
        var targetTower = new CanvasTower(getListForTower(target, towerHeight), TowerType.target);
        ConsolePrinter printer = new ConsolePrinter(startTower, tempTower, targetTower);
        printer.Print();
        System.out.println();
    }

    private List<Integer> getListForTower(Stack<Integer> s, int towerHeight)
    {
        // printer wants lists of equal size "padded" with 0
        var result = new ArrayList<>(s);
        IntStream.rangeClosed(s.size() + 1, towerHeight).forEach((i) -> result.add(0));
        return result;
    }

}
