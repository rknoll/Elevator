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
    private static final long serialVersionUID = -3194052998246909730L;

    private final Building mBuilding;

    private final JLabel mConnectionStatusLabel;
    private final JButton mReconnectButton;

    public BuildingStatusView(Building building) {
        mBuilding = building;

        setLayout(new BorderLayout());
        setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        setPreferredSize(new Dimension(0, 22));

        mConnectionStatusLabel = new JLabel();
        add(mConnectionStatusLabel, BorderLayout.LINE_START);

        mReconnectButton = new JButton("Reconnect");
        mReconnectButton.addActionListener(evt -> {
            mReconnectButton.setEnabled(false);
            mBuilding.setConnected(false);
        });
        add(mReconnectButton, BorderLayout.LINE_END);

        updateStatus();

        mBuilding.addPropertyChangeListener(Building.PROP_CONNECTED, this);
    }

    @Override
    public void unload() {
        mBuilding.removePropertyChangeListener(Building.PROP_CONNECTED, this);
    }

    private void updateStatus() {
        mReconnectButton.setEnabled(mBuilding.isConnected());
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
