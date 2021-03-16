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
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.portal.Portal;
import com.esri.arcgisruntime.security.AuthenticationManager;
import com.esri.arcgisruntime.security.DefaultAuthenticationChallengeHandler;
import com.esri.arcgisruntime.security.OAuthConfiguration;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.tasks.networkanalysis.*;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class App extends Application {

    private Point startPoint;
    private Point endPoint;
    private final int hexRed = 0xFFFF0000;
    private final int hexBlue = 0xFF0000FF;

    private GraphicsOverlay graphicsOverlay;
    private RouteTask solveRouteTask;
    private RouteParameters routeParameters;

    private MapView mapView;

    public static void main(String[] args) {

        Application.launch(args);
    }

    private void setupGraphicsOverlay() {
        if (mapView != null) {
            graphicsOverlay = new GraphicsOverlay();
            mapView.getGraphicsOverlays().add(graphicsOverlay);
        }
    }

    private void setupAuthentication() {
        String portalURL = "https://www.arcgis.com";
        String clientId = "iIciInXUPcxkdjWb";
        String redirectURI = "urn:ietf:wg:oauth:2.0:oob";

        try {
            OAuthConfiguration oAuthConfiguration = new OAuthConfiguration(portalURL, clientId, redirectURI);
            AuthenticationManager.setAuthenticationChallengeHandler(new DefaultAuthenticationChallengeHandler());
            AuthenticationManager.addOAuthConfiguration(oAuthConfiguration);
            final Portal portal = new Portal(portalURL, true);
            portal.addDoneLoadingListener(() -> {
                if (portal.getLoadStatus() == LoadStatus.LOADED) {
                    String routeServiceURI = "https://route.arcgis.com/arcgis/rest/services/World/Route/NAServer/Route_World";
                    setupRouteTask(routeServiceURI);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Portal: " + portal.getLoadError().getMessage()).show();
                }
            });
            portal.loadAsync();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void setupRouteTask(String routeServiceURI) {
        solveRouteTask = new RouteTask(routeServiceURI);
        solveRouteTask.loadAsync();
        solveRouteTask.addDoneLoadingListener(() -> {
            if (solveRouteTask.getLoadStatus() == LoadStatus.LOADED) {
                final ListenableFuture<RouteParameters> routeParamsFuture = solveRouteTask.createDefaultParametersAsync();
                routeParamsFuture.addDoneListener(() -> {

                    try {
                        routeParameters = routeParamsFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        new Alert(Alert.AlertType.ERROR, "Cannot create RouteTask parameters " + e.getMessage()).show();
                    }
                });
                createRouteAndDisplay();
            } else {
                new Alert(Alert.AlertType.ERROR, "Unable to load RouteTask " + solveRouteTask.getLoadStatus().toString()).show();
            }
        });

    }

    private void setMapMarker(Point location, SimpleMarkerSymbol.Style style, int markerColor, int outlineColor) {
        float markerSize = 8.0f;
        float markerOutlineThickness = 2.0f;
        SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(style, markerColor, markerSize);
        pointSymbol.setOutline(new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, outlineColor, markerOutlineThickness));
        Graphic pointGraphic = new Graphic(location, pointSymbol);
        graphicsOverlay.getGraphics().add(pointGraphic);
    }

    private void setStartMarker(Point location) {
        graphicsOverlay.getGraphics().clear();
        setMapMarker(location, SimpleMarkerSymbol.Style.DIAMOND, hexRed, hexBlue);
        startPoint = location;
        endPoint = null;
    }


    private void setEndMarker(Point location) {
        setMapMarker(location, SimpleMarkerSymbol.Style.SQUARE, hexBlue, hexRed);
        endPoint = location;
        solveForRoute();
    }

    private void createRouteAndDisplay() {
        mapView.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                Point2D point = new Point2D(e.getX(), e.getY());
                Point mapPoint = mapView.screenToLocation(point);

                if (startPoint == null) {
                    setStartMarker(mapPoint);
                } else if (endPoint == null) {
                    setEndMarker(mapPoint);
                } else {
                    setStartMarker(mapPoint);
                }
            }
        });
    }

    private void solveForRoute() {

        if (startPoint != null && endPoint != null) {
            routeParameters.setStops(Arrays.asList(new Stop(startPoint), new Stop(endPoint)));

            final ListenableFuture<RouteResult> routeResultFuture = solveRouteTask.solveRouteAsync(routeParameters);
            routeResultFuture.addDoneListener(() -> {
                try {
                    RouteResult routeResult = routeResultFuture.get();
                    if(routeResult.getRoutes().size() > 0) {
                        Route firstRoute = routeResult.getRoutes().get(0);

                        Polyline routePolyline = firstRoute.getRouteGeometry();
                        SimpleLineSymbol routeSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, hexBlue, 4.0f);
                        Graphic routeGraphic = new Graphic(routePolyline, routeSymbol);
                        graphicsOverlay.getGraphics().add(routeGraphic);
                    } else {
                        new Alert(Alert.AlertType.WARNING, "No routes have been found.").show();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    new Alert(Alert.AlertType.ERROR, "Solve RouteTask failed " + e.getMessage() + e.getMessage()).show();
                }
            });

        }
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
        setupAuthentication();
        createRouteAndDisplay();
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
