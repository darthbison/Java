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

import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.portal.Portal;
import com.esri.arcgisruntime.security.AuthenticationManager;
import com.esri.arcgisruntime.security.DefaultAuthenticationChallengeHandler;
import com.esri.arcgisruntime.security.OAuthConfiguration;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.net.MalformedURLException;

public class App extends Application {

    private MapView mapView;

    public static void main(String[] args) {

        Application.launch(args);
    }

    private void setupAuthentication() {
        String portalURL = "https://www.arcgis.com";
        String clientId = "YzJOSXPhUQWre58v";
        String redirectURI = "urn:ietf:wg:oauth:2.0:oob";
        try {
            OAuthConfiguration oAuthConfiguration = new OAuthConfiguration(portalURL, clientId, redirectURI);
            AuthenticationManager.setAuthenticationChallengeHandler(new DefaultAuthenticationChallengeHandler());
            AuthenticationManager.addOAuthConfiguration(oAuthConfiguration);
            final Portal portal = new Portal(portalURL, true);
            portal.addDoneLoadingListener(() -> {
                if (portal.getLoadStatus() == LoadStatus.LOADED) {
                    addTrafficLayer(portal);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Portal: " + portal.getLoadError().getMessage()).show();
                }
            });
            portal.loadAsync();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void addTrafficLayer(Portal portal) {
        String trafficURL = "https://traffic.arcgis.com/arcgis/rest/services/World/Traffic/MapServer";
        ArcGISMapImageLayer layer = new ArcGISMapImageLayer(trafficURL);
        layer.addDoneLoadingListener(() -> {
            if (portal.getLoadStatus() != LoadStatus.LOADED) {
                new Alert(Alert.AlertType.ERROR, "Layer: " + layer.getLoadError().getMessage()).show();
            }
        });
        mapView.getMap().getOperationalLayers().add(layer);
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
        setupAuthentication();
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
