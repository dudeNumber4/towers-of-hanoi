package com.dudeNumber4;

import java.util.List;

public class entry 
{

    public static void main(String[] args)
    {
        // After testing console printer, will need some new object that will accept a canvas and a move description (or whatever)
        // from the towers class and provide a new canvas.
        //new Towers().start();
        
        CanvasTower start = new CanvasTower(List.of(3, 2, 1), TowerType.start);
        CanvasTower temp = new CanvasTower(List.of(0,0,0), TowerType.temp);
        CanvasTower target = new CanvasTower(List.of(0,0,0), TowerType.target);
        ConsolePrinter printer = new ConsolePrinter(start, temp, target);
        printer.Print();
    }

}
