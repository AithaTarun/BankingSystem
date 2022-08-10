import java.sql.*;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.util.Date;
import java.sql.Timestamp;

public class Bank_database
{
    static int ID;
    static boolean exist=false;
    static Scanner temp=new Scanner(System.in);
    static BufferedReader temp1=new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[]args) throws Exception

    {
            getConnection();
            while(true)
            {
                System.out.printf("Enter option\n1.Create new account\n2.Login\n3.Delete Account\n4.Update Account\n5.Deposit\n6.Withdraw\n7.Check balance\n8.Transfer money\n9.Transaction history\n10.Logout\n11.Exit\n");
                int op = temp.nextInt();
                switch (op)
                {
                    case 1:
                        {
                        Create_Account();
                        break;
                    }
                    case 2:
                    {
                        System.out.println("Enter user ID");
                        int LogID=temp.nextInt();
                        System.out.println("Password");
                        String LogPass=temp1.readLine();
                        Login(LogID,LogPass);
                        break;
                    }
                    case 3:
                        {
                        Delete_Account();
                        break;
                    }
                    case 4:
                        {
                        Update_Account();
                        break;
                    }
                    case 5:
                        {
                        Deposit();
                        break;
                    }
                    case 6:
                        {
                        Withdraw();
                        break;
                    }
                    case 7:
                        {
                        Check_balance();
                        break;
                    }
                    case 8:
                        {
                        Tranfer();
                        break;
                    }
                    case 9:
                        {
                        Transaction_History();
                        break;
                    }
                    case 10:
                    {
                        Logout();
                        break;
                    }
                    case 11:
                        {
                        System.exit(0);
                        break;
                    }
                    default:
                        {
                        System.out.println("Invalid option");
                        break;
                    }
                }
            }
    }
    public static void Login(int LogID,String Logpass) throws Exception
    {

        PreparedStatement stmt=null;

        String verify="SELECT * from ACCOUNTS_DETAILS where ACCOUNT_ID=?";
        stmt= getConnection().prepareStatement(verify);
        stmt.setInt(1,LogID);
        ResultSet res1=stmt.executeQuery();
        while(res1.next())
        {
            int ri=res1.getInt("ACCOUNT_ID");
            String rp=res1.getString("ACCOUNT_PASSWORD");
            if(ri==LogID && rp.equals(Logpass))
            {
                exist= true;
                ID=LogID;
                System.out.println("Login successful");
            }
            else
            {
                System.out.println("Enter valid credintials");
                exist= false;
            }
        }
    }
    public static void Logout()
    {
        exist=false;
        ID=0;
    }
    public static boolean Verify(int checkID) throws Exception
    {

        PreparedStatement stmt=null;

        String verify="SELECT * from ACCOUNTS_DETAILS where ACCOUNT_ID=?";
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
    public static ResultSet Table(int ID) throws Exception
    {
        PreparedStatement stmt=null;

        String verify="SELECT * from ACCOUNTS_DETAILS where ACCOUNT_ID=?";
        stmt= getConnection().prepareStatement(verify);
        stmt.setInt(1,ID);
        ResultSet res2=stmt.executeQuery();
        return res2;
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
    public static void Create_Account() throws Exception
    {
        PreparedStatement stmt = null;

        System.out.println("Enter Account ID to create account neither than zero");
        int NewID = temp.nextInt();
        if(NewID<=0)
        {
            System.out.println("Enter valid ID");
            return;
        }
        else
        {
        boolean checkexist;
        checkexist = Verify(NewID);
        if (exist)
        {
            System.out.println("To create new account logout from existing account");
            return;
        }
        else
            {
            if (checkexist)
            {
                System.out.println("Account already exist");
            }
            else
                {

                System.out.println("Set password for the account");
                String Pass = temp1.readLine();

                System.out.println("Enter First name");
                String First = temp1.readLine();

                System.out.println("Enter Last name");
                String Last = temp1.readLine();

                System.out.println("Enter Phone number");
                long tempphonenum = temp.nextLong();
                long phonenum = 0;
                int length = (int) ((Long.toString(tempphonenum).length()));
                if (length == 10)
                {
                    phonenum = tempphonenum;
                }
                else
                    {
                    System.out.println("Enter valid 10-digit phone number");
                    return;
                }

                System.out.println("Enter date of birth");
                String Dob=temp1.readLine();

                System.out.println("Enter Email address");
                String tempmail = temp1.readLine();
                String mail = null;
                String sub = tempmail.substring(tempmail.indexOf('@'));
                if (sub.equals("@gmail.com") || sub.equals("@yahoo.com") || sub.equals("@vitap.ac.in"))
                {
                    mail = tempmail;
                }
                else
                    {
                    System.out.println("Enter valid mail address");
                    return;
                }

                System.out.println("Enter address");
                String addr = temp1.readLine();

                String insert = "exec CREATE_ACCOUNT @ACCOUNT_ID=?,@ACCOUNT_PASSWORD=?,@FIRST_NAME=?,@LAST_NAME=?,@PHONE=?,@Date_Of_Birth=?,@EMAIL=?,@Address=?";
                stmt = getConnection().prepareStatement(insert);
                stmt.setInt(1, NewID);
                stmt.setString(2, Pass);
                stmt.setString(3, First);
                stmt.setString(4, Last);
                stmt.setLong(5, phonenum);
                stmt.setString(6,Dob);
                stmt.setString(7, mail);
                stmt.setString(8, addr);
                stmt.execute();
                System.out.println("Account created");
            }
        }

        }
    }
    public static void Delete_Account() throws Exception
    {
        PreparedStatement stmt=null;

        if(exist)
        {
            String Delete="DELETE ACCOUNTS_DETAILS where ACCOUNT_ID=?";
            stmt= getConnection().prepareStatement(Delete);
            stmt.setInt(1,ID);
            stmt.execute();
            System.out.printf("Account %d deleted\n",ID);
            Logout();
        }
        else
        {
            System.out.println("Login into the account");
        }
    }
    public static void Update_Account() throws Exception
    {
        PreparedStatement stmt=null;

        if(exist)
        {
            while (true)
            {
                System.out.printf("Enter option\n1)Update Password\n2)Update First Name\n3)Update Last Name\n4)Phone number\n5)Mail address\n6)Address\n7)Exit\n");
                int op = temp.nextInt();
                switch (op)
                {
                    case 1:
                        {
                        System.out.println("Enter new password");
                        String pass = temp1.readLine();
                        String update = "update ACCOUNTS_DETAILS set ACCOUNT_PASSWORD=? where ACCOUNT_ID=?";
                        stmt = getConnection().prepareStatement(update);
                        stmt.setString(1, pass);
                        stmt.setInt(2, ID);
                        stmt.execute();
                        System.out.printf("Account %d password updated\n", ID);
                        break;
                    }
                    case 2:
                        {
                        System.out.println("Enter first name to be update");
                        String first = temp1.readLine();
                        String update = "update ACCOUNTS_DETAILS set FIRST_NAME=? where ACCOUNT_ID=?";
                        stmt = getConnection().prepareStatement(update);
                        stmt.setString(1, first);
                        stmt.setInt(2, ID);
                        stmt.execute();
                        System.out.printf("Account %d first name updated\n", ID);
                        break;
                    }
                    case 3:
                        {
                        System.out.println("Enter last name to be update");
                        String last = temp1.readLine();
                        String update = "update ACCOUNTS_DETAILS set LAST_NAME=? where ACCOUNT_ID=?";
                        stmt = getConnection().prepareStatement(update);
                        stmt.setString(1, last);
                        stmt.setInt(2, ID);
                        stmt.execute();
                        System.out.printf("Account %d last name updated\n", ID);
                        break;
                    }
                    case 4:
                        {
                        System.out.println("Enter phone number to be update");
                        long phone = temp.nextLong();
                        String update = "update ACCOUNTS_DETAILS set PHONE=? where ACCOUNT_ID=?";
                        stmt = getConnection().prepareStatement(update);
                        stmt.setLong(1, phone);
                        stmt.setInt(2, ID);
                        stmt.execute();
                        System.out.printf("Account %d phone number updated\n", ID);
                        break;
                    }
                    case 5: {
                        System.out.println("Enter mail ID to be update");
                        String mail = temp1.readLine();
                        String update = "update ACCOUNTS_DETAILS set EMAIL=? where ACCOUNT_ID=?";
                        stmt = getConnection().prepareStatement(update);
                        stmt.setString(1, mail);
                        stmt.setInt(2, ID);
                        stmt.execute();
                        System.out.printf("Account %d mail address updated\n", ID);
                        break;
                    }
                    case 6:
                        {
                        System.out.println("Enter address to be update");
                        String addr = temp1.readLine();
                        String update = "update ACCOUNTS_DETAILS set Address=? where ACCOUNT_ID=?";
                        stmt = getConnection().prepareStatement(update);
                        stmt.setString(1, addr);
                        stmt.setInt(2, ID);
                        stmt.execute();
                        System.out.printf("Account %d address updated\n", ID);
                        break;
                    }
                    case 7:
                        {
                        return;
                    }
                    case 8:
                        {
                        System.out.println("Invalid option");
                        break;
                    }
                }
            }
        }
        else
        {
            System.out.println("Login into the account");
        }

    }
    public static void Deposit() throws Exception
    {
        PreparedStatement stmt=null,stmt1=null;
        long Current_Amount=0;

        if(exist)
        {
            System.out.println("Enter amount to deposit");
            long Deposit_Amount = temp.nextLong();
            System.out.printf("%d is deposited into %d account\n",Deposit_Amount,ID);
            ResultSet res1=Table(ID);
            while(res1.next())
            {
                Current_Amount=res1.getLong("Balance");
                Deposit_Amount=Deposit_Amount+Current_Amount;
                System.out.printf("Current Amount=%d\n",Deposit_Amount);
            }
            String Deposit="exec Deposit @Deposit_Amount=?,@Account_ID=?";
            stmt= getConnection().prepareStatement(Deposit);
            stmt.setLong(1, Deposit_Amount);
            stmt.setInt(2,ID);
            stmt.execute();

            java.sql.Timestamp date_time = getCurrentDatetime();
            String insert="insert into TRANSACTION_HISTORY_Table(Account_ID,Previous_Balance,Withdrawn_Balance,Deposited_Balance,Current_Balance,Operation_Date_And_Time)values(?,?,?,?,?,?)";
            stmt1= getConnection().prepareStatement(insert);
            stmt1.setInt(1, ID);
            stmt1.setLong(2,Current_Amount);
            stmt1.setLong(3,0);
            stmt1.setLong(4,Deposit_Amount-Current_Amount);
            stmt1.setLong(5,Deposit_Amount);
            stmt1.setTimestamp(6,date_time);
            stmt1.execute();
        }
        else
        {
            System.out.println("Login into the account");
        }
    }
    public static void Withdraw() throws Exception
    {
        PreparedStatement stmt=null,stmt1=null;

        if(exist)
        {
            System.out.println("Enter amount to withdraw");
            long Withdraw_Amount = temp.nextLong();
            ResultSet res1=Table(ID);
            long Current_Amount = 0;
            while (res1.next())
            {
                Current_Amount = res1.getLong("Balance");
            }
            if (Withdraw_Amount <= Current_Amount)
            {
                Current_Amount = Current_Amount - Withdraw_Amount;
                System.out.printf("Amount %d withdrawn\n", Withdraw_Amount);
                System.out.printf("Current amount=%d\n",Current_Amount);
                String update_balance="update ACCOUNTS_DETAILS Set Balance=? WHERE ACCOUNT_ID=?";
                stmt= getConnection().prepareStatement(update_balance);
                stmt.setLong(1,Current_Amount);
                stmt.setInt(2,ID);
                stmt.execute();
            }
            else
                {
                    System.out.println("Insufficient Amount in the account");
                }
            java.sql.Timestamp date_time = getCurrentDatetime();
            String insert="insert into TRANSACTION_HISTORY_Table(Account_ID,Previous_Balance,Withdrawn_Balance,Deposited_Balance,Current_Balance,Operation_Date_And_Time)values(?,?,?,?,?,?)";
            stmt1= getConnection().prepareStatement(insert);
            stmt1.setInt(1, ID);
            stmt1.setLong(2,Current_Amount+Withdraw_Amount);
            stmt1.setLong(3,Withdraw_Amount);
            stmt1.setLong(4,0);
            stmt1.setLong(5,Current_Amount);
            stmt1.setTimestamp(6,date_time);
            stmt1.execute();
        }

        else
        {
            System.out.println("Login into the account");
        }
    }
    public static java.sql.Timestamp getCurrentDatetime()
    {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }
    public static void Check_balance() throws Exception
    {

        if(exist)
        {
            ResultSet res1=Table(ID);
            long Current_Amount = 0;
            while (res1.next())
            {
                Current_Amount = res1.getLong("Balance");
            }
            System.out.println("Current Balance="+Current_Amount);
        }

        else
        {
            System.out.println("Login into the account");
        }
    }
    public static void Tranfer() throws Exception
    {
        PreparedStatement stmt=null,stmt1=null,stmt2=null;

        System.out.println("Enter user ID to be tranfered");
        int ID2=temp.nextInt();
        boolean checkexist2;
        checkexist2=Verify(ID2);

        if((exist&&checkexist2 )&& ID!=ID2)
        {
            System.out.println("Enter amount to tranfer");
            long Transfer_Amount = temp.nextLong();
            ResultSet res1=Table(ID);
            ResultSet res2=Table(ID2);
            long Current_Amount1 = 0;
            long Current_Amount2=0;
            while (res1.next()&&res2.next())
            {
                Current_Amount1 = res1.getLong("Balance");
                Current_Amount2 = res2.getLong("Balance");
                System.out.println(Current_Amount1);
                System.out.println(Current_Amount2);
            }
            if (Transfer_Amount <= Current_Amount1)
            {
                Current_Amount1 = Current_Amount1 - Transfer_Amount;
                System.out.printf("Amount %d Transfered to %d\n", Transfer_Amount,ID2);
                System.out.printf("Current amount in %d=%d\n",ID,Current_Amount1);
                String update_balance1="update ACCOUNTS_DETAILS Set Balance=? WHERE ACCOUNT_ID=?";
                stmt= getConnection().prepareStatement(update_balance1);
                stmt.setLong(1,Current_Amount1);
                stmt.setInt(2,ID);
                stmt.execute();
                Current_Amount2=Current_Amount2+Transfer_Amount;
                System.out.printf("Current amount in %d=%d\n",ID2,Current_Amount2);
                String update_balance2="update ACCOUNTS_DETAILS Set Balance=? WHERE ACCOUNT_ID=?";
                stmt= getConnection().prepareStatement(update_balance2);
                stmt.setLong(1,Current_Amount2);
                stmt.setInt(2,ID2);
                stmt.execute();
            }
            else
            {
                System.out.println("Insufficient Amount in the account");
            }
            java.sql.Timestamp date_time = getCurrentDatetime();

            String insert="insert into TRANSACTION_HISTORY_Table(Account_ID,Previous_Balance,Withdrawn_Balance,Deposited_Balance,Current_Balance,Operation_Date_And_Time)values(?,?,?,?,?,?)";
            stmt1= getConnection().prepareStatement(insert);
            stmt1.setInt(1, ID2);
            stmt1.setLong(2,Current_Amount2-Transfer_Amount);
            stmt1.setLong(3,0);
            stmt1.setLong(4,Transfer_Amount);
            stmt1.setLong(5,Current_Amount2);
            stmt1.setTimestamp(6,date_time);
            stmt1.execute();

            String insert1="insert into TRANSACTION_HISTORY_Table(Account_ID,Previous_Balance,Withdrawn_Balance,Deposited_Balance,Current_Balance,Operation_Date_And_Time)values(?,?,?,?,?,?)";
            stmt2= getConnection().prepareStatement(insert1);
            stmt2.setInt(1, ID);
            stmt2.setLong(2,Current_Amount1+Transfer_Amount);
            stmt2.setLong(3,Transfer_Amount);
            stmt2.setLong(4,0);
            stmt2.setLong(5,Current_Amount1);
            stmt2.setTimestamp(6,date_time);
            stmt2.execute();
        }

        else
        {
            System.out.println("Login into the account");
        }
    }
    public static void Transaction_History() throws Exception
    {
        if(exist)
        {
            ResultSet res=TRANSACTION_HISTORY_Table(ID);
            while (res.next())
            {
                int Account_ID=res.getInt("Account_ID");
                long Previous_Balance =res.getLong("Previous_Balance");
                long Withdrawn_Balance=res.getLong("Withdrawn_Balance");
                long Deposited_Balance=res.getLong("Deposited_Balance");
                long Current_Balance=res.getLong("Current_Balance");
                Date Operation_Date=res.getDate("Operation_Date_And_Time");
                Date Operation_Time=res.getTime("Operation_Date_And_Time");
                System.out.println("Account id     Previous balance     Withdrawn balance     Deposited balance     Current balance     Date       Time\n");
                System.out.printf("   %d              %d                   %d                 %d                 %d      %s  %s\n",Account_ID,Previous_Balance,Withdrawn_Balance,Deposited_Balance,Current_Balance,Operation_Date,Operation_Time);

            }
        }
        else
        {
            System.out.println("Login into the account");
        }
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
