
import java.net.*;
import java.io.*;

public class EchoServer {

    private static ServerSocket server = null;

    public static void main(String[] args) {
        byte buffer[] = new byte[512];
        new Thread(new Monitor()).start();
        try {
            server = new ServerSocket(10000);
            System.out.println("Server Started");
            System.out.println("Hit Enter to stop the server");
            while (true) {
                Socket socketObject = server.accept();
                InputStream reader = socketObject.getInputStream();
                reader.read(buffer);
                OutputStream writer = socketObject.getOutputStream();
                writer.write(buffer);
                socketObject.close();
            }
        } catch (SocketException e) {
            System.out.println("Server is down");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void shutdownServer() {
        try {
            server.close();
        } catch (IOException ex) {
        }
        System.exit(0);
    }

    private static class Monitor implements Runnable {

        public void run() {
            try {
                while (System.in.read() != '\n') {
                }
            } catch (IOException ex) {
            }
            shutdownServer();
        }
    }
}