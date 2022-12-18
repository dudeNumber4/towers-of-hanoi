package com.dudeNumber4;

import lombok.Getter;
import lombok.Setter;

public class Canvas
{
    
    @Getter @Setter private int height;
    @Getter @Setter private int width;
    @Getter @Setter private int startTowerCenterPos;
    @Getter @Setter private int tempTowerCenterPos;
    @Getter @Setter private int targetTowerCenterPos;

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

}
