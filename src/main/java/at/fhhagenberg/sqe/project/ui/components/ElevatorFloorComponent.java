package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Elevator.Direction;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.ui.DynamicUIComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * GUI Component to show one Floor Element of an Elevator.
 * Contains a Button to Call the Elevator and shows if the Elevator services this Floor.
 */
public class ElevatorFloorComponent extends DynamicUIComponent implements PropertyChangeListener, ActionListener {
    private static final long serialVersionUID = -7447358754586529153L;

    /**
     * The Elevator
     */
    private final Elevator mElevator;
    /**
     * The Floor of the Elevator
     */
    private final Floor mFloor;
    /**
     * Checkbox if the Elevator serves this Floor
     */
    private JCheckBox mServeFloorCheckBox;
    /**
     * Call Button to call the Elevator to this Floor
     */
    private JButton mCallButton;

    // Thread Safety
    private Thread mWaitThread;
    private boolean mUpdateBlocked;
    private final Lock mLock;
    private final Condition mCondition;
    private final Condition mCondition2;

    /**
     * Create a new ElevatorFloorComponent that Shows a Call Button and if the Elevator services the Floor
     *
     * @param elevator The Elevator
     * @param floor    The Floor
     */
    public ElevatorFloorComponent(Elevator elevator, Floor floor) {
        mElevator = elevator;
        mFloor = floor;

        mLock = new ReentrantLock();
        mCondition = mLock.newCondition();
        mCondition2 = mLock.newCondition();

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;

        Component elevatorSetting = createComponentElevatorSettings(elevator, floor);
        add(elevatorSetting, gc);
        setPreferredSize(new Dimension(90, 60));

        // property changed listeners
        mElevator.addPropertyChangeListener(Elevator.PROP_SERVICE, this);
        mElevator.addPropertyChangeListener(Elevator.PROP_AUTOMATIC_MODE, this);
        mElevator.addPropertyChangeListener(Elevator.PROP_CURRENT_FLOOR, this);

        // action listeners
        mCallButton.addActionListener(this);
        mServeFloorCheckBox.addActionListener(this);
    }

    /**
     * Create the Panel and its Components.
     *
     * @param elevator The Elevator
     * @param floor    The Floor
     * @return The Panel to show on Screen
     */
    private Component createComponentElevatorSettings(Elevator elevator, Floor floor) {
        JPanel pnlElevatorSettings = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;

        gc.anchor = GridBagConstraints.CENTER;

        // Create Call Button
        mCallButton = new JButton("Call");
        mCallButton.setName(elevator.getDescription() + " Call " + floor.getDescription());
        pnlElevatorSettings.add(mCallButton, gc);

        gc.gridy = 1;

        // Create Serve Check Box
        mServeFloorCheckBox = new JCheckBox("Serve");
        mServeFloorCheckBox.setName(elevator.getDescription() + " Serve " + floor.getDescription());

        mServeFloorCheckBox.setSelected(elevator.getService(floor));

        mCallButton.setEnabled(!mElevator.isAutomaticMode() && mServeFloorCheckBox.isSelected());

        pnlElevatorSettings.add(mServeFloorCheckBox, gc);

        return pnlElevatorSettings;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mCallButton) {
            int targetFloor = mFloor.getFloorNumber();
            if (mElevator.getCurrentFloor() == null) {
                mElevator.setDirection(Direction.UNCOMMITTED);
            } else {
                int currentFloor = mElevator.getCurrentFloor().getFloorNumber();

                // set direction
                if (targetFloor > currentFloor) {
                    mElevator.setDirection(Direction.UP);
                } else if (targetFloor < currentFloor) {
                    mElevator.setDirection(Direction.DOWN);
                } else {
                    mElevator.setDirection(Direction.UNCOMMITTED);
                }
            }

            // set new target
            mElevator.setTarget(mFloor);
        } else if (e.getSource() == mServeFloorCheckBox) {
            mElevator.setService(mFloor, mServeFloorCheckBox.isSelected());

            updateValues();

            // keep the same Value for at least 500 ms to prevent glitches
            mLock.lock();
            try {
                if (mWaitThread != null) mWaitThread.interrupt();
                mWaitThread = new Thread(() -> {
                    mLock.lock();
                    try {
                        mUpdateBlocked = true;
                        mCondition2.signal();

                        try {
                            mCondition.await(500, TimeUnit.MILLISECONDS);
                        } catch (InterruptedException ignored) {
                            return;
                        }

                        mUpdateBlocked = false;
                        mWaitThread = null;

                        updateValues();
                    } finally {
                        mLock.unlock();
                    }
                });
                mWaitThread.start();

                try {
                    do {
                        mCondition2.await();
                    } while (!mUpdateBlocked);
                } catch (InterruptedException ignored) {
                }
            } finally {
                mLock.unlock();
            }
        }
    }

    /**
     * Update Checkbox and Enabled State of Call Button
     */
    private void updateValues() {
        boolean services = mElevator.getService(mFloor);
        mServeFloorCheckBox.setSelected(services);
        mCallButton.setEnabled(!mElevator.isAutomaticMode() && services);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Elevator.PROP_SERVICE:
                mLock.lock();
                try {
                    if (!mUpdateBlocked) updateValues();
                } finally {
                    mLock.unlock();
                }
                break;
            case Elevator.PROP_AUTOMATIC_MODE:
                updateValues();
                break;
        }
    }

    @Override
    public void unload() {
        mElevator.removePropertyChangeListener(Elevator.PROP_SERVICE, this);
        mElevator.removePropertyChangeListener(Elevator.PROP_AUTOMATIC_MODE, this);
        mElevator.removePropertyChangeListener(Elevator.PROP_CURRENT_FLOOR, this);
    }
}
