package filestreamclient;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class FileStreamClientController implements Initializable {

    @FXML
    private Button sendButton;
    @FXML
    private Button chooseButton;
    @FXML
    private Text textField;
    @FXML
    private Label statusLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private StackPane root;
    private File fileToSent;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void sendButtonOnAction(ActionEvent event) {
        Task<Void> sendFileTask = new DataSender(fileToSent, "127.0.0.1", 2137, 4);
        statusLabel.textProperty().bind(sendFileTask.messageProperty());
        progressBar.progressProperty().bind(sendFileTask.progressProperty());
        executor.submit(sendFileTask);
    }

    @FXML
    private void chooseFileButtonOnAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(null);
        if (file == null) {
            return;
        }
        fileToSent = file;
        textField.setText(fileToSent.getName() + "\nRozmiar: " + fileToSent.length() + " bajtów");
        sendButton.setDisable(false);
        statusLabel.textProperty().unbind();
        statusLabel.textProperty().set("Ready to send");
    }
    
    void close(){
        executor.shutdownNow();
    }
}
