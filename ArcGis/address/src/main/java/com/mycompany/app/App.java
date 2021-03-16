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

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;
import com.esri.arcgisruntime.tasks.geocode.GeocodeParameters;
import com.esri.arcgisruntime.tasks.geocode.GeocodeResult;
import com.esri.arcgisruntime.tasks.geocode.LocatorTask;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class App extends Application {


    private TextField searchBox;

    private GeocodeParameters geocodeParameters;
    private LocatorTask locatorTask;
    private GraphicsOverlay graphicsOverlay;

    private MapView mapView;

    private void setupGraphicsOverlay() {
        if (mapView != null) {
            graphicsOverlay = new GraphicsOverlay();
            mapView.getGraphicsOverlays().add(graphicsOverlay);
        }
    }

    private void setupTextField() {
        searchBox = new TextField();
        searchBox.setMaxWidth(400);
        searchBox.setPromptText("Search for an address");

        searchBox.setOnAction(event -> {
            String query = searchBox.getText();
            if (!"".equals(query)) {
                geocodeQuery(query);
            }
        });

    }

    private void createLocatorTaskWithParameters() {
        locatorTask = new LocatorTask("http://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer");

        geocodeParameters = new GeocodeParameters();
        geocodeParameters.getResultAttributeNames().add("*"); // return all attributes
        geocodeParameters.setMaxResults(1); // get closest match
        geocodeParameters.setOutputSpatialReference(mapView.getSpatialReference());
    }

    private void geocodeQuery(String query) {
        ListenableFuture<List<GeocodeResult>> geocode = locatorTask.geocodeAsync(query, geocodeParameters);
        geocode.addDoneListener(() -> {
            try {
                List<GeocodeResult> results = geocode.get();
                if (results.size() > 0) {
                    GeocodeResult result = results.get(0);
                    displayResult(result);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "No results found.");
                    alert.show();
                }
            } catch (InterruptedException | ExecutionException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error getting result.");
                alert.show();
            }
        });

    }

    private void displayResult(GeocodeResult geocodeResult) {
        graphicsOverlay.getGraphics().clear();

        String label = geocodeResult.getLabel();
        TextSymbol textSymbol = new TextSymbol(18, label, 0xFF000000, TextSymbol.HorizontalAlignment.CENTER, TextSymbol.VerticalAlignment.BOTTOM);
        Graphic textGraphic = new Graphic(geocodeResult.getDisplayLocation(), textSymbol);
        SimpleMarkerSymbol markerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.SQUARE, 0xFFFF0000, 12.0f);
        Graphic markerGraphic = new Graphic(geocodeResult.getDisplayLocation(), geocodeResult.getAttributes(), markerSymbol);
        graphicsOverlay.getGraphics().addAll(Arrays.asList(markerGraphic, textGraphic));

        mapView.setViewpointCenterAsync(geocodeResult.getDisplayLocation());
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
        setupGraphicsOverlay();
        setupTextField();
        stackPane.getChildren().add(searchBox);
        StackPane.setAlignment(searchBox, Pos.TOP_LEFT);
        StackPane.setMargin(searchBox, new Insets(10, 0, 0, 10));

        createLocatorTaskWithParameters();

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
