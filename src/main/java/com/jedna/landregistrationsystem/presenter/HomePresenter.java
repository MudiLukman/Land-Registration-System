package com.jedna.landregistrationsystem.presenter;

import com.jedna.landregistrationsystem.App;
import com.jedna.landregistrationsystem.model.*;
import com.jedna.landregistrationsystem.util.AlertMaker;
import com.jedna.landregistrationsystem.util.CurrentLand;
import com.jedna.landregistrationsystem.util.CurrentUser;
import com.jedna.landregistrationsystem.util.DatabaseHelper;
import com.jedna.landregistrationsystem.util.enums.*;
import com.jedna.landregistrationsystem.view.HomeView;
import com.jedna.landregistrationsystem.view.SettingsView;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class HomePresenter {

    private HomeView view;

    private String query = "";
    private String searchTerm;

    public HomePresenter(){
        view = new HomeView();
        loadAllCertificates();
        addEventHandlers();
        setDefaultsForCertificateTable();
        hideControls();
        setDefaultsForLandTable();
    }

    private void hideControls() {
        if(CurrentUser.getUserType() == UserType.ADMIN){
            getView().getQuickLinksBox().getChildren().remove((HBox) getView().getApplicationLink().getParent());
            getView().getToolBar().getItems().clear();
            getView().getToolBar().getItems().addAll(getView().getPendingToolbarItem(), getView().getApprovedToolbarItem(),
                    getView().getRejectedToolbarItem(), getView().getHelpToolbarItem(), getView().getSearchToolbarItem(),
                    getView().getSettingsToolbarItem());
            getView().getNewApplicationMenuItem().setVisible(false);
        }
        else {
            getView().getOpenAllInMap().setVisible(false);
        }
    }

    private void setDefaultsForLandTable() {
        getView().getLandTable().getColumns().get(0).setSortable(false);
        getView().getLandTable().getColumns().get(1).setSortable(false);
        getView().getLandTable().getColumns().get(2).setSortable(false);
        getView().getLandTable().getColumns().get(3).setSortable(false);
        getView().getLandTable().getColumns().get(4).setSortable(false);
        getView().getLandTable().getColumns().get(5).setSortable(false);
        getView().getLandTable().getColumns().get(6).setSortable(false);
        getView().getLandTable().getColumns().get(7).setSortable(false);
        getView().getLandTable().getColumns().get(8).setSortable(false);
        getView().getLandTable().getColumns().get(9).setSortable(false);
        setCellValueFactoryForLandTable();
    }

    private void setDefaultsForCertificateTable(){
        getView().getCertificateTable().getColumns().get(0).setSortable(false);
        getView().getCertificateTable().getColumns().get(1).setSortable(true);
        getView().getCertificateTable().getColumns().get(2).setSortable(false);
        setCellValueFactoryForCertificateTable();
    }

    private void setCellValueFactoryForLandTable(){
        Callback cellValueFactory = new Callback<TableColumn.CellDataFeatures<Land, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Land, String> p){
                int currentLandIndex = 0;
                Land currentLand = p.getValue();
                for(int i = 0; i < getView().getLandTable().getItems().size(); i++){
                    if(currentLand == getView().getLandTable().getItems().get(i)){
                        currentLandIndex = ++i;
                        break;
                    }
                }

                return new ReadOnlyStringWrapper(String.valueOf(currentLandIndex));
            }
        };

        getView().getLandTable().getColumns().get(0).setCellValueFactory(cellValueFactory);
        getView().getLandTable().getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("landId"));
        getView().getLandTable().getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("landOwner"));
        getView().getLandTable().getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("status"));
        getView().getLandTable().getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("approvalDate"));
        getView().getLandTable().getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("dimension"));
        getView().getLandTable().getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("medium"));
        getView().getLandTable().getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("activity"));
        getView().getLandTable().getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("location"));
        getView().getLandTable().getColumns().get(9).setCellValueFactory(new PropertyValueFactory<>("stage"));
        getView().getLandTable().setRowFactory(tv -> {
            TableRow<Land> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    Land rowData = row.getItem();
                    showApplicationDetail(rowData.getLandId());
                }
            });
            return row;
        });
    }

    private void showApplicationDetail(String id) {

        query = "SELECT * FROM land WHERE land_id='" + id + "'";

        ResultSet resultForLand = DatabaseHelper.executeQuery(query);
        LandOwner landOwner = null;
        Location landLocation = null;
        TitleDocs landDocs = null;
        Set<Issue> issues;

        try {
            while (resultForLand.next()){
                String landId = resultForLand.getString("land_id");
                String dimension = resultForLand.getString("dimension");
                String medium = resultForLand.getString("medium");
                String activity_type = resultForLand.getString("activity_type");
                String approval_date = resultForLand.getString("approval_date");
                String stage = resultForLand.getString("stage");
                String status = resultForLand.getString("status");
                String location_id = resultForLand.getString("location_id");
                String owner_id = resultForLand.getString("owner_id");
                String title_id = resultForLand.getString("title_id");

                //get owner details for current land
                landOwner = getOwnerDetails(owner_id);

                //get land location
                landLocation = getLocationDetails(location_id);

                //get land docs
                landDocs = getLandDocs(title_id);

                //get land issues
                issues = readLandIssues(landId);

                Mediums mediumEnum;
                if(medium.equalsIgnoreCase(Mediums.GOVERNMENT.toString())){
                    mediumEnum = Mediums.GOVERNMENT;
                }
                else if(medium.equalsIgnoreCase(Mediums.NATIVES.toString())){
                    mediumEnum = Mediums.NATIVES;
                }
                else {
                    mediumEnum = Mediums.INHERITANCE;
                }

                Activities activityEnum = (activity_type.equalsIgnoreCase(Activities.COMMERCIAL.toString())) ? Activities.COMMERCIAL : Activities.RESIDENTIAL;
                Stages stageEnum;
                if(stage.equalsIgnoreCase(Stages.PENDING.toString())){
                    stageEnum = Stages.PENDING;
                }
                else if(stage.equalsIgnoreCase(Stages.APPROVED.toString())){
                    stageEnum = Stages.APPROVED;
                }
                else {
                    stageEnum = Stages.REJECTED;
                }

                Status statusEnum = (status.equalsIgnoreCase(Status.VALID.toString())) ? Status.VALID : Status.REVOKED ;

                LocalDate dateOfApproval = null;
                try {
                    dateOfApproval = LocalDate.parse(approval_date);
                }catch (DateTimeParseException e){
                    System.out.println(e.getMessage());
                }
                CurrentLand currentLand = new CurrentLand(new Land(landId, landOwner, landLocation, landDocs, issues, dimension, mediumEnum,
                        activityEnum, dateOfApproval, stageEnum, statusEnum));

            }
        }catch (SQLException e){
            System.out.println(e);
        }

        App.loadWindow("Application Detail", Modality.NONE, new LandDetailsPresenter().getView());

        String checkForLand = "SELECT land_id FROM land WHERE land_id='" + getView().getLandTable().getSelectionModel()
                .getSelectedItem().getLandId() + "'";
        ResultSet testResultSet = DatabaseHelper.executeQuery(checkForLand);

        try {
            if(!testResultSet.next()){
                for(Land land : getView().getLandTable().getItems()){
                    if(land.getLandId().equals(getView().getLandTable().getSelectionModel().getSelectedItem().getLandId())){
                        getView().getLandTable().getItems().remove(land);
                    }
                }
            }
        } catch (SQLException | ConcurrentModificationException e) {
            System.out.println(e);
        }

    }

    private Set<Issue> readLandIssues(String landId) {
        query = "SELECT * FROM issue WHERE land_id='" + landId + "'";
        ResultSet resultForLandIssues = DatabaseHelper.executeQuery(query);
        Set<Issue> issues = new HashSet<>();
        try {
            while (resultForLandIssues.next()) {
                String issueDescription = resultForLandIssues.getString("description");
                Issue issue = new Issue(issueDescription);
                issues.add(issue);
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        return issues;
    }

    private void setCellValueFactoryForCertificateTable() {
        Callback cellValueFactory = new Callback<TableColumn.CellDataFeatures<Certificate, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Certificate, String> p){
                int currentCertIndex = 0;
                Certificate currentCert = p.getValue();
                for(int i = 0; i < getView().getCertificateTable().getItems().size(); i++){
                    if(currentCert == getView().getCertificateTable().getItems().get(i)){
                        currentCertIndex = ++i;
                        break;
                    }
                }

                return new ReadOnlyStringWrapper(String.valueOf(currentCertIndex));
            }
        };

        getView().getCertificateTable().getColumns().get(0).setCellValueFactory(cellValueFactory);
        getView().getCertificateTable().getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("certificateId"));
        getView().getCertificateTable().getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("approvalDate"));
        getView().getCertificateTable().setRowFactory(tv -> {
            TableRow<Certificate> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    Certificate rowData = row.getItem();
                    showCertDetail(rowData.getLandId());
                }
            });
            return row;
        });
    }

    private void showCertDetail(String landId) {
        query = "SELECT * FROM land where land_id='" + landId + "'";
        ResultSet landResultSet = DatabaseHelper.executeQuery(query);
        String ownerId = "";
        String activity = "";
        String dimension = "";
        String ownerPhoto = "";
        String ownerName = "";
        String address = "";
        String phone = "";
        Image passport = null;
        try{
            landResultSet.next();
            ownerId = landResultSet.getString("owner_id");
            activity = landResultSet.getString("activity_type");
            dimension = landResultSet.getString("dimension");

        }catch (SQLException e){
            AlertMaker.showErrorMessage("Error", e.getMessage());
            return;
        }

        query = "SELECT * FROM land_owner where owner_id='" + ownerId + "'";
        ResultSet ownerResultSet = DatabaseHelper.executeQuery(query);
        try {
            ownerResultSet.next();
            ownerPhoto = ownerResultSet.getString("passport");
            ownerPhoto = ownerPhoto.replace("\\", "\\\\");
            try {
                try(FileInputStream fileInputStream = new FileInputStream(ownerPhoto)){
                    passport = new Image(fileInputStream);
                } catch (FileNotFoundException e) {
                    System.out.println(e);
                }
            } catch (IOException e) {
                System.out.println(e);
            }
            ownerName = ownerResultSet.getString("fullname");
            address = ownerResultSet.getString("address");
            phone = ownerResultSet.getString("phone");
        }catch (SQLException e){
            AlertMaker.showErrorMessage("Error", e.getMessage());
            return;
        }

        LandOwner owner = new LandOwner(ownerId, ownerName, Gender.MALE, phone, address, passport, null,
                "Don't Know", null, null);
        Land land = new Land(landId, owner, null, null, null, dimension, null,
                (activity.equalsIgnoreCase(String.valueOf(Activities.COMMERCIAL)) ? Activities.COMMERCIAL : Activities.RESIDENTIAL), null, null, null);

        CertificateLayoutPresenter cert = new CertificateLayoutPresenter(land);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setWidth(820);
        Rectangle2D rectangle = Screen.getPrimary().getVisualBounds();
        stage.setMaxHeight(rectangle.getHeight());
        stage.setTitle("Certificate of Occupancy (C of O)");
        stage.setScene(new Scene(cert.getView()));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private void loadAllCertificates() {
        getView().getCertificateTable().getItems().clear();
        if(CurrentUser.getUserType() == UserType.ADMIN){
            query = "SELECT * FROM certificate";
        }
        else {
            query = "SELECT * FROM certificate WHERE owner_id='" + CurrentUser.getLandOwner().getLandOwnerId() + "'";
        }
        ResultSet resultSet = DatabaseHelper.executeQuery(query);

        try {
            while (resultSet.next()){
                String certId = resultSet.getString("cert_id");
                String landId = resultSet.getString("land_id");
                String approvalDate = resultSet.getString("approvaldate");

                getView().getCertificateTable().getItems().add(new Certificate(certId, landId, approvalDate));
            }
        }catch (SQLException e){
            AlertMaker.showErrorMessage("Error", e.getMessage());
        }
    }

    private void addEventHandlers() {

        getView().getNewApplicationMenuItem().setOnAction(event -> handleNewApplication());
        getView().getLogoutMenuItem().setOnAction(event -> handleLogout());
        getView().getExitMenuItem().setOnAction(event -> handleExit());
        getView().getShowOrHideToolbarMenuItem().setOnAction(event -> handleShowOrHideToolbar());
        getView().getOverviewMenuItem().setOnAction(event -> handleOverview());
        getView().getPendingMenuItem().setOnAction(event -> handlePending());
        getView().getApprovedMenuItem().setOnAction(event -> handleApproved());
        getView().getRevokedMenuItem().setOnAction(event -> handleRevoked());
        getView().getHelpMenuItem().setOnAction(event -> showHelpMenu());
        getView().getNewApplicationToolbarItem().setOnAction(event -> handleNewApplication());
        getView().getPendingToolbarItem().setOnAction(event -> handlePending());
        getView().getApprovedToolbarItem().setOnAction(event -> handleApproved());
        getView().getRejectedToolbarItem().setOnAction(event -> handleRevoked());
        getView().getHelpToolbarItem().setOnAction(event -> showHelpMenu());
        getView().getSettingsToolbarItem().setOnAction(event -> showSettingsDialog());
        getView().getSearchToolbarItem().setOnAction(event -> handleSearch());
        getView().getOpenAllInMap().setOnAction(event -> handleOpenAllInMap());
        getView().getLogoutButton().setOnAction(event -> handleLogout());
        getView().getOverviewIcon().setOnMouseClicked(event -> handleOverview());
        getView().getOverviewLink().setOnAction(event -> handleOverview());
        getView().getPendingIcon().setOnMouseClicked(event -> handlePending());
        getView().getPendingLink().setOnAction(event -> handlePending());
        getView().getApprovedIcon().setOnMouseClicked(event -> handleApproved());
        getView().getApprovedLink().setOnAction(event -> handleApproved());
        getView().getRevokedIcon().setOnMouseClicked(event -> handleRevoked());
        getView().getRevokedLink().setOnAction(event -> handleRevoked());
        getView().getApplyIcon().setOnMouseClicked(event -> handleNewApplication());
        getView().getApplicationLink().setOnAction(event -> handleNewApplication());
        getView().getSearchTextField().setOnAction(event -> performSearch());
        getView().getDoSearchBtn().setOnAction(event -> performSearch());

    }

    private void showSettingsDialog() {
        App.loadWindow("Settings", Modality.APPLICATION_MODAL, new SettingsPresenter().getView());
    }

    private void handleNewApplication(){
        App.loadWindow("New Application for C of O", Modality.NONE, new LandDetailsPresenter().getView());
        getView().getLandTable().getItems().clear();
        loadLandTableData();
    }

    private void handleOpenAllInMap(){
        String queryLocations = "SELECT * FROM location";
        ResultSet rsForLocation = DatabaseHelper.executeQuery(queryLocations);
        try {
            List<Location> locations = new ArrayList<>();
            while (rsForLocation.next()){
                String id = rsForLocation.getString("location_id");
                String lat = rsForLocation.getString("latitude");
                String lng = rsForLocation.getString("longitude");

                locations.add(new Location(id, Double.valueOf(lat), Double.valueOf(lng)));
            }

            App.loadWindow("All Land Titles", Modality.NONE, new CustomMapPresenter(locations).getMapView());
        }catch (SQLException e){
            System.out.println("HomePresenter.handleOpenAllInMap() : line 333" + e);
        }
    }

    private void handleLogout(){
        getView().getScene().getWindow().hide();
        if(CurrentUser.getUserType() == UserType.ADMIN){
            App.switchWindow(getView(), new SignInAdminPresenter().getView(), "logout");
        }
        else {
            App.switchWindow(getView(), new LoginPresenter().getView(), "logout");
        }
    }

    private void handleExit(){
        App.onWindowCloseRequest(null);
    }

    private void handleShowOrHideToolbar(){
        if(getView().getToolBar().isVisible()){
            getView().getToolBar().setVisible(false);
        }
        else {
            getView().getToolBar().setVisible(true);
        }
    }

    private void handleOverview(){
        changeTable(getView().getCertificateTable());
        loadAllCertificates();
    }

    private void changeTable(TableView<? extends Object> newTable){
        if(getView().getCenter() instanceof BorderPane){
            ((BorderPane) getView().getCenter()).setCenter(newTable);
        }
    }

    private void loadLandTableData(){

        getView().getLandTable().getItems().clear();

        ResultSet resultForLandDetails = DatabaseHelper.executeQuery(query);

        LandOwner landOwner = null;
        Location landLocation = null;
        TitleDocs landDocs = null;
        Set<Issue> issues;

        try {
            while (resultForLandDetails.next()){
                String landId = resultForLandDetails.getString("land_id");
                String dimension = resultForLandDetails.getString("dimension");
                String medium = resultForLandDetails.getString("medium");
                String activity_type = resultForLandDetails.getString("activity_type");
                String approval_date = resultForLandDetails.getString("approval_date");
                String stage = resultForLandDetails.getString("stage");
                String status = resultForLandDetails.getString("status");
                String location_id = resultForLandDetails.getString("location_id");
                String owner_id = resultForLandDetails.getString("owner_id");
                String title_id = resultForLandDetails.getString("title_id");

                //get owner details for current land
                landOwner = getOwnerDetails(owner_id);

                //get land location
                landLocation = getLocationDetails(location_id);

                //get land docs
                landDocs = getLandDocs(title_id);

                //get land issues
                issues = getLandIssues(landId);

                Mediums mediumEnum;
                if(medium.equalsIgnoreCase(Mediums.GOVERNMENT.toString())){
                    mediumEnum = Mediums.GOVERNMENT;
                }
                else if(medium.equalsIgnoreCase(Mediums.NATIVES.toString())){
                    mediumEnum = Mediums.NATIVES;
                }
                else {
                    mediumEnum = Mediums.INHERITANCE;
                }

                Activities activityEnum = (activity_type.equalsIgnoreCase(Activities.COMMERCIAL.toString())) ? Activities.COMMERCIAL : Activities.RESIDENTIAL;
                Stages stageEnum;
                if(stage.equalsIgnoreCase(Stages.PENDING.toString())){
                    stageEnum = Stages.PENDING;
                }
                else if(stage.equalsIgnoreCase(Stages.APPROVED.toString())){
                    stageEnum = Stages.APPROVED;
                }
                else {
                    stageEnum = Stages.REJECTED;
                }

                Status statusEnum = (status.equalsIgnoreCase(Status.VALID.toString())) ? Status.VALID : Status.REVOKED ;

                LocalDate approvalDateFromDb = null;
                try{
                    approvalDateFromDb = LocalDate.parse(approval_date);
                }catch (DateTimeParseException e){
                    System.out.println(e.getMessage());
                }
                getView().getLandTable().getItems().add(new Land(landId, landOwner, landLocation, landDocs, issues, dimension, mediumEnum,
                        activityEnum, approvalDateFromDb, stageEnum, statusEnum));

            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    private void handlePending(){
        changeTable(getView().getLandTable());
        if(CurrentUser.getUserType() == UserType.ADMIN){
            query = "SELECT * FROM land WHERE stage='" + Stages.PENDING + "'";
        }
        else {
            query = "SELECT * FROM land WHERE stage='" + Stages.PENDING + "' AND owner_id='" +
                    CurrentUser.getLandOwner().getLandOwnerId() + "'";
        }
        loadLandTableData();
    }

    private void handleApproved(){
        changeTable(getView().getLandTable());
        if(CurrentUser.getUserType() == UserType.ADMIN) {
            query = "SELECT * FROM land WHERE stage='" + Stages.APPROVED + "'";
        }else {
            query = "SELECT * FROM land WHERE stage='" + Stages.APPROVED + "' AND owner_id='" +
                    CurrentUser.getLandOwner().getLandOwnerId() + "'";
        }
        loadLandTableData();
    }

    private void handleRevoked(){
        changeTable(getView().getLandTable());
        if(CurrentUser.getUserType() == UserType.ADMIN) {
            query = "SELECT * FROM land WHERE stage='" + Stages.REJECTED + "'";
        }
        else {
            query = "SELECT * FROM land WHERE stage='" + Stages.REJECTED + "' AND owner_id='" +
                    CurrentUser.getLandOwner().getLandOwnerId() + "'";
        }
        loadLandTableData();
    }

    private void showHelpMenu(){
        App.loadWindow("About", Modality.NONE, new HelpPresenter().getView());
    }

    private void handleSearch(){
        Stage searchStage = new Stage(StageStyle.DECORATED);
        HBox searchPane = new HBox(10);
        searchPane.getChildren().addAll(getView().getSearchTextField(), getView().getDoSearchBtn());
        searchPane.setStyle("-fx-padding: 10;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 5;" +
                "-fx-border-insets: 5;");
        searchStage.setScene(new Scene(searchPane));
        searchStage.sizeToScene();
        searchStage.initModality(Modality.APPLICATION_MODAL);
        searchStage.setTitle("Search");
        searchStage.setResizable(false);
        searchStage.showAndWait();
    }

    private void performSearch(){
        if(getView().getSearchTextField().getText().trim().equalsIgnoreCase("")){
            return;
        }

        searchTerm = getView().getSearchTextField().getText();
        ((Stage) getView().getDoSearchBtn().getScene().getWindow()).close();
        retrieveResults();
    }

    private void retrieveResults() {
        changeTable(getView().getLandTable());
        if(CurrentUser.getUserType() == UserType.ADMIN){
            query = "SELECT * FROM land WHERE land_id='" + searchTerm + "'";
        }
        else {
            query = "SELECT * FROM land WHERE land_id='" + searchTerm + "' AND owner_id='" +
                    CurrentUser.getLandOwner().getLandOwnerId() + "'";
        }
        loadLandTableData();
        if(getView().getLandTable().getItems().isEmpty()){
            Label resultMessage = new Label("No results found for " + searchTerm);
            resultMessage.setFont(new Font(20));
            getView().getLandTable().setPlaceholder(resultMessage);
        }
    }

    public HomeView getView(){
        return view;
    }

    public Set<Issue> getLandIssues(String landId) {
        query = "SELECT * FROM issue WHERE land_id='" + landId + "'";
        Set<Issue> iss = new HashSet<>();
        ResultSet resultForLandIssues = DatabaseHelper.executeQuery(query);
        try {
            while(resultForLandIssues.next()){
                String issueDescription = resultForLandIssues.getString("description");
                Issue issue = new Issue(issueDescription);
                iss.add(issue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return iss;
    }

    public TitleDocs getLandDocs(String title_id) {
        query = "SELECT * FROM title_doc WHERE title_id='" + title_id + "'";
        TitleDocs titleDocs = null;
        ResultSet resultForTitleDocs = DatabaseHelper.executeQuery(query);
        try {
            resultForTitleDocs.next();
            String rootOfTitle = resultForTitleDocs.getString("root_of_title");
            String letterFromDistrictHead = resultForTitleDocs.getString("letter_from_district_head");
            String evidenceOfOwnership = resultForTitleDocs.getString("evidence_of_ownership");
            String courtAffidavit = resultForTitleDocs.getString("court_affidavit");
            String taxClearanceCert = resultForTitleDocs.getString("tax_clearance_cert");


            String rootOfTitlePath = rootOfTitle.replace("\\", "\\\\");
            String letterFromDistrictPath = letterFromDistrictHead.replace("\\", "\\\\");
            String evidenceOfOwnershipPath = evidenceOfOwnership.replace("\\", "\\\\");
            String courtAffidavitPath = courtAffidavit.replace("\\", "\\\\");
            String taxClearancePath = taxClearanceCert.replace("\\", "\\\\");

            Image rootOfTitlePhoto = null;
            Image letterFromDistrictPhoto = null;
            Image evidenceOfOwnershipPhoto = null;
            Image courtAffidavitPhoto = null;
            Image taxClearancePhoto = null;

            try {
                try (FileInputStream rootOfTitleStream = new FileInputStream(rootOfTitlePath);
                     FileInputStream letterFromDistrictStream = new FileInputStream(letterFromDistrictPath);
                     FileInputStream evidenceOfOwnershipStream = new FileInputStream(evidenceOfOwnershipPath);
                     FileInputStream courtAffidavitStream = new FileInputStream(courtAffidavitPath);
                     FileInputStream taxClearanceStream = new FileInputStream(taxClearancePath)) {

                    rootOfTitlePhoto = new Image(rootOfTitleStream);
                    letterFromDistrictPhoto = new Image(letterFromDistrictStream);
                    evidenceOfOwnershipPhoto = new Image(evidenceOfOwnershipStream);
                    courtAffidavitPhoto = new Image(courtAffidavitStream);
                    taxClearancePhoto = new Image(taxClearanceStream);

                } catch (FileNotFoundException e) {
                    System.out.println(e);
                }
            } catch (IOException e) {
                System.out.println(e);
            }

            titleDocs = new TitleDocs(title_id, rootOfTitlePhoto, letterFromDistrictPhoto, evidenceOfOwnershipPhoto,
                    courtAffidavitPhoto, taxClearancePhoto);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return titleDocs;
    }

    public LandOwner getOwnerDetails(String owner_id) {
        query = "SELECT * FROM land_owner WHERE owner_id='" + owner_id + "'";
        ResultSet resultForOwner = DatabaseHelper.executeQuery(query);
        LandOwner landOwner = null;
        try {
            resultForOwner.next();
            String ownerId = resultForOwner.getString("owner_id");
            String fullname = resultForOwner.getString("fullname");
            String phone = resultForOwner.getString("phone");
            String gender = resultForOwner.getString("gender");
            String address = resultForOwner.getString("address");
            String passport = resultForOwner.getString("passport");
            String dec_of_age = resultForOwner.getString("dec_of_age");
            String occupation = resultForOwner.getString("occupation");
            String attestation_letter = resultForOwner.getString("attestation_letter");
            String nin_card = resultForOwner.getString("nin_card");

            String passportPath = passport.replace("\\", "\\\\");
            String dec_of_agePath = dec_of_age.replace("\\", "\\\\");
            String attestationPath = attestation_letter.replace("\\", "\\\\");
            String nin_cardPath = nin_card.replace("\\", "\\\\");

            Image ownerPhoto = null;
            Image ownerDecOfAge = null;
            Image ownerAttestationLetter = null;
            Image ownerNINCard = null;
            try {
                try (FileInputStream passportStream = new FileInputStream(passportPath);
                     FileInputStream decOfAgeStream = new FileInputStream(dec_of_agePath);
                     FileInputStream attestationStream = new FileInputStream(attestationPath);
                     FileInputStream ninCardStream = new FileInputStream(nin_cardPath)) {

                    ownerPhoto = new Image(passportStream);
                    ownerDecOfAge = new Image(decOfAgeStream);
                    ownerAttestationLetter = new Image(attestationStream);
                    ownerNINCard = new Image(ninCardStream);

                } catch (FileNotFoundException e) {
                    System.out.println(e);
                }
            } catch (IOException e) {
                System.out.println(e);
            }

            landOwner = new LandOwner(ownerId, fullname, ((gender.equalsIgnoreCase(Gender.MALE.toString())) ? Gender.MALE : Gender.FEMALE),
                    phone, address, ownerPhoto, ownerDecOfAge, occupation, ownerAttestationLetter, ownerNINCard);
        }catch (SQLException e){

        }

        return landOwner;
    }

    public Location getLocationDetails(String location_id) {
        query = "SELECT * FROM location WHERE location_id='" + location_id + "'";
        ResultSet resultForLocation = DatabaseHelper.executeQuery(query);
        String locationId = "";
        String latitude = "";
        String longitude = "";
        try {
            resultForLocation.next();
            locationId = resultForLocation.getString("location_id");
            latitude = resultForLocation.getString("latitude");
            longitude = resultForLocation.getString("longitude");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return new Location(locationId, Double.parseDouble(latitude), Double.parseDouble(longitude));
    }
}
