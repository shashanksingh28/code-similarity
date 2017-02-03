
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StockTradesServer {

    public static void main(String[] args) {
        try {
            Thread tradesGenerator = new Thread(
                    new StockTradesGenerator());
            tradesGenerator.setDaemon(true);
            tradesGenerator.start();
            System.out.println(
                    "Stock trades broadcast server started");
            System.out.println("Hit Enter to stop server");
            while (System.in.read() != '\n') {
            }
        } catch (IOException ex) {
            System.out.println("Error starting server");
        }
    }

    private static class StockTradesGenerator implements Runnable {

        private DatagramSocket broadcastSocket = null;
        private String[] stockSymbols = {"IBM", "SNE", "XRX", "MHP", "NOK"};
        private static final String TIME_FORMAT_NOW = "HH:mm:ss";

        public StockTradesGenerator() {
            try {
                broadcastSocket = new DatagramSocket(4445);
            } catch (SocketException ex) {
                System.out.println("Error making socket connection");
            }
        }

        public void run() {
            byte[] buffer = new byte[80];
            try {
                while (true) {
                    int index = (int) (Math.random() * 5);
                    float trade = generateRandomTrade(index);
                    String lastTrade = String.format("%s %.2f @%s",
                            stockSymbols[index], trade, now());
                    buffer = lastTrade.getBytes();
                    try {
                        InetAddress groupBrodcastAddresses =
                                InetAddress.getByName("230.0.0.1");
                        DatagramPacket packet = new DatagramPacket(buffer,
                                buffer.length, groupBrodcastAddresses, 4446);
                        broadcastSocket.send(packet);
                        Thread.sleep((long) (Math.random() * 2000));
                    } catch (Exception ex) {
                        System.out.println("Error in communication");
                    }
                }
            } finally {
                broadcastSocket.close();
            }
        }

        private float generateRandomTrade(int index) {
            float trade = (float) Math.random();
            switch (index) {
                case 0:
                    trade += 118;
                    break;
                case 1:
                    trade += 29;
                    break;
                case 2:
                    trade += 8;
                    break;
                case 3:
                    trade += 26;
                   
                    break;
                case 4:
                    trade += 14;
                    break;
            }
            return trade;
        }

        private String now() {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_NOW);
            return sdf.format(cal.getTime());
        }
    }
}