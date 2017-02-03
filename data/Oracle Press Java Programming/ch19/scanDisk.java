
import java.awt.*;
import java.text.DateFormat;
import java.util.*;
import static java.util.concurrent.TimeUnit.*;
import java.util.concurrent.*;
import javax.swing.*;

class VirusScanner {

    private static JFrame appFrame;
    private static JLabel statusString;
    private int scanNumber = 0;
    private static final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(5);
    private GregorianCalendar calendar = new GregorianCalendar();
    private static VirusScanner app = new VirusScanner();

    public void scanDisk() {
        final Runnable scanner = new Runnable() {

            public void run() {
                {
                    try {
                        appFrame.setVisible(true);
                        scanNumber++;
                        Calendar cal = Calendar.getInstance();
                        DateFormat df = DateFormat.getDateTimeInstance(
                                DateFormat.FULL, DateFormat.MEDIUM);
                        statusString.setText(" Scan " + scanNumber
                                + " started at " + df.format(cal.getTime()));
                        Thread.sleep(1000 + new Random().nextInt(10000));
                        appFrame.setVisible(false);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        final ScheduledFuture<?> scanManager =
                scheduler.scheduleAtFixedRate(scanner, 1, 15, SECONDS);
        scheduler.schedule(new Runnable() {

            public void run() {
                scanManager.cancel(true);
                scheduler.shutdown();
                appFrame.dispose();
            }
        }, 60, SECONDS);
    }

    public static void main(String args[]) {
        appFrame = new JFrame();
        Dimension dimension =
                Toolkit.getDefaultToolkit().getScreenSize();
        appFrame.setSize(400, 70);
        appFrame.setLocation(
                dimension.width / 2 - appFrame.getWidth() / 2,
                dimension.height / 2 - appFrame.getWidth() / 2);
        statusString = new JLabel();
        appFrame.add(statusString);
        appFrame.setVisible(false);
        app.scanDisk();
    }
}