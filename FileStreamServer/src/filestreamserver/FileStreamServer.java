package filestreamserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileStreamServer {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {
        int port = 2137;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for files");
            while (true) {
                Socket socket = serverSocket.accept();
                EXECUTOR.submit(new DataReceiver(socket, "C:\\Users\\Kamil\\Desktop\\Received", 4));
            }
        } catch (IOException ex) {
            Logger.getLogger(FileStreamServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
