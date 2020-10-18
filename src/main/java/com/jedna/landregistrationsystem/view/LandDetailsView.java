package com.jedna.landregistrationsystem.view;

import com.jedna.landregistrationsystem.util.CurrentLand;
import com.jedna.landregistrationsystem.util.CustomImageView;
import com.jedna.landregistrationsystem.util.enums.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LandDetailsView extends BorderPane {

    private ImageView ownerPassportView;
    private Hyperlink choosePassportLink;
    private ImageView deleteApplication;
    private Text formTitle;
    private Text titleId;
    private TextField ownerNameField;
    private PasswordField ownerPassword;
    private ChoiceBox<Gender> genderChoiceBox;
    private TextField phoneField;
    private TextField address;
    private TextField occupation;
    private Button decAgeButton;
    private CustomImageView decAgeView;
    private Button attestationButton;
    private CustomImageView attestationView;
    private Button ninCardButton;
    private CustomImageView ninCardView;
    private ChoiceBox<Status> statusChoiceBox;
    private ChoiceBox<Stages> stageChoiceBox;
    private TextField latitudeField;
    private TextField longitudeField;
    private Button printCertButton;
    private TextField dimensionField;
    private ChoiceBox<Mediums> mediumChoiceBox;
    private ChoiceBox<Activities> activityChoiceBox;
    private Button rootOfTitleButton;
    private CustomImageView rootOfTitleView;
    private Button letterFromDistrictHEadButton;
    private CustomImageView letterFromDistrictHeadView;
    private Button evidenceOfOwnershipButton;
    private CustomImageView evidenceOfOwnershipView;
    private Button courtAffidavitButton;
    private CustomImageView courtAffidavitView;
    private Button taxClearanceButton;
    private CustomImageView taxClearanceView;
    private Button mapViewButton;
    private Label issuesLabel;
    private TextArea issuesTextArea;
    private Button submitApplication;
    private Button cancelApplication;
    private Button updateApplication;
    private VBox centerLeftFraction;

    //text field and btn below
    //will not be added to view but to dialog to note down
    //issues. its here so listeners could be set in the presenter,
    //allowing them to access fields
    private TextField reasonTextField;
    private Button saveReason;

    public LandDetailsView(){
        ownerPassportView = new ImageView(new Image(LandDetailsView.class.getResource("/profile_black.jpg").toExternalForm()));
        ownerPassportView.setSmooth(true);
        ownerPassportView.setFitWidth(70);
        ownerPassportView.setFitHeight(70);
        choosePassportLink = new Hyperlink("Choose");
        deleteApplication = new ImageView(new Image(LandDetailsView.class.getResource("/bin_black.png").toExternalForm()));
        deleteApplication.setSmooth(true);
        deleteApplication.setFitHeight(25);
        deleteApplication.setFitWidth(25);
        deleteApplication.setPickOnBounds(true);
        formTitle = new Text("Land Record");
        formTitle.setFont(new Font(24));
        titleId = new Text("21028263");
        titleId.setFont(new Font(20));
        ownerNameField = new TextField();
        ownerNameField.setPrefColumnCount(15);
        genderChoiceBox = new ChoiceBox<>();
        genderChoiceBox.setPrefWidth(190);
        genderChoiceBox.getItems().addAll(Gender.MALE, Gender.FEMALE);
        phoneField = new TextField();
        phoneField.setPrefColumnCount(15);
        address = new TextField();
        address.setPrefColumnCount(14);
        occupation = new TextField();
        occupation.setPrefColumnCount(occupation.getPrefColumnCount() + 1);
        decAgeButton = new Button("Change");
        decAgeButton.setPrefWidth(135);
        decAgeView = new CustomImageView();
        attestationButton = new Button("Change");
        attestationButton.setPrefWidth(105);
        attestationView = new CustomImageView();
        ninCardButton = new Button("Change");
        ninCardButton.setPrefWidth(150);
        ninCardView = new CustomImageView();
        statusChoiceBox = new ChoiceBox<>();
        statusChoiceBox.setPrefWidth(180);
        statusChoiceBox.getItems().addAll(Status.VALID, Status.REVOKED);
        statusChoiceBox.getSelectionModel().selectFirst();
        stageChoiceBox = new ChoiceBox<>();
        stageChoiceBox.setPrefWidth(180);
        stageChoiceBox.getItems().addAll(Stages.PENDING, Stages.APPROVED, Stages.REJECTED);
        stageChoiceBox.getSelectionModel().selectFirst();
        latitudeField = new TextField();
        latitudeField.setPrefColumnCount(14);
        longitudeField = new TextField();
        longitudeField.setPrefColumnCount(13);
        printCertButton = new Button("Print Certificate");
        dimensionField = new TextField();
        mediumChoiceBox = new ChoiceBox<>();
        mediumChoiceBox.setPrefWidth(160);
        mediumChoiceBox.getItems().addAll(Mediums.GOVERNMENT, Mediums.NATIVES, Mediums.INHERITANCE);
        activityChoiceBox = new ChoiceBox<>();
        activityChoiceBox.setPrefWidth(170);
        activityChoiceBox.getItems().addAll(Activities.RESIDENTIAL, Activities.COMMERCIAL);
        rootOfTitleButton = new Button("Change");
        rootOfTitleButton.setPrefWidth(123);
        rootOfTitleView = new CustomImageView();
        letterFromDistrictHEadButton = new Button("Change");
        letterFromDistrictHeadView = new CustomImageView();
        evidenceOfOwnershipButton = new Button("Change");
        evidenceOfOwnershipButton.setPrefWidth(70);
        evidenceOfOwnershipView = new CustomImageView();
        courtAffidavitButton = new Button("Change");
        courtAffidavitButton.setPrefWidth(117);
        courtAffidavitView = new CustomImageView();
        taxClearanceButton = new Button("Change");
        taxClearanceButton.setPrefWidth(90);
        taxClearanceView = new CustomImageView();
        ImageView markerIcon = new ImageView(new Image(LandDetailsView.class.getResource("/marker_black.png").toExternalForm()));
        markerIcon.setFitWidth(25);
        markerIcon.setFitHeight(25);
        mapViewButton = new Button("View in Map", markerIcon);
        issuesLabel = new Label("Issues:");
        issuesTextArea = new TextArea();
        issuesTextArea.setPrefColumnCount(15);
        issuesTextArea.setPrefRowCount(17);
        issuesTextArea.setWrapText(true);
        issuesTextArea.setEditable(false);
        submitApplication = new Button("Submit");
        cancelApplication = new Button("Cancel");
        updateApplication = new Button("Update");
        reasonTextField = new TextField();
        saveReason = new Button("Save");
        
        designUI();
    }

    private void designUI() {
        //make top section
        HBox topSection = new HBox(10);
        topSection.setPadding(new Insets(10));
        Pane spaceBtwLogAndTitle = new Pane();
        Pane spaceBtwTitleAndPassport = new Pane();
        HBox.setHgrow(spaceBtwLogAndTitle, Priority.SOMETIMES);
        HBox.setHgrow(spaceBtwTitleAndPassport, Priority.SOMETIMES);
        ImageView govLogo = new ImageView(new Image(LandDetailsView.class.getResource("/gov_logo.png")
                .toExternalForm()));
        govLogo.setFitHeight(70);
        govLogo.setFitWidth(80);
        govLogo.setSmooth(true);
        VBox photoVBox = new VBox(1, ownerPassportView, choosePassportLink);
        photoVBox.setAlignment(Pos.CENTER);
        VBox topCenter = new VBox(10, formTitle, titleId);
        topCenter.setAlignment(Pos.CENTER);
        topSection.getChildren().addAll(govLogo, spaceBtwLogAndTitle, topCenter, spaceBtwTitleAndPassport,
                photoVBox, deleteApplication);
        this.setTop(topSection);

        //make center
        Pane spaceBtwFirstFieldsAndSecond = new Pane();
        Pane spaceBtwSecondAndThirdFields = new Pane();
        HBox.setHgrow(spaceBtwFirstFieldsAndSecond, Priority.SOMETIMES);
        HBox.setHgrow(spaceBtwSecondAndThirdFields, Priority.SOMETIMES);
        HBox centerAsRow = new HBox(10);
        VBox centerLeftVBox = new VBox(5);
        centerLeftVBox.setPadding(new Insets(4));
        centerLeftVBox.setStyle("-fx-border-radius: 5;" +
                "-fx-border-color: rgb(115, 115, 115);" +
                "-fx-border-width: 2;" +
                "-fx-padding: 20;");
        centerLeftVBox.getChildren().addAll(new HBox(5, new Label("Name:"), ownerNameField),
                new HBox(5, new Label("Sex:"), genderChoiceBox),
                new HBox(5, new Label("Phone:"), phoneField),
                new HBox(5, new Label("Address:"), address),
                new HBox(5, new Label("Occupation:"), occupation),
                new HBox(5, new Label("Birth Certificate:"), decAgeButton, decAgeView),
                new HBox(5, new Label("Attestation Letter:"), attestationButton, attestationView),
                new HBox(5, new Label("NIN Card:"), ninCardButton, ninCardView));
        centerLeftFraction = new VBox(5);
        centerLeftFraction.setPadding(new Insets(4));
        centerLeftFraction.setStyle("-fx-border-radius: 5;" +
                "-fx-border-color: rgb(115, 115, 115);" +
                "-fx-border-width: 2;" +
                "-fx-padding: 20;");
        centerLeftFraction.getChildren().addAll(new HBox(5, new Label("Status:"), statusChoiceBox),
                new HBox(5, new Label("Stage:"), stageChoiceBox));
        VBox centerLeftBoth = new VBox(10);
        centerLeftBoth.getChildren().addAll(centerLeftVBox, centerLeftFraction);
        VBox centerCenterTop = new VBox(5);
        centerCenterTop.setPadding(new Insets(4));
        centerCenterTop.setStyle("-fx-border-radius: 5;" +
                "-fx-border-color: rgb(115, 115, 115);" +
                "-fx-border-width: 2;" +
                "-fx-padding: 20;");
        centerCenterTop.getChildren().addAll(new HBox(5, new Label("Dimension:"), dimensionField),
                new HBox(5, new Label("Medium:"), mediumChoiceBox),
                new HBox(5, new Label("Activity:"), activityChoiceBox));
        VBox centerCenterBottom = new VBox(5);
        centerCenterBottom.setPadding(new Insets(4));
        centerCenterBottom.setStyle("-fx-border-radius: 5;" +
                "-fx-border-color: rgb(115, 115, 115);" +
                "-fx-border-width: 2;" +
                "-fx-padding: 20;");
        centerCenterBottom.getChildren().addAll(new HBox(5, new Label("Root of Title:"), rootOfTitleButton, rootOfTitleView),
                new HBox(5, new Label("Letter From District Head:"), letterFromDistrictHEadButton, letterFromDistrictHeadView),
                new HBox(5, new Label("Evidence of Ownership:"), evidenceOfOwnershipButton, evidenceOfOwnershipView),
                new HBox(5, new Label("Court Affidavit:"), courtAffidavitButton, courtAffidavitView),
                new HBox(5, new Label("Tax Clearance Cert.:"), taxClearanceButton, taxClearanceView));
        VBox centerCenterBoth = new VBox(10);
        centerCenterBoth.getChildren().addAll(centerCenterTop, centerCenterBottom);
        Pane spaceOnTopIssues = new Pane();
        VBox centerRightBoth = new VBox(spaceOnTopIssues, issuesLabel , issuesTextArea);
        centerRightBoth.setPadding(new Insets(7));
        centerAsRow.getChildren().addAll(centerLeftBoth, spaceBtwFirstFieldsAndSecond,
                centerCenterBoth, spaceBtwSecondAndThirdFields, centerRightBoth);
        centerAsRow.setPadding(new Insets(7));
        this.setCenter(centerAsRow);

        //make bottom
        BorderPane bottomSection = new BorderPane();
        VBox locationVBox = new VBox(5);
        locationVBox.setPadding(new Insets(4));
        locationVBox.setStyle("-fx-border-radius: 5;" +
                "-fx-border-color: rgb(115, 115, 115);" +
                "-fx-border-width: 2;" +
                "-fx-padding: 20;");
        locationVBox.getChildren().addAll(new Label("Location"), new HBox(5, new Label("Latitude:"), latitudeField),
                new HBox(5, new Label("Longitude:"), longitudeField));
        VBox bottomLeftVBox = new VBox(5, locationVBox, printCertButton);
        printCertButton.setStyle("-fx-background-color: #090a0c, linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%), " +
                "linear-gradient(#20262b, #191d22), radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));" +
                "-fx-background-radius: 5,4,3,5;" +
                "-fx-background-insets: 0,1,2,0;" +
                "-fx-text-fill: white;" +
                "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                "-fx-font-family: Arial;" +
                "-fx-text-fill: linear-gradient(white, #d0d0d0);" +
                "-fx-font-size: 12px;" +
                "-fx-padding: 10 20 10 20;");
        bottomSection.setLeft(bottomLeftVBox);
        bottomSection.setCenter(mapViewButton);
        mapViewButton.setPrefHeight(50);
        mapViewButton.setPrefWidth(200);
        mapViewButton.setFont(new Font(20));
        VBox bottomRightButtonsAndSpacer = new VBox();
        bottomRightButtonsAndSpacer.setPadding(new Insets(0, 0, 10, 0));
        Pane spacer = new Pane();
        HBox bottomRightButtons = new HBox(5);
        bottomRightButtons.getChildren().addAll(submitApplication, updateApplication, cancelApplication);
        bottomRightButtonsAndSpacer.getChildren().addAll(spacer, bottomRightButtons);
        VBox.setVgrow(spacer, Priority.ALWAYS);
        bottomSection.setRight(bottomRightButtonsAndSpacer);
        bottomSection.setPadding(new Insets(4));
        this.setBottom(bottomSection);

        this.setPadding(new Insets(5));
    }

    public ImageView getOwnerPassportView() {
        return ownerPassportView;
    }

    public Hyperlink getChoosePassportLink() {
        return choosePassportLink;
    }

    public ImageView getDeleteApplication() {
        return deleteApplication;
    }

    public Text getFormTitle() {
        return formTitle;
    }

    public TextField getOwnerNameField() {
        return ownerNameField;
    }

    public ChoiceBox<Gender> getGenderChoiceBox() {
        return genderChoiceBox;
    }

    public TextField getPhoneField() {
        return phoneField;
    }

    public TextField getAddress() {
        return address;
    }

    public TextField getOccupation() {
        return occupation;
    }

    public Button getDecAgeButton() {
        return decAgeButton;
    }

    public CustomImageView getDecAgeView() {
        return decAgeView;
    }

    public Button getAttestationButton() {
        return attestationButton;
    }

    public CustomImageView getAttestationView() {
        return attestationView;
    }

    public Button getNinCardButton() {
        return ninCardButton;
    }

    public CustomImageView getNinCardView() {
        return ninCardView;
    }

    public ChoiceBox<Status> getStatusChoiceBox() {
        return statusChoiceBox;
    }

    public ChoiceBox<Stages> getStageChoiceBox() {
        return stageChoiceBox;
    }

    public TextField getLatitudeField() {
        return latitudeField;
    }

    public TextField getLongitudeField() {
        return longitudeField;
    }

    public Button getPrintCertButton() {
        return printCertButton;
    }

    public TextField getDimensionField() {
        return dimensionField;
    }

    public ChoiceBox<Mediums> getMediumChoiceBox() {
        return mediumChoiceBox;
    }

    public ChoiceBox<Activities> getActivityChoiceBox() {
        return activityChoiceBox;
    }

    public Button getRootOfTitleButton() {
        return rootOfTitleButton;
    }

    public CustomImageView getRootOfTitleView() {
        return rootOfTitleView;
    }

    public Button getLetterFromDistrictHEadButton() {
        return letterFromDistrictHEadButton;
    }

    public CustomImageView getLetterFromDistrictHeadView() {
        return letterFromDistrictHeadView;
    }

    public Button getEvidenceOfOwnershipButton() {
        return evidenceOfOwnershipButton;
    }

    public CustomImageView getEvidenceOfOwnershipView() {
        return evidenceOfOwnershipView;
    }

    public Button getCourtAffidavitButton() {
        return courtAffidavitButton;
    }

    public CustomImageView getCourtAffidavitView() {
        return courtAffidavitView;
    }

    public Button getTaxClearanceButton() {
        return taxClearanceButton;
    }

    public CustomImageView getTaxClearanceView() {
        return taxClearanceView;
    }

    public Button getMapViewButton() {
        return mapViewButton;
    }

    public Button getSubmitApplication() {
        return submitApplication;
    }

    public Button getCancelApplication() {
        return cancelApplication;
    }

    public Button getUpdateApplication() {
        return updateApplication;
    }

    public Text getTitleId() {
        return titleId;
    }

    public TextArea getIssuesTextArea() {
        return issuesTextArea;
    }

    public VBox getCenterLeftFraction() {
        return centerLeftFraction;
    }

    public TextField getReasonTextField() {
        return reasonTextField;
    }

    public Button getSaveReason() {
        return saveReason;
    }

    public Label getIssuesLabel() {
        return issuesLabel;
    }
}
