package com.dudeNumber4;

// Creates the canvas and uses it's calculations to print the towers.
// 2 assumptions:
// * Console is configured to use fixed width font, or it won't look quite right.
// * Console is configured wide enough to display 3 towers with 8 rings without wrapping text
public class ConsolePrinter 
{

    private Canvas canvas;

    public ConsolePrinter(CanvasTower start, CanvasTower temp, CanvasTower target)
    {
        canvas = new Canvas(start, temp, target);
    }

    public void Print()
    {
        for (int rowNum = canvas.getHeight(); rowNum >= 1; rowNum--)
        {
            for (int colNum = 1; colNum < canvas.getWidth(); colNum++) 
            {
                CanvasTower towerForRow = canvas.fallsOnRowWithinTower(colNum);
                if (towerForRow == null)
                {
                    System.out.print(" ");
                }
                else
                {
                    if (canvas.fallsOnRowInTowerCenter(colNum)) 
                    {
                        System.out.print("|");
                    }
                    if (canvas.fallsWithinRing(towerForRow, rowNum, colNum)) 
                    {
                        System.out.print("=");
                    }
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

}
