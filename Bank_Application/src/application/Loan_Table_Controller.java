package application;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Loan_Table_Controller implements Initializable
{
    static ResultSet res;
    static Connection connection = ConnectionClass.getConnection();
    static PreparedStatement stmt = null;
    @FXML
    private TableView<Model_Table_Loan> table_Loan;
    @FXML
    private TableColumn<Model_Table_Loan,Integer>table_Loan_Customer_ID;
    @FXML
    private TableColumn<Model_Table_Loan,TextField>table_Loan_Loan_ID;
    @FXML
    private TableColumn<Model_Table_Loan,Long>table_Loan_Amount;
    @FXML
    private TableColumn<Model_Table_Loan,TextField>table_Loan_Interest;
    @FXML
    private TableColumn<Model_Table_Loan,Integer>table_Loan_Tenture;
    @FXML
    private TableColumn<Model_Table_Loan,CheckBox>table_Loan_Status;
    @FXML
    private TableColumn<Model_Table_Loan,TextField>table_Loan_Message;
    @FXML
    private Label label_Loan_Proceed_Message;

    private static ObservableList<Model_Table_Loan> oblist= FXCollections.observableArrayList();

    static HBox hb;
    static HBox hb_txt;
    static HBox hb_Loan_ID;
    static HBox hb_Interest;

    public static void Set()throws Exception
    {
        String verify = "SELECT * from LOAN";
        stmt = connection.prepareStatement(verify);
        ResultSet res1 = stmt.executeQuery();
        res=res1;
        while (res1.next())
        {
            hb_Loan_ID=new HBox(7);
            hb_Loan_ID.setAlignment(Pos.CENTER);
            TextField Loan_ID=new TextField();
            Loan_ID.promptTextProperty().setValue("Enter new loan id");
            hb_Loan_ID.getChildren().add(Loan_ID);

            hb_Interest=new HBox(7);
            hb_Interest.setAlignment(Pos.CENTER);
            TextField Loan_Interest=new TextField();
            Loan_Interest.promptTextProperty().setValue("Enter rate of interest");
            hb_Interest.getChildren().add(Loan_Interest);

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
                oblist.add(new Model_Table_Loan(res1.getInt("ACCOUNT_ID"),hb_Loan_ID,res1.getLong("LOAN_AMOUNT"),hb_Interest,res1.getInt("LOAN_TENTURE"),hb,hb_txt));
            }
        }
    }
    public void initialize(URL location, ResourceBundle resources)
    {
        table_Loan_Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        table_Loan_Loan_ID.setCellValueFactory(new PropertyValueFactory<>("hb_Loan_ID"));
        table_Loan_Amount.setCellValueFactory(new PropertyValueFactory<>("Loan_Amount"));
        table_Loan_Interest.setCellValueFactory(new PropertyValueFactory<>("hb_Interest"));
        table_Loan_Tenture.setCellValueFactory(new PropertyValueFactory<>("Loan_Tenture"));
        table_Loan_Status.setCellValueFactory(new PropertyValueFactory<>("hb"));
        table_Loan_Message.setCellValueFactory(new PropertyValueFactory<>("hb_txt"));

        table_Loan.setEditable(true);
        table_Loan.setItems(oblist);
    }
    public void Loan_Proceed(ActionEvent Loan_Proceed)throws Exception
    {
        for(Model_Table_Loan val:table_Loan.getItems())
        {
            HBox hb_Loan_ID_Addr=val.getHb_Loan_ID();
            TextField Loan_ID_Addr=(TextField)hb_Loan_ID_Addr.getChildren().get(0);
            String Loan_ID=Loan_ID_Addr.getText();

            HBox hb_Interest_Addr=val.getHb_Interest();
            TextField Interest_Addr=(TextField)hb_Interest_Addr.getChildren().get(0);
            String Interest=Interest_Addr.getText();

            HBox Addr=val.getHb();
            RadioButton Accept_Addr= (RadioButton) Addr.getChildren().get(0);
            RadioButton Decline_Addr=(RadioButton) Addr.getChildren().get(1);
            Boolean Accept=Accept_Addr.isSelected();
            Boolean Decline=Decline_Addr.isSelected();

            HBox hb_txt_Addr=val.getHb_txt();
            TextField Txt_Addr=(TextField)hb_txt_Addr.getChildren().get(0);
            String message=Txt_Addr.getText();

            int LID=val.getCustomer_ID();

            String Message_Update="update LOAN set Message=? where ACCOUNT_ID=?";
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
                            String Status_Update="update LOAN set Status=? where ACCOUNT_ID=?";
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

                            String Update_Loan_ID="update LOAN set LOAN_ID=? where ACCOUNT_ID=?";
                            try
                            {
                                stmt = connection.prepareStatement(Update_Loan_ID);
                                stmt.setString(1, Loan_ID);
                                stmt.setInt(2, LID);
                                stmt.execute();
                            }
                            catch (Exception e)
                            {
                                System.out.println(e);
                            }

                            String Update_Interest="update LOAN set RATE_OF_INTEREST=? where ACCOUNT_ID=?";
                            try
                            {
                                stmt = connection.prepareStatement(Update_Interest);
                                stmt.setString(1, Interest);
                                stmt.setInt(2, LID);
                                stmt.execute();
                            }
                            catch (Exception e)
                            {
                                System.out.println(e);
                            }
                            label_Loan_Proceed_Message.setText("Action performed successfully");
                        }
                );
            }
            else if(Decline)
            {
                Platform.runLater(
                        ()->
                        {
                            String Status_Update="update LOAN set Status=? where ACCOUNT_ID=?";
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
                            label_Loan_Proceed_Message.setText("Action performed successfully");
                        }
                );
            }
        }
    }
    public void Loan_Back(ActionEvent Loan_Back)throws Exception
    {
        oblist.clear();
        ((Node) Loan_Back.getSource()).getScene().getWindow().hide();
        Stage primaryStage = new Stage();
        FXMLLoader Loader = new FXMLLoader();
        Pane Functions_Pane = Loader.load(getClass().getResource("Functions_Manager.fxml"));
        primaryStage.setTitle("Functions");
        primaryStage.setScene(new Scene(Functions_Pane, 1080, 720));
        primaryStage.show();
    }

}
