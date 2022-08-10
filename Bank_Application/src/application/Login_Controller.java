package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;

import java.sql.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Connection;

public class Login_Controller
{
    static Connection connection=ConnectionClass.getConnection();
    static int ID;
    static boolean exist=false;
    static Scanner temp=new Scanner(System.in);
    static BufferedReader temp1=new BufferedReader(new InputStreamReader(System.in));
    static boolean Manager=false,Customer=false;


    @FXML
    public TextField txt_Login_ID;
    public TextField txt_Login_Pass;
    public Label label_Login_Warning;
    public RadioButton radio_login_Customer;
    public RadioButton radio_Login_Manager;

    public void Login_Proceed(ActionEvent Login_Proceed) throws Exception
    {
        if(Manager)
        {
            int LogID = Integer.parseInt(txt_Login_ID.getText());
            String Logpass = txt_Login_Pass.getText();

            PreparedStatement stmt = null;

            String verify = "SELECT * from Manager_Account where ACCOUNT_ID=?";
            stmt = connection.prepareStatement(verify);
            stmt.setInt(1, LogID);
            ResultSet res1 = stmt.executeQuery();
            while (res1.next())
            {
                int ri = res1.getInt("ACCOUNT_ID");
                String rp = res1.getString("ACCOUNT_PASSWORD");
                if (ri == LogID && rp.equals(Logpass))
                {
                    exist = true;
                    ID = LogID;
                    ((Node) Login_Proceed.getSource()).getScene().getWindow().hide();
                    Stage primaryStage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    Pane Functions_Manager_Pane = loader.load(getClass().getResource("Functions_Manager.fxml").openStream());
                    Functions_Controller_Manager userController = (Functions_Controller_Manager) loader.getController();
                    userController.User_name_Manager(res1.getString("First_Name"), res1.getString("Last_Name"), res1.getInt("ACCOUNT_ID"));
                    primaryStage.setTitle("Functions");
                    primaryStage.setScene(new Scene(Functions_Manager_Pane, 1080, 720));
                    primaryStage.show();
                }
                else
                {
                    label_Login_Warning.setText("Enter valid credintials");
                    exist = false;
                    return;
                }
            }
            if (res1.next()==false)
            {
                label_Login_Warning.setText("Account does not exist");
                return;
            }
        }
        if(Customer)
        {
            int LogID = Integer.parseInt(txt_Login_ID.getText());
            String Logpass = txt_Login_Pass.getText();

            PreparedStatement stmt = null;

            String verify = "SELECT * from ACCOUNTS_DETAILS where ACCOUNT_ID=?";
            stmt = connection.prepareStatement(verify);
            stmt.setInt(1, LogID);
            ResultSet res1 = stmt.executeQuery();
            while (res1.next())
            {
                int ri = res1.getInt("ACCOUNT_ID");
                String rp = res1.getString("ACCOUNT_PASSWORD");
                if (ri == LogID && rp.equals(Logpass))
                {
                    if(res1.getBoolean("Status")==false)
                    {
                        label_Login_Warning.setText(res1.getString("Login_Message"));
                        return;
                    }
                    exist = true;
                    ID = LogID;
                    ((Node) Login_Proceed.getSource()).getScene().getWindow().hide();
                    Stage primaryStage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    Pane Functions_Pane = loader.load(getClass().getResource("Functions.fxml").openStream());
                    Functions_Controller userController = (Functions_Controller) loader.getController();
                    userController.User_name(res1.getString("First_Name"), res1.getString("Last_Name"), res1.getInt("ACCOUNT_ID"));
                    primaryStage.setTitle("Functions");
                    primaryStage.setScene(new Scene(Functions_Pane, 1080, 720));
                    primaryStage.show();
                }
                else
                    {
                    label_Login_Warning.setText("Enter valid credintials");
                    exist = false;
                    return;
                }
            }
            if (res1.next()==false)
            {
                label_Login_Warning.setText("Account does not exist");
                return;
            }
        }
    }
    public void Login_Back(ActionEvent Login_Back)throws Exception
    {
        ((Node)Login_Back.getSource()).getScene().getWindow().hide();
        Stage primaryStage=new Stage();
        FXMLLoader loader=new FXMLLoader();
        Pane Login_Pane= loader.load(getClass().getResource("Welcome.fxml"));
        primaryStage.setTitle("Bank of rising pheonix");
        primaryStage.setScene(new Scene(Login_Pane,1080,720));
        primaryStage.show();

    }
    public void Radio_Select(ActionEvent Radio_Select)
    {
        if (radio_Login_Manager.isSelected())
        {
            Manager= true;
            Customer=false;
        }
        if (radio_login_Customer.isSelected())
        {
            Customer= true;
            Manager=false;
        }
    }
}
