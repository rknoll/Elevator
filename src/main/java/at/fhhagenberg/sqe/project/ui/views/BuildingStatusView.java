package at.fhhagenberg.sqe.project.ui.views;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.ui.DynamicUIComponent;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Status Bar of the Building and its Connection
 */
public class BuildingStatusView extends DynamicUIComponent implements PropertyChangeListener {

    private final Building mBuilding;

    private final JLabel mConnectionStatusLabel;

    public BuildingStatusView(Building building) {
        mBuilding = building;

        setLayout(new BorderLayout());

        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(0, 18));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        mConnectionStatusLabel = new JLabel();
        mConnectionStatusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(mConnectionStatusLabel);

        add(statusPanel, BorderLayout.CENTER);

        updateStatus();

        mBuilding.addPropertyChangeListener(Building.PROP_CONNECTED, this);
    }

    @Override
    public void unload() {
        mBuilding.removePropertyChangeListener(Building.PROP_CONNECTED, this);
    }

    private void updateStatus() {
        if (mBuilding.isConnected()) {
            mConnectionStatusLabel.setText("Status: Connected");
        } else {
            mConnectionStatusLabel.setText("Status: Disconnected");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateStatus();
    }
}
