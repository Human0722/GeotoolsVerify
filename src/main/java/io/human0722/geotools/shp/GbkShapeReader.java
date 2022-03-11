package io.human0722.geotools.shp;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeType;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class GbkShapeReader {
    public static void main(String[] args) throws IOException {
        String filePath = "/Users/rebot0722/Desktop/puhou/Shapefile/省级重要湿地 上传 几何修正/江苏省省级重要湿地 几何修正.shp";
        File shapeFile = new File(filePath);

        FileDataStore dataStore = FileDataStoreFinder.getDataStore(shapeFile);
        ((ShapefileDataStore) dataStore).setCharset(Charset.forName("GBK"));
        SimpleFeatureSource fs = dataStore.getFeatureSource();
        SimpleFeatureIterator featureIterator = fs.getFeatures().features();
        while (featureIterator.hasNext()) {
            SimpleFeature feature = featureIterator.next();
            SimpleFeatureType featureType = feature.getFeatureType();
            List<AttributeType> types = featureType.getTypes();
            for (AttributeType type : types) {
                System.out.println(type.getName().toString());
            }
            break;
        }

    }
}
