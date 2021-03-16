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
import com.esri.arcgisruntime.mapping.ArcGISScene;
import com.esri.arcgisruntime.mapping.ArcGISTiledElevationSource;
import com.esri.arcgisruntime.mapping.view.Camera;
import com.esri.arcgisruntime.mapping.view.SceneView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;

public class App extends Application {

    //private MapView mapView;
    private SceneView sceneView = new SceneView();




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
        //mapView = new MapView();
        stackPane.getChildren().add(sceneView);

        // create an ArcGISMap with the default imagery basemap
        ArcGISMap map = new ArcGISMap(Basemap.createImagery());

        // display the map by setting the map on the map view
        //mapView.setMap(map);
        setupScene();
        addTrailheadsLayer();
        addElevationData();
    }

    private void setupScene() {
        if (sceneView != null) {
            ArcGISScene scene = new ArcGISScene(Basemap.createStreets());
            sceneView.setArcGISScene(scene);
            /* ** ADD ** */
            Camera camera = new Camera(
                    33.950896,
                    -118.525341,
                    16000.0,
                    0.0,
                    50.0,
                    0.0);
            sceneView.setViewpointCamera(camera);

        }
    }

    private void addElevationData() {
        ArcGISTiledElevationSource elevationSource = new ArcGISTiledElevationSource(
                "http://elevation3d.arcgis.com/arcgis/rest/services/WorldElevation3D/Terrain3D/ImageServer");
        sceneView.getArcGISScene().getBaseSurface().getElevationSources().add(elevationSource);
    }
    private void addTrailheadsLayer() {
        if (sceneView != null) {
            String url = "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Trails/FeatureServer/0";
            ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable(url);
            FeatureLayer featureLayer = new FeatureLayer(serviceFeatureTable);
            sceneView.getArcGISScene().getOperationalLayers().add(featureLayer);
        }
    }

    /**
     * Stops and releases all resources used in application.
     */
    @Override
    public void stop() {

//        if (mapView != null) {
//            mapView.dispose();
//        }

        if (sceneView != null) {
            sceneView.dispose();
        }
    }
}
