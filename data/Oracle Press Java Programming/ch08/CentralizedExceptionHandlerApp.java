
import java.io.*;
import java.net.*;

public class CentralizedExceptionHandlerApp {

    private static BufferedReader reader = null;

    public static void main(String[] args) {
        String urlStr = null;
        try {
            CentralizedExceptionHandlerApp app =
                    new CentralizedExceptionHandlerApp();
            app.openDataFile("data.txt");
            app.readData();
            reader.close();
        } catch (IOException e) {
            System.out.println("Error closing file");
        } catch (Exception ex) {
            System.out.println("Unknown error: " + ex.getMessage());
        }
    }

    void openDataFile(String fileName) {
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("Specified file not found");
        }
    }

    void readData() {
        String str;
        try {
            while ((str = reader.readLine()) != null) {
                int n = Integer.parseInt(str);
                System.out.println(n);
            }
        } catch (IOException e) {
            System.out.println("Error while reading data");
        } catch (NumberFormatException ne) {
            System.out.println("Invalid number format, skipping rest");
        }
    }
}