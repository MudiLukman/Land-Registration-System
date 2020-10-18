package com.jedna.landregistrationsystem.view;

import com.jedna.landregistrationsystem.App;
import com.jedna.landregistrationsystem.model.Certificate;
import com.jedna.landregistrationsystem.model.Land;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeView extends BorderPane {

    private MenuBar menuBar;

    private Menu fileMenu;
    private MenuItem newApplicationMenuItem;
    private MenuItem logoutMenuItem;
    private MenuItem exitMenuItem;

    private Menu viewMenu;
    private MenuItem showOrHideToolbarMenuItem;

    private Menu navigateMenu;
    private MenuItem overviewMenuItem;
    private MenuItem pendingMenuItem;
    private MenuItem approvedMenuItem;
    private MenuItem revokedMenuItem;

    private Menu helpMenu;
    private MenuItem helpMenuItem;

    private ToolBar toolBar;
    private HBox toolbarPane;
    private Button newApplicationToolbarItem;
    private Button pendingToolbarItem;
    private Button rejectedToolbarItem;
    private Button approvedToolbarItem;
    private Button helpToolbarItem;
    private Button searchToolbarItem;
    private Button settingsToolbarItem;

    private VBox quickLinksBox;
    private Hyperlink overviewLink;
    private Hyperlink pendingLink;
    private Hyperlink approvedLink;
    private Hyperlink revokedLink;
    private Hyperlink applicationLink;

    private Button openAllInMap;
    private Button logoutButton;

    private static TableView<Certificate> certificateTable;
    private TableColumn<Certificate, String> certSerialNo;
    private TableColumn<Certificate, String> certificateId;
    private TableColumn<Certificate, String> creationDate;

    public static TableView<Land> landTable;
    private TableColumn<Land, Integer> landTableItemsSerialNoCol;
    private TableColumn<Land, String> titleNumberCol;
    private TableColumn<Land, String> ownerNameCol;
    private TableColumn<Land, String> statusCol;
    private TableColumn<Land, String> approvalDateCol;
    private TableColumn<Land, String> dimensionCol;
    private TableColumn<Land, String> mediumCol;
    private TableColumn<Land, String> activityCol;
    private TableColumn<Land, String> locationCol;
    private TableColumn<Land, String> stageCol;

    private ImageView overviewIcon;
    private ImageView pendingIcon;
    private ImageView approvedIcon;
    private ImageView revokedIcon;
    private ImageView applyIcon;

    private TextField searchTextField;
    private Button doSearchBtn;

    public HomeView(){
        menuBar = new MenuBar();
        fileMenu = new Menu("_File");
        newApplicationMenuItem = new MenuItem("_New Application");
        logoutMenuItem = new MenuItem("_Logout");
        exitMenuItem = new MenuItem("_Exit");
        fileMenu.getItems().addAll(newApplicationMenuItem, logoutMenuItem, exitMenuItem);

        viewMenu = new Menu("View");
        showOrHideToolbarMenuItem = new MenuItem("Show/hide Toolbar");
        viewMenu.getItems().add(showOrHideToolbarMenuItem);

        navigateMenu = new Menu("_Navigate");
        overviewMenuItem = new MenuItem("_Show Overview");
        pendingMenuItem = new MenuItem("Show Pending");
        approvedMenuItem = new MenuItem("Show Approved");
        revokedMenuItem = new MenuItem("Show _Revoked");
        navigateMenu.getItems().addAll(overviewMenuItem, pendingMenuItem, approvedMenuItem, revokedMenuItem);

        helpMenu = new Menu("Help");
        helpMenuItem = new MenuItem("About");
        helpMenu.getItems().add(helpMenuItem);

        menuBar.getMenus().addAll(fileMenu, viewMenu, navigateMenu, helpMenu);

        toolBar = new ToolBar();
        toolbarPane = new HBox(5);
        ImageView applyToolbarIcon = new ImageView(new Image(HomeView.class.getResource("/apply_black.png")
                .toExternalForm()));
        applyToolbarIcon.setFitWidth(20);
        applyToolbarIcon.setFitHeight(20);
        newApplicationToolbarItem = new Button("", applyToolbarIcon);
        newApplicationToolbarItem.setTooltip(new Tooltip("New Application"));
        ImageView pendingToolbarIcon = new ImageView(new Image(HomeView.class.getResource("/pending_black.png")
                .toExternalForm()));
        pendingToolbarIcon.setFitHeight(20);
        pendingToolbarIcon.setFitWidth(20);
        pendingToolbarItem = new Button("", pendingToolbarIcon);
        pendingToolbarItem.setTooltip(new Tooltip("Pending"));
        ImageView approvedToolbarIcon = new ImageView(new Image(HomeView.class.getResource("/approved_black.png")
                .toExternalForm()));
        approvedToolbarIcon.setFitWidth(20);
        approvedToolbarIcon.setFitHeight(20);
        approvedToolbarItem = new Button("", approvedToolbarIcon);
        approvedToolbarItem.setTooltip(new Tooltip("Approved"));
        ImageView revokedToolbarIcon = new ImageView(new Image(HomeView.class.getResource("/revoked_black.png")
                .toExternalForm()));
        revokedToolbarIcon.setFitHeight(20);
        revokedToolbarIcon.setFitWidth(20);
        rejectedToolbarItem = new Button("", revokedToolbarIcon);
        rejectedToolbarItem.setTooltip(new Tooltip("Rejected"));
        ImageView helpToolbarIcon = new ImageView(new Image(HomeView.class.getResource("/help_black.png")
                .toExternalForm()));
        helpToolbarIcon.setFitWidth(20);
        helpToolbarIcon.setFitHeight(20);
        helpToolbarItem = new Button("", helpToolbarIcon);
        helpToolbarItem.setTooltip(new Tooltip("Help"));
        ImageView searchToolbarIcon = new ImageView(new Image(HomeView.class.getResource("/search_black.png")
                .toExternalForm()));
        searchToolbarIcon.setFitHeight(20);
        searchToolbarIcon.setFitWidth(20);
        searchToolbarItem = new Button("", searchToolbarIcon);
        searchToolbarItem.setTooltip(new Tooltip("Search"));
        ImageView settingsToolbarIcon = new ImageView(new Image(HomeView.class.getResource("/settings_black.png")
                .toExternalForm()));
        settingsToolbarIcon.setFitHeight(20);
        settingsToolbarIcon.setFitWidth(20);
        settingsToolbarItem = new Button("", settingsToolbarIcon);

        ImageView mapIcon = new ImageView(new Image(HomeView.class.getResource("/marker_black.png").toExternalForm()));
        mapIcon.setSmooth(true);
        mapIcon.setFitWidth(20);
        mapIcon.setFitHeight(20);
        openAllInMap = new Button("", mapIcon);
        openAllInMap.setTooltip(new Tooltip("Open Google Map"));
        logoutButton = new Button("Logout");

        quickLinksBox = new VBox(30);

        overviewLink = new Hyperlink("Overview");
        overviewLink.setStyle("-fx-underline: false;");
        overviewLink.setTextFill(Color.BLACK);
        pendingLink = new Hyperlink("Pending");
        pendingLink.setStyle("-fx-underline: false;");
        pendingLink.setTextFill(Color.BLACK);
        approvedLink = new Hyperlink("Approved");
        approvedLink.setStyle("-fx-underline: false;");
        approvedLink.setTextFill(Color.BLACK);
        revokedLink = new Hyperlink("Rejected");
        revokedLink.setStyle("-fx-underline: false;");
        revokedLink.setTextFill(Color.BLACK);
        applicationLink = new Hyperlink("Apply");
        applicationLink.setStyle("-fx-underline: false;");
        applicationLink.setTextFill(Color.BLACK);

        landTable = new TableView<>();
        landTableItemsSerialNoCol = new TableColumn<>("S/N");
        titleNumberCol = new TableColumn<>("Title Number");
        ownerNameCol = new TableColumn<>("Owner Name");
        statusCol = new TableColumn<>("Status");
        approvalDateCol = new TableColumn<>("Approval Date");
        dimensionCol = new TableColumn<>("Dimension");
        mediumCol = new TableColumn<>("Medium");
        activityCol = new TableColumn<>("Activity");
        locationCol = new TableColumn<>("Location");
        stageCol = new TableColumn<>("Stage");
        landTable.getColumns().addAll(landTableItemsSerialNoCol, titleNumberCol, ownerNameCol, statusCol,
                approvalDateCol, dimensionCol, mediumCol, activityCol, locationCol, stageCol);

        certificateTable = new TableView<>();
        certSerialNo = new TableColumn<>("S/N");
        certificateId = new TableColumn<>("Certificate Number");
        creationDate = new TableColumn<>("Approval Date");
        certificateTable.getColumns().addAll(certSerialNo, certificateId, creationDate);

        searchTextField = new TextField();
        doSearchBtn = new Button("Go");

        buildUI();

    }

    private void buildUI(){

        //build top part
        toolbarPane.getChildren().addAll(newApplicationToolbarItem, pendingToolbarItem, approvedToolbarItem, rejectedToolbarItem,
                helpToolbarItem, searchToolbarItem, settingsToolbarItem);
        toolBar.getItems().add(toolbarPane);
        VBox topBox = new VBox(menuBar, toolBar);
        this.setTop(topBox);

        //build right part
        ImageView govLogoView = new ImageView(new Image(HomeView.class.getResource("/gov_logo.png").toExternalForm()));
        govLogoView.setFitHeight(120);
        govLogoView.setFitWidth(150);
        govLogoView.setSmooth(true);

        overviewIcon = new ImageView(new Image(HomeView.class.getResource("/home_black.png").toExternalForm()));
        overviewIcon.setSmooth(true);
        overviewIcon.setFitWidth(25);
        overviewIcon.setFitHeight(25);
        overviewIcon.setPickOnBounds(true);
        HBox overviewBox = new HBox(7, overviewIcon, overviewLink);

        pendingIcon = new ImageView(new Image(HomeView.class.getResource("/pending_black.png").toExternalForm()));
        pendingIcon.setSmooth(true);
        pendingIcon.setFitHeight(25);
        pendingIcon.setFitWidth(25);
        pendingIcon.setPickOnBounds(true);
        HBox pendingBox = new HBox(7, pendingIcon, pendingLink);

        approvedIcon = new ImageView(new Image(HomeView.class.getResource("/approved_black.png").toExternalForm()));
        approvedIcon.setFitWidth(25);
        approvedIcon.setFitHeight(25);
        approvedIcon.setSmooth(true);
        approvedIcon.setPickOnBounds(true);
        HBox approvedBox = new HBox(7, approvedIcon, approvedLink);

        revokedIcon = new ImageView(new Image(HomeView.class.getResource("/revoked_black.png").toExternalForm()));
        revokedIcon.setSmooth(true);
        revokedIcon.setFitHeight(25);
        revokedIcon.setFitWidth(25);
        revokedIcon.setPickOnBounds(true);
        HBox revokedBox = new HBox(7, revokedIcon, revokedLink);

        applyIcon = new ImageView(new Image(HomeView.class.getResource("/apply_black.png").toExternalForm()));
        applyIcon.setFitWidth(25);
        applyIcon.setFitHeight(25);
        applyIcon.setSmooth(true);
        applyIcon.setPickOnBounds(true);
        HBox applyBox = new HBox(7, applyIcon, applicationLink);

        quickLinksBox.getChildren().addAll(overviewBox, pendingBox, approvedBox, revokedBox, applyBox);
        quickLinksBox.setAlignment(Pos.CENTER);
        quickLinksBox.setStyle("-fx-border-radius: 5;" +
                "-fx-border-color: rgb(115, 115, 115);" +
                "-fx-border-width: 2;" +
                "-fx-padding: 50;");
        VBox rightBox = new VBox(50, govLogoView, quickLinksBox);
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setPadding(new Insets(5, 10, 10, 20));
        this.setLeft(rightBox);

        //build main/center part
        Text appName = new Text("GIS-enabled Land Registration System");
        appName.setFont(new Font("Times", 24));
        BorderPane mainPart = new BorderPane();
        Pane spacer = new Pane();
        HBox buttonsHBoxTop = new HBox(15, openAllInMap, logoutButton);
        HBox appNamePart = new HBox(appName, spacer, buttonsHBoxTop);
        HBox.setHgrow(spacer, Priority.SOMETIMES);
        appNamePart.setPadding(new Insets(15, 10, 15, 10));
        appNamePart.setAlignment(Pos.CENTER);
        mainPart.setTop(appNamePart);
        mainPart.setCenter(certificateTable);
        this.setCenter(mainPart);

    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public Menu getFileMenu() {
        return fileMenu;
    }

    public MenuItem getNewApplicationMenuItem() {
        return newApplicationMenuItem;
    }

    public MenuItem getLogoutMenuItem() {
        return logoutMenuItem;
    }

    public MenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public Menu getViewMenu() {
        return viewMenu;
    }

    public MenuItem getShowOrHideToolbarMenuItem() {
        return showOrHideToolbarMenuItem;
    }

    public Menu getNavigateMenu() {
        return navigateMenu;
    }

    public MenuItem getOverviewMenuItem() {
        return overviewMenuItem;
    }

    public MenuItem getPendingMenuItem() {
        return pendingMenuItem;
    }

    public MenuItem getApprovedMenuItem() {
        return approvedMenuItem;
    }

    public MenuItem getRevokedMenuItem() {
        return revokedMenuItem;
    }

    public Menu getHelpMenu() {
        return helpMenu;
    }

    public MenuItem getHelpMenuItem() {
        return helpMenuItem;
    }

    public ToolBar getToolBar() {
        return toolBar;
    }

    public Button getNewApplicationToolbarItem() {
        return newApplicationToolbarItem;
    }

    public Button getPendingToolbarItem() {
        return pendingToolbarItem;
    }

    public Button getRejectedToolbarItem() {
        return rejectedToolbarItem;
    }

    public Button getApprovedToolbarItem() {
        return approvedToolbarItem;
    }

    public Button getHelpToolbarItem() {
        return helpToolbarItem;
    }

    public Button getSearchToolbarItem() {
        return searchToolbarItem;
    }

    public Hyperlink getOverviewLink() {
        return overviewLink;
    }

    public Hyperlink getPendingLink() {
        return pendingLink;
    }

    public Hyperlink getApprovedLink() {
        return approvedLink;
    }

    public Hyperlink getRevokedLink() {
        return revokedLink;
    }

    public Hyperlink getApplicationLink() {
        return applicationLink;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }

    public TableView<Land> getLandTable() {
        return landTable;
    }

    public TableView<Certificate> getCertificateTable() {
        return certificateTable;
    }

    public TableColumn<Certificate, String> getCertSerialNo() {
        return certSerialNo;
    }

    public TableColumn<Certificate, String> getCertificateId() {
        return certificateId;
    }

    public TableColumn<Certificate, String> getCreationDate() {
        return creationDate;
    }

    public TableColumn<Land, Integer> getLandTableItemsSerialNoCol() {
        return landTableItemsSerialNoCol;
    }

    public TableColumn<Land, String> getTitleNumberCol() {
        return titleNumberCol;
    }

    public TableColumn<Land, String> getOwnerNameCol() {
        return ownerNameCol;
    }

    public TableColumn<Land, String> getStatusCol() {
        return statusCol;
    }

    public TableColumn<Land, String> getApprovalDateCol() {
        return approvalDateCol;
    }

    public TableColumn<Land, String> getDimensionCol() {
        return dimensionCol;
    }

    public TableColumn<Land, String> getMediumCol() {
        return mediumCol;
    }

    public TableColumn<Land, String> getActivityCol() {
        return activityCol;
    }

    public TableColumn<Land, String> getLocationCol() {
        return locationCol;
    }

    public TableColumn<Land, String> getStageCol() {
        return stageCol;
    }

    public ImageView getOverviewIcon() {
        return overviewIcon;
    }

    public ImageView getPendingIcon() {
        return pendingIcon;
    }

    public ImageView getApprovedIcon() {
        return approvedIcon;
    }

    public ImageView getRevokedIcon() {
        return revokedIcon;
    }

    public ImageView getApplyIcon() {
        return applyIcon;
    }

    public Button getOpenAllInMap() {
        return openAllInMap;
    }

    public TextField getSearchTextField() {
        return searchTextField;
    }

    public Button getSettingsToolbarItem() {
        return settingsToolbarItem;
    }

    public Button getDoSearchBtn() {
        return doSearchBtn;
    }

    public VBox getQuickLinksBox() {
        return quickLinksBox;
    }

    public HBox getToolbarPane() {
        return toolbarPane;
    }
}
