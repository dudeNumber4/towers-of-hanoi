package com.dudeNumber4;

import java.util.List;

// 2 assumptions:
// * Console is configured to use fixed width font
// * Console is configured wide enough to display 3 towers with 8 rings without wrapping text
public class ConsolePrinter 
{

    private int ringCount;
    private int centerPosStart;
    private int centerPosTemp;
    private int centerPosTarget;

    public ConsolePrinter(int ringCount)
    {
        this.ringCount = ringCount;
    }

//          |                |          |       height: 7; ring count: 6
//          =                |          |       row: 7
//         ===               |          |            6
//       =======             |          |
//     ===========           |          |
//   ===============         |          |
// ===================       |          |       height: 1
//        start            temp       target     // this row not actually printed

    // RESUME: Finish filling these functions out (X are finished.)
    // X calc canvas width
    // X calc canvas height
    // X calc center position of each tower
    //      create CanvasTower
    // for canvas height down to 1
    //   for 1 to canvas width
    //       X if fallsWithinRing print '='
    //       if fallsInTowerCenter print '|'
    //       else print space
    //   print newline

    private void calculateTowerCenterPositions()
    {
        int ringWidth = CanvasTower.ringWidth(ringCount);
        int centerPointForAllRings = (ringWidth + 1) / 2;
        centerPosStart = centerPointForAllRings;
        centerPosTemp = ringWidth + 3 + centerPointForAllRings;
        centerPosTarget = ((ringWidth + 3) * 2) + centerPointForAllRings;
        
        //          =
        //         ===  sum 3, center 2
        //       =======  sum 7, center 4
        //     ===========   sum 11, center 6
        //   ===============  sum 15, center 8
        // ===================  sum 19, center 10
    }

    private int canvasHeight()
    {
        return ringCount + 1; // Hieight of tower fully loaded plus the center pole of the tower above it
    }

    private int canvasWidth()
    {
        int ringWidth = CanvasTower.ringWidth(ringCount);
        return (ringWidth * 3) + 6; // 3 rings with 2 gaps of 3 spaces between them.
    }

    /**
     * @param tower
     * @param rowNum
     * @param colNum
     * @param canvas
     * @return True if we fall anywhere within the printed portion of a ring on a tower
     */
    private boolean fallsWithinRing(CanvasTower tower, int rowNum, int colNum, Canvas canvas)
    {
        if (rowNum == canvas.getHeight() || rowNum == 1) 
        {
            return false; // top and bottom row form blank border.
        }
        if (fallsInRingRow(tower.getTower(), rowNum - 1)) // convert canvas row num to tower row num
        {
            // We know we're within a tower ROW that needs to print.
            return tower.getTowerWidthAt(rowNum - 1) > 0;
        }
        return false;
    }

    /**
     * @param tower
     * @param canvas
     * @return Position of the tower itself - the point where the center of all the rings on the tower lies.
     */
    private int getTowerPosition(CanvasTower tower, Canvas canvas)
    {
        if (tower.getTowerType() == TowerType.start) 
        {
            return canvas.getStartTowerCenterPos();
        }
        if (tower.getTowerType() == TowerType.temp)
        {
            return canvas.getTempTowerCenterPos();
        }
        return canvas.getTargetTowerCenterPos();
    }

    /**
     * @param tower
     * @param towerRowNum The tower row number; 1 is bottom row where the bottom-most ring would sit.
     * @return
     * @implNote Given a tower and a current row number, returns true if our current row falls on a ring
     *           that needs to print (without respect to ring width).
     */
    private boolean fallsInRingRow(List<Integer> tower, int towerRowNum)
    {
        return towerRowNum - 1 <= tower.size();
    }

}
