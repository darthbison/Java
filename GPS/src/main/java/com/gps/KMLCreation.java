package com.gps;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;

public class KMLCreation {

    public void generateKML(String contents)
    {
        
        String[] entries = contents.trim().split("\n");

        final Kml kml = new Kml();
        Document doc = kml.createAndSetDocument();
      
        for (String e : entries) {
            String[] coordinates = e.split(",");
            doc.createAndAddPlacemark().withName(coordinates[2])
                    .withOpen(Boolean.FALSE).createAndSetPoint()
                    .addToCoordinates(Double.parseDouble(coordinates[0]),
                            Double.parseDouble(coordinates[1]));
          
        }

        try {
            kml.marshal(new File("MJPlaces.kml"));
        } catch (FileNotFoundException e) {
            Logger.getRootLogger().error(e);
        }
    }
}
