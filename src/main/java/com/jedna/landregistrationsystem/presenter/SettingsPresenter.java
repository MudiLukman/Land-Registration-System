package com.jedna.landregistrationsystem.presenter;

import com.jedna.landregistrationsystem.model.Admin;
import com.jedna.landregistrationsystem.util.AlertMaker;
import com.jedna.landregistrationsystem.util.CurrentUser;
import com.jedna.landregistrationsystem.util.DatabaseHelper;
import com.jedna.landregistrationsystem.util.enums.UserType;
import com.jedna.landregistrationsystem.view.SettingsView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsPresenter {

    private SettingsView view;
    private String queryOldPassword;
    private String updatePasswordSql;

    public SettingsPresenter(){
        this.view = new SettingsView();
        addListeners();
    }

    private void addListeners() {
        getView().getCurrentPasswordField().setOnAction(event -> validateInputs());
        getView().getNewPasswordField().setOnAction(event -> validateInputs());
        getView().getConfirmPasswordField().setOnAction(event -> validateInputs());
        getView().getSavePassword().setOnAction(event -> validateInputs());
    }

    private void validateInputs(){
        if(getView().getCurrentPasswordField().getText().trim().equals("")){
            AlertMaker.showErrorMessage("Error", "Old Password Required");
            return;
        }

        if(getView().getNewPasswordField().getText().trim().equals("")){
            AlertMaker.showErrorMessage("Error", "New Password Required");
            return;
        }

        if(getView().getConfirmPasswordField().getText().trim().equals("")){
            AlertMaker.showErrorMessage("Error", "Input Required");
            return;
        }

        if(!getView().getNewPasswordField().getText().equals(getView().getConfirmPasswordField().getText())){
            AlertMaker.showErrorMessage("Error", "New Passwords do not match");
            getView().getNewPasswordField().setText("");
            getView().getConfirmPasswordField().setText("");
            return;
        }

        if(CurrentUser.getUserType() == UserType.ADMIN){
            queryOldPassword = "SELECT * FROM admin where username='Abubakar'";
            updatePasswordSql = "update admin set password='" + Admin.sha1(getView().getNewPasswordField().getText())
                    + "' where username='Abubakar'";
        }
        else {
            queryOldPassword = "SELECT * FROM land_owner where fullname='" + CurrentUser.getLandOwner().getFullname() + "'";
            updatePasswordSql = "update land_owner set password='" + Admin.sha1(getView().getNewPasswordField().getText())
                    + "' where fullname='" + CurrentUser.getLandOwner().getFullname() + "'";
        }

        ResultSet resultSet = DatabaseHelper.executeQuery(queryOldPassword);
        String oldPassword = "";
        String newPassword = getView().getNewPasswordField().getText();
        try {
            resultSet.next();
            oldPassword = resultSet.getString("password");
        }catch (SQLException e){
            System.out.println(e);
        }

        if(!oldPassword.equals(Admin.sha1(getView().getCurrentPasswordField().getText()))){
            AlertMaker.showErrorMessage("Error", "Wrong Password");
            return;
        }else {
            if(DatabaseHelper.insert_record(updatePasswordSql) != 0){
                AlertMaker.showSimpleAlert("Success", "Password Updated");
                getView().getScene().getWindow().hide();
            }
        }

    }
    public SettingsView getView(){
        return this.view;
    }

}
