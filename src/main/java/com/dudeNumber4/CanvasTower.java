package com.dudeNumber4;

import java.util.List;

public class CanvasTower
{
    private List<Integer> tower;
    private TowerType towerType;

    public CanvasTower(List<Integer> tower, TowerType towerType)
    {
        this.tower = tower;
        this.towerType = towerType;
    }

    public List<Integer> getTower()
    {
        return tower;
    }
    public TowerType getTowerType()
    {
        return towerType;
    }
}
