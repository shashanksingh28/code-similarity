
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class LocalTimeConverter extends JFrame {

    private final String TIME_FORMAT_NOW = "HH:mm 'on' dd MMM yyyy";
    private final SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_NOW);

    public static void main(String[] args) {
        LocalTimeConverter app = new LocalTimeConverter();
        app.setTitle("Local Time Converter");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.init();
        app.setBounds(100, 100, 700, 500);
        app.setVisible(true);
    }

    private void init() {
        final JPopupMenu popup = new JPopupMenu();
        final JMenuItem menuItem = new JMenuItem();
        popup.add(menuItem);
        menuItem.setBackground(Color.yellow);
        menuItem.setForeground(Color.blue);
        final JLabel localTime = new JLabel(getTimeNowAsString(), JLabel.CENTER);
        localTime.setHorizontalTextPosition(JLabel.CENTER);
        localTime.setFont(new Font("Tahoma", Font.PLAIN, 24));
        localTime.setForeground(new Color(0, 0, 255));
        add(localTime, BorderLayout.PAGE_START);
        List<String> zoneList = new ArrayList<String>();
        zoneList.addAll(Arrays.asList(TimeZone.getAvailableIDs()));
        Collections.sort(zoneList);
        final JList listOfZones = new JList(zoneList.toArray());
        listOfZones.setSelectionMode(
                ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listOfZones.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        listOfZones.setVisibleRowCount(-1);
        listOfZones.addMouseListener(new MouseAdapter() {

            private Calendar calendar;
            private String selectedZone;

            @Override
            public void mouseClicked(MouseEvent e) {
                localTime.setText(getTimeNowAsString());
                int index = listOfZones.locationToIndex(e.getPoint());
                if (index > -1 && !(listOfZones.isSelectionEmpty())) {
                    selectedZone = (String) listOfZones.getSelectedValue();
                    computeTimeAtSelectedZone();
// display time for remote zone in the popup
                    String timezoneName = TimeZone.getTimeZone(selectedZone).
                            getDisplayName();
                    menuItem.setText("Local time @ " + selectedZone + " "
                            + sdf.format(calendar.getTime()) + " "
                            + timezoneName);
                    popup.show(e.getComponent(),
                            e.getX(), e.getY() + 10);
                }
            }

            private void computeTimeAtSelectedZone() {
// reset calendar to local timezone
                calendar = new GregorianCalendar();
                long currentTime = calendar.getTimeInMillis();
// get time offset of local timezone wrt GMT
                int localOffset = calendar.getTimeZone().
                        getOffset(Calendar.ZONE_OFFSET);
// get time offset of remote timezone wrt GMT
                calendar.setTimeZone(TimeZone.getTimeZone(selectedZone));
                int remoteOffset = calendar.getTimeZone().
                        getOffset(Calendar.ZONE_OFFSET);
// difference in two timezones
                int totalOffset = -remoteOffset + localOffset;
// add offset to current local time
                currentTime -= totalOffset;
// set time in remote zone
                calendar.setTimeInMillis(currentTime);
            }
        });
        JScrollPane listScroller = new JScrollPane(listOfZones);
        add(listScroller, BorderLayout.CENTER);
    }

    private String getTimeNowAsString() {
        Calendar calendar = new GregorianCalendar();
        String strLocalTime = "Local Time-"
                + sdf.format(calendar.getTime()) + " "
                + calendar.getTimeZone().getDisplayName();
        return strLocalTime;
    }
}