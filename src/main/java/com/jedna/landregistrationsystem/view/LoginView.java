package com.jedna.landregistrationsystem.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LoginView extends BorderPane {

    private JFXButton exitButton;
    private JFXTextField usernameField;
    private JFXPasswordField passwordField;
    private JFXButton loginButton;
    private JFXButton registerButton;
    private JFXButton signInAdminButton;

    public LoginView(){
        exitButton = new JFXButton("EXIT");
        usernameField = new JFXTextField();
        passwordField = new JFXPasswordField();
        loginButton = new JFXButton("LOGIN");
        registerButton = new JFXButton("REGISTER");
        signInAdminButton = new JFXButton("SIGN IN");

        designGUI();
    }

    private void designGUI() {
        //make top
        Label appName = new Label("LAND REGISTRATION SYSTEM");
        appName.setFont(new Font("Bauhaus 93", 26));
        appName.setTextFill(Color.WHITE);
        exitButton.setButtonType(JFXButton.ButtonType.RAISED);
        exitButton.setPrefHeight(30);
        exitButton.setPrefWidth(64);
        exitButton.setCancelButton(true);
        exitButton.setStyle("-fx-background-color: rgb(239, 83, 80);");
        exitButton.setFont(new Font("System Bold", 18));
        HBox top = new HBox(20);
        top.setPadding(new Insets(30));
        top.setStyle("-fx-background-color: rgb(21, 21, 21);");
        Pane spaceInHeader = new Pane();
        top.getChildren().addAll(appName, spaceInHeader, exitButton);
        HBox.setHgrow(spaceInHeader, Priority.SOMETIMES);
        this.setTop(top);

        //make center
        BorderPane center = new BorderPane();
        VBox centerLeft = new VBox(50);
        centerLeft.setAlignment(Pos.CENTER);
        loginButton.setStyle("-fx-background-color: rgb(0, 200, 83);");
        loginButton.setPrefHeight(40);
        loginButton.setPrefWidth(107);
        loginButton.setDefaultButton(true);
        loginButton.setFont(new Font(14));
        usernameField.requestFocus();
        usernameField.setPromptText("Username");
        usernameField.setLabelFloat(true);
        usernameField.setFocusColor(Color.GREEN);
        usernameField.setUnFocusColor(Color.WHITE);
        usernameField.setStyle("-fx-prompt-text-fill: white;" +
                "-fx-text-fill: white;");
        passwordField.setPromptText("Password");
        passwordField.setLabelFloat(true);
        passwordField.setFocusColor(Color.GREEN);
        passwordField.setUnFocusColor(Color.WHITE);
        passwordField.setStyle("-fx-prompt-text-fill: white;" +
                "-fx-text-fill: white;");
        centerLeft.getChildren().addAll(usernameField, passwordField, loginButton);
        VBox centerRight = new VBox(50);
        Label notAMember = new Label("Not A Member ?");
        notAMember.setTextFill(Color.WHITE);
        notAMember.setFont(new Font(14));
        registerButton.setPrefWidth(107);
        registerButton.setPrefHeight(40);
        registerButton.setStyle("-fx-background-color: rgb(77, 208, 225);");
        registerButton.setFont(new Font(14));
        VBox notAMemberVBox = new VBox(10, notAMember, registerButton);
        notAMemberVBox.setAlignment(Pos.CENTER);
        centerRight.getChildren().add(notAMemberVBox);
        Label areYouAnAdmin = new Label("Are You An Admin ?");
        areYouAnAdmin.setTextFill(Color.WHITE);
        areYouAnAdmin.setFont(new Font(14));
        signInAdminButton.setPrefWidth(107);
        signInAdminButton.setPrefHeight(40);
        signInAdminButton.setFont(new Font(14));
        signInAdminButton.setStyle("-fx-background-color: rgb(251, 192, 45);");
        VBox adminVBox = new VBox(10, areYouAnAdmin, signInAdminButton);
        adminVBox.setAlignment(Pos.CENTER);
        centerRight.getChildren().add(adminVBox);
        center.setLeft(centerLeft);
        center.setRight(centerRight);
        center.setStyle("-fx-background-color: rgb(103, 58, 183);");
        center.setPadding(new Insets(30, 50, 30, 50));
        BorderPane.setMargin(centerLeft, new Insets(0, 10, 0, 20));
        BorderPane.setMargin(centerRight, new Insets(0, 20, 0, 10));
        BorderPane.setAlignment(centerLeft, Pos.CENTER);
        BorderPane.setAlignment(centerRight, Pos.CENTER);
        this.setCenter(center);

        //make bottom
        Label authorText = new Label("LAND REGISTRATION SYSTEM BY MOHAMMED ABUBAKAR");
        authorText.setFont(new Font("System Bold", 12));
        authorText.setTextFill(Color.WHITE);
        StackPane bottom = new StackPane();
        bottom.setPadding(new Insets(30));
        bottom.getChildren().add(authorText);
        bottom.setStyle("-fx-background-color: rgb(21, 21, 21);");
        this.setBottom(bottom);

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

    public JFXButton getLoginButton() {
        return loginButton;
    }

    public JFXButton getRegisterButton() {
        return registerButton;
    }

    public JFXButton getSignInAdminButton() {
        return signInAdminButton;
    }
}
