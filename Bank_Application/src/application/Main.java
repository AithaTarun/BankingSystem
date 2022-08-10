package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent Welcome_Parent=FXMLLoader.load(getClass().getResource("Welcome.fxml"));
        primaryStage.setTitle("Bank of rising pheonix");
        primaryStage.setScene(new Scene(Welcome_Parent,1080,720));
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
