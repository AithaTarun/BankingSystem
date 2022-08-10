package application;

import java.sql.*;

public class ConnectionClass
{
    public static Connection connection;
    public static Connection getConnection()
    {
        try
        {
            String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url="jdbc:sqlserver://localhost:1433;"+"databaseName=Bank";
            String username="Bank";
            String password="123456";
            Class.forName(driver);
            connection =DriverManager.getConnection(url,username,password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return connection;
    }
}
