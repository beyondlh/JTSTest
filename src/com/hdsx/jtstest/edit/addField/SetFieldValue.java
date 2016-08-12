package com.hdsx.jtstest.edit.addField;

import org.geotools.data.*;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.factory.GeoTools;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by minim on 2016/7/23.
 */
public class SetFieldValue {

    private static FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2(GeoTools.getDefaultHints());


    public SetFieldValue(String filePath) throws Exception {
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
            Transaction transaction = new DefaultTransaction("edit");
            store.setTransaction(transaction);
            FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter);
            FeatureIterator<SimpleFeature> features = collection.features();
            String newLMXL = "测试";
            int i = 0;
            while (features.hasNext()) {
                i = i + 1;
                SimpleFeature feature = features.next();
                String lmlx = feature.getAttribute("LMLX").toString();
                feature.setAttribute("NewLMLX", newLMXL);
            }
            try {
                System.out.println(i);
                transaction.commit();
            } catch (Exception eek) {
                System.out.println("回滚修改");
                transaction.rollback();
            } finally {
                transaction.close();
                System.out.println("执行结束");
            }
        } else {
            System.out.println("没有写入数据权限");
        }
    }


    public static void SetFieldValue1(String filePath) throws Exception {
        File file = new File(filePath);
        Map map = new HashMap();
        map.put("charset", "GBK");
        map.put("url", file.toURI().toURL());
        DataStore dataStore = DataStoreFinder.getDataStore(map);
        String typeName = dataStore.getTypeNames()[0];
        FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore
                .getFeatureSource(typeName);
        Filter filter = Filter.INCLUDE;
        Filter filter1 = ff.equals(ff.property("LMLX"), ff.literal("水泥混凝土"));
        if (source instanceof SimpleFeatureStore) {
            SimpleFeatureStore store = (SimpleFeatureStore) source;
            Transaction transaction = new DefaultTransaction("edit45");
            store.setTransaction(transaction);

/*
            FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter1);
            FeatureIterator<SimpleFeature> features = collection.features();*/
//            System.out.println("size:"+collection.size());
            String newLMXL = "无路面";
            store.modifyFeatures("NewLMLX", "简易铺装路面", filter1);
            try {
                transaction.commit();
            } catch (Exception eek) {
                System.out.println("回滚修改");
                transaction.rollback();
            } finally {
                transaction.close();
            }
            System.out.println("执行结束");
        } else {
            System.out.println("没有写入数据权限");
        }
    }

    public static void main(String[] args) {
        try {
            String filePath = "D:/testData/Export_Output.shp";
//            SetFieldValue getMValue = new SetFieldValue(filePath);
            SetFieldValue1(filePath);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}

