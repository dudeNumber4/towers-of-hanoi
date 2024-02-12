package com.dudeNumber4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class CanvasTower
{

    //          |                           |                  |       height: 7; ring count: 6, row: 1
    //          =                           |                  |
    //         ===                          |                  |
    //       =======                        |                  |
    //     ===========                      |                  |
    //   ===============                    |                  |
    // ===================                  |                  |       row: 7, first int in our tower list
    //        start                       temp               target     // this row not actually printed

    // The center of each tower is similar to above; only need be calculated once, it remains the same
    // throughout the drawing period.
    //          height is 7
    //          =
    //         ===  sum 3, center 2
    //       =======  sum 7, center 4
    //     ===========   sum 11, center 6
    //   ===============  sum 15, center 8
    // ===================  sum 19, center 10
    @Getter @Setter private int towerCenter;

    // List of ints representing the ring number at that position in the tower.
    // If there is no ring at that position the value is 0; else it's the number represented by the ring:
    // If tower consists of 3 rings, the rings will be 1,2,3
    @Getter @Setter private List<Integer> tower;
    @Getter @Setter private TowerType towerType;

    // Item 0 reflects width for ring at row height: height: 7; ring count 6
    // Item 5 reflects width for ring at row 1: height: 7; ring count 6
    private final ArrayList<Integer> ringWidths;

    // The positions of this tower at it's left and right column within the canvas, set by the owner
    @Getter @Setter private int leftPos;
    @Getter @Setter private int rightPos;

    /**
     * @param tower
     * @param towerType start, temp, target
     * @implNote Represents a single "tower."  Three towers (from Towers class) show during every printTower: src, temp, target
     *           A tower is just a list of int, each int representing the number that represents how large that ring is.
     *           1 = smallest ring; 8 = largest ring
     *           The three towers are recreated for each "printTower"
     */
    public CanvasTower(List<Integer> tower, TowerType towerType)
    {
        this.tower = tower;
        this.towerType = towerType;
        ringWidths = new ArrayList<>(tower.stream().map(CanvasTower::ringWidth).toList());
    }

    public int maxWidth()
    {
        return ringWidths.stream().max(Comparator.naturalOrder()).orElse(0);
    }

    /**
     * @param row See diagram above.
     * @return Width of the edge that shows on either side of the ring (0 for largest ring)
     */
    public int getRingWidth(int row)
    {
        if (row == 1)
            return 0; // Row 1 never has a ring in it.
        return ringWidths.get((tower.size() - 1) - (row - 2));
    }

    // The ring "number" is simply the value in the list underlying the tower.
    public int getRingNumber(int row)
    {
        if (row == 1)
            return 0; // Row 1 never has a ring in it.
        return tower.get(tower.size() - row + 1);
    }

    /**
     * @param ringNum 1 smallest ring; 8 largest
     * @return Given a ring number, return the width in '=' characters that ring should print.
     */
    public static int ringWidth(int ringNum)
    {
        if (ringNum == 0 || ringNum > 8)
        {
            return 0;
        }
        if (ringNum == 1)
        {
            return 3;
        }
        if (ringNum == 2)
        {
            return 5;
        }
        return (ringNum + 4) + (ringNum - 3) * 3;
    }

}
