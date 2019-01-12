package com.bitris;

import com.bitris.controllers.MainController;
import com.bitris.controllers.Navigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
 
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        stage.setScene(createScene(loadMainPane()));
        stage.setResizable(false);
        stage.setTitle("BITRIS");
        stage.show();
    }

    private Pane loadMainPane() throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        
        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.MAIN));
        MainController mainController = loader.getController();
        Navigator.setMainController(mainController);
        Navigator.loadVista(Navigator.MENU);
        Navigator.playMusic();

        return mainPane;
    }

    private Scene createScene(Pane mainPane) {
        
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().setAll(getClass().getResource("/com/bitris/resources.styles/styleSheet.css").toExternalForm());

        return scene;
    }

    public static void main(String[] args) { launch(args); }
}
