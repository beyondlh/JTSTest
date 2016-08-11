package com.hdsx.jtstest.edit.addField;

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
 * Created by minim on 2016/7/23.
 */
public class SetFieldValue {

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

        SimpleFeatureStore store = (SimpleFeatureStore) source;
        Transaction transaction = new DefaultTransaction("edit");
        store.setTransaction(transaction);

        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter);
        FeatureIterator<SimpleFeature> features = collection.features();
        String newLMXL = "无路面";
        int i = 0;
        while (features.hasNext()) {
            i = i + 1;
            SimpleFeature feature = features.next();
            String lmlx = feature.getAttribute("LMLX").toString();
            switch (lmlx) {
                case "水泥混凝土":
                    newLMXL = "水泥混凝土";
                    break;
                case "沥青混凝土":
                    newLMXL = "沥青混凝土";
                    break;
                case "沥青贯入式":
                    newLMXL = "简易铺装路面";
                    break;
                case "沥青碎石":
                    newLMXL = "简易铺装路面";
                    break;
                case "沥青表面处治":
                    newLMXL = "简易铺装路面";
                    break;
                case "砖铺路面":
                    newLMXL = "未铺装路面";
                    break;
                case "砂石路面":
                    newLMXL = "未铺装路面";
                    break;
                case "石质路面":
                    newLMXL = "未铺装路面";
                    break;
                case "渣石路面":
                    newLMXL = "未铺装路面";
                    break;
                case "砼预制块":
                    newLMXL = "未铺装路面";
                    break;
            }
            feature.setAttribute("NewLMLX", newLMXL);
        }
        try {
            System.out.println(i);
            // perform edits here!
            transaction.commit();
        } catch (Exception eek) {
            System.out.println("回滚修改");
            transaction.rollback();
        } finally {
            transaction.close();
        }
    }

    public static void main(String[] args) {
        try {
            String filePath = "E:/工作文档/江西项目/江西公路局项目/LMLX.shp";
            SetFieldValue getMValue = new SetFieldValue(filePath);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
        }

    }
}

