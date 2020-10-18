package com.jedna.landregistrationsystem.presenter;

import com.jedna.landregistrationsystem.App;
import com.jedna.landregistrationsystem.model.Admin;
import com.jedna.landregistrationsystem.util.AlertMaker;
import com.jedna.landregistrationsystem.util.DatabaseHelper;
import com.jedna.landregistrationsystem.util.KeyGenerator;
import com.jedna.landregistrationsystem.util.enums.Gender;
import com.jedna.landregistrationsystem.view.RegisterLandOwnerView;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class RegisterLandOwnerPresenter {

    private RegisterLandOwnerView view;
    private ObservableMap<String, File> docFilesMap;

    public RegisterLandOwnerPresenter(){
        view = new RegisterLandOwnerView();
        docFilesMap = FXCollections.observableHashMap();
        attachEventListeners();
    }

    private void attachEventListeners() {
        getView().getFullnameField().setOnAction(this::handleFullnameAction);
        getView().getPasswordField().setOnAction(this::handlePasswordAction);
        getView().getConfirmPasswordField().setOnAction(this::handleConfirmPasswordAction);
        getView().getPhoneNumberField().setOnAction(this::handlePhoneNumberAction);
        getView().getAddress().setOnAction(this::handleAddressAction);
        getView().getOccupation().setOnAction(this::handleOccupationAction);
        getView().getPassportBtn().setOnAction(event -> handleChoosePassport());
        getView().getPassportView().setOnMouseClicked(event -> handleViewPassport());
        getView().getBirthCertBtn().setOnAction(event -> handleChangeDecOfAge());
        getView().getBirthCertView().setOnMouseClicked(event -> handleViewDecOfAge());
        getView().getAttestationBtn().setOnAction(event -> handleChangeAttestation());
        getView().getAttestationView().setOnMouseClicked(event -> handleViewAttestation());
        getView().getNinBtn().setOnAction(event -> handleChangeNINCard());
        getView().getNinView().setOnMouseClicked(event -> handleViewNIN());
        getView().getRegisterButton().setOnAction(this::handleRegisterClicked);
        getView().getBackButton().setOnAction(this::handleBackPressed);

        docFilesMap.addListener(this::handleMapValueChange);
    }

    private void handleMapValueChange(Observable map){
        if(!docFilesMap.containsKey("declarationOfAge")){
            getView().getBirthCertView().setVisible(false);
        }
        else {
            getView().getBirthCertView().setVisible(true);
        }
        if(!docFilesMap.containsKey("attestationLetter")){
            getView().getAttestationView().setVisible(false);
        }
        else {
            getView().getAttestationView().setVisible(true);
        }
        if(!docFilesMap.containsKey("ninCard")){
            getView().getNinView().setVisible(false);
        }
        else {
            getView().getNinView().setVisible(true);
        }
        if(!docFilesMap.containsKey("passport")){
            getView().getPassportView().setVisible(false);
        }
        else {
            getView().getPassportView().setVisible(true);
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

    private void handleChangeNINCard(){
        chooseImageFile("ninCard");
    }

    private void handleViewNIN(){
        try {
            showDocumentInWindow(new Image(docFilesMap.get("ninCard").toURI().toURL().toExternalForm()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void handleChangeAttestation(){
        chooseImageFile("attestationLetter");
    }

    private void handleViewAttestation(){
        try {
            showDocumentInWindow(new Image(docFilesMap.get("attestationLetter").toURI().toURL().toExternalForm()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void handleChangeDecOfAge(){
        chooseImageFile("declarationOfAge");
    }

    private void handleViewDecOfAge(){
        try {
            showDocumentInWindow(new Image(docFilesMap.get("declarationOfAge").toURI().toURL().toExternalForm()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void handleChoosePassport(){
        chooseImageFile("passport");
    }

    private void handleViewPassport(){
        try {
            showDocumentInWindow(new Image(docFilesMap.get("passport").toURI().toURL().toExternalForm()));
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

    private void handleFullnameAction(ActionEvent event){
        getView().getPasswordField().requestFocus();
    }

    private void handlePasswordAction(ActionEvent event){
        getView().getConfirmPasswordField().requestFocus();
    }

    private void handleConfirmPasswordAction(ActionEvent event){
        getView().getPhoneNumberField().requestFocus();
    }

    private void handleAddressAction(ActionEvent event){
        getView().getOccupation().requestFocus();
    }

    private void handleOccupationAction(ActionEvent event){
        getView().getPassportBtn().requestFocus();
    }

    private void handlePhoneNumberAction(ActionEvent event){
        handleRegisterClicked(event);
    }

    private void handleRegisterClicked(ActionEvent event) {
        if(!fieldsValidation()){
            return;
        }

        String username = getView().getFullnameField().getText();
        String password = getView().getPasswordField().getText();
        String phoneNumber = getView().getPhoneNumberField().getText();
        Gender gender = getView().getGender().getSelectedToggle() == getView().getGender().getToggles().get(0) ?
                Gender.MALE : Gender.FEMALE;
        String address = getView().getAddress().getText();
        String occupation = getView().getOccupation().getText();
        Image birthCertImage = null;
        Image attestationImage = null;
        Image ninCardImage = null;
        Image passportImage = null;

        try {
            birthCertImage = new Image(docFilesMap.get("declarationOfAge").toURI().toURL().toExternalForm());
            attestationImage = new Image(docFilesMap.get("attestationLetter").toURI().toURL().toExternalForm());
            ninCardImage = new Image(docFilesMap.get("ninCard").toURI().toURL().toExternalForm());
            passportImage = new Image(docFilesMap.get("passport").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //save land owner details
        String landOwnerId = KeyGenerator.generateKey();
        String ownerDocsPrefix = "C:\\landregsystem\\land_owners\\" + landOwnerId + "\\";

        File passportFile = new File(ownerDocsPrefix + "passport.jpg");
        File birthCertFile = new File(ownerDocsPrefix + "declarationOfAge.jpg");
        File attestationFile = new File(ownerDocsPrefix + "attestationLetter.jpg");
        File ninCardFile = new File(ownerDocsPrefix + "ninCard.jpg");

        if(!passportFile.exists()){
            passportFile.mkdirs();
        }
        if(!birthCertFile.exists()){
            birthCertFile.mkdirs();
        }
        if(!attestationFile.exists()){
            attestationFile.mkdirs();
        }
        if(!ninCardFile.exists()){
            ninCardFile.mkdirs();
        }

        String format = "JPG";
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(passportImage, null), format, passportFile);
            ImageIO.write(SwingFXUtils.fromFXImage(birthCertImage, null), format, birthCertFile);
            ImageIO.write(SwingFXUtils.fromFXImage(attestationImage, null), format, attestationFile);
            ImageIO.write(SwingFXUtils.fromFXImage(ninCardImage, null), format, ninCardFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(DatabaseHelper.insertLandOwner(landOwnerId, username, Admin.sha1(password), phoneNumber, gender, address, passportFile.getAbsolutePath(),
                birthCertFile.getAbsolutePath(), occupation, attestationFile.getAbsolutePath(), ninCardFile.getAbsolutePath()) != 0){
            AlertMaker.showSimpleAlert("Done", "New user has been added successfully");
            getView().getScene().getWindow().hide();
        }
        else {
            AlertMaker.showSimpleAlert("Error", "Unable to create user");
        }

        passportFile = null;
        birthCertFile = null;
        attestationFile = null;
        ninCardFile = null;
    }

    private boolean fieldsValidation() {
        if(getView().getFullnameField().getText().equals("") || getView().getPasswordField().getText().equals("") ||
                getView().getConfirmPasswordField().getText().equals("") || getView().getPhoneNumberField().getText().equals("") ||
                getView().getGender().getSelectedToggle() == null || getView().getAddress().getText().equals("") ||
                getView().getOccupation().getText().equals("")){
            AlertMaker.showSimpleAlert("Error", "All fields must be filled");
            return false;
        }

        if(docFilesMap.size() < 4){
            AlertMaker.showErrorMessage("Error", "All Documents must be provided");
            return false;
        }

        if(!getView().getPasswordField().getText().equals(getView().getConfirmPasswordField().getText())){
            AlertMaker.showSimpleAlert("Error", "Both passwords must be the same");
            return false;
        }
        return true;
    }

    private void handleBackPressed(ActionEvent event){
        getView().getScene().getWindow().hide();
    }

    public RegisterLandOwnerView getView(){
        return view;
    }

}
