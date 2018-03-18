/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filestreamclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author kamil
 */
public class Main extends Application {
    private FileStreamClientController fileStreamClientController;
    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.setTitle("Wysyłanie pliku do serwera");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FileStreamClient.fxml"));
        Parent root = loader.load();
        fileStreamClientController = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        fileStreamClientController.close();
        super.stop();
    }
    
}
