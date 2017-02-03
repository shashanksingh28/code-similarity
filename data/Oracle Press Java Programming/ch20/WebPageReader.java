
import java.io.InputStream;
import java.net.*;
import java.util.Scanner;

public class WebPageReader {

    public static void main(String[] args) {
        try {
            String strURL =
                    "http://www.oracle.com/us/technologies/java/index.html";
            URL url = new URL(strURL);
            URLConnection connection = url.openConnection();
            InputStream inStream = connection.getInputStream();
            Scanner reader = new Scanner(inStream);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                System.out.println(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}