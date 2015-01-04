package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.ui.DynamicUIComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * GUI Component to show the Status of all Buttons inside an Elevator
 */
public class ElevatorButtonsComponent extends DynamicUIComponent implements PropertyChangeListener {

    private Building mBuilding;
    private Elevator mElevator;

    //private JLabel mElevatorButtons;

    private Map<Floor, ElevatorButtonComponent> mElevatorButtons;

    public ElevatorButtonsComponent(Building building, Elevator elevator) {
        mBuilding = building;
        mElevator = elevator;

        int floors = mBuilding.getNumberOfFloors();
        int columns = getColumns(floors);
        int rows = (int)Math.ceil(1.0 * floors / columns);
        setLayout(new GridLayout(0,columns,5,5));

        mElevatorButtons = new HashMap<Floor, ElevatorButtonComponent>();
        ElevatorButtonComponent[] components = new ElevatorButtonComponent[rows * columns];

        for (Floor f : mBuilding.getFloors()) {
            ElevatorButtonComponent component = new ElevatorButtonComponent(f.getFloorNumber());
            components[positionOf(f.getFloorNumber(), floors, columns, rows)] = component;
            mElevatorButtons.put(f, component);
        }

        for(int i = 0; i < components.length; ++i) {
            if (components[i] != null) {
                add(components[i]);
            } else {
                add(Box.createGlue());
            }
        }

        showDetails();

        mElevator.addPropertyChangeListener(Elevator.PROP_BUTTON, this);
    }

    private int positionOf(int floorNumber, int floors, int columns, int rows) {
        int n = floorNumber + columns * rows - floors;
        int r = rows - n / columns - 1;
        int c = n % columns;
        return r * columns + c;
    }

    private int getColumns(int numberOfFloors) {
        final double golden = (1+Math.sqrt(5))/2;
        double lastDistance = Double.MAX_VALUE;
        for (int i = 1; i < numberOfFloors; ++i) {
            int rows = (int)Math.ceil(1.0 * numberOfFloors / i);
            double ratio = 1.0 * rows / i;
            if (ratio < golden) {
                double distance = golden - ratio;
                return lastDistance < distance ? i - 1 : i;
            }
            lastDistance = ratio - golden;
        }
        return 1;
    }

    @Override
    public void unload() {
        mElevator.removePropertyChangeListener(Elevator.PROP_BUTTON, this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        showDetails();
    }

    private void showDetails() {
        for (Floor f : mBuilding.getFloors()) {
            mElevatorButtons.get(f).setPressed(mElevator.getButton(f));
        }
    }
}
