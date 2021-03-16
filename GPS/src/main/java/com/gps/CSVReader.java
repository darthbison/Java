package com.gps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;

public class CSVReader {

    private CSVReader() {}
    
    public static void main(String[] args) {
        String filename = "coordinates.csv";
        StringBuilder contents = new StringBuilder();

        BufferedReader br = null;
        FileInputStream fin = null;

        try {

            fin = new FileInputStream(filename);
            br = new BufferedReader(new InputStreamReader(fin, "UTF-8"));
            String s;
            while ((s = br.readLine()) != null) {
                contents.append(s);
                contents.append("\n");
            }
           

        } catch (IOException ioe) {
            Logger.getRootLogger().debug(ioe);
        } finally {

            try {
                if (br != null)
                   br.close();
                if (fin != null)
                   fin.close();
            } catch (IOException e) {
                Logger.getRootLogger().debug(e);
            }
        }

        String fileContents = contents.toString();
        String[] entries = fileContents.trim().split("\n");

        final Kml kml = new Kml();
        Document doc = kml.createAndSetDocument();
       
        for (String e : entries) {
            String[] coordinates = e.split(",");
            String longitude = coordinates[0].replaceAll("\"", "");
            String latitude = coordinates[1].replaceAll("\"", "");
            
            double lat = 0;
            double lon = 0;
            
            try {
                lon = Double.parseDouble(longitude);
            }
            catch (Exception e1)
            {
                longitude = longitude.replaceAll("-","");
                lon = Double.parseDouble(longitude) * -1;
                Logger.getRootLogger().debug(e1);
            }
            
            try {
                lat = Double.parseDouble(latitude);
            }
            catch (Exception e1)
            {
                latitude = longitude.replaceAll("-","");
                lat = Double.parseDouble(latitude) * -1;
                Logger.getRootLogger().debug(e1);
            }
            
            
            doc.createAndAddPlacemark().withName(coordinates[2])
                    .withOpen(Boolean.FALSE).createAndSetPoint()
                    .addToCoordinates(lon,lat);
                            
            
        }

        try {
            kml.marshal(new File("AllPlaces.kml"));
        } catch (FileNotFoundException e) {
            Logger.getRootLogger().debug(e);
        }

    }

}
