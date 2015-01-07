package at.fhhagenberg.sqe.project.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * GUI Component that Represents a Button inside an Elevator
 */
public class ElevatorButtonComponent extends JLabel {
    private static final long serialVersionUID = -2173760156678087371L;

    /**
     * Default Component size
     */
    public static final int DEFAULT_RADIUS = 10;
    /**
     * Preferred Size of this Component
     */
    private final int mRadius;
    /**
     * Current state of this Button
     */
    private boolean mPressed;
    /**
     * Shape of this Component, lazily initialized
     */
    private Shape shape;

    /**
     * Create a new ElevatorButtonComponent with the specified Text and the default Size
     *
     * @param shortDescription The Text shown in this Component
     */
    public ElevatorButtonComponent(String shortDescription) {
        this(shortDescription, DEFAULT_RADIUS);
    }

    /**
     * Create a new ElevatorButtonComponent with the specified Text and Size
     *
     * @param shortDescription The Text shown in this Component
     * @param radius           The size of this Component
     */
    public ElevatorButtonComponent(String shortDescription, int radius) {
        super(shortDescription);
        mRadius = radius;
        setHorizontalAlignment(CENTER);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(mRadius * 2, mRadius * 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (mPressed) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.LIGHT_GRAY);
        }
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.black);
        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        return shape.contains(x, y);
    }

    /**
     * Set if this Button is pressed.
     *
     * @param pressed true if this Button is pressed, false otherwise
     */
    public void setPressed(boolean pressed) {
        if (mPressed == pressed) return;
        mPressed = pressed;
        this.repaint();
    }

    /**
     * Get if this Button is pressed.
     *
     * @return true if this Button is pressed
     */
    public boolean isPressed() {
        return mPressed;
    }
}
