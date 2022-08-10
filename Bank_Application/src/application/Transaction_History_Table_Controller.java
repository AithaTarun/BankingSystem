package application;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jdk.nashorn.internal.objects.annotations.Where;

import java.lang.*;

public class Transaction_History_Table_Controller implements Initializable
{
    private ResultSet res;
    public static ResultSet resw,resd,restw,restd,resall;
    private int ID;
    @FXML
    private TableView<Model_Table> table_Functions_Transaction;
    @FXML
    private TableColumn<Model_Table,Integer> table_Column_ID;
    @FXML
    private TableColumn<Model_Table,Long>table_Column_Pre_Bal;
    @FXML
    private TableColumn<Model_Table,Long>table_Column_With_Bal;
    @FXML
    private TableColumn<Model_Table,Long>table_Column_Depo_Bal;
    @FXML
    private TableColumn<Model_Table,Long>table_Column_With_Tran;
    @FXML
    private TableColumn<Model_Table,Long>table_Column_Depo_Tran;
    @FXML
    private TableColumn<Model_Table,Long>table_Column_Current_Bal;
    @FXML
    private TableColumn<Model_Table,Long>table_Column_From_ID;
    @FXML
    private TableColumn<Model_Table,Long>table_Column_To_ID;
    @FXML
    private TableColumn<Model_Table,Timestamp>table_Column_Date_Time;

    static Connection connection=ConnectionClass.getConnection();

    private ObservableList<Model_Table>oblist= FXCollections.observableArrayList();
    private ObservableList<Model_Table>oblist1= FXCollections.observableArrayList();
    private ObservableList<Model_Table>oblist2= FXCollections.observableArrayList();
    private ObservableList<Model_Table>oblist3= FXCollections.observableArrayList();
    private ObservableList<Model_Table>oblist4= FXCollections.observableArrayList();
    private ObservableList<Model_Table>oblist5= FXCollections.observableArrayList();



    public void Set(ResultSet resf)throws Exception
    {
        res=resf;
        while (res.next())
        {
            oblist.add(new Model_Table(res.getInt(1),res.getLong(2),res.getLong(3),res.getLong(4),res.getLong(5),res.getLong(6),res.getLong(7),res.getInt(8),res.getInt(9),res.getTimestamp(10)));
        }
    }

    public void SetAll(ResultSet resf)throws Exception
    {
        resall=resf;
    }

    public void WithR(ResultSet resf)throws Exception
    {
        resw=resf;
    }

    public void DepoR(ResultSet resf)throws Exception
    {
        resd=resf;
    }

    public void Transfer_With(ResultSet resf)throws Exception
    {
        restw=resf;
    }
    public void Transfer_Depo(ResultSet resf)throws Exception
    {
        restd=resf;
    }

    public void initialize(URL location, ResourceBundle resources)
    {
        table_Column_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        table_Column_Pre_Bal.setCellValueFactory(new PropertyValueFactory<>("Prev"));
        table_Column_With_Bal.setCellValueFactory(new PropertyValueFactory<>("With"));
        table_Column_Depo_Bal.setCellValueFactory(new PropertyValueFactory<>("Depo"));
        table_Column_With_Tran.setCellValueFactory(new PropertyValueFactory<>("Trans_With"));
        table_Column_Depo_Tran.setCellValueFactory(new PropertyValueFactory<>("Trans_Depo"));
        table_Column_Current_Bal.setCellValueFactory(new PropertyValueFactory<>("Current"));
        table_Column_From_ID.setCellValueFactory(new PropertyValueFactory<>("Trans_ID_From"));
        table_Column_To_ID.setCellValueFactory(new PropertyValueFactory<>("Trans_ID_To"));
        table_Column_Date_Time.setCellValueFactory(new PropertyValueFactory<>("Date_Time"));

        table_Functions_Transaction.setItems(oblist);
    }

    public void Table_Back(ActionEvent Table_Back)throws Exception
    {
        ((Node)Table_Back.getSource()).getScene().getWindow().hide();
        Stage primaryStage=new Stage();
        FXMLLoader loader=new FXMLLoader();
        Pane Table_Pane= loader.load(getClass().getResource("Functions.fxml"));
        primaryStage.setTitle("Functions");
        primaryStage.setScene(new Scene(Table_Pane,1080,720));
        primaryStage.show();
    }

    public void Table_With(ActionEvent Table_With)throws Exception
    {
        while (resw.next())
        {
            if(resw.getLong("Withdrawn_Balance")!=0)
            {
                oblist1.add(new Model_Table(resw.getInt(1),resw.getLong(2),resw.getLong(3),resw.getLong(4),resw.getLong(5),resw.getLong(6),resw.getLong(7),resw.getInt(8),resw.getInt(9),resw.getTimestamp(10)));
            }
        }
        table_Functions_Transaction.setItems(oblist1);
        table_Column_With_Bal.setVisible(true);
        table_Column_Depo_Bal.setVisible(false);
        table_Column_With_Tran.setVisible(false);
        table_Column_Depo_Tran.setVisible(false);
        table_Column_From_ID.setVisible(false);
        table_Column_To_ID.setVisible(false);
    }
    public void Table_Deposit(ActionEvent Table_Deposit)throws Exception
    {
        while (resd.next())
        {
            if(resd.getLong("Deposited_Balance")!=0)
            {
                oblist2.add(new Model_Table(resd.getInt(1),resd.getLong(2),resd.getLong(3),resd.getLong(4),resd.getLong(5),resd.getLong(6),resd.getLong(7),resd.getInt(8),resd.getInt(9),resd.getTimestamp(10)));
            }
        }
        table_Functions_Transaction.setItems(oblist2);
        table_Column_With_Bal.setVisible(false);
        table_Column_Depo_Bal.setVisible(true);
        table_Column_With_Tran.setVisible(false);
        table_Column_Depo_Tran.setVisible(false);
        table_Column_From_ID.setVisible(false);
        table_Column_To_ID.setVisible(false);
    }
    public void Table_Transfer_With(ActionEvent Table_Transfer_With)throws Exception
    {
        while (restw.next())
        {
            if(restw.getLong("Withdrawn_by_Transaction")!=0)
            {
                oblist3.add(new Model_Table(restw.getInt(1),restw.getLong(2),restw.getLong(3),restw.getLong(4),restw.getLong(5),restw.getLong(6),restw.getLong(7),restw.getInt(8),restw.getInt(9),restw.getTimestamp(10)));
            }
        }
        table_Functions_Transaction.setItems(oblist3);
        table_Column_With_Bal.setVisible(false);
        table_Column_Depo_Bal.setVisible(false);
        table_Column_With_Tran.setVisible(true);
        table_Column_Depo_Tran.setVisible(false);
        table_Column_From_ID.setVisible(false);
        table_Column_To_ID.setVisible(true);
    }
    public void Table_Transfer_Depo(ActionEvent Table_Transfer_Depo)throws Exception
    {
        while (restd.next())
        {
            if(restd.getLong("Deposited_by_Transaction")!=0)
            {
                oblist4.add(new Model_Table(restd.getInt(1),restd.getLong(2),restd.getLong(3),restd.getLong(4),restd.getLong(5),restd.getLong(6),restd.getLong(7),restd.getInt(8),restd.getInt(9),restd.getTimestamp(10)));
            }
        }
        table_Functions_Transaction.setItems(oblist4);
        table_Column_With_Bal.setVisible(false);
        table_Column_Depo_Bal.setVisible(false);
        table_Column_With_Tran.setVisible(false);
        table_Column_Depo_Tran.setVisible(true);
        table_Column_From_ID.setVisible(true);
        table_Column_To_ID.setVisible(false);
    }
    public void Table_All(ActionEvent Table_All)throws Exception
    {
        while (resall.next())
        {
            oblist5.add(new Model_Table(resall.getInt(1),resall.getLong(2),resall.getLong(3),resall.getLong(4),resall.getLong(5),resall.getLong(6),resall.getLong(7),resall.getInt(8),resall.getInt(9),resall.getTimestamp(10)));
        }
        table_Functions_Transaction.setItems(oblist5);
        table_Column_With_Bal.setVisible(true);
        table_Column_Depo_Bal.setVisible(true);
        table_Column_With_Tran.setVisible(true);
        table_Column_Depo_Tran.setVisible(true);
        table_Column_From_ID.setVisible(true);
        table_Column_To_ID.setVisible(true);
    }

}
