package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

/**
 * Created by rknoll on 17/12/14.
 */
public class ElevatorPositionComponent extends JComponent implements PropertyChangeListener {

    private Building mBuilding;
    private Elevator mElevator;
    private JPanel mElevatorPanel;

    public ElevatorPositionComponent(Building building, Elevator elevator) {
        mBuilding = building;
        mElevator = elevator;

        setLayout(new BorderLayout());

        Component positionPanel = CreateComponentElevatorPosition();
        add(positionPanel, BorderLayout.CENTER);

        addComponentListener(new ResizeListener());
        elevator.addPropertyChangeListener(Elevator.PROP_POSITION, this);
    }

    private Color randomColor() {
        Random rand = new Random();
        // Java 'Color' class takes 3 floats, from 0 to 1.
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }

    private Component CreateComponentElevatorPosition()
    {
        JPanel pnlFloorPosition = new JPanel(null);

        pnlFloorPosition.setPreferredSize(new Dimension(30, 30));

        mElevatorPanel = new JPanel();
        mElevatorPanel.setBackground(randomColor());
        mElevatorPanel.setBounds(0, 0, 30, 60);

        pnlFloorPosition.add(mElevatorPanel);

        return pnlFloorPosition;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        double floorHeight = this.getHeight() / mBuilding.getNumberOfFloors();
        double elevatorPos = (double)mElevator.getPosition() / mBuilding.getHeight();
        double pos = this.getHeight() - floorHeight - elevatorPos * this.getHeight();
        mElevatorPanel.setLocation(0, (int)pos);
    }

    class ResizeListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            mElevatorPanel.setBounds(0, 0, 30, e.getComponent().getHeight() / mBuilding.getNumberOfFloors());
            double floorHeight = e.getComponent().getHeight() / mBuilding.getNumberOfFloors();
            double elevatorPos = (double)mElevator.getPosition() / mBuilding.getHeight();
            double pos = e.getComponent().getHeight() - floorHeight - elevatorPos * e.getComponent().getHeight();
            mElevatorPanel.setLocation(0, (int)pos);
        }
    }

}
