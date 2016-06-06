package com.hdsx.jtstest.measure;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.operation.distance.DistanceOp;

public class ClosestPoint {
    public static Coordinate[] findClosestPoint(Geometry polygon, Geometry point) {
        try {
            DistanceOp distOp = new DistanceOp(polygon, point);
            Coordinate[] closestPt = distOp.nearestPoints();
            return closestPt;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
