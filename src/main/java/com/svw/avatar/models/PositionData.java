package com.svw.avatar.models;


public class PositionData {
    public float startX;
    public float startY;
    public float endX;
    public float endY;

    @Override
    public String toString() {
        return startX + "," + startY + "," + endX + "," + endY;
    }
}
