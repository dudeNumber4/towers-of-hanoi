package com.dudeNumber4;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class CanvasTower
{
    
    @Getter @Setter private List<Integer> tower;
    @Getter @Setter private TowerType towerType;

    ArrayList<Integer> ringWidths;

    /**
     * @param tower
     * @param towerType
     * @implNote Represents a single "tower."  Three towers (from Towers class) show during every move: src, temp, target
     *           A tower is just a list of int, each int representing the number that represents how large that ring is.
     *           1 = smallest ring; 8 = largest ring
     *           The three towers are recreated for each "move"
     */
    public CanvasTower(List<Integer> tower, TowerType towerType)
    {
        this.tower = tower;
        this.towerType = towerType;
        ringWidths = new ArrayList<Integer>(tower.stream().map((i) -> ringWidth(i)).toList());
    }

    /**
     * @param row Row number of that tower assuming complete (1 is top; 8 is largest bottom).
     * @return The current width of that row within the tower taking into account it's current state.
     */
    public int getTowerWidthAt(int row)
    {
        // rows are 1 based
        return row <= tower.size() ? ringWidths.indexOf(ringWidth(row)) : 0;
    }

    /**
     * @param ringNum
     * @return Given a ring number (1 smallest ring; 8 largest), return the width in '=' characters that ring should print.
     */
    public static int ringWidth(int ringNum)
    {
        if (ringNum == 1) 
        {
            return 1;
        }
        if (ringNum == 2) 
        {
            return 3;
        }
        return (ringNum + 4) + (ringNum - 3) * 3;
    }
    
}
