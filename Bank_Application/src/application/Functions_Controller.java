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

public class Functions_Controller implements Initializable
{
    static int ID;
    PreparedStatement stmt = null;


    static Connection connection = ConnectionClass.getConnection();
    @FXML
    public Label lable_Functions_Welcome;
    public Label label_Functions_Check_Balance;
    public Label label_Funtions_Deposit;
    public Label label_Functions_Withdraw;
    public Label label_Functions_Transfer;
    public Label label_Functions_Loan_Message;
    public TextField txt_Functions_Deposit_Amount;
    public TextField txt_Functions_Withdraw;
    public TextField txt_Funtions_Transfer_Amount;
    public TextField txt_Functions_ToID;
    public TextField txt_Functions_Loan_Amount;
    public TextField txt_Functions_Loan_Tenture;
    static String ini_User_First_Name,ini_User_Last_Name;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        lable_Functions_Welcome.setText("Welcome" + " " + ini_User_First_Name + " " + ini_User_Last_Name);
    }
    public static ResultSet Table(int ID) throws Exception
    {
        PreparedStatement stmt=null;

        String verify="SELECT * from ACCOUNTS_DETAILS where ACCOUNT_ID=?";
        stmt= connection.prepareStatement(verify);
        stmt.setInt(1,ID);
        ResultSet res2=stmt.executeQuery();
        return res2;
    }

    public void User_name(String User_First_name, String User_Last_name, int User_ID)
    {

        ID = User_ID;
        ini_User_First_Name=User_First_name;
        ini_User_Last_Name=User_Last_name;
        lable_Functions_Welcome.setText("Welcome" + " " + User_First_name + " " + User_Last_name);
    }
    public void Check_balance(ActionEvent Check_balance) throws Exception
    {
        ResultSet res1=Table(ID);
        long Current_Amount = 0;
        while (res1.next())
        {
            Current_Amount = res1.getLong("Balance");
        }
        label_Functions_Check_Balance.setText("Current Balance="+Current_Amount);
    }
    public static java.sql.Timestamp getCurrentDatetime()
    {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }
    public void Deposit(ActionEvent Deposit)throws Exception
    {
        long Deposit_Amount=Long.parseLong(txt_Functions_Deposit_Amount.getText());
        PreparedStatement stmt=null,stmt1=null;
        long Current_Amount=0;
        label_Funtions_Deposit.setText("Amount"+" "+Long.toString(Deposit_Amount)+" "+"deposited");
        ResultSet res1=Table(ID);
        while(res1.next())
        {
            Current_Amount=res1.getLong("Balance");
            Deposit_Amount=Deposit_Amount+Current_Amount;
        }
        String Dep="exec Deposit @Deposit_Amount=?,@Account_ID=?";
        stmt= connection.prepareStatement(Dep);
        stmt.setLong(1, Deposit_Amount);
        stmt.setInt(2,ID);
        stmt.execute();

        java.sql.Timestamp date_time = getCurrentDatetime();
        String insert="insert into TRANSACTION_HISTORY_Table(Account_ID,Previous_Balance,Deposited_Balance,Current_Balance,Operation_Date_And_Time)values(?,?,?,?,?)";
        stmt1= connection.prepareStatement(insert);
        stmt1.setInt(1, ID);
        stmt1.setLong(2,Current_Amount);
        stmt1.setLong(3,Deposit_Amount-Current_Amount);
        stmt1.setLong(4,Deposit_Amount);
        stmt1.setTimestamp(5,date_time);
        stmt1.execute();
    }
    public void Withdraw(ActionEvent Withdraw)throws Exception
    {
        PreparedStatement stmt=null,stmt1=null;
        long Withdraw_Amount =Long.parseLong(txt_Functions_Withdraw.getText());
        ResultSet res1=Table(ID);
        long Current_Amount = 0;
        while (res1.next())
        {
            Current_Amount = res1.getLong("Balance");
        }
        if (Withdraw_Amount <= Current_Amount)
        {
            Current_Amount = Current_Amount - Withdraw_Amount;
            label_Functions_Withdraw.setText("Amount" +" " +Withdraw_Amount +" "+"Withdrawn");
            String update_balance="update ACCOUNTS_DETAILS Set Balance=? WHERE ACCOUNT_ID=?";
            stmt= connection.prepareStatement(update_balance);
            stmt.setLong(1,Current_Amount);
            stmt.setInt(2,ID);
            stmt.execute();
        }
        else
        {
            label_Functions_Withdraw.setText("Insufficient Amount in the account");
            return;
        }
        java.sql.Timestamp date_time = getCurrentDatetime();
        String insert="insert into TRANSACTION_HISTORY_Table(Account_ID,Previous_Balance,Withdrawn_Balance,Current_Balance,Operation_Date_And_Time)values(?,?,?,?,?)";
        stmt1= connection.prepareStatement(insert);
        stmt1.setInt(1, ID);
        stmt1.setLong(2,Current_Amount+Withdraw_Amount);
        stmt1.setLong(3,Withdraw_Amount);
        stmt1.setLong(4,Current_Amount);
        stmt1.setTimestamp(5,date_time);
        stmt1.execute();
    }
    public static boolean Verify(int checkID) throws Exception
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
    public void Transfer(ActionEvent Transfer)throws Exception
    {
        PreparedStatement stmt = null, stmt1 = null, stmt2 = null;

        int ID2 = Integer.parseInt(txt_Functions_ToID.getText());
        boolean checkexist2;
        checkexist2 = Verify(ID2);
        if ((checkexist2) && ID != ID2)
        {
            long Transfer_Amount = Long.parseLong(txt_Funtions_Transfer_Amount.getText());
            ResultSet res1 = Table(ID);
            ResultSet res2 = Table(ID2);
            long Current_Amount1 = 0;
            long Current_Amount2 = 0;
            while (res1.next() && res2.next())
            {
                Current_Amount1 = res1.getLong("Balance");
                Current_Amount2 = res2.getLong("Balance");
            }
            if (Transfer_Amount <= Current_Amount1)
            {
                Current_Amount1 = Current_Amount1 - Transfer_Amount;
                label_Functions_Transfer.setText("Amount" + " " + Transfer_Amount + " " + "Transfered" + " " + "to" + " " + ID2);
                String update_balance1 = "update ACCOUNTS_DETAILS Set Balance=? WHERE ACCOUNT_ID=?";
                stmt = getConnection().prepareStatement(update_balance1);
                stmt.setLong(1, Current_Amount1);
                stmt.setInt(2, ID);
                stmt.execute();
                Current_Amount2 = Current_Amount2 + Transfer_Amount;
                String update_balance2 = "update ACCOUNTS_DETAILS Set Balance=? WHERE ACCOUNT_ID=?";
                stmt = getConnection().prepareStatement(update_balance2);
                stmt.setLong(1, Current_Amount2);
                stmt.setInt(2, ID2);
                stmt.execute();
            }
            else
            {
                label_Functions_Transfer.setText("Insufficient Amount in the account");
                return;
            }
            java.sql.Timestamp date_time = getCurrentDatetime();

            String insert="insert into TRANSACTION_HISTORY_Table(Account_ID,Previous_Balance,Deposited_by_Transaction,Current_Balance,Transfered_From_Account_ID,Operation_Date_And_Time)values(?,?,?,?,?,?)";
            stmt1= getConnection().prepareStatement(insert);
            stmt1.setInt(1, ID2);
            stmt1.setLong(2,Current_Amount2-Transfer_Amount);
            stmt1.setLong(3,Transfer_Amount);
            stmt1.setLong(4,Current_Amount2);
            stmt1.setLong(5,ID);
            stmt1.setTimestamp(6,date_time);
            stmt1.execute();

            String insert1="insert into TRANSACTION_HISTORY_Table(Account_ID,Previous_Balance,Withdrawn_by_Transaction,Current_Balance,Transferd_To_Account_ID,Operation_Date_And_Time)values(?,?,?,?,?,?)";
            stmt2= getConnection().prepareStatement(insert1);
            stmt2.setInt(1, ID);
            stmt2.setLong(2,Current_Amount1+Transfer_Amount);
            stmt2.setLong(3,Transfer_Amount);
            stmt2.setLong(4,Current_Amount1);
            stmt2.setInt(5,ID2);
            stmt2.setTimestamp(6,date_time);
            stmt2.execute();
        }
        else
        {
            label_Functions_Transfer.setText("Enter valid account ID");
            return;
        }
    }
    public void Transaction_History(ActionEvent Transaction_History)throws Exception
    {
        ((Node)Transaction_History.getSource()).getScene().getWindow().hide();
        Stage primaryStage=new Stage();
        FXMLLoader loader=new FXMLLoader();
        Pane Transaction_Pane= loader.load(getClass().getResource("Transaction_History_Table.fxml").openStream());
        Transaction_History_Table_Controller IDController = (Transaction_History_Table_Controller) loader.getController();
        IDController.Set(TRANSACTION_HISTORY_Table(ID));
        IDController.WithR(TRANSACTION_HISTORY_Table(ID));
        IDController.DepoR(TRANSACTION_HISTORY_Table(ID));
        IDController.Transfer_With(TRANSACTION_HISTORY_Table(ID));
        IDController.Transfer_Depo(TRANSACTION_HISTORY_Table(ID));
        IDController.SetAll(TRANSACTION_HISTORY_Table(ID));
        primaryStage.setTitle("Transaction history");
        primaryStage.setScene(new Scene(Transaction_Pane,1920,900));
        primaryStage.show();
    }
    public void Apply_Loan(ActionEvent Apply_Loan)throws Exception
    {
        ResultSet res=Loan_Table(ID);
        while (res.next())
        {
            if(res.getInt("ACCOUNT_ID")==ID)
            {
                label_Functions_Loan_Message.setText("Cannot apply for new loan clear previous loan");
                return;
            }
        }
        if(txt_Functions_Loan_Amount.getText()==null || txt_Functions_Loan_Tenture.getText()==null)
        {
            label_Functions_Loan_Message.setText("Insert loan amount and tenture");
            return;
        }
        long Loan_Amount=Long.parseLong(txt_Functions_Loan_Amount.getText());
        int Loan_Tenture=Integer.parseInt(txt_Functions_Loan_Tenture.getText());

        String Loan_Insert = "exec UPDATE_LOAN @ACCOUNT_ID=?,@LOAN_AMOUNT=?,@LOAN_TENTURE=?";
        stmt = getConnection().prepareStatement(Loan_Insert);
        stmt.setInt(1, ID);
        stmt.setLong(2, Loan_Amount);
        stmt.setInt(3, Loan_Tenture);
        stmt.execute();
        label_Functions_Loan_Message.setText("Loan request have been placed");
    }
    public void Loan_Status(ActionEvent Loan_Status)throws Exception
    {
        String Loan_Message="SELECT * FROM LOAN WHERE ACCOUNT_ID=?";
        stmt= getConnection().prepareStatement(Loan_Message);
        stmt.setInt(1,ID);
        ResultSet res3=stmt.executeQuery();
        while(res3.next())
        {
            if (res3.getBoolean("STATUS"))
            {
                label_Functions_Loan_Message.setText(res3.getString("MESSAGE") +" "+"and your Loan ID is:" + res3.getInt("LOAN_ID"));
            }
            else
                {
                label_Functions_Loan_Message.setText(res3.getString("MESSAGE"));
            }
        }
    }
    public void Loan_Details(ActionEvent Loan_Details)throws Exception
    {
        ((Node)Loan_Details.getSource()).getScene().getWindow().hide();
        Stage primaryStage=new Stage();
        FXMLLoader loader=new FXMLLoader();
        Pane Loan_Pane= loader.load(getClass().getResource("Loan_Details.fxml"));
        Loan_Details_Controller IDController = new Loan_Details_Controller();/*(Loan_Details_Controller) loader.getController();*/
        IDController.Set(ID);
        primaryStage.setTitle("Loan details");
        primaryStage.setScene(new Scene(Loan_Pane,1080,720));
        primaryStage.show();
    }

    public void Logout(ActionEvent Logout)throws Exception
    {
        ((Node)Logout.getSource()).getScene().getWindow().hide();
        Stage primaryStage=new Stage();
        FXMLLoader loader=new FXMLLoader();
        Pane Functions_Pane= loader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(Functions_Pane,1080,720));
        primaryStage.show();
    }
    public static ResultSet TRANSACTION_HISTORY_Table(int ID) throws Exception
    {
        PreparedStatement stmt=null;

        String verify="SELECT * from TRANSACTION_HISTORY_Table where ACCOUNT_ID=? ORDER by Operation_Date_And_Time";
        stmt= getConnection().prepareStatement(verify);
        stmt.setInt(1,ID);
        ResultSet res3=stmt.executeQuery();
        return res3;
    }
    public static ResultSet Loan_Table(int ID) throws Exception
    {
        PreparedStatement stmt=null;

        String verify="SELECT * from LOAN where ACCOUNT_ID=?";
        stmt= getConnection().prepareStatement(verify);
        stmt.setInt(1,ID);
        ResultSet res3=stmt.executeQuery();
        return res3;
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
