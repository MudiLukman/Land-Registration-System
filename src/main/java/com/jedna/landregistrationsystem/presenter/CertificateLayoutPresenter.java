package com.jedna.landregistrationsystem.presenter;

import com.jedna.landregistrationsystem.model.Certificate;
import com.jedna.landregistrationsystem.model.Land;
import com.jedna.landregistrationsystem.util.AlertMaker;
import com.jedna.landregistrationsystem.util.DatabaseHelper;
import com.jedna.landregistrationsystem.view.CertificateLayoutView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CertificateLayoutPresenter {

    private CertificateLayoutView view;
    private Land land;

    public CertificateLayoutPresenter(Land land){
        this.view = new CertificateLayoutView();
        this.land = land;
        setupUI();
    }

    private void setupUI() {
        getView().getLandId().setText(land.getLandId());
        getView().getPassport().setImage(land.getLandOwner().getPassport());
        getView().getOwnerName().setText(land.getLandOwner().getFullname());
        getView().getAddress().setText(land.getLandOwner().getAddress());
        getView().getPhone().setText(land.getLandOwner().getPhone());
        getView().getActivity().setText(land.getActivity().toString());
        getView().getDimension().setText(land.getDimension());

        // read approval date from cert table in db
        String sql = "SELECT * FROM certificate WHERE land_id='" + land.getLandId() + "'";
        ResultSet resultSet = DatabaseHelper.executeQuery(sql);

        try {
            while (resultSet.next()){

                String approvalDate = resultSet.getString("approvaldate");
                getView().getApprovalDate().setText(getView().getApprovalDate().getText() + approvalDate);
            }
        }catch (SQLException e){
            AlertMaker.showErrorMessage("Error", e.getMessage());
        }
    }

    public CertificateLayoutView getView(){
        return view;
    }
}
