package com.jedna.landregistrationsystem.presenter;

import com.jedna.landregistrationsystem.model.Location;
import com.jedna.landregistrationsystem.view.CustomMapView;

import java.util.List;

public class CustomMapPresenter {

    private static CustomMapView mapView;

    public CustomMapPresenter(List<Location> locations){
        mapView = new CustomMapView(locations);
    }

    public CustomMapView getMapView(){
        return mapView;
    }

}
