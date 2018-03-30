package filestreamclient;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;

public class DataSender extends Task<Void> {

    private final File fileToSend;
    private final String ipAddress;
    private final int portNumber;
    private final int bufferSizeKB;

    public DataSender(File fileToSend, String ipAddress, int portNumber, int bufferSizeKB) {
        this.fileToSend = fileToSend;
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
        this.bufferSizeKB = bufferSizeKB;
    }

    @Override
    protected Void call() throws Exception {
        updateMessage("Initializing");
        byte[] buffer = new byte[1024 * bufferSizeKB];
        int readSize;
        int dataSent = 0;
        try (FileInputStream file = new FileInputStream(fileToSend);
                Socket socket = new Socket(ipAddress, portNumber);
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream out = new DataOutputStream(outputStream)) {
            out.writeUTF(fileToSend.getName());
            out.writeLong(fileToSend.length());
            updateMessage("Sending");
            while ((readSize = file.read(buffer)) != -1) {
                dataSent += readSize;
                out.write(buffer, 0, readSize);
                updateProgress(dataSent, fileToSend.length());
                if (Thread.interrupted()) {
                    out.close();
                    outputStream.close();
                    socket.close();
                    return null;
                }
            }
        } catch (IOException ex) {
            updateMessage("ERROR");
            Logger.getLogger(DataSender.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        updateMessage("Finished");
        return null;
    }

}
