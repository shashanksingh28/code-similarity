
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GraphicsEditor4 extends JApplet
        implements ActionListener {

    private Point ptFirst = new Point(0, 0);
    private Point ptSecond = new Point(0, 0);
    private Point ptOld = new Point(0, 0);
    private boolean isDrawing = false;
    private ArrayList<Line> lines = new ArrayList<Line>();
    private JPopupMenu popMenu;
    private JMenuItem menuRed, menuGreen, menuBlue, menuCustom;
    private Color drawingColor = Color.red;

    @Override
    public void init() {
        popMenu = new JPopupMenu("Colors");
        menuRed = new JMenuItem("Red");
        menuRed.addActionListener(this);
        menuGreen = new JMenuItem("Green");
        menuGreen.addActionListener(this);
        menuBlue = new JMenuItem("Blue");
        menuBlue.addActionListener(this);
        menuCustom = new JMenuItem("Custom");
        menuCustom.addActionListener(this);
        popMenu.add(menuRed);
        popMenu.add(menuGreen);
        popMenu.add(menuBlue);
        popMenu.addSeparator();
        popMenu.add(menuCustom);
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

    public void setClr(Color clr) {
        this.drawingColor = clr;
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
        } else if (e.getSource() == menuCustom) {
            (new ColorPalette("Color Palette",
                    this)).setVisible(true);
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

    public Line(Point ptStart, Point ptEnd, Color clr) {
        this.ptStart = ptStart;
        this.ptEnd = ptEnd;
        this.lineColor = clr;
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

class ColorPalette extends JFrame implements AdjustmentListener {

    private GraphicsEditor4 applet;
    private JScrollBar redScroll =
            new JScrollBar(Scrollbar.HORIZONTAL, 0, 1, 0, 256);
    private JScrollBar greenScroll =
            new JScrollBar(Scrollbar.HORIZONTAL, 0, 1, 0, 256);
    private JScrollBar blueScroll =
            new JScrollBar(Scrollbar.HORIZONTAL, 0, 1, 0, 256);
    private ColorCanvas colorCanvas;
    private MessageCanvas messageCanvas;
    private int redValue, greenValue, blueValue;

    public int getBlueValue() {
        return blueValue;
    }

    public int getGreenValue() {
        return greenValue;
    }

    public int getRedValue() {
        return redValue;
    }

    public ColorPalette(String string, GraphicsEditor4 applet)
            throws HeadlessException {
        setTitle(string);
        this.applet = applet;
        initGUI();
    }

    private void initGUI() {
        setLayout(new GridLayout(8, 1, 5, 5));
        add(new JLabel("Red"));
        add(redScroll);
        add(new JLabel("Green"));
        add(greenScroll);
        add(new JLabel("Blue"));
        add(blueScroll);
        messageCanvas = new MessageCanvas(this);
        add(messageCanvas);
        colorCanvas = new ColorCanvas(this);
        add(colorCanvas);
        redScroll.addAdjustmentListener(this);
        blueScroll.addAdjustmentListener(this);
        greenScroll.addAdjustmentListener(this);
        setBounds(200, 200, 200, 200);
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        if (e.getAdjustable() == redScroll) {
            redValue = redScroll.getValue();
        }
        if (e.getAdjustable() == greenScroll) {
            greenValue = greenScroll.getValue();
        }
        if (e.getAdjustable() == blueScroll) {
            blueValue = blueScroll.getValue();
        }
        messageCanvas.repaint();
        colorCanvas.repaint();
        applet.setClr(new Color(redValue, greenValue, blueValue));
    }
}

class MessageCanvas extends Canvas {

    private ColorPalette frame;
    private String strDisplay = "";

    MessageCanvas(ColorPalette frame) {
        this.frame = frame;
    }

    @Override
    public void paint(Graphics g) {
        strDisplay = "Red:" + String.valueOf(frame.getRedValue());
        strDisplay += " Green:" + String.valueOf(frame.getGreenValue());
        strDisplay += " Blue:" + String.valueOf(frame.getBlueValue());
        g.drawString(strDisplay, 10, 10);
    }
}

class ColorCanvas extends Canvas {

    private ColorPalette frame;

    ColorCanvas(ColorPalette applet) {
        this.frame = applet;
    }

    @Override
    public void paint(Graphics g) {
        Rectangle rect = getBounds();
        g.setColor(new Color(frame.getRedValue(),
                frame.getGreenValue(),
                frame.getBlueValue()));
        g.fillRect(0, 0, rect.width, rect.height);
    }
}