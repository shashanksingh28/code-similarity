// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.io.*;
import java.net.*;

/**
 * This program waits to receive datagrams sent the specified port.
 * When it receives one, it displays the sending host and prints the
 * contents of the datagram as a string.  Then it loops and waits again.
 **/
public class UDPReceive {
  public static void main(String args[]) {
    try {
      if (args.length != 1) 
        throw new IllegalArgumentException("Wrong number of arguments");

      // Get the port from the command line
      int port = Integer.parseInt(args[0]);

      // Create a socket to listen on the port.
      DatagramSocket dsocket = new DatagramSocket(port);

      // Create a buffer to read datagrams into.  If anyone sends us a 
      // packet containing more than will fit into this buffer, the excess
      // will simply be discarded!
      byte[] buffer = new byte[2048];
      

      // Now loop forever, waiting to receive packets and printing them out.
      for(;;) {
        // Create a packet with an empty buffer to receive data
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        // Wait to receive a datagram
        dsocket.receive(packet);

        // Convert the contents to a string, and display them
        String msg = new String(buffer, 0, packet.getLength());
        System.out.println(packet.getAddress().getHostName() + ": " + msg);
      }
    }
    catch (Exception e) {
      System.err.println(e);
      System.err.println("Usage: java UDPReceive <port>");
    }
  }
}
