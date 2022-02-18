package io.human0722.geotools.shp;

import org.geotools.data.DataStore;
import org.geotools.data.FeatureWriter;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.util.GeometryFixer;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

public class FixGeometry {

    public static void main(String[] args) throws Exception {
        File brokenShapefile = new File("/Users/rebot0722/Desktop/puhou/Shapefile/龙潭跨江大桥临时施工设施占地红线/龙潭跨江大桥临时施工设施占地红线.shp");
        SimpleFeatureSource simpleFeatureSource = ValidateGeometry.readShp(brokenShapefile);
        int i = ValidateGeometry.validateFeatureGeometry(simpleFeatureSource);
        if (i == 0) {
            System.out.println("矢量图合法性检查通过");
            System.exit(0);
        }
        File fixedFile = new File("src/main/resources/Fixed/target.shp");
        SimpleFeatureType sourceSchema = simpleFeatureSource.getSchema();
        SimpleFeatureCollection simpleFeatures = simpleFeatureSource.getFeatures();
        
        // Build File
        ShapefileDataStoreFactory factory = new ShapefileDataStoreFactory();
        HashMap<String, Serializable> create = new HashMap<>();
        create.put(factory.URLP.key, fixedFile.toURL());
        DataStore targetDataStore = factory.createDataStore(create);
        SimpleFeatureType retype = SimpleFeatureTypeBuilder.retype(sourceSchema, sourceSchema.getCoordinateReferenceSystem());
        targetDataStore.createSchema(retype);
        String createName = targetDataStore.getTypeNames()[0];

        FeatureWriter<SimpleFeatureType, SimpleFeature> writer = targetDataStore.getFeatureWriterAppend(createName, Transaction.AUTO_COMMIT);
        try (SimpleFeatureIterator iterator =  simpleFeatures.features()) {
            while (iterator.hasNext()) {
                SimpleFeature sourceFeature = iterator.next();
                SimpleFeature writerCursor = writer.next();
                writerCursor.setAttributes(sourceFeature.getAttributes());
                Geometry geom = (Geometry) sourceFeature.getDefaultGeometry();
                Geometry PC = null;
                if (geom !=null && !geom.isValid()) {
                    System.out.println("Fix Invalid Geometry:" + simpleFeatures.getID());
                    PC = GeometryFixer.fix(geom);
//                    GeometryFixer.
                }else {
                    PC = geom;
                }
                writerCursor.setDefaultGeometry(PC);
                writer.write();
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.close();
            targetDataStore.dispose();
        }
        System.out.println("Finish Fixed. New File Location:" + fixedFile.getAbsolutePath());
    }
}
