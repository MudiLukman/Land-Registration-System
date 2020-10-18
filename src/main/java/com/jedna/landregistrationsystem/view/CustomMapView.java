package com.jedna.landregistrationsystem.view;

import com.jedna.landregistrationsystem.model.Location;
import com.teamdev.jxmaps.*;
import com.teamdev.jxmaps.javafx.MapView;

import java.util.List;

public class CustomMapView extends MapView {

    private Map map;

    public CustomMapView(List<Location> locations){
        setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus mapStatus) {
                if(mapStatus == MapStatus.MAP_STATUS_OK){
                    map = getMap();
                    MapOptions mapOptions = new MapOptions();
                    MapTypeControlOptions controlOptions = new MapTypeControlOptions();
                    mapOptions.setMapTypeControlOptions(controlOptions);
                    map.setOptions(mapOptions);
                    map.setCenter(new LatLng(9.5300312, 6.4663736));
                    map.setZoom(11.0);

                    for(Location location : locations){
                        Marker marker = new Marker(map);
                        marker.setTitle(location.getLocationId());
                        marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                    }

                }
            }
        });
    }

}
