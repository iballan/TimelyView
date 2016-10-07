package com.mbh.timelyview.model;


import com.mbh.timelyview.model.number.Eight;
import com.mbh.timelyview.model.number.Five;
import com.mbh.timelyview.model.number.Four;
import com.mbh.timelyview.model.number.Nine;
import com.mbh.timelyview.model.number.Null;
import com.mbh.timelyview.model.number.One;
import com.mbh.timelyview.model.number.Seven;
import com.mbh.timelyview.model.number.Six;
import com.mbh.timelyview.model.number.Three;
import com.mbh.timelyview.model.number.Two;
import com.mbh.timelyview.model.number.Zero;

import java.security.InvalidParameterException;

public class NumberUtils {

    public static float[][] getControlPointsFor(int start) {
        switch (start) {
            case (-1):
                return Null.getInstance().getControlPoints();
            case 0:
                return Zero.getInstance().getControlPoints();
            case 1:
                return One.getInstance().getControlPoints();
            case 2:
                return Two.getInstance().getControlPoints();
            case 3:
                return Three.getInstance().getControlPoints();
            case 4:
                return Four.getInstance().getControlPoints();
            case 5:
                return Five.getInstance().getControlPoints();
            case 6:
                return Six.getInstance().getControlPoints();
            case 7:
                return Seven.getInstance().getControlPoints();
            case 8:
                return Eight.getInstance().getControlPoints();
            case 9:
                return Nine.getInstance().getControlPoints();
            default:
                throw new InvalidParameterException("Unsupported number requested. Must be between -1,9");
        }
    }
}
