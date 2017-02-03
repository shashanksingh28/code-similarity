
import java.io.*;

public class EnumSerialization {

    public static void main(String[] args) {
        ColorPalette drawingColor = ColorPalette.GREEN;
        try {
            System.out.println("Saving color setting");
            ObjectOutputStream outStream = new ObjectOutputStream(
                    new FileOutputStream("Settings.dat"));
            outStream.writeObject(drawingColor);
            outStream.close();
            ObjectInputStream inStream = new ObjectInputStream(
                    new FileInputStream("Settings.dat"));
            System.out.println("Retrieved object: "
                    + (ColorPalette) inStream.readObject());
            inStream.close();
        } catch (IOException e) {
            System.out.println("Error reading/writing object");
        } catch (ClassNotFoundException cfe) {
            System.out.println("Class casting error");
        }
    }
}

enum ColorPalette {

    RED, GREEN, BLUE
}