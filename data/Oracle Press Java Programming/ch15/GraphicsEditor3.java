
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GraphicsEditor3 extends JApplet
        implements ActionListener {

    private Point ptFirst = new Point(0, 0);
    private Point ptSecond = new Point(0, 0);
    private Point ptOld = new Point(0, 0);
    private boolean isDrawing = false;
    private ArrayList<Line> lines = new ArrayList<Line>();
    private JPopupMenu popMenu;
    private JMenuItem menuRed, menuGreen, menuBlue;
    private Color drawingColor = Color.red;
    private JApplet app;

    @Override
    public void init() {
        popMenu = new JPopupMenu("Colors");
        menuRed = new JMenuItem("Red");
        menuRed.addActionListener(this);
        menuGreen = new JMenuItem("Green");
        menuGreen.addActionListener(this);
        menuBlue = new JMenuItem("Blue");
        menuBlue.addActionListener(this);
        popMenu.add(menuRed);
        popMenu.add(menuGreen);
        popMenu.add(menuBlue);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
                    popMenu.show(e.getComponent(), e.getX(), e.getY());
                } else if (e.getModifiers()
                        == InputEvent.BUTTON1_MASK) {
                    if (!isDrawing) {
                        ptFirst = new Point(e.getX(), e.getY());
                        ptSecond = new Point(e.getX(), e.getY());
                        isDrawing = true;
                        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    } else {
                        isDrawing = false;
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        lines.add(new Line(ptFirst, ptSecond, drawingColor));
                    }
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
// erase old line
        g.setColor(Color.white);
        g.drawLine(ptFirst.x, ptFirst.y, ptOld.x, ptOld.y);
// draw new line
        g.setColor(drawingColor);
        g.drawLine(ptFirst.x, ptFirst.y, ptSecond.x, ptSecond.y);
        Iterator<Line> it = lines.iterator();
        while (it.hasNext()) {
            Line line = it.next();
            g.setColor(line.getLineColor());
            g.drawLine(line.getStartPoint().x, line.getStartPoint().y,
                    line.getEndPoint().x, line.getEndPoint().y);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuRed) {
            drawingColor = Color.red;
        } else if (e.getSource() == menuGreen) {
            drawingColor = Color.green;
        } else if (e.getSource() == menuBlue) {
            drawingColor = Color.blue;
        }
    }
}

class Line {

    private Point ptStart;
    private Point ptEnd;
    private Color lineColor;

    public Line(Point ptStart, Point ptEnd) {
        this.ptStart = ptStart;
        this.ptEnd = ptEnd;
    }

    public Line(Point ptStart, Point ptEnd, Color lineColor) {
        this.ptStart = ptStart;
        this.ptEnd = ptEnd;
        this.lineColor = lineColor;
    }

    public Point getEndPoint() {
        return ptEnd;
    }

    public Point getStartPoint() {
        return ptStart;
    }

    public Color getLineColor() {
        return lineColor;
    }
}