package com.jedna.landregistrationsystem.presenter;

import com.jedna.landregistrationsystem.App;
import com.jedna.landregistrationsystem.model.Admin;
import com.jedna.landregistrationsystem.util.AlertMaker;
import com.jedna.landregistrationsystem.util.CurrentUser;
import com.jedna.landregistrationsystem.util.DatabaseHelper;
import com.jedna.landregistrationsystem.util.enums.UserType;
import com.jedna.landregistrationsystem.view.SignInAdminView;
import javafx.event.ActionEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInAdminPresenter {

    private SignInAdminView view;

    public SignInAdminPresenter(){
        view = new SignInAdminView();
        attachEventListeners();
    }

    private void attachEventListeners() {
        getView().getExitButton().setOnAction(this::handleExitClicked);
        getView().getUsernameField().setOnAction(this::handleUsernameAction);
        getView().getPasswordField().setOnAction(this::handlePasswordAction);
        getView().getSignInButton().setOnAction(this::handleSignInClicked);
        getView().getBackButton().setOnAction(this::handleBackPressed);
    }

    private void handleSignInClicked(ActionEvent event){

        if(!validateFieldValues()){
            return;
        }
        ResultSet resultSet = DatabaseHelper.getUserNamePassword_admin();

        try {
            if(resultSet.next()){
                if(getView().getUsernameField().getText().equals(resultSet.getString("username")) &&
                        Admin.sha1(getView().getPasswordField().getText()).equals(resultSet.getString("password"))){
                    CurrentUser.setUserType(UserType.ADMIN);
                    App.switchWindow(getView(), new HomePresenter().getView(), "Admin: " + getView().getUsernameField().getText());
                }
                else {
                    AlertMaker.showSimpleAlert("Error", "No such user found");
                }
            }
        } catch (SQLException e) {
            AlertMaker.showErrorMessage("Error", e.getMessage());
        }
    }

    private boolean validateFieldValues() {
        if(getView().getUsernameField().getText().trim().equals("")){
            AlertMaker.showSimpleAlert("Error", "You must enter a username");
            return false;
        }

        if(getView().getPasswordField().getText().equals("")){
            AlertMaker.showSimpleAlert("Error", "You must enter a password");
            return false;
        }

        return true;
    }

    private void handleBackPressed(ActionEvent event){
        App.switchWindow(getView(), new LoginPresenter().getView(), "Login");
    }

    private void handlePasswordAction(ActionEvent event){
        handleSignInClicked(event);
    }

    private void handleUsernameAction(ActionEvent event){
        getView().getPasswordField().requestFocus();
    }

    private void handleExitClicked(ActionEvent event){
        System.exit(0);
    }

    public SignInAdminView getView(){
        return view;
    }
}
