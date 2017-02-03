
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JApplet;

public class KeyboardDemoApp extends JApplet {

    private Point pt = new Point(0, 0);
    private String strInput = "";
    private Boolean textMode = false;
    private ArrayList<Caption> captionList = new ArrayList<>();

    @Override
    public void init() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                textMode = !textMode;
                if (textMode) {
                    requestFocus();
                    pt = new Point(e.getX(), e.getY());
                    setCursor(new Cursor(Cursor.TEXT_CURSOR));
                } else {
                    captionList.add(new Caption(pt, strInput));
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    strInput = "";
                }
            }
        });
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                strInput += e.getKeyChar();
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        g.drawString(strInput, pt.x, pt.y);
        Iterator<Caption> it = captionList.iterator();
        while (it.hasNext()) {
            Caption caption = it.next();
            g.drawString(caption.getStrDisplay(),
                    caption.getPt().x,
                    caption.getPt().y);
        }
    }
}

class Caption {

    private Point pt;
    private String strDisplay = "";

    public Caption(Point pt, String strDisplay) {
        this.pt = pt;
        this.strDisplay = strDisplay;
    }

    public Point getPt() {
        return pt;
    }

    public String getStrDisplay() {
        return strDisplay;
    }
}