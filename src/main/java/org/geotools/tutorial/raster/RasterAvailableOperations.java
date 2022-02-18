package org.geotools.tutorial.raster;

import org.geotools.coverage.processing.CoverageProcessor;
import org.opengis.coverage.processing.Operation;

public class RasterAvailableOperations {
    public static void main(String[] args) {
        CoverageProcessor proc = new CoverageProcessor();
        for (Operation o : proc.getOperations()) {
            System.out.println("### " + o.getName());
            System.out.println("> " + o.getDescription());
        }
    }
}
