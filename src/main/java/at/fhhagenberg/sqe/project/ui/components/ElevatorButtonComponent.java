package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Floor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * GUI Component that Represents a Button inside an Elevator
 */
public class ElevatorButtonComponent extends JLabel {
    private static final long serialVersionUID = -2173760156678087371L;

    public static final int DEFAULT_RADIUS = 20;

    private final int mRadius;

    private boolean mPressed;
    private Shape shape;

    public ElevatorButtonComponent(Floor floor) {
        this(floor, DEFAULT_RADIUS);
    }

    public ElevatorButtonComponent(Floor floor, int radius) {
        super(floor.getShortDescription());
        mRadius = radius;
        setHorizontalAlignment(CENTER);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(mRadius, mRadius);
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

    public void setPressed(boolean pressed) {
        if (mPressed == pressed) return;
        mPressed = pressed;
        this.repaint();
    }

    public boolean isPressed() {
        return mPressed;
    }
}
