package com.hdsx.jtstest.utils;

import org.geotools.data.*;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by minim on 2016/8/22.
 */
public class ReadData {
    public static FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection;
    public static FeatureSource<SimpleFeatureType, SimpleFeature> featureSource;

    public static FeatureSource<SimpleFeatureType, SimpleFeature> getFeatureSource(String filePath) throws Exception {
        if (featureSource == null) {
            File file = new File(filePath);
            Map map = new HashMap();
            map.put("charset", "GBK");
            map.put("url", file.toURI().toURL());
            DataStore dataStore = DataStoreFinder.getDataStore(map);
            String typeName = dataStore.getTypeNames()[0];
            FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore
                    .getFeatureSource(typeName);
            Filter filter = Filter.INCLUDE;
            if (source instanceof SimpleFeatureStore) {
                SimpleFeatureStore store = (SimpleFeatureStore) source;
                featureCollection = source.getFeatures(filter);
                FeatureIterator<SimpleFeature> features = featureCollection.features();
                while (features.hasNext()) {
                    SimpleFeature feature = features.next();
                }
            } else {
                System.out.println("没有写入数据权限");
            }
        }
        return featureSource;
    }
}
