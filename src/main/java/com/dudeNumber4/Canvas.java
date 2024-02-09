package com.dudeNumber4;

import lombok.Getter;
import wtf.g4s8.tuples.Pair;

import java.util.List;

// Owns the towers and does calculations to enable console printer
public class Canvas
{
    
    private static final int WIDTH_BETWEEN_TOWERS = 2;

    // width at widest point for all towers, whether they have any rings at the time or not.
    // In ascii art below, width would be 19
    private final int maxTowerWidth;

    // The center of each tower is similar to above; only need be calculated once, it remains the same
    // throughout the drawing period.
    //          height is 7
    //          =
    //         ===  sum 3, center 2
    //       =======  sum 7, center 4
    //     ===========   sum 11, center 6
    //   ===============  sum 15, center 8
    // ===================  sum 19, center 10
    private final int towerCenter;

    private final int ringCount;  // number of rings per tower
    private final CanvasTower start;
    private final CanvasTower temp;
    private final CanvasTower target;

    @Getter private final int height;
    @Getter private final int width;

    /**
     * @implNote The canvas is just an object that holds positions; it doesn't actually print anything.
     */
    public Canvas(List<Integer> start, List<Integer> temp, List<Integer> target)
    {
        // I want to extract this into a method named "InitializeTowers," but java is not smart enough for that.
        this.start = new CanvasTower(start, TowerType.start);
        this.temp = new CanvasTower(temp, TowerType.temp);
        this.target = new CanvasTower(target, TowerType.target);

        if (this.start.getTower().size() == this.temp.getTower().size() && this.temp.getTower().size() == this.target.getTower().size())
        {
            this.ringCount = this.start.getTower().size();
            this.height = canvasHeight();
            this.width = canvasWidth();
            this.maxTowerWidth = Math.max(Math.max(this.start.maxWidth(), this.temp.maxWidth()), this.target.maxWidth());
            configureTowers();
            this.towerCenter = (this.maxTowerWidth / 2) + 1;
        }
        else
        {
            throw new IllegalCanvas("Towers must be of equal height");
        }
    }

    public CanvasTower fallsOnRowWithinTower(int colNum)
    {
        // start WIDTH_BETWEEN_TOWERS temp WIDTH_BETWEEN_TOWERS target
        // It sure seems like some sort of pattern matching would be nice here, but it doesn't fit any java 21 form.
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
     * @param rowNum: 1 is top of tower above the top ring (but the tower center still prints here).  See diagram in CanvasTawer.
     * @param colNum
     * @return True if we fall anywhere within the printed portion of a ring on a tower, int that, if non-0 indicates tower number
     * @implNote Pre: we know we fall somewhere within a tower's area, now determine whether we are in a ring on that tower's area or in the edge/empty space.
     */
    public Pair<Boolean, Integer> fallsWithinRing(CanvasTower tower, int rowNum, int colNum)
    {
        var ringWidth = tower.getRingWidth(rowNum);

        if (ringWidth == 0) // no ring for this tower for this row
            return Pair.of(false, 0);

        // expected to be called left to right
        if (maxTowerWidth == ringWidth)
            return Pair.of(true, 0);  // ring occupies full width

        return fallsWithinRingOnCol(tower, ringWidth, colNum);
    }

    /*
    All these "fallsOnRow" and "fallsWithinRingOnCol" functions are currently designed to be called in order to arrive at the correct position.
     */

    private Pair<Boolean, Integer> fallsWithinRingOnCol(CanvasTower tower, int ringWidth, int colNum)
    {
        var baseEdgeForCurrentRingWidth = (maxTowerWidth - ringWidth) / 2;
        var leftEdgeForCurrentRingWidth = baseEdgeForCurrentRingWidth + 1;
        var rightEdgeForCurrentRingWidth = maxTowerWidth - baseEdgeForCurrentRingWidth;
        if (tower.getTowerType() == TowerType.temp)
        {
            leftEdgeForCurrentRingWidth = maxTowerWidth + WIDTH_BETWEEN_TOWERS + baseEdgeForCurrentRingWidth + 1;
            rightEdgeForCurrentRingWidth = (maxTowerWidth * 2) + WIDTH_BETWEEN_TOWERS - baseEdgeForCurrentRingWidth;
        }
        if (tower.getTowerType() == TowerType.target)
        {
            leftEdgeForCurrentRingWidth = (maxTowerWidth * 2) + (WIDTH_BETWEEN_TOWERS * 2) + baseEdgeForCurrentRingWidth + 1;
            rightEdgeForCurrentRingWidth = (maxTowerWidth * 2) + (WIDTH_BETWEEN_TOWERS * 2) + maxTowerWidth - baseEdgeForCurrentRingWidth ;
        }

        // this func return tuple; boolean, int: print if not 0
        // may need to involve tower.getRingWidth(rowNum)

        return Pair.of((colNum >= leftEdgeForCurrentRingWidth) && (colNum <= rightEdgeForCurrentRingWidth), 0);
    }

    private boolean fallsOnRowWithinStartTower(int colNum)
    {
        return colNum <= maxTowerWidth;
    }

    private boolean fallsOnRowBetweenStartAndTemp(int colNum)
    {
        return colNum <= maxTowerWidth + WIDTH_BETWEEN_TOWERS;
    }

    private boolean fallsOnRowWithinTempTower(int colNum)
    {
        return colNum <= temp.getRightPos();
    }

    private boolean fallsOnRowBetweenTempAndTarget(int colNum)
    {
        return colNum < target.getLeftPos();
    }

    private boolean fallsOnCenterOfStart(int colNum)
    {
        return colNum == towerCenter;
    }

    private boolean fallsOnCenterOfTemp(int colNum)
    {
        return colNum == temp.getLeftPos() + towerCenter - 1;
    }

    private boolean fallsOnCenterOfTarget(int colNum)
    {
        return colNum == target.getLeftPos() + towerCenter - 1;
    }

    private void configureTowers()
    {
        this.start.setLeftPos(1);
        this.temp.setLeftPos(1 + maxTowerWidth + WIDTH_BETWEEN_TOWERS);
        this.target.setLeftPos(1 + (maxTowerWidth * 2) + (WIDTH_BETWEEN_TOWERS * 2));
        this.start.setRightPos(this.start.getLeftPos() + maxTowerWidth);
        this.temp.setRightPos(this.temp.getLeftPos() + maxTowerWidth);
        this.target.setRightPos(this.target.getLeftPos() + maxTowerWidth);
    }

    private int canvasHeight()
    {
        return ringCount + 1; // Height of tower fully loaded plus the center pole of the tower above it
    }

    private int canvasWidth()
    {
        int ringWidth = CanvasTower.ringWidth(ringCount);
        return (ringWidth * 3) + (WIDTH_BETWEEN_TOWERS * 2);
    }

}
