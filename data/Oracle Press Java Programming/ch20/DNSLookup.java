
import java.net.*;

public class DNSLookup {

    public static void main(String[] args) {
        InetAddress[] inetHost = null;
        try {
            System.out.println("List of Google servers");
            inetHost = InetAddress.getAllByName("www.google.com");
            for (InetAddress address : inetHost) {
                System.out.println(address);
            }
            System.out.println("\nList of CNN servers");
            inetHost = InetAddress.getAllByName("cnn.com");
            for (InetAddress address : inetHost) {
                System.out.println(address);
            }
            System.out.println("\nLocal machine");
            System.out.println(InetAddress.getLocalHost().toString());
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }
}