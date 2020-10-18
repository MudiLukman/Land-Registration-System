package com.jedna.landregistrationsystem.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.*;

public class SettingsView extends VBox {

    private PasswordField currentPasswordField;
    private PasswordField newPasswordField;
    private PasswordField confirmPasswordField;
    private Button savePassword;

    public SettingsView(){
        currentPasswordField = new PasswordField();
        newPasswordField = new PasswordField();
        confirmPasswordField = new PasswordField();
        savePassword = new Button("Save");
        makeSettingsUI();
    }

    private void makeSettingsUI() {
        HBox lineOne = new HBox(5, new Label("Old Password:"), currentPasswordField);
        HBox lineTwo = new HBox(5, new Label("New Password:"), newPasswordField);
        HBox lineThree = new HBox(5, new Label("Confirm Password:"), confirmPasswordField);
        Pane spacer = new Pane();
        HBox line4 = new HBox(5, spacer, savePassword);
        HBox.setHgrow(spacer, Priority.SOMETIMES);

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20));
        this.setSpacing(5);
        this.getChildren().addAll(new Label("Change Password"), lineOne, lineTwo, lineThree, line4);
    }

    public PasswordField getCurrentPasswordField() {
        return currentPasswordField;
    }

    public PasswordField getNewPasswordField() {
        return newPasswordField;
    }

    public PasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public Button getSavePassword() {
        return savePassword;
    }
}
