package com.dudeNumber4;

import java.util.List;

public class CanvasTower
{
    private List<Integer> tower;
    private TowerType towerType;

    /**
     * @param tower
     * @param towerType
     * @implNote Represents a single "tower."  Three towers (from Towers class) show during every move: src, temp, target
     *           A tower is just a list of int, each int representing the number that represents how large that ring is.
     *           1 = smallest ring; 8 = largest ring
     */
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
