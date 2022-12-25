package com.dudeNumber4;

import lombok.Getter;
import lombok.Setter;

// Owns the towers and does calculations to enable canvas printer
public class Canvas
{
    
//          |                |          |       height: 7; ring count: 6
//          =                |          |       row: 7
//         ===               |          |            6
//       =======             |          |
//     ===========           |          |
//   ===============         |          |
// ===================       |          |       height: 1
//        start            temp       target     // this row not actually printed

    private static final int WIDTH_BETWEEN_TOWERS = 3;

    private int ringCount;  // number of rings per tower

    @Getter @Setter private int height;
    @Getter @Setter private int width;
    /*@Getter @Setter*/ private CanvasTower start;
    /*@Getter @Setter*/ private CanvasTower temp;
    /*@Getter @Setter*/ private CanvasTower target;

    /**
     * @param height
     * @param width
     * @implNote The canvas is just an object that holds positions; it doesn't actually print anything.
     */
    public Canvas(CanvasTower start, CanvasTower temp, CanvasTower target)
    {
        this.ringCount = start.getTower().size() + temp.getTower().size() + target.getTower().size();
        this.start = start;
        this.temp = temp;
        this.target = target;
        this.height = canvasHeight();
        this.width = canvasWidth();
    }

    public CanvasTower fallsOnRowWithinTower(int colNum)
    {
        // start WIDTH_BETWEEN_TOWERS temp WIDTH_BETWEEN_TOWERS target
        if (fallsOnRowWithinStartTower(colNum))
        {
            return start;
        }
        if (fallsOnRowBetweenStartAndTemp(colNum))
        {
            return null;
        }
        if (fallsOnRowWithinTempTower(colNum))
        {
            return temp;
        }
        if (fallsOnRowBetweenTempAndTarget(colNum))
        {
            return null;
        }
        return target;
    }

    public boolean fallsOnRowInTowerCenter(int colNum)
    {
        return (fallsOnCenterOfStart(colNum) || fallsOnCenterOfTemp(colNum) || fallsOnCenterOfTarget(colNum));
    }

    /**
     * @param tower
     * @param rowNum
     * @param colNum
     * @param canvas
     * @return True if we fall anywhere within the printed portion of a ring on a tower
     * @implNote Pre: we know we fall somewhere within a tower's area, now determine whether we are in a ring on that tower's area.
     */
    public boolean fallsWithinRing(CanvasTower tower, int rowNum, int colNum)
    {
        if (fallsInRingRow(tower, rowNum - 1)) // convert canvas row num to tower row num
        {
            // We know we're within a tower ROW that needs to print, now find whether we're within a tower ring by column
            return colNum <= tower.getTowerWidthAt(rowNum - 1);
        }
        return false;
    }

    private boolean fallsOnRowWithinStartTower(int colNum)
    {
        return colNum <= temp.maxWidth();
    }

    private boolean fallsOnRowBetweenStartAndTemp(int colNum)
    {
        return colNum <= start.maxWidth() + WIDTH_BETWEEN_TOWERS;
    }

    private boolean fallsOnRowWithinTempTower(int colNum)
    {
        return colNum <= (start.maxWidth() * 2) + WIDTH_BETWEEN_TOWERS;
    }

    private boolean fallsOnRowBetweenTempAndTarget(int colNum)
    {
        return colNum <= (start.maxWidth() * 2) + (WIDTH_BETWEEN_TOWERS * 2);
    }

    private boolean fallsOnCenterOfStart(int colNum)
    {
        return colNum == start.center();
    }

    private boolean fallsOnCenterOfTemp(int colNum)
    {
        return colNum == (start.center() * 2) + WIDTH_BETWEEN_TOWERS;
    }

    private boolean fallsOnCenterOfTarget(int colNum)
    {
        return colNum == (start.center() * 3) + (WIDTH_BETWEEN_TOWERS * 2);
    }

    /**
     * @param tower
     * @param towerRowNum The tower row number; 1 is bottom row where the bottom-most ring would sit.
     * @return
     * @implNote Given a tower and a current row number, returns true if our current row falls on a ring
     *           that needs to print (without respect to ring width).
     */
    private boolean fallsInRingRow(CanvasTower tower, int towerRowNum)
    {
        return towerRowNum - 1 <= tower.maxWidth();
    }

    private int canvasHeight()
    {
        return ringCount + 1; // Hieight of tower fully loaded plus the center pole of the tower above it
    }

    private int canvasWidth()
    {
        int ringWidth = CanvasTower.ringWidth(ringCount);
        return (ringWidth * 3) + (WIDTH_BETWEEN_TOWERS * 2);
    }

}
