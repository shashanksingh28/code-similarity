
import java.io.*;

public class FileCopy {

    public static void main(String[] args) {
        int numberRead = 0;
        InputStream readerStream = null;
        OutputStream writerStream = null;
        byte buffer[] = new byte[512];
        if (args.length < 2) {
            System.out.println("Usage: java FileCopy file1 file2");
            System.exit(0);
        }
        try {
            readerStream = new FileInputStream(args[0]);
        } catch (FileNotFoundException fe) {
            System.out.println(args[0] + " not found");
            System.exit(0);
        }
        try {
            writerStream = new FileOutputStream(args[1]);
        } catch (FileNotFoundException fe) {
            System.out.println(args[1] + " not found");
            System.exit(0);
        }
        try {
            while ((numberRead = readerStream.read(buffer)) != -1) {
                writerStream.write(buffer, 0, numberRead);
            }
        } catch (IOException ioe) {
            System.out.println("Error reading/writing file");
        } finally {
            try {
                readerStream.close();
                writerStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("1 file copied!");
    }
}