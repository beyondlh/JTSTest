package com.hdsx.jtstest;

import com.hdsx.jtstest.measure.ClosestPoint;
import com.hdsx.jtstest.measure.DISByTwoPoints;
import com.vividsolutions.jts.geom.*;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;

public class App {
    public static void main(String[] args) throws Exception {
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        Point startPoint = geometryFactory.createPoint(new Coordinate(22.0, 117.9));
        Envelope envelope = new Envelope(22.22353667, 22.22442722, 113.25189254, 113.25580000);
        Polygon polygon = JTS.toGeometry(envelope);
        Coordinate end = ClosestPoint.findClosestPoint(polygon, startPoint)[0];
        DISByTwoPoints dis = new DISByTwoPoints();
        dis.getDis(startPoint.getCoordinate(), end, "EPSG:4326");
    }
}