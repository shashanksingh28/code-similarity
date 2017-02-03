
import java.awt.*;
import java.awt.event.*;
import javax.swing.JApplet;

public class GraphicsEditor extends JApplet {

    private Point pt = new Point(0, 0);

    @Override
    public void init() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                pt = new Point(e.getX(), e.getY());
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        if (pt.x > 0 && pt.x > 0) {
            String printString = String.format("(%d, %d)", pt.x, pt.y);
            g.drawString(printString, pt.x, pt.y);
        }
    }
}