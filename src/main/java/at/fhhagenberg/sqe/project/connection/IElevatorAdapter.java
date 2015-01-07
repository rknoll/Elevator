package at.fhhagenberg.sqe.project.connection;

import at.fhhagenberg.sqe.project.model.Elevator;

/**
 * Adapter Interface to Elevator
 */
public interface IElevatorAdapter {

    /**
     * Retrieves the committed direction of the specified elevator (up / down / uncommitted).
     *
     * @param elevatorNumber - elevator number whose committed direction is being retrieved
     * @return the current direction of the specified elevator
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public Elevator.Direction getCommittedDirection(int elevatorNumber) throws ElevatorConnectionLostException;

    /**
     * Provides the current acceleration of the specified elevator in feet per sec^2.
     *
     * @param elevatorNumber - elevator number whose acceleration is being retrieved
     * @return returns the acceleration of the indicated elevator where positive speed is acceleration and negative is deceleration
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public int getElevatorAccel(int elevatorNumber) throws ElevatorConnectionLostException;

    /**
     * Provides the status of a floor request button on a specified elevator (on/off).
     *
     * @param elevatorNumber - elevator number whose button status is being retrieved
     * @param floor          - floor number button being checked on the selected elevator
     * @return returns boolean to indicate if floor button on the elevator is active (true) or not (false)
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public boolean getElevatorButton(int elevatorNumber, int floor) throws ElevatorConnectionLostException;

    /**
     * Provides the current status of the doors of the specified elevator (open/closed).
     *
     * @param elevatorNumber - elevator number whose door status is being retrieved
     * @return returns the door status of the indicated elevator
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public Elevator.DoorStatus getElevatorDoorStatus(int elevatorNumber) throws ElevatorConnectionLostException;

    /**
     * Provides the current location of the specified elevator to the nearest floor
     *
     * @param elevatorNumber - elevator number whose location is being retrieved
     * @return returns the floor number of the floor closest to the indicated elevator
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public int getElevatorFloor(int elevatorNumber) throws ElevatorConnectionLostException;

    /**
     * Retrieves the number of elevators in the building.
     *
     * @return total number of elevators
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public int getElevatorNum() throws ElevatorConnectionLostException;

    /**
     * Provides the current location of the specified elevator in feet from the bottom of the building.
     *
     * @param elevatorNumber - elevator number whose location is being retrieved
     * @return returns the location in feet of the indicated elevator from the bottom of the building
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public int getElevatorPosition(int elevatorNumber) throws ElevatorConnectionLostException;

    /**
     * Provides the current speed of the specified elevator in feet per sec.
     *
     * @param elevatorNumber - elevator number whose speed is being retrieved
     * @return returns the speed of the indicated elevator where positive speed is up and negative is down
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public int getElevatorSpeed(int elevatorNumber) throws ElevatorConnectionLostException;

    /**
     * Retrieves the weight of passengers on the specified elevator.
     *
     * @param elevatorNumber - elevator number whose service is being retrieved
     * @return total weight of all passengers in lbs
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public int getElevatorWeight(int elevatorNumber) throws ElevatorConnectionLostException;

    /**
     * Retrieves the maximum number of passengers that can fit on the specified elevator.
     *
     * @param elevatorNumber - elevator number whose service is being retrieved
     * @return number of passengers
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public int getElevatorCapacity(int elevatorNumber) throws ElevatorConnectionLostException;

    /**
     * Provides the status of the Down button on specified floor (on/off).
     *
     * @param floor - floor number whose Down button status is being retrieved
     * @return returns boolean to indicate if button is active (true) or not (false)
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public boolean getFloorButtonDown(int floor) throws ElevatorConnectionLostException;

    /**
     * Provides the status of the Up button on specified floor (on/off).
     *
     * @param floor - floor number whose Up button status is being retrieved
     * @return returns boolean to indicate if button is active (true) or not (false)
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public boolean getFloorButtonUp(int floor) throws ElevatorConnectionLostException;

    /**
     * Retrieves the height of the floors in the building.
     *
     * @return floor height (ft)
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public int getFloorHeight() throws ElevatorConnectionLostException;

    /**
     * Retrieves the number of floors in the building.
     *
     * @return total number of floors
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public int getFloorNum() throws ElevatorConnectionLostException;

    /**
     * Retrieves whether or not the specified elevator will service the specified floor (yes/no).
     *
     * @param elevatorNumber elevator number whose service is being retrieved
     * @param floor          floor whose service status by the specified elevator is being retrieved
     * @return service status whether the floor is serviced by the specified elevator (yes=true,no=false)
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public boolean getServicesFloors(int elevatorNumber, int floor) throws ElevatorConnectionLostException;

    /**
     * Retrieves the floor target of the specified elevator.
     *
     * @param elevatorNumber elevator number whose target floor is being retrieved
     * @return current floor target of the specified elevator
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public int getTarget(int elevatorNumber) throws ElevatorConnectionLostException;

    /**
     * Sets the committed direction of the specified elevator (up / down / uncommitted).
     *
     * @param elevatorNumber elevator number whose committed direction is being set
     * @param direction      direction being set
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public void setCommittedDirection(int elevatorNumber, Elevator.Direction direction) throws ElevatorConnectionLostException;

    /**
     * Sets whether or not the specified elevator will service the specified floor (yes/no).
     *
     * @param elevatorNumber elevator number whose service is being defined
     * @param floor          floor whose service by the specified elevator is being set
     * @param service        indicates whether the floor is serviced by the specified elevator (yes=true,no=false)
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public void setServicesFloors(int elevatorNumber, int floor, boolean service) throws ElevatorConnectionLostException;

    /**
     * Sets the floor target of the specified elevator.
     *
     * @param elevatorNumber elevator number whose target floor is being set
     * @param target         floor number which the specified elevator is to target
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public void setTarget(int elevatorNumber, int target) throws ElevatorConnectionLostException;

    /**
     * Retrieves the current clock tick of the elevator control system.
     *
     * @return clock tick
     * @throws ElevatorConnectionLostException - No Connection or Connection Lost
     */
    public long getClockTick() throws ElevatorConnectionLostException;
}
