package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Update_Account_Controller implements Initializable
{
    Connection connection=ConnectionClass.getConnection();
    static int ID;
    static boolean proceed=true;
    static Scanner temp=new Scanner(System.in);
    static BufferedReader temp1=new BufferedReader(new InputStreamReader(System.in));

    @FXML
    public void initialize(URL location, ResourceBundle resources)
    {

    }
    public TextField txt_Update_Account_Pass;
    public TextField txt_Update_Account_Fname;
    public TextField txt_Update_Account_Lname;
    public TextField txt_Update_Account_Phone;
    public DatePicker txt_Update_Account_Dob;
    public TextField txt_Update_Account_Email;
    public TextField txt_Update_Account_Address;
    public Label label_Update_Account_Phone;
    public Label lable_Update_Account_Email;
    public Label lable_Update_Account_Sucess;
    public void User_ID(int User_ID)
    {
        ID = User_ID;
    }
    public void Update_Account_Proceed(ActionEvent Update_Account_Proceed)throws Exception
    {
        PreparedStatement stmt=null;
        String Pass = txt_Update_Account_Pass.getText();

        String First = txt_Update_Account_Fname.getText();

        String Last = txt_Update_Account_Lname.getText();

        long tempphonenum = Long.parseLong(txt_Update_Account_Phone.getText());
        long phonenum = 0;
        int length = (int) ((Long.toString(tempphonenum).length()));
        if (length == 10)
        {
            phonenum = tempphonenum;
        }
        else
        {
            label_Update_Account_Phone.setText("Enter valid 10-Digit number");
            proceed=false;
        }

        LocalDate Date=txt_Update_Account_Dob.getValue();
        String Dob=Date.toString();

        String tempmail = txt_Update_Account_Email.getText();
        String mail = null;
        String sub = tempmail.substring(tempmail.indexOf('@'));
        if (sub.equals("@gmail.com") || sub.equals("@yahoo.com") || sub.equals("@vitap.ac.in"))
        {
            mail = tempmail;
        }
        else
        {
            lable_Update_Account_Email.setText("Enter valid mail address");
            proceed=false;
        }

        String addr = txt_Update_Account_Address.getText();
        if(proceed==false)
        {
            return;
        }

        String updatep = "update ACCOUNTS_DETAILS set ACCOUNT_PASSWORD=? where ACCOUNT_ID=?";
        stmt = connection.prepareStatement(updatep);
        stmt.setString(1, Pass);
        stmt.setInt(2,ID);
        stmt.execute();

        String updatef = "update ACCOUNTS_DETAILS set First_Name=? where ACCOUNT_ID=?";
        stmt = connection.prepareStatement(updatef);
        stmt.setString(1, First);
        stmt.setInt(2,ID);
        stmt.execute();

        String updatel = "update ACCOUNTS_DETAILS set Last_Name=? where ACCOUNT_ID=?";
        stmt = connection.prepareStatement(updatel);
        stmt.setString(1, Last);
        stmt.setInt(2,ID);
        stmt.execute();

        String updateph = "update ACCOUNTS_DETAILS set PHONE=? where ACCOUNT_ID=?";
        stmt = connection.prepareStatement(updateph);
        stmt.setLong(1, phonenum);
        stmt.setInt(2,ID);
        stmt.execute();

        String updatedo = "update ACCOUNTS_DETAILS set Date_Of_Birth=? where ACCOUNT_ID=?";
        stmt = connection.prepareStatement(updatedo);
        stmt.setString(1, Dob);
        stmt.setInt(2,ID);
        stmt.execute();

        String updatem = "update ACCOUNTS_DETAILS set EMAIL=? where ACCOUNT_ID=?";
        stmt = connection.prepareStatement(updatem);
        stmt.setString(1, mail);
        stmt.setInt(2,ID);
        stmt.execute();

        String updatea = "update ACCOUNTS_DETAILS set Address=? where ACCOUNT_ID=?";
        stmt = connection.prepareStatement(updatea);
        stmt.setString(1, addr);
        stmt.setInt(2,ID);
        stmt.execute();

        if(proceed)
        {
            lable_Update_Account_Sucess.setText("Update account sucessfully");
        }
    }
    public void Update_Account_Back(ActionEvent Update_Account_Back)throws Exception
    {
        ((Node)Update_Account_Back.getSource()).getScene().getWindow().hide();
        Stage primaryStage=new Stage();
        FXMLLoader loader=new FXMLLoader();
        Pane Login_Pane= loader.load(getClass().getResource("Functions_Manager.fxml"));
        primaryStage.setTitle("Functions");
        primaryStage.setScene(new Scene(Login_Pane,1080,720));
        primaryStage.show();
    }
}
