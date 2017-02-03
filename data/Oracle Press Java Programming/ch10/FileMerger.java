
import java.io.*;
import java.util.*;

public class FileMerger {

    private Vector listOfFileNames = new Vector();
    private Vector fileList = new Vector();

    public static void main(String args[]) throws IOException {
        FileMerger app = new FileMerger();
        app.getFileNames();
        if (!app.createFileList()) {
            System.exit(0);
        }
        app.mergeFiles();
    }

    private void getFileNames() {
        String fileName = "";
        System.out.println("Enter file names (one on a line): ");
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                fileName = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading keyboard");
            }
            if ((fileName.equals("over"))) {
                break;
            }
            listOfFileNames.add(fileName);
        }
    }

    private boolean createFileList() {
        Enumeration list = listOfFileNames.elements();
        while (list.hasMoreElements()) {
            String fileName = (String) list.nextElement();
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(fileName);
            } catch (FileNotFoundException fe) {
                System.out.println("File not found: " + fileName);
            }
            fileList.add(inputStream);
        }
        return true;
    }

    private void mergeFiles() throws FileNotFoundException {
        try (
                OutputStream outStream =
                        new FileOutputStream("monthlyDataFile.txt");
                SequenceInputStream inputStream =
                        new SequenceInputStream(fileList.elements());) {
            byte[] buffer = new byte[4096];
            int numberRead = 0;
            while ((numberRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, numberRead);
            }
        } catch (IOException e) {
            System.out.println("Error reading/writing file");
        }
        System.out.println(
                "Created monthlyDataFile.txt "
                + "in your current folder");
    }
}