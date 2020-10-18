package com.jedna.landregistrationsystem.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SignInAdminView extends BorderPane {

    private JFXButton exitButton;
    private JFXTextField usernameField;
    private JFXPasswordField passwordField;
    private JFXButton signInButton;
    private JFXButton backButton;

    public SignInAdminView(){
        exitButton = new JFXButton("EXIT");
        usernameField = new JFXTextField();
        passwordField = new JFXPasswordField();
        signInButton = new JFXButton("SIGN IN");
        backButton = new JFXButton("BACK");

        designGUI();
    }

    private void designGUI() {
        Label appName = new Label("LAND REGISTRATION SYSTEM");
        appName.setFont(new Font("Bauhaus 93", 26));
        appName.setTextFill(Color.WHITE);
        exitButton.setButtonType(JFXButton.ButtonType.RAISED);
        exitButton.setPrefHeight(30);
        exitButton.setPrefWidth(64);
        exitButton.setStyle("-fx-background-color: rgb(239, 83, 80);");
        exitButton.setFont(new Font("System Bold", 18));
        HBox top = new HBox(20);
        top.setPadding(new Insets(30));
        top.setStyle("-fx-background-color: rgb(21, 21, 21);");
        Pane spaceInHeader = new Pane();
        top.getChildren().addAll(appName, spaceInHeader, exitButton);
        HBox.setHgrow(spaceInHeader, Priority.SOMETIMES);
        this.setTop(top);

        VBox center = new VBox(50);
        HBox lowerCenter = new HBox();
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.SOMETIMES);
        signInButton.setButtonType(JFXButton.ButtonType.RAISED);
        signInButton.setPrefHeight(40);
        signInButton.setPrefWidth(97);
        signInButton.setFont(new Font(14));
        signInButton.setDefaultButton(true);
        signInButton.setStyle("-fx-background-color: rgb(0, 200, 83);" +
                "-fx-background-radius: 20;");
        backButton.setButtonType(JFXButton.ButtonType.RAISED);
        backButton.setPrefHeight(40);
        backButton.setPrefWidth(97);
        backButton.setCancelButton(true);
        backButton.setFont(new Font(14));
        backButton.setStyle("-fx-background-color: rgb(224, 64, 251);" +
                "-fx-background-radius: 20;");
        lowerCenter.getChildren().addAll(signInButton, spacer, backButton);
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(224);
        usernameField.setFocusColor(Color.YELLOW);
        usernameField.setUnFocusColor(Color.WHITE);
        usernameField.setLabelFloat(true);
        usernameField.setPrefHeight(30);
        usernameField.setStyle("-fx-prompt-text-fill: white;" +
                "-fx-text-fill: white;");

        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(224);
        passwordField.setFocusColor(Color.YELLOW);
        passwordField.setUnFocusColor(Color.WHITE);
        passwordField.setLabelFloat(true);
        passwordField.setPrefHeight(30);
        passwordField.setStyle("-fx-prompt-text-fill: white;" +
                "-fx-text-fill: white;");
        center.getChildren().addAll(usernameField, passwordField, lowerCenter);
        center.setPadding(new Insets(40));
        center.setStyle("-fx-background-color: rgb(13, 71, 161);");
        this.setCenter(center);
    }

    public JFXButton getExitButton() {
        return exitButton;
    }

    public JFXTextField getUsernameField() {
        return usernameField;
    }

    public JFXPasswordField getPasswordField() {
        return passwordField;
    }

    public JFXButton getSignInButton() {
        return signInButton;
    }

    public JFXButton getBackButton() {
        return backButton;
    }
}
