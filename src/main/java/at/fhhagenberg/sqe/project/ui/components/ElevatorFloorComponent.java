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

    private final Elevator mElevator;
    private final Floor mFloor;

    private final Lock mLock;
    private final Condition mCondition;
    private final Condition mCondition2;

    private JCheckBox mServeFloorCheckBox;
    private JButton mCallButton;
    private Thread mWaitThread;
    private boolean mUpdateBlocked;

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

    private Component createComponentElevatorSettings(Elevator elevator, Floor floor) {
        JPanel pnlElevatorSettings = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;

        gc.anchor = GridBagConstraints.CENTER;

        mCallButton = new JButton("Call");
        mCallButton.setName(elevator.getDescription() + " Call " + floor.getDescription());
        pnlElevatorSettings.add(mCallButton, gc);

        gc.gridy = 1;
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
            mCallButton.setEnabled(!mElevator.isAutomaticMode() && mServeFloorCheckBox.isSelected());

            mElevator.setService(mFloor, mServeFloorCheckBox.isSelected());

            mLock.lock();
            try {
                if (mWaitThread != null) {
                    mWaitThread.interrupt();
                }
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

                        updateValues();

                        mWaitThread = null;
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

    private void updateValues() {
        boolean newValue = mElevator.getService(mFloor);
        boolean oldValue = mServeFloorCheckBox.isSelected();

        if (newValue != oldValue) {
            mServeFloorCheckBox.setSelected(newValue);
            mCallButton.setEnabled(!mElevator.isAutomaticMode() && newValue);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Elevator.PROP_SERVICE:
                mLock.lock();
                try {
                    if (mUpdateBlocked) {
                        break;
                    }
                    updateValues();
                    break;
                } finally {
                    mLock.unlock();
                }
            case Elevator.PROP_AUTOMATIC_MODE:
                mCallButton.setEnabled(!mElevator.isAutomaticMode() && mServeFloorCheckBox.isSelected());
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
