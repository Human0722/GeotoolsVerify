package io.human0722.geotools.shp;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import java.io.File;
import java.io.IOException;

public class DetectEncoding {
    public static void main(String[] args) throws IOException, FactoryException {
        File taihu = new File("src/main/resources/shapefiles/Taihu.shp");
        FileDataStore dataStore =  FileDataStoreFinder.getDataStore(taihu);
        SimpleFeatureSource featureSource =  dataStore.getFeatureSource();
        SimpleFeatureCollection features = featureSource.getFeatures();
        CoordinateReferenceSystem coordinateReferenceSystem = features.getSchema().getCoordinateReferenceSystem();
        System.out.println(CRS.lookupEpsgCode(coordinateReferenceSystem,false));

    }

}
