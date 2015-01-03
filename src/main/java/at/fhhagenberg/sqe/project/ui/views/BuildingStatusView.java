package at.fhhagenberg.sqe.project.ui.views;

import at.fhhagenberg.sqe.project.ui.DynamicUIComponent;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by rknoll on 03/01/15.
 */
public class BuildingStatusView extends DynamicUIComponent implements PropertyChangeListener {

    public BuildingStatusView() {
        setLayout(new BorderLayout());

        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(0, 18));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        JLabel statusLabel = new JLabel("Status: Connected");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);

        add(statusPanel, BorderLayout.CENTER);
    }

    @Override
    public void unload() {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
