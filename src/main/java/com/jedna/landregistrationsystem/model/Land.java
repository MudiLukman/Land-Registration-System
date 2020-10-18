package com.jedna.landregistrationsystem.model;

import com.jedna.landregistrationsystem.util.enums.Activities;
import com.jedna.landregistrationsystem.util.enums.Mediums;
import com.jedna.landregistrationsystem.util.enums.Stages;
import com.jedna.landregistrationsystem.util.enums.Status;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Land {

    private StringProperty landId = new SimpleStringProperty(this, "landId", null);
    private ObjectProperty<LandOwner> landOwner = new SimpleObjectProperty<>(this, "landOwner", null);
    private ObjectProperty<Location> location = new SimpleObjectProperty<>(this, "location", null);
    private ObjectProperty<TitleDocs> titleDocs = new SimpleObjectProperty<>(this, "titleDocs", null);
    private ObjectProperty<Set<Issue>> issues = new SimpleObjectProperty<Set<Issue>>(this, "issues", null);
    private StringProperty dimension = new SimpleStringProperty(this, "dimension", null);
    private ObjectProperty<Enum<Mediums>> medium = new SimpleObjectProperty<>(this, "medium", null);
    private ObjectProperty<Enum<Activities>> activity = new SimpleObjectProperty<>(this, "activity", null);
    private ObjectProperty<LocalDate> approvalDate = new SimpleObjectProperty<>(this, "approvalDate", null);
    private ObjectProperty<Enum<Stages>> stage = new SimpleObjectProperty<>(this, "stage", null);
    private ObjectProperty<Enum<Status>> status = new SimpleObjectProperty<>(this, "status", null);

    public Land(String landId, LandOwner landOwner, Location location, TitleDocs titleDocs, Set<Issue> issues, String dimension,
                Mediums medium, Activities activity, LocalDate approvalDate, Stages stage, Status status){

        this.landId.setValue(landId);
        this.landOwner.setValue(landOwner);
        this.location.setValue(location);
        this.titleDocs.setValue(titleDocs);
        this.issues.setValue(issues);
        this.dimension.setValue(dimension);
        this.medium.setValue(medium);
        this.activity.setValue(activity);
        this.approvalDate.setValue(approvalDate);
        this.stage.setValue(stage);
        this.status.setValue(status);

    }

    public String getLandId() {
        return landId.get();
    }

    public StringProperty landIdProperty() {
        return landId;
    }

    public void setLandId(String landId) {
        this.landId.set(landId);
    }

    public LandOwner getLandOwner() {
        return landOwner.get();
    }

    public ObjectProperty<LandOwner> landOwnerProperty() {
        return landOwner;
    }

    public void setLandOwner(LandOwner landOwner) {
        this.landOwner.set(landOwner);
    }

    public Location getLocation() {
        return location.get();
    }

    public ObjectProperty<Location> locationProperty() {
        return location;
    }

    public void setLocation(Location location) {
        this.location.set(location);
    }

    public TitleDocs getTitleDocs() {
        return titleDocs.get();
    }

    public ObjectProperty<TitleDocs> titleDocsProperty() {
        return titleDocs;
    }

    public void setTitleDocs(TitleDocs titleDocs) {
        this.titleDocs.set(titleDocs);
    }

    public Set<Issue> getIssues() {
        return issues.get();
    }

    public Set<Issue> getCopyOfIssues(){
        return new HashSet<>(issues.get());
    }

    public ObjectProperty<Set<Issue>> issuesProperty() {
        return issues;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues.set(issues);
    }

    public String getDimension() {
        return dimension.get();
    }

    public StringProperty dimensionProperty() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension.set(dimension);
    }

    public Enum<Mediums> getMedium() {
        return medium.get();
    }

    public ObjectProperty<Enum<Mediums>> mediumProperty() {
        return medium;
    }

    public void setMedium(Enum<Mediums> medium) {
        this.medium.set(medium);
    }

    public Enum<Activities> getActivity() {
        return activity.get();
    }

    public ObjectProperty<Enum<Activities>> activityProperty() {
        return activity;
    }

    public void setActivity(Enum<Activities> activity) {
        this.activity.set(activity);
    }

    public LocalDate getApprovalDate() {
        return approvalDate.get();
    }

    public ObjectProperty<LocalDate> approvalDateProperty() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate.set(approvalDate);
    }

    public Enum<Stages> getStage() {
        return stage.get();
    }

    public ObjectProperty<Enum<Stages>> stageProperty() {
        return stage;
    }

    public void setStage(Enum<Stages> stage) {
        this.stage.set(stage);
    }

    public Enum<Status> getStatus() {
        return status.get();
    }

    public ObjectProperty<Enum<Status>> statusProperty() {
        return status;
    }

    public void setStatus(Enum<Status> status) {
        this.status.set(status);
    }

}
