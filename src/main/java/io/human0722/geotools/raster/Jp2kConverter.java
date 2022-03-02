package io.human0722.geotools.raster;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridEnvelope2D;
import org.geotools.coverage.grid.GridGeometry2D;
import org.geotools.coverageio.jp2k.JP2KFormat;
import org.geotools.coverageio.jp2k.JP2KReader;
import org.geotools.geometry.GeneralEnvelope;
import org.opengis.coverage.grid.GridCoverage;
import org.opengis.parameter.GeneralParameterValue;
import org.opengis.parameter.ParameterValue;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Jp2kConverter {
    public static void main(String[] args) throws IOException {
        File file = new File("C:/Users/puhou/Documents/B09.jp2");
        JP2KReader reader = new JP2KReader(file);

        // Setting GridGeometry for reading half coverage
        Rectangle range = ((GridEnvelope2D) reader.getOriginalGridRange()).getBounds();
        GeneralEnvelope originalEnvelope = reader.getOriginalEnvelope();
        final GeneralEnvelope reducedEnvelope = new GeneralEnvelope(
                new double[] {
                        originalEnvelope.getLowerCorner().getOrdinate(0),
                        originalEnvelope.getLowerCorner().getOrdinate(1)},
                new double[] {
                        originalEnvelope.getMedian().getOrdinate(0),
                        originalEnvelope.getMedian().getOrdinate(1)});
        reducedEnvelope.setCoordinateReferenceSystem(reader.getCoordinateReferenceSystem());

        final ParameterValue<GridGeometry2D> gg = JP2KFormat.READ_GRIDGEOMETRY2D.createValue();
        gg.setValue(new GridGeometry2D(new GridEnvelope2D(new Rectangle(
                0,
                0,
                (int) (range.width / 2.0),
                (int) (range.height / 2.0))), reducedEnvelope)
        );

        // /////////////////////////////////////////////////////////////////////
        //
        // Read with subsampling and crop, using Jai and customized tilesize
        //
        // /////////////////////////////////////////////////////////////////////

        // //
        //
        // Customizing Tile Size
        //
        // //
        final ParameterValue<String> tilesize = JP2KFormat.SUGGESTED_TILE_SIZE.createValue();
        tilesize.setValue("512,512");

        // //
        //
        // Setting read type: use JAI ImageRead
        //
        // //
        final ParameterValue<Boolean> useJaiRead = JP2KFormat.USE_JAI_IMAGEREAD.createValue();
        useJaiRead.setValue(true);

        final GridCoverage gc = (GridCoverage2D) reader.read(new GeneralParameterValue[] { gg,tilesize, useJaiRead });
        System.out.printf("Over");

    }
}
