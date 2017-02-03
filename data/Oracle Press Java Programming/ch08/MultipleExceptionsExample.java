
import java.io.*;
import java.net.*;

public class MultipleExceptionsExample {

    public static void main(String[] args) {
        String urlStr = null;
        while (true) {
            try {
                System.out.print("Enter url: ");
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));
                urlStr = reader.readLine();
                if (urlStr.length() == 0) {
                    System.out.println("No url specified:");
                    continue;
                }
                System.out.println("Opening " + urlStr);
                URL url = new URL(urlStr);
                reader = new BufferedReader(new InputStreamReader(
                        url.openStream()));
                System.out.println(reader.readLine());
                reader.close();
            } catch (MalformedURLException e) {
                System.out.println("Invalid URL " + urlStr + ": "
                        + e.getMessage());
            } catch (IOException e) {
                System.out.println("Unable to execute " + urlStr + ": "
                        + e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}