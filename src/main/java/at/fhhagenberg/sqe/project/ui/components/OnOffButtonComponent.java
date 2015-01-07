package at.fhhagenberg.sqe.project.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * GUI Component for a Simple On/Off Switch
 */
public class OnOffButtonComponent extends AbstractButton {
    private static final long serialVersionUID = -9158297973761432411L;

    // Color Constants
    private final Color colorBright = new Color(220, 220, 220);
    private final Color colorDark = new Color(150, 150, 150);
    private final Color black = new Color(0, 0, 0, 100);
    private final Color white = new Color(255, 255, 255, 100);
    private final Color light = new Color(220, 220, 220);
    private final Color textColor = new Color(0, 0, 0, 200);
    private final Color falseColor = new Color(192, 192, 192);
    private final Color trueColor = new Color(230, 46, 46);

    // Default Font
    private final Font font = new JLabel().getFont();

    // Calculated Sizes
    private final int gap;
    private final int globalWidth;
    private final Dimension thumbBounds;
    private final int max;
    private final int trueLength;
    private final int falseLength;

    // Captions
    private final String trueLabel;
    private final String falseLabel;

    /**
     * Create a new OnOffButtonComponent in a Slider Style.
     *
     * @param trueLabel  Text to show if the Value is True
     * @param falseLabel Text to show if the Value is False
     */
    public OnOffButtonComponent(String trueLabel, String falseLabel) {
        this.trueLabel = trueLabel;
        this.falseLabel = falseLabel;

        trueLength = (int) getFontMetrics(font).getStringBounds(trueLabel, getGraphics()).getWidth();
        falseLength = (int) getFontMetrics(font).getStringBounds(falseLabel, getGraphics()).getWidth();

        max = Math.max(trueLength, falseLength);
        gap = Math.abs(trueLength - falseLength);

        thumbBounds = new Dimension(30, 18);
        globalWidth = max + thumbBounds.width + gap;

        setModel(new DefaultButtonModel());
        setSelected(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // check if the Click was in our Component
                if (new Rectangle(getPreferredSize()).contains(e.getPoint())) {
                    setSelected(!isSelected());
                }
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(globalWidth, thumbBounds.height);
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            setText(trueLabel);
            setBackground(trueColor);
        } else {
            setText(falseLabel);
            setBackground(falseColor);
        }
        super.setSelected(selected);
    }

    @Override
    public int getHeight() {
        return getPreferredSize().height;
    }

    @Override
    public int getWidth() {
        return getPreferredSize().width;
    }

    @Override
    public Font getFont() {
        return font;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(1, 1, getWidth() - 2 - 1, getHeight() - 2, 2, 2);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(black);
        g2.drawRoundRect(1, 1, getWidth() - 2 - 1, getHeight() - 2 - 1, 2, 2);
        g2.setColor(white);
        g2.drawRoundRect(1 + 1, 1 + 1, getWidth() - 2 - 3, getHeight() - 2 - 3, 2, 2);

        int x;
        int lx;

        if (isSelected()) {
            x = max + gap;
            lx = (max - trueLength) / 2;
        } else {
            x = 0;
            lx = thumbBounds.width + (max - falseLength) / 2;
        }

        int y = 0;
        int w = thumbBounds.width;
        int h = thumbBounds.height;

        g2.setPaint(new GradientPaint(x, (int) (y - 0.1 * h), colorDark, x, (int) (y + 1.2 * h), light));
        g2.fillRect(x, y, w, h);
        g2.setPaint(new GradientPaint(x, (int) (y + .65 * h), light, x, (int) (y + 1.3 * h), colorDark));
        g2.fillRect(x, (int) (y + .65 * h), w, (int) (h - .65 * h));

        // Draw Thumb Part
        if (w > 14) {
            int size = 10;
            g2.setColor(colorBright);
            g2.fillRect(x + w / 2 - size / 2, y + h / 2 - size / 2, size, size);
            g2.setColor(new Color(120, 120, 120));
            g2.fillRect(x + w / 2 - 4, h / 2 - 4, 2, 2);
            g2.fillRect(x + w / 2 - 1, h / 2 - 4, 2, 2);
            g2.fillRect(x + w / 2 + 2, h / 2 - 4, 2, 2);
            g2.setColor(colorDark);
            g2.fillRect(x + w / 2 - 4, h / 2 - 2, 2, 6);
            g2.fillRect(x + w / 2 - 1, h / 2 - 2, 2, 6);
            g2.fillRect(x + w / 2 + 2, h / 2 - 2, 2, 6);
            g2.setColor(new Color(170, 170, 170));
            g2.fillRect(x + w / 2 - 4, h / 2 + 2, 2, 2);
            g2.fillRect(x + w / 2 - 1, h / 2 + 2, 2, 2);
            g2.fillRect(x + w / 2 + 2, h / 2 + 2, 2, 2);
        }

        g2.setColor(black);
        g2.drawRoundRect(x, y, w - 1, h - 1, 2, 2);
        g2.setColor(white);
        g2.drawRoundRect(x + 1, y + 1, w - 3, h - 3, 2, 2);

        // Write Text
        g2.setColor(textColor);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(getFont());
        g2.drawString(getText(), lx + gap / 2, y + h / 2 + h / 4);
    }
}
