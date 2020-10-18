package com.jedna.landregistrationsystem.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HelpView extends BorderPane {

    public HelpView(){
        BackgroundImage myBI= new BackgroundImage(new Image(HelpView.class.getResource("/about_bg.jpg")
                .toExternalForm(),600,400,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.setBackground(new Background(myBI));
        makeGUI();
    }

    private void makeGUI(){
        VBox center = new VBox(5);
        ImageView icon = new ImageView(new Image(HelpView.class.getResource("/gov_logo.png").toExternalForm()));
        icon.setSmooth(true);
        icon.setFitWidth(70);
        icon.setFitHeight(70);
        Text appName = new Text("Land Registration system");
        appName.setStyle("-fx-font-weight: bold;");
        appName.setFont(new Font(24));
        appName.setFill(Color.WHITE);
        HBox header = new HBox(10, icon, appName);

        Text line1 = new Text("LAS 2019.1.1");
        line1.setStyle("-fx-font-weight: bold;");
        line1.setFont(new Font(14));
        line1.setFill(Color.WHITE);

        Text line2 = new Text("JRE 1.8.0_131-release-1-b1");
        line2.setStyle("-fx-font-weight: bold;");
        line2.setFont(new Font(12));
        line2.setFill(Color.WHITE);

        Text line3 = new Text("JVM: OpenJDK 64-Bit Server VM by Oracle Corporation");
        line3.setStyle("-fx-font-weight: bold;");
        line3.setFont(new Font(12));
        line3.setFill(Color.WHITE);

        Text line4 = new Text("Powered by Jedna");
        line4.setStyle("-fx-font-weight: bold;");
        line4.setFont(new Font(12));
        line4.setFill(Color.WHITE);

        center.getChildren().addAll(header, line1, line2, line3, line4);
        StackPane layout = new StackPane(center);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(0, 0, 20, 0));
        this.setCenter(layout);

        StackPane bottom = new StackPane();
        bottom.setAlignment(Pos.CENTER);
        VBox bottomVBox = new VBox(5);

        Text line5 = new Text("Simplicity is the soul of efficiency");
        line5.setStyle("-fx-font-weight: bold;");
        line5.setFont(new Font(12));
        line5.setFill(Color.BLACK);

        Text line6 = new Text("© 2019 Jedna Tech. All rights reserved");
        line6.setStyle("-fx-font-weight: bold;");
        line6.setFont(new Font(12));
        line6.setFill(Color.BLACK);

        bottomVBox.getChildren().addAll(line5, line6);
        bottom.getChildren().add(bottomVBox);

        this.setPadding(new Insets(25));
        this.setBottom(bottom);
    }

}
