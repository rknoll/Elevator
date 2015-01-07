package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.ui.DynamicUIComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * GUI Component to show the current Position of an Elevator in the Building.
 */
public class ElevatorPositionComponent extends DynamicUIComponent implements PropertyChangeListener {
    private static final long serialVersionUID = -5323061259405307970L;

    /**
     * The Building
     */
    private final Building mBuilding;
    /**
     * The Elevator
     */
    private final Elevator mElevator;
    /**
     * The Panel of the Elevator View
     */
    private JPanel mElevatorPanel;

    /**
     * Create a new ElevatorPositionComponent to visualize the Position of an Elevator.
     *
     * @param building The Building of the Elevator
     * @param elevator The Elevator
     */
    public ElevatorPositionComponent(Building building, Elevator elevator) {
        mBuilding = building;
        mElevator = elevator;

        setLayout(new BorderLayout());

        // Panel for the whole Elevator Shaft
        JPanel pnlFloorPosition = new JPanel(null);

        pnlFloorPosition.setPreferredSize(new Dimension(30, 30));
        pnlFloorPosition.setBackground(Color.lightGray);
        pnlFloorPosition.setBorder(BorderFactory.createLineBorder(Color.black));

        // Panel for the actual Elevator
        mElevatorPanel = new JPanel();
        mElevatorPanel.setBackground(createColorCode(mElevator.getElevatorNumber(), mBuilding.getNumberOfElevators()));
        mElevatorPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        pnlFloorPosition.add(mElevatorPanel);

        add(pnlFloorPosition, BorderLayout.CENTER);

        // add resize listener to correctly position the Elevator inside the Shaft
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                mElevatorPanel.setBounds(0, 0, 30, e.getComponent().getHeight() / mBuilding.getNumberOfFloors());
                updateView();
            }
        });

        mElevator.addPropertyChangeListener(Elevator.PROP_POSITION, this);
    }

    /**
     * Create a new Color based on the Total Number of Colors and the Index.
     * This uses the Hue Value to generate numColors different Colors and selects the one
     * at Index colorIndex.
     *
     * @param colorIndex Index of the Color to Create
     * @param numColors  Total Number of Colors to generate
     * @return
     */
    private static Color createColorCode(int colorIndex, int numColors) {
        float hueMax = (float) 0.85;
        float sat = (float) 0.8;
        float hue = hueMax * colorIndex / numColors;
        if (colorIndex % 2 == 0) {
            return Color.getHSBColor(hue, sat, (float) 0.9);
        } else {
            return Color.getHSBColor(hue, sat, (float) 0.7);
        }
    }

    /**
     * Update the Position of the Elevator in the View
     */
    private void updateView() {
        double floorHeight = getHeight() / mBuilding.getNumberOfFloors();
        double elevatorPos = (double) mElevator.getPosition() / mBuilding.getHeight();
        double pos = getHeight() - floorHeight - elevatorPos * getHeight();
        mElevatorPanel.setLocation(0, (int) pos);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateView();
    }

    @Override
    public void unload() {
        mElevator.removePropertyChangeListener(Elevator.PROP_POSITION, this);
    }

}
