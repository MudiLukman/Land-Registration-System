package com.jedna.landregistrationsystem.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class ShowDocumentView extends HBox {
    private ImageView document;

    public ShowDocumentView(){
        document = new ImageView();
        document.setFitHeight(650);
        document.setFitWidth(550);
        document.setSmooth(true);
        this.getChildren().add(document);
    }

    public ImageView getDocument() {
        return document;
    }
}
