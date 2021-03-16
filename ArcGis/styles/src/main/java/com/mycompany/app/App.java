/**
 * Copyright 2019 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.mycompany.app;

import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.symbology.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App extends Application {

    /* ** ADD ** */
    private final int hexRed = 0xFFFF0000;
    private final int hexBlue = 0xFF0000FF;
    private final int hexGreen = 0xFF00FF00;
    private final int hexPurple = 0xFF551A8B;
    private final int hexBlack = 0xFF000000;
    private final int hexOrange = 0xFFFFA500;
    /* ** ADD ** */

    private MapView mapView;

    private FeatureLayer addFeatureLayer(String uri) {

        ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable(uri);
        FeatureLayer featureLayer = new FeatureLayer(serviceFeatureTable);
        ArcGISMap map = mapView.getMap();
        map.getOperationalLayers().add(featureLayer);

        return featureLayer;
    }

    private void addTrailheadsLayer() {
        String url = "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Trailheads/FeatureServer/0";
        FeatureLayer featureLayer = addFeatureLayer(url);
        /* ** ADD ** */
        String pictureURI = "http://static.arcgis.com/images/Symbols/NPS/npsPictograph_0231b.png";
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(pictureURI);
        SimpleRenderer simpleRenderer = new SimpleRenderer(pictureMarkerSymbol);
        featureLayer.setRenderer(simpleRenderer);
    }

    private void addTrailsLayer() {

        String url = "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Trails/FeatureServer/0";
        FeatureLayer featureLayer = addFeatureLayer(url);

        /* ** ADD ** */
        UniqueValueRenderer trailsUniqueValueRenderer = new UniqueValueRenderer();
        trailsUniqueValueRenderer.getFieldNames().add("USE_BIKE");
        featureLayer.setRenderer(trailsUniqueValueRenderer);

        /* ** ADD ** */
        SimpleLineSymbol bikeTrailSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.DOT, hexBlue, 2.0f);
        SimpleLineSymbol noBikeTrailSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.DOT, hexRed, 2.0f);
        UniqueValueRenderer.UniqueValue trailsForBike = new UniqueValueRenderer.UniqueValue("Bike trails", "Bike", bikeTrailSymbol, Arrays.asList("Yes"));
        UniqueValueRenderer.UniqueValue trailsNoBike = new UniqueValueRenderer.UniqueValue("No bike trails", "No Bike", noBikeTrailSymbol, Arrays.asList("No"));
        trailsUniqueValueRenderer.getUniqueValues().add(trailsForBike);
        trailsUniqueValueRenderer.getUniqueValues().add(trailsNoBike);
    }

    private void addOpenSpaceLayer() {

        String openSpacesFeaturesUrl =
                "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Parks_and_Open_Space/FeatureServer/0";
        FeatureLayer featureLayer = addFeatureLayer(openSpacesFeaturesUrl);
        /* ** ADD ** */
        SimpleLineSymbol fillOutlineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, hexBlack, 0.5f);
        SimpleFillSymbol greenClassSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, hexGreen, fillOutlineSymbol);
        SimpleFillSymbol purpleClassSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, hexPurple, fillOutlineSymbol);
        SimpleFillSymbol orangeClassSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, hexOrange, fillOutlineSymbol);

        /* ** ADD ** */
        ClassBreaksRenderer.ClassBreak greenClassBreak = new ClassBreaksRenderer.ClassBreak("Under 1,629", "0 - 1629", 0.0, 1629.0, greenClassSymbol);
        ClassBreaksRenderer.ClassBreak purpleClassBreak = new ClassBreaksRenderer.ClassBreak("1,629 to 3,754", "1629 - 3754", 1629.0, 3754.0, purpleClassSymbol);
        ClassBreaksRenderer.ClassBreak orangeClassBreak = new ClassBreaksRenderer.ClassBreak("3,754 to 11,438", "3754 - 11438", 3754.0, 11438.0, orangeClassSymbol);

        List<ClassBreaksRenderer.ClassBreak> acreageBreaks = new ArrayList<>();
        acreageBreaks.add(greenClassBreak);
        acreageBreaks.add(purpleClassBreak);
        acreageBreaks.add(orangeClassBreak);

        /* ** ADD ** */
        ClassBreaksRenderer openSpacesClassBreaksRenderer = new ClassBreaksRenderer("GIS_ACRES", acreageBreaks);
        featureLayer.setRenderer(openSpacesClassBreaksRenderer);
    }


    public static void main(String[] args) {

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        // set the title and size of the stage and show it
        stage.setTitle("My Map App");
        stage.setWidth(800);
        stage.setHeight(700);
        stage.show();

        // create a JavaFX scene with a stack pane as the root node and add it to the scene
        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane);
        stage.setScene(scene);

        // create a MapView to display the map and add it to the stack pane
        mapView = new MapView();
        stackPane.getChildren().add(mapView);

        // create an ArcGISMap with the default imagery basemap
        ArcGISMap map = new ArcGISMap(Basemap.createImagery());

        // display the map by setting the map on the map view
        mapView.setMap(map);

        /* ** ADD ** */
        addOpenSpaceLayer();

        /* ** ADD ** */
        addTrailsLayer();
        /* ** ADD ** */
        addTrailheadsLayer();
    }

    /**
     * Stops and releases all resources used in application.
     */
    @Override
    public void stop() {

        if (mapView != null) {
            mapView.dispose();
        }
    }
}
