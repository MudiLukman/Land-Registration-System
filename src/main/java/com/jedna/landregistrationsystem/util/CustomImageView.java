package com.jedna.landregistrationsystem.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomImageView extends ImageView {

    public CustomImageView(){
        Image image = new Image(CustomImageView.class.getResource("/view_black.png").toExternalForm());
        this.setImage(image);
        this.setFitHeight(15);
        this.setFitWidth(15);
        this.setSmooth(true);
        this.setPickOnBounds(true);
    }

}
