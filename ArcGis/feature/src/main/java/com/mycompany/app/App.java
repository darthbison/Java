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

import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureCollection;
import com.esri.arcgisruntime.data.FeatureCollectionTable;
import com.esri.arcgisruntime.data.Field;
import com.esri.arcgisruntime.geometry.GeometryType;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.FeatureCollectionLayer;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleRenderer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App extends Application {

    private MapView mapView;

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

        // *** ADD ***
        FeatureCollection featureCollection = new FeatureCollection();
        FeatureCollectionLayer featureCollectionLayer = new FeatureCollectionLayer(featureCollection);
        map.getOperationalLayers().add(featureCollectionLayer);
        createPointTable(featureCollection);
    }

    private void createPointTable(FeatureCollection featureCollection) {
        List<Feature> features = new ArrayList<>();
        List<Field> pointFields = new ArrayList<>();
        pointFields.add(Field.createString("Place", "Place Name", 50));
        FeatureCollectionTable pointsTable = new FeatureCollectionTable(pointFields, GeometryType.POINT, SpatialReferences.getWgs84());
        SimpleMarkerSymbol simpleMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.TRIANGLE, 0xFF0000FF, 18);
        SimpleRenderer renderer = new SimpleRenderer(simpleMarkerSymbol);
        pointsTable.setRenderer(renderer);
        featureCollection.getTables().add(pointsTable);
        // Dodger Stadium
        Map<String, Object> attributes1 = new HashMap<>();
        attributes1.put(pointFields.get(0).getName(), "Dodger Stadium");
        Point point1 = new Point(-118.2406294, 34.0736221, SpatialReferences.getWgs84());
        features.add(pointsTable.createFeature(attributes1, point1));

        // Los Angeles Memorial Coliseum
        Map<String, Object> attributes2 = new HashMap<>();
        attributes2.put(pointFields.get(0).getName(), "LA Coliseum");
        Point point2 = new Point(-118.287767, 34.013999, SpatialReferences.getWgs84());
        features.add(pointsTable.createFeature(attributes2, point2));

        // Staples Center
        Map<String, Object> attributes3 = new HashMap<>();
        attributes3.put(pointFields.get(0).getName(), "Staples Center");
        Point point3 = new Point(-118.267028, 34.043145, SpatialReferences.getWgs84());
        features.add(pointsTable.createFeature(attributes3, point3));
        pointsTable.addFeaturesAsync(features);







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
