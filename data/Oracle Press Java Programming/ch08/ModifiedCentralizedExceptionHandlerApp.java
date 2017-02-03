
import java.io.*;
import java.net.*;

public class ModifiedCentralizedExceptionHandlerApp {

    private static BufferedReader reader = null;

    public static void main(String[] args) {
        String urlStr = null;
        try {
            ModifiedCentralizedExceptionHandlerApp app =
                    new ModifiedCentralizedExceptionHandlerApp();
            app.openDataFile("data.txt");
            app.readData();
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Specified file not found");
        } catch (IOException e) {
            System.out.println("Error closing file");
        } catch (NumberFormatException ne) {
            System.out.println("Invalid number format, skipping rest");
        } catch (Exception ex) {
            System.out.println("Unknown error: " + ex.getMessage());
        }
    }

    void openDataFile(String fileName) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(fileName));
    }

    void readData() throws IOException, NumberFormatException {
        String str;
        while ((str = reader.readLine()) != null) {
            int n = Integer.parseInt(str);
            System.out.println(n);
        }
    }
}