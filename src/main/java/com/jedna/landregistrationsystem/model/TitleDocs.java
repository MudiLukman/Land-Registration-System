package com.jedna.landregistrationsystem.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class TitleDocs {

    private String titleDocsId;
    private Image rootOfTitle;
    private Image letterOfConfirmationFromDistrictHead;
    private Image evidenceOfOwnership;
    private Image courtAffidavit;
    private Image taxClearanceCert;

    public TitleDocs(String titleDocsId, Image rootOfTitle, Image letterOfConfirmationFromDistrictHead,
                     Image evidenceOfOwnership, Image courtAffidavit, Image taxClearanceCert) {
        this.titleDocsId = titleDocsId;
        this.rootOfTitle = rootOfTitle;
        this.letterOfConfirmationFromDistrictHead = letterOfConfirmationFromDistrictHead;
        this.evidenceOfOwnership = evidenceOfOwnership;
        this.courtAffidavit = courtAffidavit;
        this.taxClearanceCert = taxClearanceCert;
    }

    public Image getRootOfTitle() {
        return rootOfTitle;
    }

    public void setRootOfTitle(Image rootOfTitle) {
        this.rootOfTitle = rootOfTitle;
    }

    public Image getLetterOfConfirmationFromDistrictHead() {
        return letterOfConfirmationFromDistrictHead;
    }

    public void setLetterOfConfirmationFromDistrictHead(Image letterOfConfirmationFromDistrictHead) {
        this.letterOfConfirmationFromDistrictHead = letterOfConfirmationFromDistrictHead;
    }

    public Image getEvidenceOfOwnership() {
        return evidenceOfOwnership;
    }

    public void setEvidenceOfOwnership(Image evidenceOfOwnership) {
        this.evidenceOfOwnership = evidenceOfOwnership;
    }

    public Image getCourtAffidavit() {
        return courtAffidavit;
    }

    public void setCourtAffidavit(Image courtAffidavit) {
        this.courtAffidavit = courtAffidavit;
    }

    public Image getTaxClearanceCert() {
        return taxClearanceCert;
    }

    public void setTaxClearanceCert(Image taxClearanceCert) {
        this.taxClearanceCert = taxClearanceCert;
    }

    public String getTitleDocsId() {
        return titleDocsId;
    }

    public void setTitleDocsId(String titleDocsId) {
        this.titleDocsId = titleDocsId;
    }
}
