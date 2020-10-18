package com.jedna.landregistrationsystem.view;

import com.jedna.landregistrationsystem.util.CustomImageView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class RegisterLandOwnerView extends BorderPane {

    private JFXTextField fullnameField;
    private JFXPasswordField passwordField;
    private JFXPasswordField confirmPasswordField;
    private JFXTextField phoneNumberField;
    private JFXRadioButton maleBtn;
    private JFXRadioButton femaleBtn;
    private ToggleGroup gender;
    private JFXTextField address;
    private JFXTextField occupation;
    private CustomImageView passportView;
    private Button passportBtn;
    private CustomImageView birthCertView;
    private Button birthCertBtn;
    private CustomImageView attestationView;
    private Button attestationBtn;
    private CustomImageView ninView;
    private Button ninBtn;
    private JFXButton registerButton;
    private JFXButton backButton;

    public RegisterLandOwnerView(){
        fullnameField = new JFXTextField();
        passwordField = new JFXPasswordField();
        confirmPasswordField = new JFXPasswordField();
        phoneNumberField = new JFXTextField();
        maleBtn = new JFXRadioButton("Male");
        femaleBtn = new JFXRadioButton("Female");
        gender = new ToggleGroup();
        address = new JFXTextField();
        occupation = new JFXTextField();
        passportView = new CustomImageView();
        passportBtn = new Button("Choose File");
        birthCertView = new CustomImageView();
        birthCertBtn = new Button("Choose File");
        attestationView = new CustomImageView();
        attestationBtn = new Button("Choose File");
        ninView = new CustomImageView();
        ninBtn = new Button("Choose File");
        registerButton = new JFXButton("REGISTER");
        backButton = new JFXButton("BACK");

        designGUI();
    }

    private void designGUI() {
        StackPane topStackPane = new StackPane();
        topStackPane.setStyle("-fx-background-color: black;");
        Label formName = new Label("New User Registration");
        formName.setFont(new Font("System Bold", 14));
        formName.setTextFill(Color.WHITE);
        topStackPane.setPadding(new Insets(10));
        topStackPane.getChildren().add(formName);
        this.setTop(topStackPane);

        VBox center = new VBox(20);
        fullnameField.setPromptText("Full Name");
        fullnameField.setPadding(new Insets(0, 0, 10, 0));
        fullnameField.setFocusColor(Color.BLUE);
        fullnameField.setLabelFloat(true);
        fullnameField.setPrefWidth(306);
        passwordField.setPromptText("Password");
        passwordField.setLabelFloat(true);
        passwordField.setFocusColor(Color.BLUE);
        passwordField.setPadding(new Insets(0, 0, 10, 0));
        passwordField.setPrefWidth(306);
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setFocusColor(Color.BLUE);
        confirmPasswordField.setLabelFloat(true);
        confirmPasswordField.setPadding(new Insets(0, 0, 10, 0));
        confirmPasswordField.setPrefWidth(306);
        phoneNumberField.setPromptText("Phone Number");
        phoneNumberField.setFocusColor(Color.BLUE);
        phoneNumberField.setLabelFloat(true);
        phoneNumberField.setPadding(new Insets(0, 0, 10, 0));
        phoneNumberField.setPrefWidth(306);
        gender.getToggles().addAll(maleBtn, femaleBtn);
        address.setPromptText("Address");
        address.setPadding(new Insets(0, 0, 10, 0));
        address.setFocusColor(Color.BLUE);
        address.setLabelFloat(true);
        address.setPrefWidth(306);
        occupation.setPromptText("Occupation");
        occupation.setPadding(new Insets(0, 0, 10, 0));
        occupation.setFocusColor(Color.BLUE);
        occupation.setLabelFloat(true);
        occupation.setPrefWidth(306);
        center.setPadding(new Insets(40, 20, 40, 20));
        HBox genderHBox = new HBox(5);
        genderHBox.getChildren().addAll(maleBtn, femaleBtn);
        passportView.setVisible(false);
        birthCertView.setVisible(false);
        attestationView.setVisible(false);
        ninView.setVisible(false);
        center.getChildren().addAll(fullnameField, passwordField, confirmPasswordField, phoneNumberField, genderHBox,
                address, occupation, new HBox(5, new Label("Passport:"), passportBtn, passportView),
                new HBox(5, new Label("Birth Certificate:"), birthCertBtn, birthCertView),
                new HBox(5, new Label("Letter of Attestation:"), attestationBtn, attestationView),
                new HBox(5, new Label("NIN Card:"), ninBtn, ninView));
        this.setCenter(center);

        HBox bottom = new HBox();
        Pane space1 = new Pane();
        Pane space2 = new Pane();
        Pane space3 = new Pane();
        bottom.setPadding(new Insets(10, 0, 10, 0));
        registerButton.setButtonType(JFXButton.ButtonType.RAISED);
        registerButton.setPrefWidth(135);
        registerButton.setPrefHeight(40);
        registerButton.setDefaultButton(true);
        registerButton.setStyle("-fx-background-color: rgb(76, 175, 80);");
        backButton.setButtonType(JFXButton.ButtonType.RAISED);
        backButton.setPrefWidth(135);
        backButton.setPrefHeight(40);
        backButton.setCancelButton(true);
        backButton.setStyle("-fx-background-color: rgb(21, 101, 192);");
        bottom.getChildren().addAll(space1, registerButton, space2, backButton, space3);
        HBox.setHgrow(space1, Priority.SOMETIMES);
        HBox.setHgrow(space2, Priority.SOMETIMES);
        HBox.setHgrow(space3, Priority.SOMETIMES);
        this.setBottom(bottom);
        this.setStyle("-fx-border-radius: 5;" +
                "-fx-border-width: 2;");
    }

    public JFXTextField getFullnameField() {
        return fullnameField;
    }

    public JFXPasswordField getPasswordField() {
        return passwordField;
    }

    public JFXPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public JFXTextField getPhoneNumberField() {
        return phoneNumberField;
    }

    public JFXButton getRegisterButton() {
        return registerButton;
    }

    public JFXButton getBackButton() {
        return backButton;
    }

    public JFXRadioButton getMaleBtn() {
        return maleBtn;
    }

    public JFXRadioButton getFemaleBtn() {
        return femaleBtn;
    }

    public JFXTextField getAddress() {
        return address;
    }

    public JFXTextField getOccupation() {
        return occupation;
    }

    public CustomImageView getPassportView() {
        return passportView;
    }

    public Button getPassportBtn() {
        return passportBtn;
    }

    public CustomImageView getBirthCertView() {
        return birthCertView;
    }

    public Button getBirthCertBtn() {
        return birthCertBtn;
    }

    public CustomImageView getAttestationView() {
        return attestationView;
    }

    public Button getAttestationBtn() {
        return attestationBtn;
    }

    public CustomImageView getNinView() {
        return ninView;
    }

    public Button getNinBtn() {
        return ninBtn;
    }

    public ToggleGroup getGender() {
        return gender;
    }
}
