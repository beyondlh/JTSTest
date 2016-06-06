package com.hdsx.jtstest.measure;

import com.vividsolutions.jts.geom.Coordinate;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * Created by Administrator on 2016.06.06 0006.
 */
public class DISByTwoPoints {
    public void getDis(Coordinate start, Coordinate end, String crsCode) throws Exception {
        CoordinateReferenceSystem crs = null;
        try {
            crs = CRS.decode(crsCode);
        } catch (NoSuchAuthorityCodeException e) {
            e.printStackTrace();
        } catch (FactoryException e) {
            e.printStackTrace();
        }

        double distance = JTS.orthodromicDistance(start, end, crs);
        int totalmeters = (int) distance;
        int km = totalmeters / 1000;
        int meters = totalmeters - (km * 1000);
        float remaining_cm = (float) (distance - totalmeters) * 10000;
        remaining_cm = Math.round(remaining_cm);
        float cm = remaining_cm / 100;

        System.out.println("距离 = " + km + "km " + meters + "m " + cm + "cm");
    }
}
