package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.*;
import java.lang.*;
import java.time.LocalDate;
import java.util.*;
import java.io.*;
import java.sql.Connection;
public class Create_Manager_Account_Controller
{
    Connection connection=ConnectionClass.getConnection();
    static boolean proceed=true;
    static Scanner temp=new Scanner(System.in);
    static BufferedReader temp1=new BufferedReader(new InputStreamReader(System.in));

    @FXML
    public TextField txt_Create_Account_ID;
    public TextField txt_Create_Account_Pass;
    public TextField txt_Create_Account_Fname;
    public TextField txt_Create_Account_Lname;
    public TextField txt_Create_Account_Phone;
    public TextField txt_Create_Account_Email;
    public Label lable_Create_Account_ID;
    public Label label_Create_Account_Phone;
    public Label lable_Create_Account_Email;
    public Label lable_Create_Account_Sucess;


    public static boolean Verify_Manager(int checkID) throws Exception
    {

        PreparedStatement stmt=null;

        String verify="SELECT * from Manager_Account where ACCOUNT_ID=?";
        stmt= getConnection().prepareStatement(verify);
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
    public void Create_Account_Proceed(ActionEvent Create_Account_Proceed) throws Exception
    {
        PreparedStatement stmt=null;

        int NewID = Integer.parseInt(txt_Create_Account_ID.getText());
            if (NewID <= 0)
            {
                lable_Create_Account_ID.setText("Enter valid ID");
                proceed = false;
            }
            else
                {
                boolean checkexist;
                checkexist = Verify_Manager(NewID);
                if (checkexist)
                {
                    lable_Create_Account_ID.setText("Account already exist");
                    proceed = false;
                }
                else
                    {

                    String Pass = txt_Create_Account_Pass.getText();

                    String First = txt_Create_Account_Fname.getText();

                    String Last = txt_Create_Account_Lname.getText();

                    long tempphonenum = Long.parseLong(txt_Create_Account_Phone.getText());
                    long phonenum = 0;
                    int length = (int) ((Long.toString(tempphonenum).length()));
                    if (length == 10) {
                        phonenum = tempphonenum;
                    } else
                        {

                        label_Create_Account_Phone.setText("Enter valid 10-Digit number");
                        proceed = false;
                    }

                    String tempmail = txt_Create_Account_Email.getText();
                    String mail = null;
                    String sub = tempmail.substring(tempmail.indexOf('@'));
                    if (sub.equals("@gmail.com") || sub.equals("@yahoo.com") || sub.equals("@vitap.ac.in")) {
                        mail = tempmail;
                    }
                    else
                        {
                        lable_Create_Account_Email.setText("Enter valid mail address");
                        proceed = false;
                    }

                    if (proceed == false)
                    {
                        return;
                    }
                    String insert = "exec CREATE_ACCOUNT_Manager @ACCOUNT_ID=?,@ACCOUNT_PASSWORD=?,@FIRST_NAME=?,@LAST_NAME=?,@PHONE=?,@EMAIL=?";
                    stmt = getConnection().prepareStatement(insert);
                    stmt.setInt(1, NewID);
                    stmt.setString(2, Pass);
                    stmt.setString(3, First);
                    stmt.setString(4, Last);
                    stmt.setLong(5, phonenum);
                    stmt.setString(6, mail);
                    stmt.execute();
                }
                if (proceed)
                {
                    lable_Create_Account_Sucess.setText("Account created sucessfully");
                }
            }
    }
    public void Create_Account_Back(ActionEvent Create_Account_Back)throws Exception
    {
        ((Node) Create_Account_Back.getSource()).getScene().getWindow().hide();
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane Credits_pane = loader.load(getClass().getResource("Functions_Manager.fxml"));
        primaryStage.setTitle("Functions");
        primaryStage.setScene(new Scene(Credits_pane, 1080, 720));
        primaryStage.show();
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
