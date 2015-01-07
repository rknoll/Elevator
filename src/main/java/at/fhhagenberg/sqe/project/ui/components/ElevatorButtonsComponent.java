package at.fhhagenberg.sqe.project.ui.components;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.ui.DynamicUIComponent;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

/**
 * GUI Component to show the Status of all Buttons inside an Elevator
 */
public class ElevatorButtonsComponent extends DynamicUIComponent implements PropertyChangeListener {
    private static final long serialVersionUID = 2928612880686052934L;

    /**
     * The Building of this Elevator
     */
    private final Building mBuilding;
    /**
     * The Elevator
     */
    private final Elevator mElevator;
    /**
     * Child Components for each Floor
     */
    private final Map<Floor, ElevatorButtonComponent> mElevatorButtons;

    /**
     * Create a new ElevatorButtonsComponent for an Elevator inside a Building.
     *
     * @param building The Building
     * @param elevator The Elevator
     */
    public ElevatorButtonsComponent(Building building, Elevator elevator) {
        mBuilding = building;
        mElevator = elevator;
        mElevatorButtons = new HashMap<>();

        int floors = mBuilding.getNumberOfFloors();

        // calculate good ratio between rows and columns for the number of floors
        int columns = getColumns(floors);
        int rows = getRows(floors, columns);

        setLayout(new GridLayout(rows, columns, 5, 5));

        ElevatorButtonComponent[] components = new ElevatorButtonComponent[rows * columns];

        for (Floor f : mBuilding.getFloors()) {
            // create child component
            ElevatorButtonComponent component = new ElevatorButtonComponent(f.getShortDescription());
            // put it into the correct position
            components[positionOf(f.getFloorNumber(), floors, columns, rows)] = component;
            mElevatorButtons.put(f, component);
        }

        // insert all child Components
        for (ElevatorButtonComponent component : components) {
            add(component != null ? component : Box.createGlue());
        }

        showDetails();

        mElevator.addPropertyChangeListener(Elevator.PROP_BUTTON, this);
    }

    /**
     * Get the desired Position of a Floor Component in the Grid.
     *
     * @param floorNumber The Floor Number that needs to be placed
     * @param floors      The total Number of Floors
     * @param columns     The number of Columns
     * @param rows        The number of Rows
     * @return The desired Position of that Floor
     */
    private static int positionOf(int floorNumber, int floors, int columns, int rows) {
        int n = floorNumber + columns * rows - floors;
        int r = rows - n / columns - 1;
        int c = n % columns;
        return r * columns + c;
    }

    /**
     * Calculate the Number of Columns depending on the total Number of Floors.
     * This uses the golden ratio to be visually appealing.
     *
     * @param numberOfFloors Total Number of Floors
     * @return Number of Columns to be used
     */
    private static int getColumns(int numberOfFloors) {
        final double golden = (1 + Math.sqrt(5)) / 2;
        double lastDistance = Double.MAX_VALUE;
        for (int columns = 1; columns < numberOfFloors; ++columns) {
            int rows = getRows(numberOfFloors, columns);
            double ratio = 1.0 * rows / columns;
            if (ratio < golden) {
                double distance = golden - ratio;
                return lastDistance < distance ? columns - 1 : columns;
            }
            lastDistance = ratio - golden;
        }
        return 1;
    }

    /**
     * Calculate the number of Rows so that rows * columns >= floors.
     *
     * @param numberOfFloors  Total Number of Floors
     * @param numberOfColumns Calculated Number of Columns
     * @return Number of Rows
     */
    private static int getRows(int numberOfFloors, int numberOfColumns) {
        return (int) Math.ceil(1.0 * numberOfFloors / numberOfColumns);
    }

    @Override
    public void unload() {
        mElevator.removePropertyChangeListener(Elevator.PROP_BUTTON, this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        showDetails();
    }

    /**
     * Set the Child Components pressed States
     */
    private void showDetails() {
        for (Floor f : mBuilding.getFloors()) {
            mElevatorButtons.get(f).setPressed(mElevator.getButton(f));
        }
    }
}
