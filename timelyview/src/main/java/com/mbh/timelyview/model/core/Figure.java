package com.mbh.timelyview.model.core;

/**
 * Model class for cubic bezier figure
 */
public abstract class Figure {
    private static final int NO_VALUE = -1;

    private int pointsCount = NO_VALUE;

    //A chained sequence of points P0,P1,P2,P3/0,P1,P2,P3/0,...
    private float[][] controlPoints = null;

    protected Figure(float[][] controlPoints) {
        this.controlPoints = controlPoints;
        this.pointsCount = (controlPoints.length + 2) / 3;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public float[][] getControlPoints() {
        return controlPoints;
    }
}
