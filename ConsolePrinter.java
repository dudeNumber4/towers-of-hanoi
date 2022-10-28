import java.util.List;

public class ConsolePrinter 
{

    // RESUME: Get this turned into a package/project running and debugging
    private boolean fallsInRingVert(CanvasTower tower, int rowNum, int colNum, Canvas canvas)
    {
        if (rowNum == canvas.getHeight() || rowNum == 1) 
        {
            return false;
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

    private int getTowerPosition(CanvasTower tower, Canvas canvas)
    {
        if (tower.getTowerType() == TowerType.start) 
        {
            return canvas.getStartTowerPos();
        }
        if (tower.getTowerType() == TowerType.temp)
        {
            return canvas.getTempTowerPos();
        }
        return canvas.getTargetTowerPos();
    }

    /**
     * @param ringNum
     * @return Given a ring number (1 smallest ring; 8 prolly largest), return the width in '=' characters that ring should print.
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
