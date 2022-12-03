package com.dudeNumber4;

// Unchecked: no recovery
public class IllegalMove extends Error
{
    public IllegalMove(String message) 
    {
        super(message);
    }
}