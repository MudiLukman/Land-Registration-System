package com.jedna.landregistrationsystem;

import com.jedna.landregistrationsystem.presenter.LoginPresenter;
import com.jedna.landregistrationsystem.util.AlertMaker;
import com.jedna.landregistrationsystem.util.DatabaseHelper;
import com.jedna.landregistrationsystem.view.HelpView;
import com.jedna.landregistrationsystem.view.HomeView;
import com.jedna.landregistrationsystem.view.LandDetailsView;
import com.jedna.landregistrationsystem.view.ShowDocumentView;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App extends Application{

    @Override
    public void start(Stage primaryStage) {

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(new LoginPresenter().getView()));
        primaryStage.show();
    }

    public static void loadWindow(String title, Modality modality, Pane view){
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        if(view instanceof LandDetailsView){
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
            stage.setMaxHeight(visualBounds.getHeight());
        }
        if(view instanceof ShowDocumentView){
            stage.setResizable(false);
            stage.sizeToScene();
        }

        if(view instanceof HelpView){
            stage.setWidth(500);
            stage.setHeight(400);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if(!stage.isFocused()){
                    stage.close();
                }
            });
        }

        stage.setTitle(title);
        stage.setScene(new Scene(view));
        stage.initModality(modality);
        stage.showAndWait();
    }

    public static void switchWindow(Pane from, Pane to, String title){
        Stage stage = new Stage();
        stage.setResizable(false);
        if(to instanceof HomeView){
            stage.initStyle(StageStyle.DECORATED);
            stage.setOnCloseRequest(App::onWindowCloseRequest);
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
            stage.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight());
            stage.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);
            stage.setWidth(visualBounds.getWidth());
            stage.setHeight(visualBounds.getHeight());
        }
        stage.setTitle(title);
        stage.setScene(new Scene(to));
        ((Stage) from.getScene().getWindow()).close();
        stage.show();

    }

    public static void onWindowCloseRequest(WindowEvent event){

        HBox buttonsHBox = new HBox(10);
        buttonsHBox.setAlignment(Pos.CENTER_RIGHT);
        Button ok = new Button("Ok");
        ok.setDefaultButton(true);
        Button cancel = new Button("Cancel");
        cancel.setCancelButton(true);
        buttonsHBox.getChildren().addAll(ok, cancel);
        VBox pane = new VBox(5, new Label("Are you sure you want to exit?"), buttonsHBox);
        pane.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-padding: 10;");
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Confirm Exit");
        stage.setScene(new Scene(pane));
        stage.show();
        ok.setOnAction(event1 -> {
            System.exit(0);
        });
        cancel.setOnAction(event1 -> {
            cancel.getScene().getWindow().hide();
        });

        if(event != null) {
            event.consume();
        }

    }

    public static void main(String[] args){
        new Thread(DatabaseHelper::create_all_tables).start();
        launch(args);
    }

}
