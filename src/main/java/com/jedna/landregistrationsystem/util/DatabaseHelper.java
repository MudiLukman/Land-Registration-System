package com.jedna.landregistrationsystem.util;

import com.jedna.landregistrationsystem.util.enums.Gender;
import javafx.application.Platform;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DatabaseHelper {

    private static final String Jdbc_driver = "com.mysql.cj.jdbc.Driver";
    private static final String Connection_string = "jdbc:mysql://localhost/landregsystem?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static final String admin_table = "CREATE TABLE if not exists admin(username VARCHAR(20) PRIMARY KEY NOT NULL, " +
            "password VARCHAR(512) NOT NULL)";

    public static final String land_table = "CREATE TABLE if not exists land(land_id VARCHAR(20) not null, dimension VARCHAR(20), medium VARCHAR(20), " +
            "activity_type VARCHAR(20), approval_date VARCHAR(20), stage VARCHAR(20), status VARCHAR(20), location_id VARCHAR(20), " +
            "owner_id VARCHAR(20), title_id VARCHAR(20), constraint fk_l_owner_id foreign key (owner_id) references land_owner (owner_id), " +
            "constraint fk_l_location_id foreign key (location_id) references location (location_id), constraint fk_l_title_id foreign key (title_id) " +
            "references title_doc (title_id), constraint pk_land primary key (land_id));";

    public static final String location_table = "CREATE TABLE if not exists location(location_id VARCHAR(20) not null, " +
            "latitude VARCHAR(20), longitude VARCHAR(20), constraint pk_location primary key (location_id));";

    public static final String landOwner_table = "CREATE TABLE if not exists land_owner(owner_id VARCHAR(20) not null, fullname VARCHAR(20), " +
            "password VARCHAR(512) NOT NULL, phone VARCHAR(20) NOT NULL, gender VARCHAR(20), address VARCHAR(20), passport TEXT, dec_of_age TEXT, occupation VARCHAR(20), " +
            "attestation_letter TEXT, nin_card TEXT, constraint pk_owner primary key (owner_id));";

    public static final String titleDocs_table = "CREATE TABLE if not exists title_doc(title_id VARCHAR(20) not null, root_of_title TEXT, " +
            "letter_from_district_head TEXT, evidence_of_ownership TEXT, court_affidavit TEXT, tax_clearance_cert TEXT, " +
            "constraint pk_title_doc primary key (title_id));";

    public static final String issue_table = "CREATE TABLE if not exists issue(issue_id VARCHAR(20) not null, land_id VARCHAR(20), description " +
            "TEXT, constraint fk_i_land_id foreign key (land_id) references land (land_id), constraint pk_issue primary key (issue_id));";

    public static final String certificate_table = "CREATE TABLE if not exists certificate(cert_id VARCHAR(20) not null, land_id VARCHAR(20), owner_id VARCHAR(20)," +
            "approvaldate VARCHAR(20), constraint fk_c_owner_id foreign key (owner_id) references land_owner(owner_id), " +
            "constraint fk_c_land_id foreign key (land_id) references land (land_id), constraint pk_cert primary key (cert_id));";

    static Connection con = null;
    static Statement stmt = null;

    public static void connect(){
        try {
            Class.forName(Jdbc_driver);
            System.out.println("Driver Loaded");
            con = DriverManager.getConnection(Connection_string, "abubakar", "landreg2019");
            System.out.println("Connection established");
            stmt = con.createStatement();

        }catch (ClassNotFoundException | SQLException e){
            Platform.runLater(() -> AlertMaker.showErrorMessage("Error", "Unable to establish connection"));
        }
    }

    public static void create_table(String table_name){
        try{

            stmt.executeUpdate(table_name);

        }catch (SQLException e){
            Platform.runLater(() -> {
                AlertMaker.showErrorMessage("Error", e.getMessage());
            });
        }catch (NullPointerException e){
            Platform.runLater(() -> {
                AlertMaker.showErrorMessage("Error", e.getMessage());
                System.exit(1);
            });
        }
    }

    public static void create_admin_table(){
        create_table(admin_table);
    }

    public static void create_all_tables(){
        connect();
        create_admin_table();
        insert_root_admin();
        create_table(location_table);
        create_table(landOwner_table);
        create_table(titleDocs_table);
        create_table(land_table);
        create_table(issue_table);
        create_table(certificate_table);
        disconnect();
    }

    public static void insert_root_admin()
    {
        //landreg2019
        String sql="insert into admin values('" + "Abubakar" + "', '1f5b6cbc5ea6114884ecf7152cbee501ea62f75e')";
        try{
            stmt.executeUpdate(sql);
        }
        catch(SQLException ex)
        {
            System.out.println("Root admin already present.");
        }
        catch (NullPointerException e){
            System.out.println("Server may be down");
        }

    }

    public static void disconnect(){
        if(con != null){
            try {

                stmt.close();
                con.close();

            }catch (SQLException e){
                Platform.runLater(() -> {
                    AlertMaker.showErrorMessage("Database Error", "Could not disconnect");
                });
            }
        }
    }

    public static ResultSet getUserNamePassword_admin()
    {
        String sql="Select * from admin";
        return executeQuery(sql);
    }

    public static ResultSet executeQuery(String sql)
    {
        connect();
        ResultSet rs = null;

        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Platform.runLater(() -> {
                AlertMaker.showErrorMessage("Error", ex.getMessage());
            });
        }

        return rs;
    }

    public static int insertLandOwner(String ownerId, String fullname, String password, String phone,
                                      Gender gender, String address, String passportPath, String decOfAgePath, String occupation,
                                      String attestationPath, String ninPath){

        String insertOwner = "INSERT INTO land_owner values(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = null;
        int val = 0;
        try{
            connect();
            ps = con.prepareStatement(insertOwner);
            ps.setString(1, ownerId);
            ps.setString(2, fullname);
            ps.setString(3, password);
            ps.setString(4, phone);
            ps.setString(5, gender.toString());
            ps.setString(6, address);
            ps.setString(7, passportPath);
            ps.setString(8, decOfAgePath);
            ps.setString(9, occupation);
            ps.setString(10, attestationPath);
            ps.setString(11, ninPath);

            val = ps.executeUpdate();

            disconnect();

        } catch (SQLException e) {
            e.printStackTrace();
            Platform.runLater(() -> {
                AlertMaker.showErrorMessage("Error", e.getMessage());
            });
        }

        return val;

    }

    public static int delete_record(String sql){
        int val = 0;
        try {
            connect();
            val = stmt.executeUpdate(sql);
        } catch( SQLException e ) {
            System.out.println(e.getMessage());
        }
        return val;
    }

    public static int insertTitleDocs(String titleId, String rootOfTitle, String letterFromDistrict, String evidence,
                                      String courtCert, String taxCert){

        String insertOwner = "INSERT INTO title_doc values(?,?,?,?,?,?)";

        PreparedStatement ps = null;
        int val = 0;
        try{
            connect();

            ps = con.prepareStatement(insertOwner);
            ps.setString(1, titleId);
            ps.setString(2, rootOfTitle);
            ps.setString(3, letterFromDistrict);
            ps.setString(4, evidence);
            ps.setString(5, courtCert);
            ps.setString(6, taxCert);

            val = ps.executeUpdate();

            disconnect();

        } catch (SQLException e) {
            Platform.runLater(() -> {
                AlertMaker.showErrorMessage("Error", e.getMessage());
            });
        }

        return val;

    }

    public static int insert_record(String sql) {
        int val = 0;
        try {
            connect();
            val = stmt.executeUpdate(sql);
        } catch ( SQLException e ) {
            System.out.println(e.getMessage());
        }
        return val;
    }

}
