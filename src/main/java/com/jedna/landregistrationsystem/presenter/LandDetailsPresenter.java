package com.jedna.landregistrationsystem.presenter;

import com.jedna.landregistrationsystem.App;
import com.jedna.landregistrationsystem.model.*;
import com.jedna.landregistrationsystem.util.*;
import com.jedna.landregistrationsystem.util.enums.*;
import com.jedna.landregistrationsystem.view.HomeView;
import com.jedna.landregistrationsystem.view.LandDetailsView;
import com.jedna.landregistrationsystem.view.ShowDocumentView;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LandDetailsPresenter {

    private LandDetailsView view;
    private ObservableMap<String, File> docFilesMap;
    private ObservableMap<String, Image> docImagesMap;
    private Land land;

    private Image ownerPhoto;
    private String ownerName;
    private String ownerPassword;
    private Gender gender;
    private String phone;
    private String address;
    private String occupation;
    private Image decOfAgeImage;
    private Image attestationImage;
    private Image ninCardImage;
    private Double latitude;
    private Double longitude;
    private String dimension;
    private Mediums medium;
    private Activities activity;
    private Image rootOfTitleImage;
    private Image letterFromDistrictImage;
    private Image evidenceOfOwnershipImage;
    private Image courtAffidavit;
    private Image taxClearanceCertImage;
    private Location location;

    private Observable obs;
    private Status oldVal;
    private Status newVal;

    public LandDetailsPresenter(){
        this.view = new LandDetailsView();
        docFilesMap = FXCollections.observableHashMap();
        docImagesMap = FXCollections.observableHashMap();
        if((land = CurrentLand.getCurrentLand()) == null){
            if(CurrentUser.getUserType() == UserType.LAND_OWNER){
                presetGUIControlsValues();
                disableNonEditableControls();
            }
            reworkGUI();
        }
        else {
            if(CurrentUser.getUserType() == UserType.LAND_OWNER){
                disableNonEditableControls();
            }
            else {
                disableNonEditableControls();
                getView().getSubmitApplication().setVisible(false);
                getView().getCancelApplication().setVisible(false);
            }

            populateFilesMapFromLandDetails();
            presetGUIControlsValues();
        }
        addListeners();
    }

    private void disableNonEditableControls() {
        if(CurrentUser.getUserType() == UserType.LAND_OWNER && land == null){
            getView().getChoosePassportLink().setVisible(false);
            getView().getOwnerNameField().setEditable(false);
            getView().getGenderChoiceBox().setDisable(true);
            getView().getGenderChoiceBox().setStyle("-fx-opacity: 1;");
            getView().getPhoneField().setEditable(false);
            getView().getAddress().setEditable(false);
            getView().getOccupation().setEditable(false);
            getView().getDecAgeButton().setDisable(true);
            getView().getAttestationButton().setDisable(true);
            getView().getNinCardButton().setDisable(true);
        }
        else if(CurrentUser.getUserType() == UserType.ADMIN && land !=  null) {
            getView().getChoosePassportLink().setVisible(false);
            getView().getOwnerNameField().setEditable(false);
            getView().getGenderChoiceBox().setDisable(true);
            getView().getGenderChoiceBox().setStyle("-fx-opacity: 1;");
            getView().getPhoneField().setEditable(false);
            getView().getAddress().setEditable(false);
            getView().getOccupation().setEditable(false);
            getView().getDeleteApplication().setVisible(false);
            getView().getLatitudeField().setEditable(false);
            getView().getLongitudeField().setEditable(false);
            getView().getDimensionField().setEditable(false);
            getView().getMediumChoiceBox().setDisable(true);
            getView().getMediumChoiceBox().setStyle("-fx-opacity: 1;");
            getView().getActivityChoiceBox().setDisable(true);
            getView().getActivityChoiceBox().setStyle("-fx-opacity: 1;");
            getView().getRootOfTitleButton().setDisable(true);
            getView().getLetterFromDistrictHEadButton().setDisable(true);
            getView().getEvidenceOfOwnershipButton().setDisable(true);
            getView().getCourtAffidavitButton().setDisable(true);
            getView().getTaxClearanceButton().setDisable(true);
            getView().getDecAgeButton().setDisable(true);
            getView().getAttestationButton().setDisable(true);
            getView().getNinCardButton().setDisable(true);
        }
        else if(CurrentUser.getUserType() == UserType.LAND_OWNER && land != null){
            getView().getChoosePassportLink().setVisible(false);
            getView().getOwnerNameField().setEditable(false);
            getView().getGenderChoiceBox().setDisable(true);
            getView().getGenderChoiceBox().setStyle("-fx-opacity: 1;");
            getView().getPhoneField().setEditable(false);
            getView().getAddress().setEditable(false);
            getView().getOccupation().setEditable(false);
            getView().getDimensionField().setEditable(false);
            getView().getMediumChoiceBox().setDisable(true);
            getView().getMediumChoiceBox().setStyle("-fx-opacity: 1;");
            getView().getActivityChoiceBox().setDisable(true);
            getView().getActivityChoiceBox().setStyle("-fx-opacity: 1;");
            getView().getRootOfTitleButton().setDisable(true);
            getView().getEvidenceOfOwnershipButton().setDisable(true);
            getView().getCourtAffidavitButton().setDisable(true);
            getView().getTaxClearanceButton().setDisable(true);
            getView().getStatusChoiceBox().setDisable(true);
            getView().getStatusChoiceBox().setStyle("-fx-opacity: 1;");
            getView().getStageChoiceBox().setDisable(true);
            getView().getStageChoiceBox().setStyle("-fx-opacity: 1;");
            getView().getLatitudeField().setEditable(false);
            getView().getLongitudeField().setEditable(false);
            getView().getIssuesTextArea().setEditable(false);
            getView().getSubmitApplication().setVisible(false);
            getView().getUpdateApplication().setVisible(false);
            getView().getPrintCertButton().setVisible(false);
            getView().getDecAgeButton().setDisable(true);
            getView().getAttestationButton().setDisable(true);
            getView().getNinCardButton().setDisable(true);
            getView().getLetterFromDistrictHEadButton().setDisable(true);
            if(land.getStage() == Stages.APPROVED){
                getView().getDeleteApplication().setVisible(false);
            }
        }
    }

    private void populateFilesMapFromLandDetails() {

        File passportFile = null;
        File decOfAgeFile = null;
        File attestationFile = null;
        File ninCardFile = null;
        File rootOfTitleFile = null;
        File letterFromDistrictFile = null;
        File evidenceFile = null;
        File courtAffidavitFile = null;
        File taxClearanceCertFile = null;

        String queryLandDetails = "SELECT * FROM land WHERE land_id='" + land.getLandId() + "'";

        ResultSet resultForLand = DatabaseHelper.executeQuery(queryLandDetails);

        try {
            while (resultForLand.next()){
                String owner_id = resultForLand.getString("owner_id");
                String title_id = resultForLand.getString("title_id");

                //get owner details for current land
                String queryOwnerDetails = "SELECT * FROM land_owner WHERE owner_id='" + owner_id + "'";
                ResultSet resultForOwner = DatabaseHelper.executeQuery(queryOwnerDetails);
                while (resultForOwner.next()){
                    String passport = resultForOwner.getString("passport");
                    String dec_of_age = resultForOwner.getString("dec_of_age");
                    String attestation_letter = resultForOwner.getString("attestation_letter");
                    String nin_card = resultForOwner.getString("nin_card");

                    String passportPath = passport.replace("\\", "\\\\");
                    passportFile = new File(passportPath);
                    String dec_of_agePath = dec_of_age.replace("\\", "\\\\");
                    decOfAgeFile = new File(dec_of_agePath);
                    String attestationPath = attestation_letter.replace("\\", "\\\\");
                    attestationFile = new File(attestationPath);
                    String nin_cardPath = nin_card.replace("\\", "\\\\");
                    ninCardFile = new File(nin_cardPath);

                }

                //get land docs
                queryOwnerDetails = "SELECT * FROM title_doc WHERE title_id='" + title_id + "'";
                ResultSet resultForTitleDocs = DatabaseHelper.executeQuery(queryOwnerDetails);
                while (resultForTitleDocs.next()){
                    String rootOfTitle = resultForTitleDocs.getString("root_of_title");
                    String letterFromDistrictHead = resultForTitleDocs.getString("letter_from_district_head");
                    String evidenceOfOwnership = resultForTitleDocs.getString("evidence_of_ownership");
                    String courtAffidavit = resultForTitleDocs.getString("court_affidavit");
                    String taxClearanceCert = resultForTitleDocs.getString("tax_clearance_cert");

                    String rootOfTitlePath = rootOfTitle.replace("\\", "\\\\");
                    rootOfTitleFile = new File(rootOfTitlePath);
                    String letterFromDistrictPath = letterFromDistrictHead.replace("\\", "\\\\");
                    letterFromDistrictFile = new File(letterFromDistrictPath);
                    String evidenceOfOwnershipPath = evidenceOfOwnership.replace("\\", "\\\\");
                    evidenceFile = new File(evidenceOfOwnershipPath);
                    String courtAffidavitPath = courtAffidavit.replace("\\", "\\\\");
                    courtAffidavitFile = new File(courtAffidavitPath);
                    String taxClearancePath = taxClearanceCert.replace("\\", "\\\\");
                    taxClearanceCertFile = new File(taxClearancePath);

                }

            }
        }catch (SQLException e){
            AlertMaker.showErrorMessage("Error", e.getMessage());
        }
        docFilesMap.put("declarationOfAge", decOfAgeFile);
        docFilesMap.put("attestationLetter", attestationFile);
        docFilesMap.put("ninCard", ninCardFile);
        docFilesMap.put("rootOfTitle", rootOfTitleFile);
        docFilesMap.put("letterOfConfirmationFromDistrictHead", letterFromDistrictFile);
        docFilesMap.put("evidenceOfOwnership", evidenceFile);
        docFilesMap.put("courtAffidavit", courtAffidavitFile);
        docFilesMap.put("taxClearanceCert", taxClearanceCertFile);
        docFilesMap.put("passport", passportFile);
    }

    private void presetGUIControlsValues() {
        if(CurrentUser.getUserType() == UserType.LAND_OWNER && land == null){
            getView().getOwnerPassportView().setImage(CurrentUser.getLandOwner().getPassport());
            getView().getOwnerNameField().setText(CurrentUser.getLandOwner().getFullname());
            getView().getGenderChoiceBox().setValue(CurrentUser.getLandOwner().getGender() == Gender.MALE ? Gender.MALE : Gender.FEMALE);
            getView().getPhoneField().setText(CurrentUser.getLandOwner().getPhone());
            getView().getAddress().setText(CurrentUser.getLandOwner().getAddress());
            getView().getOccupation().setText(CurrentUser.getLandOwner().getOccupation());
            docImagesMap.put("declarationOfAge", CurrentUser.getLandOwner().getDeclarationOfAge());
            docImagesMap.put("attestationLetter", CurrentUser.getLandOwner().getAttestationLetter());
            docImagesMap.put("ninCard", CurrentUser.getLandOwner().getNinCard());
            docImagesMap.put("passport", CurrentUser.getLandOwner().getPassport());
        }
        else if(CurrentUser.getUserType() == UserType.LAND_OWNER && land != null){
            getView().getStatusChoiceBox().setValue(land.getStatus() == Status.VALID ? Status.VALID : Status.REVOKED);
            getView().getOwnerPassportView().setImage(land.getLandOwner().getPassport());
            if(getView().getIssuesTextArea().getText() == null){
                getView().getIssuesTextArea().setText("");
            }
            int i = 0;
            for(Issue issue : land.getIssues()){
                if(getView().getIssuesTextArea().getText() == null || getView().getIssuesTextArea().getText().trim().equalsIgnoreCase("")){
                    getView().getIssuesTextArea().setText((i + 1) + ". " + issue.getDescription());
                }
                else {
                    getView().getIssuesTextArea().setText(getView().getIssuesTextArea().getText() + "\n" + (i + 1) + ". " + issue.getDescription());
                }
                ++i;
            }
            if(land.getStage() == Stages.PENDING){
                getView().getStageChoiceBox().setValue(Stages.PENDING);
            }
            else if(land.getStage() == Stages.APPROVED){
                getView().getStageChoiceBox().setValue(Stages.APPROVED);
            }
            else {
                getView().getStageChoiceBox().setValue(Stages.REJECTED);
            }
            getView().getDimensionField().setEditable(false);

            if(land.getMedium() == Mediums.GOVERNMENT){
                getView().getMediumChoiceBox().setValue(Mediums.GOVERNMENT);
            }
            else if(land.getMedium() == Mediums.NATIVES){
                getView().getMediumChoiceBox().setValue(Mediums.NATIVES);
            }
            else {
                getView().getMediumChoiceBox().setValue(Mediums.INHERITANCE);
            }
            getView().getActivityChoiceBox().setValue(land.getActivity() == Activities.RESIDENTIAL ?
                    Activities.RESIDENTIAL : Activities.COMMERCIAL);

            getView().getOwnerPassportView().setImage(land.getLandOwner().getPassport());
            getView().getTitleId().setText(land.getLandId());
            getView().getOwnerNameField().setText(land.getLandOwner().getFullname());
            getView().getGenderChoiceBox().setValue(land.getLandOwner().getGender() == Gender.MALE ? Gender.MALE : Gender.FEMALE);
            getView().getPhoneField().setText(land.getLandOwner().getPhone());
            getView().getAddress().setText(land.getLandOwner().getAddress());
            getView().getOccupation().setText(land.getLandOwner().getOccupation());
            getView().getLatitudeField().setText(land.getLocation().getLatitude().toString());
            getView().getLongitudeField().setText(land.getLocation().getLongitude().toString());
            getView().getDimensionField().setText(land.getDimension());
        }
        else if(CurrentUser.getUserType() == UserType.ADMIN && land != null){
            getView().getOwnerPassportView().setImage(land.getLandOwner().getPassport());
            getView().getTitleId().setText(land.getLandId());
            getView().getOwnerNameField().setText(land.getLandOwner().getFullname());
            getView().getGenderChoiceBox().setValue((Gender) land.getLandOwner().getGender());
            getView().getPhoneField().setText(land.getLandOwner().getPhone());
            getView().getAddress().setText(land.getLandOwner().getAddress());
            getView().getOccupation().setText(land.getLandOwner().getOccupation());
            getView().getStatusChoiceBox().setValue((Status) land.getStatus());
            getView().getStageChoiceBox().setValue((Stages) land.getStage());
            getView().getLatitudeField().setText(land.getLocation().getLatitude().toString());
            location = land.getLocation();
            getView().getLongitudeField().setText(land.getLocation().getLongitude().toString());
            getView().getDimensionField().setText(land.getDimension());
            getView().getMediumChoiceBox().setValue((Mediums) land.getMedium());
            getView().getActivityChoiceBox().setValue((Activities) land.getActivity());
            if(getView().getIssuesTextArea().getText() == null){
                getView().getIssuesTextArea().setText("");
            }

            int i = 0;
            for(Issue issue : land.getIssues()){
                if(getView().getIssuesTextArea().getText() == null ||
                        getView().getIssuesTextArea().getText().trim().equalsIgnoreCase("")){
                    getView().getIssuesTextArea().setText((i + 1) + ". " + issue.getDescription());
                }
                else {
                    getView().getIssuesTextArea().setText(getView().getIssuesTextArea().getText() +
                            "\n" + (i + 1) + ". " + issue.getDescription());
                }
                ++i;
            }
        }
    }

    private void reworkGUI() {
        //hide some controls
        getView().getDeleteApplication().setVisible(false);
        getView().getIssuesTextArea().setVisible(false);
        getView().getCenterLeftFraction().setVisible(false);
        getView().getMapViewButton().setVisible(false);
        getView().getUpdateApplication().setVisible(false);
        getView().getPrintCertButton().setVisible(false);
        getView().getDecAgeView().setVisible(false);
        getView().getAttestationView().setVisible(false);
        getView().getNinCardView().setVisible(false);
        getView().getRootOfTitleView().setVisible(false);
        getView().getLetterFromDistrictHeadView().setVisible(false);
        getView().getEvidenceOfOwnershipView().setVisible(false);
        getView().getCourtAffidavitView().setVisible(false);
        getView().getTaxClearanceView().setVisible(false);
        getView().getTitleId().setVisible(false);
        getView().getIssuesLabel().setVisible(false);

        //rename controls
        getView().getFormTitle().setText("Application for Certificate of Ownership");
        getView().getDecAgeButton().setText("Choose");
        getView().getAttestationButton().setText("Choose");
        getView().getNinCardButton().setText("Choose");
        getView().getRootOfTitleButton().setText("Choose");
        getView().getLetterFromDistrictHEadButton().setText("Choose");
        getView().getEvidenceOfOwnershipButton().setText("Choose");
        getView().getCourtAffidavitButton().setText("Choose");
        getView().getTaxClearanceButton().setText("Choose");
    }

    private void addListeners() {
        getView().getDeleteApplication().setOnMouseClicked(event -> handleDeleteApplication());
        getView().getChoosePassportLink().setOnAction(event -> handleChoosePassport());
        getView().getPrintCertButton().setOnAction(event -> handlePrintJob());
        getView().getDecAgeButton().setOnAction(event -> handleChangeDecOfAge());
        getView().getDecAgeView().setOnMouseClicked(event -> handleViewDecOfAge());
        getView().getAttestationButton().setOnAction(event -> handleChangeAttestation());
        getView().getAttestationView().setOnMouseClicked(event -> handleViewAttestation());
        getView().getNinCardButton().setOnAction(event -> handleChangeNINCard());
        getView().getNinCardView().setOnMouseClicked(event -> handleViewNIN());
        getView().getRootOfTitleButton().setOnAction(event -> handleChangeRootOfTitle());
        getView().getRootOfTitleView().setOnMouseClicked(event -> handleViewRootOfTitle());
        getView().getLetterFromDistrictHEadButton().setOnAction(event -> handleChangeLetterFromDistrictHead());
        getView().getLetterFromDistrictHeadView().setOnMouseClicked(event -> handleViewLetterFromDistrictHead());
        getView().getEvidenceOfOwnershipButton().setOnAction(event -> handleChangeEvidenceOfOwnership());
        getView().getEvidenceOfOwnershipView().setOnMouseClicked(event -> handleViewEvidenceOfOwnership());
        getView().getCourtAffidavitButton().setOnAction(event -> handleChangeCourtAffidavit());
        getView().getCourtAffidavitView().setOnMouseClicked(event -> handleViewCourtAffidavit());
        getView().getTaxClearanceButton().setOnAction(event -> handleChangeTaxClearance());
        getView().getTaxClearanceView().setOnMouseClicked(event -> handleViewTaxClearance());
        getView().getMapViewButton().setOnAction(event -> handleShowInMap());
        getView().getUpdateApplication().setOnAction(event -> handleUpdate());
        getView().getSubmitApplication().setOnAction(event -> handleSubmit());
        getView().getCancelApplication().setOnAction(event -> {
            getView().getScene().getWindow().hide();
            land = null;
        });
        getView().getReasonTextField().setOnAction(event -> handleSaveReason());
        getView().getSaveReason().setOnAction(event -> handleSaveReason());
        getView().getStatusChoiceBox().valueProperty().addListener(this::handleStatusChange);

        docFilesMap.addListener(this::handleMapValueChange);
        docImagesMap.addListener(this::handleMapValueChange);
        // the following image was added to map to trigger map event handler when page loads
        docImagesMap.put("test", new Image(LandDetailsPresenter.class.getResource("/about_bg.jpg").toExternalForm()));
    }

    private void handleMapValueChange(Observable map){
        if(docFilesMap.size() != 0){
            if(!docFilesMap.containsKey("declarationOfAge")){
                getView().getDecAgeView().setVisible(false);
            }
            else {
                getView().getDecAgeView().setVisible(true);
            }
            if(!docFilesMap.containsKey("attestationLetter")){
                getView().getAttestationView().setVisible(false);
            }
            else {
                getView().getAttestationView().setVisible(true);
            }
            if(!docFilesMap.containsKey("ninCard")){
                getView().getNinCardView().setVisible(false);
            }
            else {
                getView().getNinCardView().setVisible(true);
            }
            if(!docFilesMap.containsKey("rootOfTitle")){
                getView().getRootOfTitleView().setVisible(false);
            }
            else {
                getView().getRootOfTitleView().setVisible(true);
            }
            if(!docFilesMap.containsKey("letterOfConfirmationFromDistrictHead")){
                getView().getLetterFromDistrictHeadView().setVisible(false);
            }
            else {
                getView().getLetterFromDistrictHeadView().setVisible(true);
            }
            if(!docFilesMap.containsKey("evidenceOfOwnership")){
                getView().getEvidenceOfOwnershipView().setVisible(false);
            }
            else {
                getView().getEvidenceOfOwnershipView().setVisible(true);
            }
            if(!docFilesMap.containsKey("courtAffidavit")){
                getView().getCourtAffidavitView().setVisible(false);
            }
            else {
                getView().getCourtAffidavitView().setVisible(true);
            }
            if(!docFilesMap.containsKey("taxClearanceCert")){
                getView().getTaxClearanceView().setVisible(false);
            }
            else {
                getView().getTaxClearanceView().setVisible(true);
            }
        }
        else {
            if(!docImagesMap.containsKey("declarationOfAge")){
                getView().getDecAgeView().setVisible(false);
            }
            else {
                getView().getDecAgeView().setVisible(true);
            }
            if(!docImagesMap.containsKey("attestationLetter")){
                getView().getAttestationView().setVisible(false);
            }
            else {
                getView().getAttestationView().setVisible(true);
            }
            if(!docImagesMap.containsKey("ninCard")){
                getView().getNinCardView().setVisible(false);
            }
            else {
                getView().getNinCardView().setVisible(true);
            }
            if(!docImagesMap.containsKey("rootOfTitle")){
                getView().getRootOfTitleView().setVisible(false);
            }
            else {
                getView().getRootOfTitleView().setVisible(true);
            }
            if(!docImagesMap.containsKey("letterOfConfirmationFromDistrictHead")){
                getView().getLetterFromDistrictHeadView().setVisible(false);
            }
            else {
                getView().getLetterFromDistrictHeadView().setVisible(true);
            }
            if(!docImagesMap.containsKey("evidenceOfOwnership")){
                getView().getEvidenceOfOwnershipView().setVisible(false);
            }
            else {
                getView().getEvidenceOfOwnershipView().setVisible(true);
            }
            if(!docImagesMap.containsKey("courtAffidavit")){
                getView().getCourtAffidavitView().setVisible(false);
            }
            else {
                getView().getCourtAffidavitView().setVisible(true);
            }
            if(!docImagesMap.containsKey("taxClearanceCert")){
                getView().getTaxClearanceView().setVisible(false);
            }
            else {
                getView().getTaxClearanceView().setVisible(true);
            }
        }
    }

    private void chooseImageFile(String key){
        FileChooser fileDialog = new FileChooser();
        fileDialog.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png"));
        fileDialog.setTitle("Select File");
        File file = fileDialog.showOpenDialog(getView().getScene().getWindow());
        if(file == null){
            return;
        }

        docFilesMap.put(key, file);
    }

    private void handleSubmit(){

        dimension = getView().getDimensionField().getText().trim();
        if(dimension.equals("")){
            AlertMaker.showErrorMessage("Error", "Invalid input for Dimension");
            return;
        }
        medium = getView().getMediumChoiceBox().getValue();
        activity = getView().getActivityChoiceBox().getValue();

        if(docFilesMap.size() < 5){
            AlertMaker.showErrorMessage("Error", "All Documents must be provided");
            return;
        }

        if(getView().getDimensionField().getText().trim().equals("") || getView().getMediumChoiceBox().getValue() == null ||
                getView().getActivityChoiceBox().getValue() == null || getView().getLatitudeField().getText().trim().equals("")
                || getView().getLongitudeField().getText().trim().equals("")){
            AlertMaker.showErrorMessage("Error", "All field values must be filled");
            return;
        }

        try {
            rootOfTitleImage = new Image(docFilesMap.get("rootOfTitle").toURI().toURL().toExternalForm());
            letterFromDistrictImage = new Image(docFilesMap.get("letterOfConfirmationFromDistrictHead").toURI().toURL().toExternalForm());
            evidenceOfOwnershipImage = new Image(docFilesMap.get("evidenceOfOwnership").toURI().toURL().toExternalForm());
            courtAffidavit = new Image(docFilesMap.get("courtAffidavit").toURI().toURL().toExternalForm());
            taxClearanceCertImage = new Image(docFilesMap.get("taxClearanceCert").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //save details to database

        //generate unique land id
        String landId = KeyGenerator.generateKey();

        //save location details
        String locationId = KeyGenerator.generateKey();
        try{
            latitude = Double.valueOf(getView().getLatitudeField().getText().trim());
            longitude = Double.valueOf(getView().getLongitudeField().getText().trim());
        }catch (NumberFormatException e){
            AlertMaker.showErrorMessage("Error", e.getMessage());
        }
        String insertLocation = "insert into location(location_id, latitude, longitude) values (" +
                "'" + locationId + "', '" + latitude + "', '" + longitude + "');";
        int locationVal = DatabaseHelper.insert_record(insertLocation);
        if(locationVal == 0) {
            AlertMaker.showErrorMessage("Error", "There is a problem with your input");
            return;
        }

        //save title documents
        String titleDocsId = KeyGenerator.generateKey();
        String titleDocsPrefix = "C:\\landregsystem\\land\\" + landId + "\\";

        File rootOfTitleFile = new File(titleDocsPrefix + "rootOfTitle.jpg");
        File letterFromDistrictFile = new File(titleDocsPrefix + "letterFromDistrictHead.jpg");
        File evidenceFile = new File(titleDocsPrefix + "evidenceOfOwnership.jpg");
        File courtFile = new File(titleDocsPrefix + "courtAffidavit.jpg");
        File taxFile = new File(titleDocsPrefix + "taxClearanceCert.jpg");

        if(!rootOfTitleFile.exists()){
            rootOfTitleFile.mkdirs();
        }
        if(!letterFromDistrictFile.exists()){
            letterFromDistrictFile.mkdirs();
        }
        if(!evidenceFile.exists()){
            evidenceFile.mkdirs();
        }
        if(!courtFile.exists()){
            courtFile.mkdirs();
        }
        if(!taxFile.exists()){
            taxFile.mkdirs();
        }

        String format = "JPG";

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(rootOfTitleImage, null), format, rootOfTitleFile);
            ImageIO.write(SwingFXUtils.fromFXImage(letterFromDistrictImage, null), format, letterFromDistrictFile);
            ImageIO.write(SwingFXUtils.fromFXImage(evidenceOfOwnershipImage, null), format, evidenceFile);
            ImageIO.write(SwingFXUtils.fromFXImage(courtAffidavit, null), format, courtFile);
            ImageIO.write(SwingFXUtils.fromFXImage(taxClearanceCertImage, null), format, taxFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DatabaseHelper.insertTitleDocs(titleDocsId, rootOfTitleFile.getAbsolutePath(), letterFromDistrictFile.getAbsolutePath(),
                evidenceFile.getAbsolutePath(), courtFile.getAbsolutePath(), taxFile.getAbsolutePath());

        //save land details
        String dimension = getView().getDimensionField().getText().trim();
        String medium = getView().getMediumChoiceBox().getValue().toString();
        String activity = getView().getActivityChoiceBox().getValue().toString();

        String insertLand = "insert into land(land_id, dimension, medium, activity_type, approval_date, stage, status, location_id, owner_id, title_id) values (" +
                "'" + landId + "', '" + dimension + "', '" + medium + "', '" + activity + "', 'UNDEFINED', '" + Stages.PENDING + "', '" + Status.VALID +
                "', '" + locationId + "', '" + CurrentUser.getLandOwner().getLandOwnerId() + "', '" + titleDocsId + "');";

        int landVal = DatabaseHelper.insert_record(insertLand);
        if(locationVal == 0) {
            AlertMaker.showErrorMessage("Error", "There is a problem with your input");
            return;
        }
        else {
            AlertMaker.showSimpleAlert("Done", "Application submitted successfully");
            clearAllFields();
        }

        rootOfTitleFile = null;
        letterFromDistrictFile = null;
        evidenceFile = null;
        courtFile = null;
        taxFile = null;

    }

    private void handleShowInMap(){
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(land.getLocation().getLocationId(), land.getLocation().getLatitude(), land.getLocation().getLongitude()));
        App.loadWindow("Map", Modality.APPLICATION_MODAL, new CustomMapPresenter(locations).getMapView());
    }

    private void handleViewTaxClearance(){
        try {
            showDocumentInWindow(new Image(docFilesMap.get("taxClearanceCert").toURI().toURL().toExternalForm()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void handleChangeTaxClearance(){
        chooseImageFile("taxClearanceCert");
    }

    private void handleViewCourtAffidavit(){
        try {
            showDocumentInWindow(new Image(docFilesMap.get("courtAffidavit").toURI().toURL().toExternalForm()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void handleChangeCourtAffidavit(){
        chooseImageFile("courtAffidavit");
    }

    private void handleViewEvidenceOfOwnership(){
        try {
            showDocumentInWindow(new Image(docFilesMap.get("evidenceOfOwnership").toURI().toURL().toExternalForm()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void handleChangeEvidenceOfOwnership(){
        chooseImageFile("evidenceOfOwnership");
    }

    private void handleViewLetterFromDistrictHead(){
        try {
            showDocumentInWindow(new Image(docFilesMap.get("letterOfConfirmationFromDistrictHead").toURI().toURL().toExternalForm()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void showDocumentInWindow(Image image) {
        ShowDocumentPresenter documentPresenter = new ShowDocumentPresenter();
        for(Node node : documentPresenter.getView().getChildren()){
            ((ImageView) node).setImage(image);
        }
        App.loadWindow("Document", Modality.NONE, documentPresenter.getView());
    }

    private void handleChangeLetterFromDistrictHead(){
        chooseImageFile("letterOfConfirmationFromDistrictHead");
    }

    private void handleViewRootOfTitle(){
        try {
            showDocumentInWindow(new Image(docFilesMap.get("rootOfTitle").toURI().toURL().toExternalForm()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void handleChangeRootOfTitle(){
        chooseImageFile("rootOfTitle");
    }

    private void handleViewNIN(){
        try {
            showDocumentInWindow(new Image(docFilesMap.get("ninCard").toURI().toURL().toExternalForm()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            showDocumentInWindow(docImagesMap.get("ninCard"));
        }
    }

    private void handleChangeNINCard(){
        chooseImageFile("ninCard");
    }

    private void handleViewAttestation(){
        try {
            showDocumentInWindow(new Image(docFilesMap.get("attestationLetter").toURI().toURL().toExternalForm()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            showDocumentInWindow(docImagesMap.get("attestationLetter"));
        }
    }

    private void handleUpdate(){

        String updateLand = "UPDATE land SET status='" + getView().getStatusChoiceBox().getValue() +
                "', stage='" + getView().getStageChoiceBox().getValue() + "' WHERE land_id='" + land.getLandId() + "'";

        try {
            if (!oldVal.equals(Status.REVOKED) && newVal.equals(Status.REVOKED)) {
                if (!showNoteDownIssuesDialog(oldVal)) {
                    return;
                }
            }
        }catch (NullPointerException e){
            System.out.println("LandDetailsPresenter:handleUpdate:771" + e);
        }
        if(DatabaseHelper.insert_record(updateLand) != 0){
            AlertMaker.showSimpleAlert("Success", "Record updated successfully");
        }
        else {
            AlertMaker.showErrorMessage("Failed", "Unable to update record");
        }
    }

    private void handleStatusChange(Observable observable, Status oldValue, Status newValue){
        obs = observable;
        oldVal = oldValue;
        newVal = newValue;
    }

    private boolean showNoteDownIssuesDialog(Status oldValue) {
        final boolean[] reasonTaken = {true};
        Stage noteStage = new Stage(StageStyle.DECORATED);
        HBox notePane = new HBox(10);
        notePane.getChildren().addAll(getView().getReasonTextField(), getView().getSaveReason());
        notePane.setStyle("-fx-padding: 10;" +
                "-fx-border-radius: 5;" +
                "-fx-border-insets: 5;");
        noteStage.setScene(new Scene(notePane));
        noteStage.sizeToScene();
        noteStage.setOnCloseRequest(event -> {
            getView().getStatusChoiceBox().setValue(oldValue);
            reasonTaken[0] = false;
        });
        noteStage.initModality(Modality.APPLICATION_MODAL);
        noteStage.setTitle("Reason");
        noteStage.setResizable(false);
        noteStage.showAndWait();
        return reasonTaken[0];
    }

    private void handleSaveReason(){
        if(getView().getReasonTextField().getText().trim().equalsIgnoreCase("")){
            return;
        }
        String issue = getView().getReasonTextField().getText().trim();
        String issueSql = "insert into issue(issue_id, land_id, description) values (" +
                "'" + KeyGenerator.generateKey() + "', '" + land.getLandId() + "', '" + issue + "');";
        int issueVal = DatabaseHelper.insert_record(issueSql);
        if(issueVal == 0) {
            AlertMaker.showErrorMessage("Error", "Failed to record reason");
        }
        else {
            land.getIssues().add(new Issue(getView().getReasonTextField().getText()));
            getView().getIssuesTextArea().setText("");
            int i = 0;
            for(Issue currentIssue : land.getIssues()){
                if(getView().getIssuesTextArea().getText().trim().equalsIgnoreCase("")){
                    getView().getIssuesTextArea().setText((i + 1) + ". " + currentIssue.getDescription());
                }
                else {
                    getView().getIssuesTextArea().setText(getView().getIssuesTextArea().getText() + "\n" + (i + 1) + ". " + currentIssue.getDescription());
                }
                ++i;
            }
            ((Stage) getView().getReasonTextField().getScene().getWindow()).close();
            AlertMaker.showSimpleAlert("Done", "Reason added");
        }
    }

    private void handleChangeAttestation(){
        chooseImageFile("attestationLetter");
    }

    private void handleViewDecOfAge(){
        try {
            showDocumentInWindow(new Image(docFilesMap.get("declarationOfAge").toURI().toURL().toExternalForm()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            showDocumentInWindow(docImagesMap.get("declarationOfAge"));
        }
    }

    private void handleChangeDecOfAge(){
        chooseImageFile("declarationOfAge");
    }

    private void handlePrintJob(){
        if(getView().getStatusChoiceBox().getValue() != Status.VALID ){
            AlertMaker.showErrorMessage("Error", "Cannot print certificate for a revoked application");
            return;
        }

        Stage stage = new Stage(StageStyle.DECORATED);
        BorderPane layout = new BorderPane();
        Button proceed = new Button("Proceed");
        proceed.setOnAction(event -> {
            stage.hide();
            showCertBeforePrinting();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> {
            ((Stage) cancel.getScene().getWindow()).close();
        });
        VBox top = new VBox(3);
        top.getChildren().add(new Label("Confirm Action"));
        top.getChildren().add(new Label("This will print a certificate of ownership (C of O)"));
        top.setPadding(new Insets(7));
        layout.setTop(top);
        HBox bottom = new HBox(10);
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().addAll(proceed, cancel);
        proceed.setDefaultButton(true);
        cancel.setCancelButton(true);
        layout.setBottom(bottom);
        layout.setStyle("-fx-padding: 10;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 5;" +
                "-fx-border-insets: 5;");
        stage.setTitle("Confirm Action");
        stage.initOwner(getView().getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(layout));
        stage.showAndWait();

    }

    private void showCertBeforePrinting(){
        LocalDate approvalDate = LocalDate.now();
        land.setStage(Stages.APPROVED);
        getView().getStageChoiceBox().setValue(Stages.APPROVED);
        String updateStageSql = "UPDATE land set stage='" + Stages.APPROVED + "', approval_date='"
               + approvalDate.toString() + "' WHERE land_id='" + land.getLandId() + "'";
        if(DatabaseHelper.insert_record(updateStageSql) != 0){
            System.out.println("Successfully updated stage before printing");
        }
        else {
            AlertMaker.showErrorMessage("Error", "Unable to update stage");
            return;
        }
        CertificateLayoutPresenter cert = new CertificateLayoutPresenter(land);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setWidth(820);
        Rectangle2D rectangle = Screen.getPrimary().getVisualBounds();
        stage.setMaxHeight(rectangle.getHeight());
        stage.setTitle("Certificate of Occupancy (C of O)");
        stage.setScene(new Scene(cert.getView()));
        cert.getView().getApprovalDate().setText(cert.getView().getApprovalDate().getText() + LocalDate.now().toString());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        String certSql = "insert into certificate(cert_id, land_id, owner_id, approvaldate) values ('" + KeyGenerator.generateKey()
                + "', '" + land.getLandId() + "', '" + land.getLandOwner().getLandOwnerId()+ "', '" + LocalDate.now() + "');";

        if(DatabaseHelper.insert_record(certSql) != 0){
            System.out.println("Cert printed successfully");
        }
        else {
            System.out.println("Failed to add cert to db");
        }

        Runnable printerTask = () -> {
            PrinterJob job = PrinterJob.createPrinterJob();

            if(job == null){
                return;
            }

            boolean proceed = job.showPrintDialog(stage);
            if(proceed){
                boolean printed = job.printPage(cert.getView());
                if(printed){
                    job.endJob();
                }
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(printerTask);
        service.shutdown();

    }

    private void handleChoosePassport(){
        chooseImageFile("passport");
        if(docFilesMap.containsKey("passport")){
            try {
                getView().getOwnerPassportView().setImage(new Image(docFilesMap.get("passport").toURI().toURL().toExternalForm()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleDeleteApplication(){
        Stage stage = new Stage(StageStyle.DECORATED);
        BorderPane layout = new BorderPane();
        Button proceed = new Button("Proceed");
        proceed.setOnAction(event -> {
            String deleteIssueSql = "delete from issue where land_id='"
                    + HomeView.landTable.getSelectionModel().getSelectedItem().getLandId() + "'";
            String deleteCertSql = "delete from certificate where land_id='" +
                    HomeView.landTable.getSelectionModel().getSelectedItem().getLandId() + "'";
            String deleteLandSql = "delete from land where land_id='" +
                    HomeView.landTable.getSelectionModel().getSelectedItem().getLandId() + "'";
            String deleteLocationSql = "delete from location where location_id='" +
                    HomeView.landTable.getSelectionModel().getSelectedItem().getLocation().getLocationId() + "'";
            String deleteLandOwnerSql = "delete from land_owner where owner_id='" +
                    HomeView.landTable.getSelectionModel().getSelectedItem().getLandOwner().getLandOwnerId() + "'";
            String deleteTitleDocsSql = "delete from title_doc where title_id='" +
                    HomeView.landTable.getSelectionModel().getSelectedItem().getTitleDocs().getTitleDocsId() + "'";

            String[] docsPath = new String[3];
            docsPath[0] = "SELECT * FROM certificate WHERE land_id='" + HomeView.landTable.getSelectionModel()
                    .getSelectedItem().getLandId() + "'";
            docsPath[1] = "SELECT * FROM land_owner WHERE owner_id='" + HomeView.landTable.getSelectionModel()
                    .getSelectedItem().getLandOwner().getLandOwnerId() + "'";
            docsPath[2] = "SELECT * FROM title_doc WHERE title_id='" + HomeView.landTable.getSelectionModel()
                    .getSelectedItem().getTitleDocs().getTitleDocsId() + "'";

            String pathToCert = "";
            String pathToPassport = "";
            String pathToDecOfAge = "";
            String pathToAttestation = "";
            String pathToNIN = "";
            String pathToRootOfTitle = "";
            String pathToLetterFromDH = "";
            String pathToEvidence = "";
            String pathToCourtAffidavit = "";
            String pathToClearance = "";

            ResultSet certRS = DatabaseHelper.executeQuery(docsPath[0]);
            try {
                if(certRS.next()){
                    pathToCert = certRS.getString("picture");
                }

            }catch (SQLException e){
                System.out.println(e);
            }

            ResultSet ownerDocsRS = DatabaseHelper.executeQuery(docsPath[1]);
            try {
                while(ownerDocsRS.next()){
                    pathToPassport = ownerDocsRS.getString("passport");
                    pathToDecOfAge = ownerDocsRS.getString("dec_of_age");
                    pathToAttestation = ownerDocsRS.getString("attestation_letter");
                    pathToNIN = ownerDocsRS.getString("nin_card");
                }

            }catch (SQLException e){
                System.out.println(e);
            }

            ResultSet titleDocRS = DatabaseHelper.executeQuery(docsPath[2]);
            try {
                while(titleDocRS.next()){
                    pathToRootOfTitle = titleDocRS.getString("root_of_title");
                    pathToLetterFromDH = titleDocRS.getString("letter_from_district_head");
                    pathToEvidence = titleDocRS.getString("evidence_of_ownership");
                    pathToCourtAffidavit = titleDocRS.getString("court_affidavit");
                    pathToClearance = titleDocRS.getString("tax_clearance_cert");
                }

            }catch (SQLException e){
                System.out.println(e);
            }

            DatabaseHelper.delete_record(deleteIssueSql);

            if(DatabaseHelper.delete_record(deleteLandSql) > 0 && DatabaseHelper.delete_record(deleteLocationSql) > 0
                    && DatabaseHelper.delete_record(deleteLandOwnerSql) > 0 && DatabaseHelper.delete_record(deleteTitleDocsSql) > 0){

                File passportFile = new File(pathToPassport.replace("\\", "\\\\"));
                File decOfAgeFile = new File(pathToDecOfAge.replace("\\", "\\\\"));
                File attestationFile = new File(pathToAttestation.replace("\\", "\\\\"));
                File ninFile = new File(pathToNIN.replace("\\", "\\\\"));
                File rootOfTitleFile = new File(pathToRootOfTitle.replace("\\", "\\\\"));
                File letterFromDHFile = new File(pathToLetterFromDH.replace("\\", "\\\\"));
                File evidenceFile = new File(pathToEvidence.replace("\\", "\\\\"));
                File courtFile = new File(pathToCourtAffidavit.replace("\\", "\\\\"));
                File taxFile = new File(pathToClearance.replace("\\", "\\\\"));

                if(passportFile.exists()){
                    passportFile.delete();
                }
                if(decOfAgeFile.exists()){
                    decOfAgeFile.delete();
                }
                if(attestationFile.exists()){
                    attestationFile.delete();
                }
                if(ninFile.exists()){
                    ninFile.delete();
                }
                if(rootOfTitleFile.exists()){
                    rootOfTitleFile.delete();
                }
                if(letterFromDHFile.exists()){
                    letterFromDHFile.delete();
                }
                if(evidenceFile.exists()){
                    evidenceFile.delete();
                }
                if(courtFile.exists()){
                    courtFile.delete();
                }
                if(taxFile.exists()){
                    taxFile.delete();
                }

                taxFile = null;
                courtFile = null;
                evidenceFile = null;
                letterFromDHFile = null;
                rootOfTitleFile = null;
                ninFile = null;
                attestationFile = null;
                decOfAgeFile = null;
                passportFile = null;

                clearAllFields();

                AlertMaker.showSimpleAlert("Success", "Application unregistered successfully");

            }
            else {
                AlertMaker.showErrorMessage("Error", "Failed to unregister application");
                return;
            }
            stage.close();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> {
            ((Stage) cancel.getScene().getWindow()).close();
        });
        VBox top = new VBox(3);
        top.getChildren().add(new Label("Confirm Delete"));
        top.getChildren().add(new Label("This will Delete record from database"));
        top.setPadding(new Insets(7));
        layout.setTop(top);
        HBox bottom = new HBox(10);
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().addAll(proceed, cancel);
        proceed.setDefaultButton(true);
        cancel.setCancelButton(true);
        layout.setBottom(bottom);
        layout.setStyle("-fx-padding: 10;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 5;" +
                "-fx-border-insets: 5;");
        stage.setTitle("Confirm Delete");
        stage.initOwner(getView().getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(layout));
        stage.showAndWait();
    }

    private void clearAllFields() {
        getView().getScene().getWindow().hide();
    }

    public LandDetailsView getView(){
        return view;
    }

}
