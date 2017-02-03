
import java.io.*;
import java.net.*;

public class StocksTrader {

    public static void main(String[] args) throws IOException {
        MulticastSocket socket = new MulticastSocket(4446);
        InetAddress address = InetAddress.getByName("230.0.0.1");
        socket.joinGroup(address);
        for (int i = 0; i < 10; i++) {
            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Last Trade: " + received);
        }
        socket.leaveGroup(address);
        socket.close();
    }
}