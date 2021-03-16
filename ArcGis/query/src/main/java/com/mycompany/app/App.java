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
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleRenderer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class App extends Application {

    /* ** ADD ** */
    private final int hexRed = 0xFFFF0000;

    private GraphicsOverlay graphicsOverlay;
    /* ** ADD ** */
    private MapView mapView;

    public static void main(String[] args) {

        Application.launch(args);
    }

    private void setupGraphicsOverlay() {
        if (mapView != null) {
            graphicsOverlay = new GraphicsOverlay();
            mapView.getGraphicsOverlays().add(graphicsOverlay);
            /* ** ADD * */
            SimpleLineSymbol symbol = new SimpleLineSymbol(SimpleLineSymbol.Style.DASH_DOT, hexRed, 2.0f);
            graphicsOverlay.setRenderer(new SimpleRenderer(symbol));
        }
    }

    private void queryFeaturesFromTable() {
        ServiceFeatureTable table = new ServiceFeatureTable(
                "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Trails/FeatureServer/0");
        table.loadAsync();

        /* ** ADD ** */
        table.addDoneLoadingListener(() -> {
            QueryParameters query = new QueryParameters();
            query.setWhereClause("TRL_NAME like '%backbone%'");
            query.setReturnGeometry(true);
            ListenableFuture<FeatureQueryResult> tableQueryResult = table.queryFeaturesAsync(query);
            /* ** ADD ** */
            tableQueryResult.addDoneListener(() -> {
                try {
                    List<Graphic> graphics = new ArrayList<>();
                    FeatureQueryResult result = tableQueryResult.get();
                    result.forEach(feature -> {
                        graphics.add(new Graphic(feature.getGeometry()));
                    });
                    graphicsOverlay.getGraphics().addAll(graphics);
                } catch (ExecutionException | InterruptedException e) {
                    new Alert(Alert.AlertType.ERROR, "Error querying Service Table:  " + e.getMessage()).show();
                }
            });
        });
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
        setupGraphicsOverlay();

        /* ** ADD ** */
        queryFeaturesFromTable();

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
