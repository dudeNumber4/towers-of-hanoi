package com.dudeNumber4;

public class Canvas
{
    private int height;
    private int width;
    private int startTowerPos;
    private int tempTowerPos;
    private int targetTowerPos;

    public Canvas(int height, int width, int startTowerPos, int tempTowerPos, int targetTowerPos)
    {
        this.height = height;
        this.width = width;
        this.startTowerPos = startTowerPos;
        this.tempTowerPos = tempTowerPos;
        this.targetTowerPos = targetTowerPos;
    }

    public int getHeight()
    {
        return height;
    }
    public int getWidth()
    {
        return width;
    }
    public int getStartTowerPos()
    {
        return startTowerPos;
    }
    public int getTempTowerPos()
    {
        return tempTowerPos;
    }
    public int getTargetTowerPos()
    {
        return targetTowerPos;
    }
    public int ringRowNumFrom(int canvasRowNum)
    {
        return height - canvasRowNum;
    }
}
