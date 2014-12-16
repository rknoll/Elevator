package at.fhhagenberg.sqe.project.ui;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.services.IElevatorInfoListener;

import javax.swing.*;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorWindow {
    private JFrame mFrame;
    private Building mBuilding;

    public ElevatorWindow(Building building) {
        mBuilding = building;

        mFrame = new JFrame();
        mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        for (Elevator e : mBuilding.getElevators()) {
            mBuilding.addListener(new IElevatorInfoListener() {

                @Override
                public Elevator getElevator() {
                    return e;
                }

                @Override
                public void update() {
                    System.out.println(e.getDescription() + " changed.");
                }
            });
        }

        mFrame.setSize(300, 200);
        //mFrame.pack();
        mFrame.setVisible(true);
    }
}
