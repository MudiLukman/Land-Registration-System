package com.jedna.landregistrationsystem.model;

import com.jedna.landregistrationsystem.util.enums.Gender;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class LandOwner {

    private String landOwnerId;
    private StringProperty fullname = new SimpleStringProperty(this, "fullname", null);
    private ObjectProperty<Enum<Gender>> gender = new SimpleObjectProperty<>(this, "gender", null);
    private StringProperty phone = new SimpleStringProperty(this, "phone", null);
    private StringProperty address = new SimpleStringProperty(this, "address", null);
    private Image passport;
    private Image declarationOfAge;
    private StringProperty occupation = new SimpleStringProperty(this, "occupation", null);
    private Image attestationLetter;
    private Image ninCard;

    public LandOwner(String landOwnerId, String fullname, Gender gender, String phone, String address, Image passport,
                     Image declarationOfAge, String occupation, Image attestationLetter, Image ninCard){

        this.landOwnerId = landOwnerId;
        this.fullname.setValue(fullname);
        this.gender.setValue(gender);
        this.phone.setValue(phone);
        this.address.setValue(address);
        this.passport = passport;
        this.declarationOfAge = declarationOfAge;
        this.occupation.setValue(occupation);
        this.attestationLetter = attestationLetter;
        this.ninCard = ninCard;

    }

    public String getFullname() {
        return fullname.get();
    }

    public StringProperty fullnameProperty() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname.set(fullname);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public Image getPassport() {
        return passport;
    }

    public void setPassport(Image passport) {
        this.passport = passport;
    }

    public Image getDeclarationOfAge() {
        return declarationOfAge;
    }

    public void setDeclarationOfAge(Image declarationOfAge) {
        this.declarationOfAge = declarationOfAge;
    }

    public String getOccupation() {
        return occupation.get();
    }

    public StringProperty occupationProperty() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation.set(occupation);
    }

    public Image getAttestationLetter() {
        return attestationLetter;
    }

    public void setAttestationLetter(Image attestationLetter) {
        this.attestationLetter = attestationLetter;
    }

    public Image getNinCard() {
        return ninCard;
    }

    public void setNinCard(Image ninCard) {
        this.ninCard = ninCard;
    }

    public Enum<Gender> getGender() {
        return gender.get();
    }

    public ObjectProperty<Enum<Gender>> genderProperty() {
        return gender;
    }

    public void setGender(Enum<Gender> gender) {
        this.gender.set(gender);
    }

    public String getLandOwnerId() {
        return landOwnerId;
    }

    public void setLandOwnerId(String landOwnerId) {
        this.landOwnerId = landOwnerId;
    }

    @Override
    public String toString() {

        return getFullname();
    }
}
