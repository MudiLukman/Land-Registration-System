package com.jedna.landregistrationsystem.presenter;

import com.jedna.landregistrationsystem.App;
import com.jedna.landregistrationsystem.model.Admin;
import com.jedna.landregistrationsystem.model.LandOwner;
import com.jedna.landregistrationsystem.util.AlertMaker;
import com.jedna.landregistrationsystem.util.CurrentUser;
import com.jedna.landregistrationsystem.util.DatabaseHelper;
import com.jedna.landregistrationsystem.util.enums.Gender;
import com.jedna.landregistrationsystem.util.enums.UserType;
import com.jedna.landregistrationsystem.view.LoginView;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.stage.Modality;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPresenter {

    private LoginView view;

    public LoginPresenter(){
        view = new LoginView();
        addEventListeners();
    }

    private void addEventListeners() {
        getView().getExitButton().setOnAction(LoginPresenter::handleExitPressed);
        getView().getUsernameField().setOnAction(this::handleUsernameAction);
        getView().getPasswordField().setOnAction(this::handlePasswordAction);
        getView().getLoginButton().setOnAction(this::handleLoginClicked);
        getView().getRegisterButton().setOnAction(this::handleRegisterClicked);
        getView().getSignInAdminButton().setOnAction(this::handleSignInAdminClicked);
    }

    private static void handleExitPressed(ActionEvent event){
        System.exit(0);
    }

    private void handleUsernameAction(ActionEvent event){
        getView().getPasswordField().requestFocus();
    }

    private void handlePasswordAction(ActionEvent event){
        handleLoginClicked(event);
    }

    private void handleLoginClicked(ActionEvent event){
        if(!validateFields()){
            return;
        }
        getUserFromDb();
    }

    private boolean validateFields() {
        if(getView().getUsernameField().getText().equals("") || getView().getPasswordField().getText().equals("")){
            AlertMaker.showSimpleAlert("Error", "All fields must be entered");
            return false;
        }

        return true;
    }

    private void handleRegisterClicked(ActionEvent event){
        App.loadWindow("New Land Owner", Modality.APPLICATION_MODAL, new RegisterLandOwnerPresenter().getView());
    }

    private void handleSignInAdminClicked(ActionEvent event){
        App.switchWindow(getView(), new SignInAdminPresenter().getView(), "Admin Sign In");
    }

    public LoginView getView(){
        return view;
    }

    public void getUserFromDb() {
        String sql = "SELECT * FROM land_owner WHERE fullname='" + getView().getUsernameField().getText() + "'";
        ResultSet resultSet = DatabaseHelper.executeQuery(sql);

        try {
            while(resultSet.next()){
                if(getView().getUsernameField().getText().equals(resultSet.getString("fullname")) &&
                        Admin.sha1(getView().getPasswordField().getText()).equals(resultSet.getString("password"))){

                    String ownerId = resultSet.getString("owner_id");
                    String fullname = resultSet.getString("fullname");
                    Gender gender = resultSet.getString("gender").equalsIgnoreCase("Male")
                            ? Gender.MALE : Gender.FEMALE;
                    String phone = resultSet.getString("phone");
                    String address = resultSet.getString("address");
                    String occupation = resultSet.getString("occupation");
                    String passportPath = resultSet.getString("passport");
                    passportPath = passportPath.replace("\\", "\\\\");
                    String birthCertPath = resultSet.getString("dec_of_age");
                    birthCertPath = birthCertPath.replace("\\", "\\\\");
                    String attestationPath = resultSet.getString("attestation_letter");
                    attestationPath = attestationPath.replace("\\", "\\\\");
                    String ninPath = resultSet.getString("nin_card");
                    ninPath = ninPath.replace("\\", "\\\\");

                    File passportFile = new File(passportPath);
                    File birthCertFile = new File(birthCertPath);
                    File attestationFile = new File(attestationPath);
                    File ninFile = new File(ninPath);

                    Image passportImage = new Image(passportFile.toURI().toURL().toExternalForm());
                    Image birthCertImage = new Image(birthCertFile.toURI().toURL().toExternalForm());
                    Image attestationImage = new Image(attestationFile.toURI().toURL().toExternalForm());
                    Image ninImage = new Image(ninFile.toURI().toURL().toExternalForm());

                    LandOwner landOwner = new LandOwner(ownerId, fullname, gender, phone, address, passportImage,
                            birthCertImage, occupation, attestationImage, ninImage);

                    CurrentUser.setLandOwner(landOwner);
                    CurrentUser.setUserType(UserType.LAND_OWNER);
                    App.switchWindow(getView(), new HomePresenter().getView(), "User: " + getView().getUsernameField().getText());
                }
                else {
                    AlertMaker.showSimpleAlert("Error", "No such user found");
                }
            }
        } catch (SQLException e) {
            AlertMaker.showErrorMessage("Error", e.getMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
