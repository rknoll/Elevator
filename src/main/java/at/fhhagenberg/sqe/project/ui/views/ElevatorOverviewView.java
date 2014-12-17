package at.fhhagenberg.sqe.project.ui.views;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.listeners.IElevatorPositionListener;
import at.fhhagenberg.sqe.project.ui.components.ElevatorFloorComponent;
import at.fhhagenberg.sqe.project.ui.components.ElevatorModeComponent;
import at.fhhagenberg.sqe.project.ui.components.ElevatorPositionComponent;
import at.fhhagenberg.sqe.project.ui.components.FloorStatusComponent;
import at.fhhagenberg.sqe.project.ui.views.listeners.IElevatorDetailSelectListener;

import javax.swing.*;

import java.awt.*;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorOverviewView extends JComponent {

    private Building mBuilding;

    public ElevatorOverviewView(Building building, IElevatorDetailSelectListener selectListener) {
        mBuilding = building;

        setLayout(new FlowLayout());
        JPanel mainPanel = CreateMainPanel(selectListener);
        add(mainPanel);
    }
    
    private JPanel CreateMainPanel(IElevatorDetailSelectListener selectListener) {
		final GridBagLayout layout = new GridBagLayout();
		JPanel mainPanel = new JPanel(layout);

		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;

		gc.anchor = GridBagConstraints.CENTER;

		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridwidth = 2;

		for (Elevator e : mBuilding.getElevators()) {
			Component selElevators = new ElevatorModeComponent(e, selectListener);
			mainPanel.add(selElevators, gc);

			gc.gridy = 1;
			gc.gridwidth = 1;
			gc.gridheight = mBuilding.getNumberOfFloors();

			mainPanel.add(new ElevatorPositionComponent(mBuilding, e), gc);

			gc.gridheight = 1;
			gc.gridx += 1;
			for (Floor f : mBuilding.getFloors()) {
				mainPanel.add(new ElevatorFloorComponent(mBuilding, e, f), gc);
				gc.gridy += 1;
			}

			gc.gridy = 0;
			gc.gridx += 1;
			gc.gridwidth = 2;
		}

		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;

		for (Floor f : mBuilding.getFloors()) {
			mainPanel.add(new FloorStatusComponent(mBuilding, f), gc);
			gc.gridy += 1;
		}

		return mainPanel;
	}

}
