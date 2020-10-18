package com.jedna.landregistrationsystem.presenter;

import com.jedna.landregistrationsystem.view.ShowDocumentView;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShowDocumentPresenter {

    private ShowDocumentView view;

    public ShowDocumentPresenter(){
        view = new ShowDocumentView();
    }

    public ShowDocumentView getView(){
        return view;
    }

}
