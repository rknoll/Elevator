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

    private Building mBuilding;
    private Elevator mElevator;
    private JPanel mElevatorPanel;

    private Color mElevatorColor;

    public ElevatorPositionComponent(Building building, Elevator elevator) {
        mBuilding = building;
        mElevator = elevator;

        mElevatorColor = createColorCode(elevator.getElevatorNumber(), mBuilding.getNumberOfElevators());

        setLayout(new BorderLayout());

        Component positionPanel = CreateComponentElevatorPosition();
        add(positionPanel, BorderLayout.CENTER);

        addComponentListener(new ResizeListener());
        elevator.addPropertyChangeListener(Elevator.PROP_POSITION, this);
    }

    private Color createColorCode(int colorIndex, int numColors) {
        float hueMax = (float) 0.85;
        float sat = (float) 0.8;
        float hue = hueMax * colorIndex / numColors;
        if (colorIndex % 2 == 0) {
            return Color.getHSBColor(hue, sat, (float) 0.9);
        } else {
            return Color.getHSBColor(hue, sat, (float) 0.7);
        }
    }

    private Component CreateComponentElevatorPosition() {
        JPanel pnlFloorPosition = new JPanel(null);

        pnlFloorPosition.setPreferredSize(new Dimension(30, 30));
        pnlFloorPosition.setBackground(Color.lightGray);
        pnlFloorPosition.setBorder(BorderFactory.createLineBorder(Color.black));

        mElevatorPanel = new JPanel();
        mElevatorPanel.setBackground(mElevatorColor);
        mElevatorPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        pnlFloorPosition.add(mElevatorPanel);

        return pnlFloorPosition;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        double floorHeight = this.getHeight() / mBuilding.getNumberOfFloors();
        double elevatorPos = (double) mElevator.getPosition() / mBuilding.getHeight();
        double pos = this.getHeight() - floorHeight - elevatorPos * this.getHeight();
        mElevatorPanel.setLocation(0, (int) pos);
    }

    @Override
    public void unload() {
        mElevator.removePropertyChangeListener(Elevator.PROP_POSITION, this);
    }

    class ResizeListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            mElevatorPanel.setBounds(0, 0, 30, e.getComponent().getHeight() / mBuilding.getNumberOfFloors());
            double floorHeight = e.getComponent().getHeight() / mBuilding.getNumberOfFloors();
            double elevatorPos = (double) mElevator.getPosition() / mBuilding.getHeight();
            double pos = e.getComponent().getHeight() - floorHeight - elevatorPos * e.getComponent().getHeight();
            mElevatorPanel.setLocation(0, (int) pos);
        }
    }

}
