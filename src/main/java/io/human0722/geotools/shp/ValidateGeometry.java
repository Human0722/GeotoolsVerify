package io.human0722.geotools.shp;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.swing.action.SafeAction;
import org.locationtech.jts.geom.Geometry;
import org.opengis.feature.simple.SimpleFeature;

import java.io.File;
import java.nio.charset.Charset;

public class ValidateGeometry  {
    /**
     * 读取shapefile文件
     *
     * @param file
     * @return
     */
    public static SimpleFeatureSource readShp(File file){
        SimpleFeatureSource featureSource = null;

        ShapefileDataStore shpDataStore = null;
        try {
            shpDataStore = new ShapefileDataStore(file.toURL());

            //设置编码
            Charset charset = Charset.forName("GBK");
            shpDataStore.setCharset(charset);
            String tableName = shpDataStore.getTypeNames()[0];
            featureSource = shpDataStore.getFeatureSource(tableName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            shpDataStore.dispose();
        }
        return featureSource;

    }

    /**
     *
     * @param featureSource 特征源
     * @return
     * @throws Exception
     */
    public static int validateFeatureGeometry(SimpleFeatureSource featureSource) throws Exception {
        Integer numInvalidGeometries = 0;
        SimpleFeatureCollection featureCollection = featureSource.getFeatures();
        SimpleFeatureIterator cursor = featureCollection.features();
        while (cursor.hasNext()) {
            SimpleFeature feature = cursor.next();
            Geometry geom = (Geometry) feature.getDefaultGeometry();
            if (geom != null && !geom.isValid()) {
                System.out.println("invalid Dimension" + geom.getDimension());
                numInvalidGeometries++;
                System.out.println("Invalid Geometry:" + feature.getID());
            }
        }
        cursor.close();
        return numInvalidGeometries;
    }

    public static void main(String[] args) throws Exception {
        // broken shapefile
        File brokenShapefile = new File("/Users/rebot0722/Desktop/puhou/Shapefile/龙潭跨江大桥临时施工设施占地红线/龙潭跨江大桥临时施工设施占地红线.shp");
        // perfect shapefile
        File perfectShapeFile = new File("/Users/rebot0722/Desktop/puhou/Shapefile/省级重要湿地 上传 几何修正/江苏省省级重要湿地 几何修正.shp");
        // fixed File
        File fixedFile = new File("/Users/rebot0722/Documents/Code/Java/GeotoolsVerify/src/main/resources/Fixed/target.shp");

        System.out.println("broken detect count:" + validateFeatureGeometry(readShp(brokenShapefile)));
        System.out.println("perfect detect count:" + validateFeatureGeometry(readShp(perfectShapeFile)));
        System.out.println("fixed detect count:" + validateFeatureGeometry(readShp(fixedFile)));

    }
}
