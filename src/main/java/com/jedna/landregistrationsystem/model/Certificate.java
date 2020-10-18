package com.jedna.landregistrationsystem.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Certificate {

    private StringProperty certificateId = new SimpleStringProperty(this, "certificateId", null);
    private StringProperty landId = new SimpleStringProperty(this, "landId", null);
    private StringProperty approvalDate = new SimpleStringProperty(this, "approvalDate", null);

    public Certificate(String certificateId, String landId, String approvalDate){
        this.certificateId.setValue(certificateId);
        this.landId.setValue(landId);
        this.approvalDate.setValue(approvalDate);
    }

    public String getCertificateId() {
        return certificateId.get();
    }

    public StringProperty certificateIdProperty() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId.set(certificateId);
    }

    public String getLandId() {
        return landId.get();
    }

    public StringProperty landIdProperty() {
        return landId;
    }

    public void setLandId(String landId) {
        this.landId.set(landId);
    }

    public String getApprovalDate() {
        return approvalDate.get();
    }

    public StringProperty approvalDateProperty() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate.set(approvalDate);
    }
}
