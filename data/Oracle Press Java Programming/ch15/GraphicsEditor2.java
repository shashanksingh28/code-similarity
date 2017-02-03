
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JApplet;

public class GraphicsEditor2 extends JApplet {

    private Point ptFirst = new Point(0, 0);
    private Point ptSecond = new Point(0, 0);
    private Point ptOld = new Point(0, 0);
    private boolean isDrawing = false;
    private ArrayList<Line> lines = new ArrayList<>();
    

    @Override
    public void init() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isDrawing) {
                    ptFirst = new Point(e.getX(), e.getY());
                    ptSecond = new Point(e.getX(), e.getY());
                    isDrawing = true;
                    setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                } else {
                    isDrawing = false;
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    lines.add(new Line(ptFirst, ptSecond));
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                if (isDrawing) {
                    ptOld = ptSecond;
                    ptSecond = new Point(e.getX(), e.getY());
                    repaint();
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
// erase earlier line
        g.setColor(Color.white);
        g.drawLine(ptFirst.x, ptFirst.y, ptOld.x, ptOld.y);
// draw new line
        g.setColor(Color.red);
        g.drawLine(ptFirst.x, ptFirst.y, ptSecond.x, ptSecond.y);
// draw all previous lines
        Iterator<Line> it = lines.iterator();
        while (it.hasNext()) {
            Line line = it.next();
            g.drawLine(line.getStartPoint().x, line.getStartPoint().y,
                    line.getEndPoint().x, line.getEndPoint().y);
        }
    }
}

class Line {

    private Point ptStart;
    private Point ptEnd;

    public Line(Point ptStart, Point ptEnd) {
        this.ptStart = ptStart;
        this.ptEnd = ptEnd;
    }

    public Point getEndPoint() {
        return ptEnd;
    }

    public Point getStartPoint() {
        return ptStart;
    }
}