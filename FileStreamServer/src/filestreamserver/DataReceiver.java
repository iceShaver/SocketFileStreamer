package filestreamserver;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class DataReceiver implements Runnable {

    private final Socket socket;
    private final String directoryPath;
    private final int bufferSizeKB;

    public DataReceiver(Socket socket, String directoryPath, int bufferSizeKB) {
        this.socket = socket;
        this.directoryPath = directoryPath;
        this.bufferSizeKB = bufferSizeKB;
    }

    @Override
    public void run() {
        long threadName = Thread.currentThread().getId();
        String filename = null;
        long fileSize, receivedBytes = 0;
        try (InputStream inputStream = socket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);) {
            filename = dataInputStream.readUTF();
            fileSize = dataInputStream.readLong();
            System.out.println(filename + ": receiving started (Thread: " + threadName + ")");
            File file = new File(directoryPath + "/" + filename);
            file.getParentFile().mkdirs();
            file.createNewFile();
            try (OutputStream outputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024 * bufferSizeKB];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    receivedBytes += bytesRead;
                    outputStream.write(buffer, 0, bytesRead);
                }
                if (receivedBytes != fileSize) {
                    throw new IOException("File size mismatch, probably transmission was interrupt");
                }
            }
            System.out.println(filename + ": receiving completed (Thread: " + threadName + ")");
        } catch (IOException ex) {
            System.err.println(filename + ": ERROR OCCURED: " + ex.getMessage() + "(Thread: " + threadName + ")");
        }

    }

}
