
import java.io.*;

public class FileLength {

    public static void main(String[] args) {
        int count = 0;
        InputStream streamReader = null;
        if (args.length < 1) {
            System.out.println("Usage: java FileLength <filename>");
            System.exit(0);
        }
        try {
            streamReader = new FileInputStream(args[0]);
            while (streamReader.read() != -1) {
                count++;
            }
            System.out.println(args[0] + " length = " + count);
            streamReader.close();
        } catch (FileNotFoundException fe) {
            System.out.println("File " + args[0] + " was not found");
            System.exit(0);
        } catch (IOException ie) {
            System.out.println("Error reading file");
        } finally {
            try {
                streamReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}