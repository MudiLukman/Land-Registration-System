package com.jedna.landregistrationsystem.view;

import com.jedna.landregistrationsystem.util.LongText;
import com.jedna.landregistrationsystem.util.enums.Activities;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import static com.jedna.landregistrationsystem.util.LongText.note1;

public class CertificateLayoutView extends StackPane {

    private ScrollPane scrollPane;
    private BorderPane innerPane;

    private Text landId;
    private Text ownerName;
    private Text address;
    private Text phone;
    private Text activity;
    private Text dimension;
    private Label approvalDate;
    private ImageView passport;

    public CertificateLayoutView(){
        scrollPane = new ScrollPane();
        innerPane = new BorderPane();

        landId = new Text("38736363");
        landId.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
        ownerName = new Text("Mudison Lukmanistan");
        ownerName.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
        address = new Text("Bosso");
        address.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
        phone = new Text("08166725637");
        phone.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
        activity = new Text("Residential");
        activity.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
        dimension = new Text("100 X 100");
        dimension.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
        approvalDate = new Label("Approved On: ");
        passport = new ImageView(new Image(CertificateLayoutView.class.getResource("/profile_black.jpg").toExternalForm()));
        passport.setFitWidth(80);
        passport.setFitHeight(80);

        makeUI();
    }

    private void makeUI() {
        //make top
        Text text1 = new Text("Federal Republic of Nigeria");
        text1.setFont(Font.font("Times New Roman", FontWeight.BOLD, 11));
        Text text2 = new Text("Second Schedule");
        text2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 11));
        Text text3 = new Text("State Land Law of Nigeria");
        text3.setFont(Font.font("Times New Roman", FontWeight.BOLD, 11));
        Text text4 = new Text("Certificate of Occupancy no. ");
        text4.setFont(Font.font("Times New Roman", FontWeight.BOLD, 11));
        Text text5 = new Text("Federal Republic of Nigeria");
        text5.setFont(Font.font("Times New Roman", FontWeight.BOLD, 11));
        VBox topCenter = new VBox(2, text1, text2, text3, new HBox(2, text4, landId), text5);
        topCenter.setAlignment(Pos.CENTER);
        HBox top = new HBox(10);
        ImageView icon = new ImageView(
                new Image(CertificateLayoutView.class.getResource("/gov_logo.png").toExternalForm()));
        icon.setFitHeight(60);
        icon.setFitWidth(60);
        Pane spacer1 = new Pane();
        Pane spacer2 = new Pane();
        HBox.setHgrow(spacer1, Priority.SOMETIMES);
        HBox.setHgrow(spacer2, Priority.SOMETIMES);
        top.getChildren().addAll(icon, spacer1, topCenter, spacer2, passport);

        //make center
        VBox center = new VBox(10);
        Text text6 = new Text("THIS IS TO CERTIFY THAT: ");
        text6.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
        HBox line1 = new HBox(text6, ownerName);
        Text text7 = new Text("Whose address is: ");
        HBox line2 = new HBox();
        line2.getChildren().addAll(text7, address);
        Text line3 = new Text(note1);
        line3.setWrappingWidth(770);
        Text[] numberings = new Text[6];
        for(int i = 0; i < numberings.length;){
            numberings[i] = new Text(++i + ")");
        }
        HBox note1Box = new HBox(12);
        note1Box.getChildren().addAll(numberings[0], new Text(LongText.line1));
        HBox note2Box = new HBox(12);
        note2Box.getChildren().addAll(numberings[1], new Text(LongText.line2));
        HBox note3Box = new HBox(12);
        HBox activityBox = new HBox(new Text("To use the said land for "), activity, new Text(" purposes."));
        note3Box.getChildren().addAll(numberings[2], activityBox);
        HBox note4Box = new HBox(12);
        note4Box.getChildren().addAll(numberings[3], new Text(LongText.line3));
        HBox note5Box = new HBox(12);
        note5Box.getChildren().addAll(numberings[4], new Text(LongText.line4));

        center.getChildren().addAll(line1, line2, line3, note1Box, note2Box, note3Box, note4Box, note5Box);

        //make bottom
        VBox bottomVBox1 = new VBox(3);
        bottomVBox1.setAlignment(Pos.CENTER);
        Text scheduleText = new Text("SCHEDULE");
        scheduleText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 11));
        HBox plotSize = new HBox(4, new Label("Plot size: "), dimension);
        plotSize.setAlignment(Pos.CENTER);
        bottomVBox1.getChildren().addAll(scheduleText,
                new Label("(DESCRIPTION AND DIMENSION OF PARCEL OF LAND TO WHICH ABOVE CERTIFICATE OF OCCUPANCY RELATES)"),
                plotSize, approvalDate);
        VBox bottomVBox2 = new VBox(50);
        HBox signatureHBox= new HBox();
        signatureHBox.setPadding(new Insets(0, 30, 0, 30));
        Pane spacer3 = new Pane();
        HBox.setHgrow(spacer3, Priority.SOMETIMES);
        VBox signature2 = new VBox(2);
        signature2.getChildren().addAll(new Text("Hon. Commissioner of Lands and Surveys"), new Text("For and On behalf of the Executive Governor of Niger State"));
        signatureHBox.getChildren().addAll(new Text("Director of Lands"), spacer3, signature2);
        bottomVBox2.getChildren().addAll(new Text("Signed, Sealed and delivered by:"), signatureHBox);
        VBox bottom = new VBox();
        bottom.getChildren().addAll(bottomVBox1, bottomVBox2);

        innerPane.setTop(top);
        innerPane.setCenter(center);
        innerPane.setBottom(bottom);
        innerPane.setStyle("-fx-border-radius: 5;" +
                "-fx-border-width: 2;");
        innerPane.setPadding(new Insets(10));
        scrollPane.setContent(innerPane);
        this.getChildren().add(scrollPane);
    }

    public Text getLandId() {
        return landId;
    }

    public Text getOwnerName() {
        return ownerName;
    }

    public Text getAddress() {
        return address;
    }

    public Text getPhone() {
        return phone;
    }

    public Text getActivity() {
        return activity;
    }

    public Text getDimension() {
        return dimension;
    }

    public ImageView getPassport() {
        return passport;
    }

    public Label getApprovalDate() {
        return approvalDate;
    }
}
