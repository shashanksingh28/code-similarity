
import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class EchoMultiServer {

    private static ServerSocket server = null;
    private static ExecutorService threadPool;

    public static void main(String[] args) {
        try {
            threadPool = Executors.newCachedThreadPool();
            threadPool.submit(new Monitor());
            server = new ServerSocket(10000);
            System.out.println("Server listening on port 10000 ...");
            System.out.println("Hit Enter to stop the server");
            while (true) {
                Socket socketObject = server.accept();
                System.out.println("Thread created");
                threadPool.submit(new EchoThread(socketObject));
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
        threadPool.shutdown();
        System.exit(0);
    }

    private static class Monitor implements Runnable {

        @Override
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

class EchoThread implements Runnable {

    private Socket socketObject = null;
    private byte buffer[] = new byte[512];

    public EchoThread(Socket socketObject) {
        this.socketObject = socketObject;
    }

    @Override
    public void run() {
        try {
            try {
                InputStream reader = socketObject.getInputStream();
                reader.read(buffer);
                OutputStream writer = socketObject.getOutputStream();
                writer.write(buffer);
            } finally {
                socketObject.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}