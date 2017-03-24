import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * A simple demonstration of MouseEvents.  Shapes are drawn
 * on a black background when the user clicks the panel.  If
 * the user Shift-clicks, the panel is cleared.  If the user
 * right-clicks the panel, a blue oval is drawn.  Otherwise,
 * when the user clicks, a red rectangle is drawn.  Information
 * about the shapes that have been drawn is stored in an ArrayList,
 * which is used in paintComponent() to draw the contents of
 * the panel.
 */
public class SimpleStamperWithArrayList extends JPanel 
                               implements MouseListener, MouseMotionListener {

    public static void main(String[] args) {
        JFrame window = new JFrame("Simple Stamper");
        SimpleStamperWithArrayList content = new SimpleStamperWithArrayList();
        window.setContentPane(content);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(120,70);
        window.setSize(400,300);
        window.setVisible(true);
    }

    // ----------------------------------------------------------------------
    
    /**
     * A class to hold information about one shape.
     */
    private static class ShapeInfo {
        int x,y;  // The location of a shape
        boolean rect;  // true if it's a red rectangle, false for a blue oval
        ShapeInfo(int x, int y, boolean isRect) {
            this.x = x;
            this.y = y;
            rect = isRect;
        }
    }
    
    private ArrayList<ShapeInfo> shapes;  // Holds shapes that have been added.

    /**
     * This variable is set to true during a drag operation, unless the
     * user was holding down the shift key when the mouse was first
     * pressed (since in that case, the mouse gesture simply clears the
     * panel and no figures should be drawn if the user drags the mouse).
     */
    private boolean dragging;
    

    /**
     * This constructor simply sets the background color of the panel to be black
     * and sets the panel to listen for mouse events on itself.
     */
    public SimpleStamperWithArrayList() {
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
        shapes = new ArrayList<ShapeInfo>();
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for ( ShapeInfo shape : shapes ) {
            if ( shape.rect ) {
                    // Draw a red rectangle centered at (x,y).
                g.setColor(Color.RED);   // Red interior.
                g.fillRect( shape.x - 30, shape.y - 15, 60, 30 );
                g.setColor(Color.BLACK); // Black outline.
                g.drawRect( shape.x - 30, shape.y - 15, 60, 30 );
            }
            else {
                    // draw a blue oval centered at (x,y)
                g.setColor(Color.BLUE);  // Blue interior.
                g.fillOval( shape.x - 30, shape.y - 15, 60, 30 );
                g.setColor(Color.BLACK); // Black outline.
                g.drawOval( shape.x - 30, shape.y - 15, 60, 30 );
            }
        }
    }
    
    /**
     *  This method will be called when the user clicks the mouse on the panel.
     *  If the shift key is down, the panel is cleared.  Otherwise, it adds
     *  a shape to the panel and starts a drag operation.
     */
    public void mousePressed(MouseEvent evt) {

        if ( evt.isShiftDown() ) {
                // The user was holding down the Shift key.
                // Set shapeCount to 0 to record that there are no shapes.
            dragging = false;
            shapes.clear();
            repaint();
            return;
        }

        dragging = true;

        int x = evt.getX();  // x-coordinate where user clicked.
        int y = evt.getY();  // y-coordinate where user clicked.

        ShapeInfo newShape;
        if ( evt.isMetaDown() ) {
            newShape = new ShapeInfo(x,y,false);
        }
        else {
            newShape = new ShapeInfo(x,y,true);
        }
        shapes.add(newShape);
        repaint();  // repaint so that user can see the new shape!

    } // end mousePressed();


    /**
     *  This method is called when the user drags the mouse.  If a the value of the
     *  instance variable dragging is true, it will add a shape to the panel.
     */
    public void mouseDragged(MouseEvent evt) {
        if ( dragging == false ) { 
            return;
        }
        int x = evt.getX();  // x-coordinate where user clicked.
        int y = evt.getY();  // y-coordinate where user clicked.

        ShapeInfo newShape;
        if ( evt.isMetaDown() ) {
            newShape = new ShapeInfo(x,y,false);
        }
        else {
            newShape = new ShapeInfo(x,y,true);
        }
        shapes.add(newShape);

        repaint();  // repaint so that user can see the new shape!
    } // end mouseDragged();


    // The next four empty routines are required by the MouseListener interface.
    // They don't do anything in this class, so their definitions are empty.

    public void mouseEntered(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }
    public void mouseClicked(MouseEvent evt) { }
    public void mouseReleased(MouseEvent evt) { }

    // The next routines is required by the MouseMotionListener interface.

    public void mouseMoved(MouseEvent evt) { }

} // end class SimpleStamperWithArrayList
