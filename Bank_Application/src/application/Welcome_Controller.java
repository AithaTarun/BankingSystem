package application;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;

import java.io.File;
import java.lang.*;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class Welcome_Controller implements Initializable
{
    @FXML
    public MediaView Video;
    public MediaPlayer Play;
    public Media Me;
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        String path=new File("src/application/coins.mp4").getAbsolutePath();
        Me=new Media(new File(path).toURI().toString());
        Play=new MediaPlayer(Me);
        Video.setMediaPlayer(Play);
        Play.setAutoPlay(true);
        Play.setCycleCount(MediaPlayer.INDEFINITE);
        /*DoubleProperty width=Video.fitWidthProperty();
        DoubleProperty height=Video.fitHeightProperty();
        width.bind(Bindings.selectDouble(Video.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(Video.sceneProperty(), "height"));*/
    }

    static Connection connection=ConnectionClass.getConnection();

    public  void Login(ActionEvent Login ) throws Exception
    {
        ((Node)Login.getSource()).getScene().getWindow().hide();
        Stage primaryStage=new Stage();
        FXMLLoader loader=new FXMLLoader();
        Pane Login_Pane= loader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(Login_Pane,1080,720));
        primaryStage.show();

    }
    public void Create_Account(ActionEvent Create_Account)throws Exception
    {
        ((Node)Create_Account.getSource()).getScene().getWindow().hide();
        Stage primaryStage=new Stage();
        FXMLLoader loader=new FXMLLoader();
        Pane Create_Account_pane= loader.load(getClass().getResource("Create_Account.fxml"));
        Create_Account_Controller IDController = (Create_Account_Controller) loader.getController();
        IDController.Manager(false);
        primaryStage.setTitle("Create account");
        primaryStage.setScene(new Scene(Create_Account_pane,1080,720));
        primaryStage.show();
    }
    public void Exit(ActionEvent Exit)throws Exception
    {
        System.exit(0);
    }
}
