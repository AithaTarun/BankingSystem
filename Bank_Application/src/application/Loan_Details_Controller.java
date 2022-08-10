package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.lang.Math;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Loan_Details_Controller implements Initializable
{
    static ResultSet res;
    static Connection connection = ConnectionClass.getConnection();
    static PreparedStatement stmt = null;
    @FXML
    private TableView<Model_Table_Loan_Details> table_Loan_Details;
    @FXML
    private TableColumn<Model_Table_Loan_Details,Integer> table_Loan_Details_Year;
    @FXML
    private TableColumn<Model_Table_Loan_Details,Double> table_Loan_Details_Principle;
    @FXML
    private TableColumn<Model_Table_Loan_Details,Double> table_Loan_Details_Interest;
    @FXML
    private TableColumn<Model_Table_Loan_Details,Double> table_Loan_Details_Payment;
    @FXML
    private TableColumn<Model_Table_Loan_Details,Double> table_Loan_Details_Balance;

    private static ObservableList<Model_Table_Loan_Details> oblist= FXCollections.observableArrayList();
    static int loan_tenture,ID;
    static double principal,temp_principal,Balance,loan_rate_of_interest,loan_interest,EMI,total_interest=0;
    public Label label_Loan_Details_EMI;
    public Label label_Loan_Details_Total_Interest;

    public void Set(int Set_ID)throws Exception
    {
        ID=Set_ID;
        try
        {
            String verify = "SELECT * from LOAN WHERE ACCOUNT_ID=?";
            stmt = connection.prepareStatement(verify);
            stmt.setInt(1, ID);
            ResultSet res1 = stmt.executeQuery();
            while (res1.next())
            {
                principal = res1.getDouble("LOAN_AMOUNT");
                loan_rate_of_interest = res1.getDouble("RATE_OF_INTEREST");
                loan_rate_of_interest = (loan_rate_of_interest / 12) / 100;
                loan_tenture = res1.getInt("LOAN_TENTURE");
                loan_tenture = loan_tenture * 12;
                EMI();
            }
            for (int i = 1; i <= loan_tenture; i++)
            {
                loan_interest = loan_rate_of_interest * principal;
                total_interest = total_interest + loan_interest;
                temp_principal = principal;
                principal = EMI - loan_interest;
                Balance = temp_principal - principal;
                if (i == loan_tenture)
                {
                    Balance = 0;
                }
                oblist.add(new Model_Table_Loan_Details(i, principal, loan_interest, principal + loan_interest, Balance));
                principal = Balance;
            }
            label_Loan_Details_EMI.setText("EMI="+EMI);
            label_Loan_Details_Total_Interest.setText("Total interest="+total_interest);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public void initialize(URL location, ResourceBundle resources)
    {
        table_Loan_Details_Year.setCellValueFactory(new PropertyValueFactory<>("Year"));
        table_Loan_Details_Principle.setCellValueFactory(new PropertyValueFactory<>("Principle"));
        table_Loan_Details_Interest.setCellValueFactory(new PropertyValueFactory<>("Interest"));
        table_Loan_Details_Payment.setCellValueFactory(new PropertyValueFactory<>("Total_Payment"));
        table_Loan_Details_Balance.setCellValueFactory(new PropertyValueFactory<>("Balance"));

        table_Loan_Details.setEditable(true);
        table_Loan_Details.setItems(oblist);
    }
    public static void EMI()
    {
        EMI=principal*loan_rate_of_interest*((Math.pow((1+loan_rate_of_interest),loan_tenture))/(Math.pow((1+loan_rate_of_interest),loan_tenture)-1));
    }
    public void Loan_Details_Back(ActionEvent Loan_Details_Back)throws Exception
    {
        oblist.clear();
        ((Node) Loan_Details_Back.getSource()).getScene().getWindow().hide();
        Stage primaryStage = new Stage();
        FXMLLoader Loader = new FXMLLoader();
        Pane Functions_Pane = Loader.load(getClass().getResource("Functions.fxml"));
        primaryStage.setTitle("Functions");
        primaryStage.setScene(new Scene(Functions_Pane, 1080, 720));
        primaryStage.show();
    }
}
