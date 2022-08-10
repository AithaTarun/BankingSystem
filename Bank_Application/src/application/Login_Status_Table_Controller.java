package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Login_Status_Table_Controller implements Initializable
{
    static ResultSet res;
    static Connection connection = ConnectionClass.getConnection();
    static PreparedStatement stmt = null;

    @FXML
    private TableView<Model_Table_Login> table_Login;
    @FXML
    private TableColumn<Model_Table_Login,Integer> table_Login_ID;
    @FXML
    private TableColumn<Model_Table_Login,String>table_Login_Fname;
    @FXML
    private TableColumn<Model_Table_Login,String>table_Login_Lname;
    @FXML
    private TableColumn<Model_Table_Login,Long>table_Login_Phone;
    @FXML
    private TableColumn<Model_Table_Login,String>table_Login_DOB;
    @FXML
    private TableColumn<Model_Table_Login,String>table_Login_Mail;
    @FXML
    private TableColumn<Model_Table_Login,String>table_Login_Address;
    @FXML
    private TableColumn<Model_Table_Login,CheckBox>table_Login_Status;
    @FXML
    private TableColumn<Model_Table_Login,TextField>table_Login_Message;
    @FXML
    private Label label_Login_Proceed_Message;

    private static ObservableList<Model_Table_Login> oblist= FXCollections.observableArrayList();

    static HBox hb;
    static HBox hb_txt;

    public static void Set()throws Exception
    {
        String verify = "SELECT * from ACCOUNTS_DETAILS";
        stmt = connection.prepareStatement(verify);
        ResultSet res1 = stmt.executeQuery();
        res=res1;
        while (res1.next())
        {
            hb = new HBox(7);
            hb.setAlignment(Pos.CENTER);
            final ToggleGroup group = new ToggleGroup();

            RadioButton Accept_but = new RadioButton("Accept\t");
            Accept_but.setToggleGroup(group);

            RadioButton Decline_but=new RadioButton("Decline");
            Decline_but.setToggleGroup(group);

            hb.getChildren().add(Accept_but);
            hb.getChildren().add(Decline_but);

            hb_txt=new HBox(7);
            hb_txt.setAlignment(Pos.CENTER);
            TextField txt=new TextField();
            hb_txt.getChildren().add(txt);

            if(res1.getBoolean("Status")==false)
            {
                oblist.add(new Model_Table_Login(res1.getInt("Account_ID"), res1.getString("First_Name"), res1.getString("Last_Name"),res1.getLong("Phone"),res1.getString("Date_Of_Birth"),res1.getString("EMAIL"),res1.getString("Address"),hb,hb_txt));
            }
        }
    }
    public void initialize(URL location, ResourceBundle resources)
    {
        table_Login_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        table_Login_Fname.setCellValueFactory(new PropertyValueFactory<>("Fname"));
        table_Login_Lname.setCellValueFactory(new PropertyValueFactory<>("Lname"));
        table_Login_Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        table_Login_DOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        table_Login_Mail.setCellValueFactory(new PropertyValueFactory<>("Mail"));
        table_Login_Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        table_Login_Status.setCellValueFactory(new PropertyValueFactory<>("hb"));
        table_Login_Message.setCellValueFactory(new PropertyValueFactory<>("hb_txt"));

        table_Login.setEditable(true);
        table_Login.setItems(oblist);
    }
    public void Login_Status_Proceed(ActionEvent Login_Status_Proceed)throws Exception
    {
        for(Model_Table_Login val:table_Login.getItems())
        {
            HBox Addr=val.getHb();
            RadioButton Accept_Addr= (RadioButton) Addr.getChildren().get(0);
            RadioButton Decline_Addr=(RadioButton) Addr.getChildren().get(1);
            Boolean Accept=Accept_Addr.isSelected();
            Boolean Decline=Decline_Addr.isSelected();

            HBox hb_txt_Addr=val.getHb_txt();
            TextField Txt_Addr=(TextField)hb_txt_Addr.getChildren().get(0);
            String message=Txt_Addr.getText();

            int LID=val.getID();

            String Message_Update="update ACCOUNTS_DETAILS set Login_Message=? where ACCOUNT_ID=?";
            try
            {
                stmt = connection.prepareStatement(Message_Update);
                stmt.setString(1, message);
                stmt.setInt(2, LID);
                stmt.execute();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
            if(Accept)
            {
                Platform.runLater(
                        ()->
                        {
                            String Status_Update="update ACCOUNTS_DETAILS set Status=? where ACCOUNT_ID=?";
                            try
                            {
                                stmt = connection.prepareStatement(Status_Update);
                                stmt.setBoolean(1, true);
                                stmt.setInt(2, LID);
                                stmt.execute();
                            }
                            catch (Exception e)
                            {
                                System.out.println(e);
                            }
                            label_Login_Proceed_Message.setText("Action performed successfully");
                }
                );
            }
            else if(Decline)
            {
                Platform.runLater(
                        ()->
                        {
                            String Status_Update="update ACCOUNTS_DETAILS set Status=? where ACCOUNT_ID=?";
                            try
                            {
                                stmt = connection.prepareStatement(Status_Update);
                                stmt.setBoolean(1, false);
                                stmt.setInt(2, LID);
                                stmt.execute();
                            }
                            catch (Exception e)
                            {
                                System.out.println(e);
                            }
                            label_Login_Proceed_Message.setText("Action performed successfully");
                        }
                );
            }
        }
    }
    public void Login_Status_Back(ActionEvent Login_Status_Back)throws Exception
    {
        oblist.clear();
        ((Node) Login_Status_Back.getSource()).getScene().getWindow().hide();
        Stage primaryStage = new Stage();
        FXMLLoader Loader = new FXMLLoader();
        Pane Functions_Pane = Loader.load(getClass().getResource("Functions_Manager.fxml"));
        primaryStage.setTitle("Functions");
        primaryStage.setScene(new Scene(Functions_Pane, 1080, 720));
        primaryStage.show();
    }
}
