package com.dudeNumber4;

import java.util.List;

// 2 assumptions:
// * Console is configured to use fixed width font
// * Console is configured wide enough to display 3 towers with 8 rings without wrapping text
public class ConsolePrinter 
{

    /**
     *
     */
    private static final int WIDTH_BETWEEN_TOWERS = 3;
    private int ringCount;
    private int centerPosStart;
    private int centerPosTemp;
    private int centerPosTarget;
    private CanvasTower start;
    private CanvasTower temp;
    private CanvasTower target;

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

    // RESUME: Calling functions finished; start filling out main func
    // calc canvas width
    // calc canvas height
    // calc center position of each tower
    //    create CanvasTowers
    // for canvas height down to 1
    //   for 1 to canvas width
    //      if fallsWithinTowerArea(column)
    //      if fallsWithinRing print '='
    //      if tower.Center print '|'
    //      else print space
    //   print newline

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
            // We know we're within a tower ROW that needs to print, now find whether we're within a tower ring by column
            return colNum <= tower.getTowerWidthAt(rowNum - 1);
        }
        return false;
    }

    private CanvasTower fallsWithinTowerArea(int colNum)
    {
        // start WIDTH_BETWEEN_TOWERS temp WIDTH_BETWEEN_TOWERS target
        if (fallsWithinStartTower(colNum))
        {
            return start;
        }
        if (fallsBetweenStartAndTemp(colNum))
        {
            return null;
        }
        if (fallsWithinTempTower(colNum))
        {
            return temp;
        }
        if (fallsBetweenTempAndTarget(colNum))
        {
            return null;
        }
        return target;
    }

    private boolean fallsWithinStartTower(int colNum)
    {
        return colNum <= temp.maxWidth();
    }

    private boolean fallsBetweenStartAndTemp(int colNum)
    {
        return colNum <= start.maxWidth() + WIDTH_BETWEEN_TOWERS;
    }

    private boolean fallsWithinTempTower(int colNum)
    {
        return colNum <= (start.maxWidth() * 2) + WIDTH_BETWEEN_TOWERS;
    }

    private boolean fallsBetweenTempAndTarget(int colNum)
    {
        return colNum <= (start.maxWidth() * 2) + (WIDTH_BETWEEN_TOWERS * 2);
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
