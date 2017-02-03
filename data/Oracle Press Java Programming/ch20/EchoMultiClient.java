//
//import java.io.*;
//import java.net.*;
//import java.util.Scanner;
//
//public class EchoMultiClient {
//
//    private static int counter;
//
//    public static void main(String[] args) {
//        for (int i = 0; i < 100; i++) {
//            counter++;
//            new Thread(new Client(counter)).start();
//        }
//    }
//
//    private static class Client implements Runnable {
//
//        private int counter;
//
//        public Client(int counter) {
//            this.counter = counter;
//        }
//
//        public void run() {
//            try {
//                Socket socketObject = new Socket();
//                socketObject.connect(
//                        new InetSocketAddress("localhost", 10000), 1000);
//                try {
//                    OutputStream oStream = socketObject.getOutputStream();
//                    String str = "Hello from Client " + counter;
//                    oStream.write(str.getBytes());
//                    InputStream inStream = socketObject.getInputStream();
//                    Scanner in = new Scanner(inStream);
//                    while (in.hasNextLine()) {
//                        String line = in.nextLine();
//                        System.out.println(line);
//                    }
//                    Thread.sleep(10);
//                } finally {
//                    socketObject.close();
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}