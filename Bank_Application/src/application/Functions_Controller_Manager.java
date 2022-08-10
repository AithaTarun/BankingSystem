package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.Date;

import java.net.URL;
import java.sql.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Connection;
public class Functions_Controller_Manager implements Initializable
{
    static Connection connection = ConnectionClass.getConnection();
    static int ID;
    static String ini_User_First_Name,ini_User_Last_Name;
    PreparedStatement stmt = null;
    @FXML
    public Label label_Functions_Manager_Welcome;
    public Label label_Functions_Manager_Delete;
    public TextField txt_Functions_Manager_Delete_Customer;
    public Label label_Functions_Manager_Delete_Customer;
    public TextField txt_Functions_Manager_Update_Customer;
    public Label label_Functions_Manager_Update_Customer;
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        label_Functions_Manager_Welcome.setText("Welcome"+" "+ini_User_First_Name+" "+ini_User_Last_Name);
    }
    public void User_name_Manager(String User_First_Name,String User_Last_Name,int User_ID)
    {
        ID=User_ID;
        ini_User_First_Name=User_First_Name;
        ini_User_Last_Name=User_Last_Name;
        label_Functions_Manager_Welcome.setText("Welcome"+" "+User_First_Name+" "+User_Last_Name);
    }
    public void Delete_Account(ActionEvent Delete_Account)throws Exception
    {
        String Delete = "DELETE Manager_Account where ACCOUNT_ID=?";
        stmt = connection.prepareStatement(Delete);
        stmt.setInt(1, ID);
        stmt.execute();
        label_Functions_Manager_Delete.setText("Account deleted");
    }
    public void Update_Account(ActionEvent Update_Account)throws Exception
    {
        String verify = "SELECT * from ACCOUNTS_DETAILS";
        stmt = connection.prepareStatement(verify);
        ResultSet res1 = stmt.executeQuery();
        while (res1.next())
        {
            ((Node)Update_Account.getSource()).getScene().getWindow().hide();
            Stage primaryStage=new Stage();
            FXMLLoader loader=new FXMLLoader();
            Pane Update_Pane= loader.load(getClass().getResource("Update_Account_Manager.fxml").openStream());
            Update_Account_Manger_Controller userController = (Update_Account_Manger_Controller) loader.getController();
            userController.Set(ID);
            primaryStage.setTitle("Update account");
            primaryStage.setScene(new Scene(Update_Pane,1080,720));
            primaryStage.show();
        }
    }
    public void New_Users(ActionEvent New_Users)throws Exception
    {
            ((Node)New_Users.getSource()).getScene().getWindow().hide();
            Stage primaryStage=new Stage();
            FXMLLoader loader=new FXMLLoader();
            Pane Login_Pane= loader.load(getClass().getResource("Login_Status_Table.fxml"));
            Login_Status_Table_Controller IDController = (Login_Status_Table_Controller) loader.getController();
            IDController.Set();
            primaryStage.setTitle("Login status");
            primaryStage.setScene(new Scene(Login_Pane,1920,1080));
            primaryStage.show();
    }
    public void Functions_Manager_Back(ActionEvent Functions_Manager_Back)throws Exception
    {
        ((Node) Functions_Manager_Back.getSource()).getScene().getWindow().hide();
        Stage primaryStage = new Stage();
        FXMLLoader Loader = new FXMLLoader();
        Pane Login_Pane = Loader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(Login_Pane, 1080, 720));
        primaryStage.show();
    }
    public void Create_Customer(ActionEvent Create_Customer)throws Exception
    {
        ((Node)Create_Customer.getSource()).getScene().getWindow().hide();
        Stage primaryStage=new Stage();
        FXMLLoader loader=new FXMLLoader();
        Pane Create_Account_pane= loader.load(getClass().getResource("Create_Account.fxml"));
        Create_Account_Controller IDController = (Create_Account_Controller) loader.getController();
        IDController.Manager(true);
        primaryStage.setTitle("Create account");
        primaryStage.setScene(new Scene(Create_Account_pane,1080,720));
        primaryStage.show();
    }
    public void Create_Manager(ActionEvent Create_Manager)throws Exception
    {
        ((Node)Create_Manager.getSource()).getScene().getWindow().hide();
        Stage primaryStage=new Stage();
        FXMLLoader loader=new FXMLLoader();
        Pane Create_Manager_Account_pane= loader.load(getClass().getResource("Create_Manager_Account.fxml"));
        primaryStage.setTitle("Create manager account");
        primaryStage.setScene(new Scene(Create_Manager_Account_pane,1080,720));
        primaryStage.show();
    }
    public void Delete_Customer(ActionEvent Delete_Customer)throws Exception
    {
        int Cus_ID=Integer.parseInt(txt_Functions_Manager_Delete_Customer.getText());
        boolean exist=Verify_Customer(Cus_ID);
        if(exist)
        {
            String Delete = "DELETE ACCOUNTS_DETAILS where ACCOUNT_ID=?";
            stmt = connection.prepareStatement(Delete);
            stmt.setInt(1, Cus_ID);
            stmt.execute();
            label_Functions_Manager_Delete_Customer.setText("Customer account deleted");
        }
        else
            {
            label_Functions_Manager_Delete_Customer.setText("Account does not exist");
        }
    }
    public void Update_Customer(ActionEvent Update_Customer)throws Exception
    {
        int Cus_ID=Integer.parseInt(txt_Functions_Manager_Update_Customer.getText());
        boolean exist=Verify_Customer(Cus_ID);
        if(exist)
        {
            ((Node) Update_Customer.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane Update_Pane = loader.load(getClass().getResource("Update_Account.fxml").openStream());
            Update_Account_Controller userController = (Update_Account_Controller) loader.getController();
            userController.User_ID(Cus_ID);
            primaryStage.setTitle("Update account");
            primaryStage.setScene(new Scene(Update_Pane, 1080, 720));
            primaryStage.show();
            label_Functions_Manager_Update_Customer.setText("Customer account updated");
        }
        else
        {
            label_Functions_Manager_Update_Customer.setText("Account does not exist");
        }

    }
    public void Approve_Loan(ActionEvent Approve_Loan)throws Exception
    {
        ((Node)Approve_Loan.getSource()).getScene().getWindow().hide();
        Stage primaryStage=new Stage();
        FXMLLoader loader=new FXMLLoader();
        Pane Login_Pane= loader.load(getClass().getResource("Loan_Table.fxml"));
        Loan_Table_Controller IDController = (Loan_Table_Controller) loader.getController();
        IDController.Set();
        primaryStage.setTitle("Loans");
        primaryStage.setScene(new Scene(Login_Pane,1320,900));
        primaryStage.show();
    }
    public static boolean Verify_Customer(int checkID) throws Exception
    {

        PreparedStatement stmt=null;

        String verify="SELECT * from ACCOUNTS_DETAILS where ACCOUNT_ID=?";
        stmt= connection.prepareStatement(verify);
        stmt.setInt(1,checkID);
        ResultSet res1=stmt.executeQuery();
        boolean checkexist=false;
        while(res1.next())
        {
            int r=res1.getInt("ACCOUNT_ID");
            if(r==checkID)
            {
                checkexist= true;
            }
            else
            {
                checkexist= false;
            }
        }
        return checkexist;
    }
    public static Connection getConnection() throws Exception
    {
        try
        {
            String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url="jdbc:sqlserver://localhost:1433;"+"databaseName=Bank";
            String username="Bank";
            String password="123456";
            Class.forName(driver);
            Connection conn=DriverManager.getConnection(url,username,password);
            /*conn.setAutoCommit(false);*/
            return conn;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
}
