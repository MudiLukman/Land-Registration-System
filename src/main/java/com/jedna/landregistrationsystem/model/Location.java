package com.jedna.landregistrationsystem.model;

public class Location {

    private String locationId;
    private Double latitude = 0.0;
    private Double longitude = 0.0;

    public Location(String locationId, Double latitude, Double longitude){
        this.locationId = locationId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "lat: " + latitude +
                ", long: " + longitude;
    }
}
