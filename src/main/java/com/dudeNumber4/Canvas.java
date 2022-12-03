package com.dudeNumber4;

public class Canvas
{
    private int height;
    private int width;
    private int startTowerCenterPos;
    private int tempTowerCenterPos;
    private int targetTowerCenterPos;

    /**
     * @param height
     * @param width
     * @param startTowerCenterPos
     * @param tempTowerCenterPos
     * @param targetTowerCenterPos
     * @implNote The canvas is just an object that holds positions; it doesn't actually print anything.
     */
    public Canvas(int height, int width, int startTowerCenterPos, int tempTowerCenterPos, int targetTowerCenterPos)
    {
        this.height = height;
        this.width = width;
        this.startTowerCenterPos = startTowerCenterPos;
        this.tempTowerCenterPos = tempTowerCenterPos;
        this.targetTowerCenterPos = targetTowerCenterPos;
    }

    public int getHeight()
    {
        return height;
    }
    public int getWidth()
    {
        return width;
    }
    public int getStartTowerCenterPos()
    {
        return startTowerCenterPos;
    }
    public int getTempTowerCenterPos()
    {
        return tempTowerCenterPos;
    }
    public int getTargetTowerCenterPos()
    {
        return targetTowerCenterPos;
    }
    public int ringRowNumFrom(int canvasRowNum)
    {
        return height - canvasRowNum;
    }
}
