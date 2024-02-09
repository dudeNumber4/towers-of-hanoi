package com.dudeNumber4;

import wtf.g4s8.tuples.Pair;

import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;

// Creates the canvas and uses it's calculations to print the towers.
// 2 assumptions:
// * Console is configured to use fixed width font, or it won't look quite right.
// * Console is configured wide enough to display 3 towers with 8 rings without wrapping text
public class ConsolePrinter 
{

    private final Canvas canvas;

    public ConsolePrinter(List<Integer> start, List<Integer> temp, List<Integer> target)
    {
        canvas = new Canvas(start, temp, target);
    }

    public void Print()
    {
        for (int rowNum = 1; rowNum <= canvas.getHeight(); rowNum++)
        {
            for (int colNum = 1; colNum <= canvas.getWidth(); colNum++)
            {
                CanvasTower towerForRow = canvas.fallsOnRowWithinTower(colNum);
                if (towerForRow == null)
                {
                    System.out.print(" ");
                }
                else
                {
                    if (handleRingArea(towerForRow, rowNum, colNum))
                    {
                        continue;
                    }
                    else if (canvas.fallsOnRowInTowerCenter(colNum))
                    {
                        System.out.print("|");
                    }
                    else
                    {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }
    }

    private boolean handleRingArea(CanvasTower tower, int rowNum, int colNum)
    {
        final AtomicReference<Boolean> result = new AtomicReference<>();
        Pair<Boolean, Integer> fallsWithinRingResult = canvas.fallsWithinRing(tower, rowNum, colNum);
        fallsWithinRingResult.accept((fallsWithinRing, towerNumber) ->
        {
            result.set(fallsWithinRing);
            if (fallsWithinRing)
            {
                System.out.print("=");
            }
        });
        return result.get();
    }

}
