package com.dudeNumber4;

import java.util.List;

// 2 assumptions:
// * Console is configured to use fixed width font
// * Console is configured wide enough to display 3 towers with 8 rings without wrapping text
public class ConsolePrinter 
{


//          |                |          |       height: 8; ring count: 6
//          =                |          |       row: 7
//         ===               |          |            6
//       =======             |          |
//     ===========           |          |
//   ===============         |          |
// ===================       |          |
//        start            temp       target     height: 1

    // RESUME: Start filling these functions out.
    // calc canvas width
    // calc canvas height
    // calc center position of each tower
    //      create Canvas
    // for canvas height down to 1
    //   for 1 to canvas width
    //       if fallsInRingVert
    //          if fallsInRingHorz print '='
    //          if fallsInTowerCenter print '|'
    //          else print space
    //   print newline

    /**
     * @param tower
     * @param rowNum
     * @param colNum
     * @param canvas
     * @return True if we fall anywhere within the printed portion of a tower and it's rings?
     */
    private boolean fallsInRingVert(CanvasTower tower, int rowNum, int colNum, Canvas canvas)
    {
        if (rowNum == canvas.getHeight() || rowNum == 1) 
        {
            return false; // top and bottom row form blank border.
        }
        if (fallsInRingRingRow(tower.getTower(), rowNum - 1)) // convert canvas row num to tower row num
        {
            // we know we're within a tower ROW that needs to print
            // eval based on these values.
            int ringWidth = ringWidth(rowNum);
            int ringRowNum = canvas.ringRowNumFrom(rowNum);
            int towerPosition = getTowerPosition(tower, canvas);
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
     * @param ringNum
     * @return Given a ring number (1 smallest ring; 8 largest), return the width in '=' characters that ring should print.
     */
    private int ringWidth(int ringNum)
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
    
    /**
     * @param tower
     * @param towerRowNum The tower row number; 1 is bottom row where the bottom-most ring would sit.
     * @return
     * @implNote Given a tower and a current row number, returns true if our current row falls on a ring
     *           that needs to print (without respect to ring width).
     */
    private boolean fallsInRingRingRow(List<Integer> tower, int towerRowNum)
    {
        return towerRowNum - 1 <= tower.size();
    }

}
