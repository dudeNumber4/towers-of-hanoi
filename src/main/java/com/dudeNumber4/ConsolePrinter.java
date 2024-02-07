package com.dudeNumber4;

import java.util.List;

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
                    if (canvas.fallsWithinRing(towerForRow, rowNum, colNum))
                    {
                        System.out.print("=");
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

}
