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
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Update_Account_Manger_Controller implements Initializable
{
    Connection connection=ConnectionClass.getConnection();
    static int ID;
    static boolean proceed=true;
    static Scanner temp=new Scanner(System.in);
    static BufferedReader temp1=new BufferedReader(new InputStreamReader(System.in));
    PreparedStatement stmt = null;

    @FXML
    public void initialize(URL location, ResourceBundle resources)
    {

    }
    public TextField txt_Update_Manager_Pass;
    public TextField txt_Update_Manager_Fname;
    public TextField txt_Update_Manager_Lname;
    public TextField txt_Update_Manager_Phone;
    public TextField txt_Update_Manager_Email;
    public Label label_Update_Manger_Success;
    public Label label_Update_Manager_Phone_Warning;
    public Label label_Update_Manager_Mail_Warning;

    public void Set(int tempID)throws Exception
    {
        ID=tempID;
    }
    public void Update_Manager_Proceed(ActionEvent Update_Manager_Proceed)throws Exception
    {
        PreparedStatement stmt=null;
        String Pass = txt_Update_Manager_Pass.getText();

        String First = txt_Update_Manager_Fname.getText();

        String Last = txt_Update_Manager_Lname.getText();

        long tempphonenum = Long.parseLong(txt_Update_Manager_Phone.getText());
        long phonenum = 0;
        int length = (int) ((Long.toString(tempphonenum).length()));
        if (length == 10)
        {
            phonenum = tempphonenum;
        }
        else
        {
            label_Update_Manager_Phone_Warning.setText("Enter valid 10-Digit number");
            proceed=false;
        }

        String tempmail = txt_Update_Manager_Email.getText();
        String mail = null;
        String sub = tempmail.substring(tempmail.indexOf('@'));
        if (sub.equals("@gmail.com") || sub.equals("@yahoo.com") || sub.equals("@vitap.ac.in"))
        {
            mail = tempmail;
        }
        else
        {
            label_Update_Manager_Mail_Warning.setText("Enter valid mail address");
            proceed=false;
        }

        if(proceed==false)
        {
            return;
        }

        String updatep = "update Manager_Account set ACCOUNT_PASSWORD=? where ACCOUNT_ID=?";
        stmt = connection.prepareStatement(updatep);
        stmt.setString(1, Pass);
        stmt.setInt(2,ID);
        stmt.execute();

        String updatef = "update Manager_Account set First_Name=? where ACCOUNT_ID=?";
        stmt = connection.prepareStatement(updatef);
        stmt.setString(1, First);
        stmt.setInt(2,ID);
        stmt.execute();

        String updatel = "update Manager_Account set Last_Name=? where ACCOUNT_ID=?";
        stmt = connection.prepareStatement(updatel);
        stmt.setString(1, Last);
        stmt.setInt(2,ID);
        stmt.execute();

        String updateph = "update Manager_Account set PHONE=? where ACCOUNT_ID=?";
        stmt = connection.prepareStatement(updateph);
        stmt.setLong(1, phonenum);
        stmt.setInt(2,ID);
        stmt.execute();

        String updatem = "update Manager_Account set EMAIL=? where ACCOUNT_ID=?";
        stmt = connection.prepareStatement(updatem);
        stmt.setString(1, mail);
        stmt.setInt(2,ID);
        stmt.execute();

        if(proceed)
        {
            label_Update_Manger_Success.setText("Update account sucessfully");
        }
    }
    public void Update_Manager_Back(ActionEvent Update_Manager_Back)throws Exception
    {
            ((Node)Update_Manager_Back.getSource()).getScene().getWindow().hide();
            Stage primaryStage=new Stage();
            FXMLLoader loader=new FXMLLoader();
            Pane Login_Pane= loader.load(getClass().getResource("Functions_Manager.fxml").openStream());
            primaryStage.setTitle("Functions");
            primaryStage.setScene(new Scene(Login_Pane, 1080, 720));
            primaryStage.show();
    }
}
