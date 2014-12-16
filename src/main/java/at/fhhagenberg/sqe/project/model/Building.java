package at.fhhagenberg.sqe.project.model;

import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;

/**
 * Created by rknoll on 16/12/14.
 */
public class Building {

    private IElevatorAdapter mAdapter;

    public Building(IElevatorAdapter adapter) {
        mAdapter = adapter;
    }

}
