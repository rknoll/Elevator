package at.fhhagenberg.sqe.project.ui.views;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.ui.DynamicUIComponent;
import at.fhhagenberg.sqe.project.ui.views.listeners.IElevatorOverviewSelectListener;

import javax.swing.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorDetailView extends DynamicUIComponent implements PropertyChangeListener {

	private Building mBuilding;
	private Elevator mElevator;

	private JLabel mDescriptionLabel;

	private JLabel mAutomaticMode;
	private JLabel mCurrentFloor;
	private JLabel mPosition;
	private JLabel mTarget;
	private JLabel mDirection;
	private JLabel mDoorStatus;
	private JLabel mSpeed;
	private JLabel mAcceleration;
	private JLabel mCapacity;
	private JLabel mWeight;

	private JLabel mElevatorButtons;

	public ElevatorDetailView(Building building, Elevator elevator, IElevatorOverviewSelectListener selectListener) {
		mBuilding = building;
		mElevator = elevator;
		mDescriptionLabel = new JLabel();

		setLayout(new FlowLayout());

		add(CreateMainPanel(selectListener));

		elevator.addPropertyChangeListener(this);
	}

	private JPanel CreateMainPanel(IElevatorOverviewSelectListener selectListener)
	{
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.CENTER;

		JButton returnButton = new JButton("Return");
		returnButton.addActionListener(event -> selectListener.selectOverview());
		mainPanel.add(returnButton);

		gc.gridx += 1;
		mainPanel.add(mDescriptionLabel, gc);

		gc.gridx = 0;
		gc.gridy = 1;

		Component detailInfo = CreateComponentDetailInfo();
		mainPanel.add(detailInfo, gc);

		gc.gridx += 1;
		gc.gridy = 1;
		mElevatorButtons = new JLabel();
		mainPanel.add(mElevatorButtons, gc);

		showDetails();

		return mainPanel;
	}

	private Component CreateComponentDetailInfo()
	{
		JPanel infoPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.gridx = 0;
		gc.gridy = 0;

		gc.anchor = GridBagConstraints.WEST;

		infoPanel.add(new JLabel("Current Mode:"), gc);
		gc.gridx += 1;
		mAutomaticMode = new JLabel();
		infoPanel.add(mAutomaticMode, gc);
		gc.gridx -= 1;
		gc.gridy += 1;

		infoPanel.add(new JLabel("Position:"), gc);
		gc.gridx += 1;
		mCurrentFloor = new JLabel();
		infoPanel.add(mCurrentFloor, gc);
		gc.gridx -= 1;
		gc.gridy += 1;

		infoPanel.add(new JLabel("Position:"), gc);
		gc.gridx += 1;
		mPosition = new JLabel();
		infoPanel.add(mPosition, gc);
		gc.gridx -= 1;
		gc.gridy += 1;

		infoPanel.add(new JLabel("Target:"), gc);
		gc.gridx += 1;
		mTarget = new JLabel();
		infoPanel.add(mTarget, gc);
		gc.gridx -= 1;
		gc.gridy += 1;

		infoPanel.add(new JLabel("Direction:"), gc);
		gc.gridx += 1;
		mDirection = new JLabel();
		infoPanel.add(mDirection, gc);
		gc.gridx -= 1;
		gc.gridy += 1;

		infoPanel.add(new JLabel("Door State:"), gc);
		gc.gridx += 1;
		mDoorStatus = new JLabel();
		infoPanel.add(mDoorStatus, gc);
		gc.gridx -= 1;
		gc.gridy += 1;

		infoPanel.add(new JLabel("Speed:"), gc);
		gc.gridx += 1;
		mSpeed = new JLabel();
		infoPanel.add(mSpeed, gc);
		gc.gridx -= 1;
		gc.gridy += 1;

		infoPanel.add(new JLabel("Acceleration:"), gc);
		gc.gridx += 1;
		mAcceleration = new JLabel();
		infoPanel.add(mAcceleration, gc);
		gc.gridx -= 1;
		gc.gridy += 1;

		infoPanel.add(new JLabel("Capacity:"), gc);
		gc.gridx += 1;
		mCapacity = new JLabel();
		infoPanel.add(mCapacity, gc);
		gc.gridx -= 1;
		gc.gridy += 1;

		infoPanel.add(new JLabel("Weight:"), gc);
		gc.gridx += 1;
		mWeight = new JLabel();
		infoPanel.add(mWeight, gc);
		gc.gridx -= 1;
		gc.gridy += 1;

		return infoPanel;
	}

	private void showDetails() {
		mDescriptionLabel.setText(mElevator.getDescription());

		mAutomaticMode.setText(mElevator.isAutomaticMode() ? "Automatic" : "Manual");
		mCurrentFloor.setText(mElevator.getCurrentFloor() != null ? mElevator.getCurrentFloor().getDescription() : "None");
		mPosition.setText("" + mElevator.getPosition());
		mTarget.setText(mElevator.getTarget() != null ? mElevator.getTarget().getDescription() : "None");
		mDirection.setText(mElevator.getDirection() != null ? mElevator.getDirection().name() : "None");
		mDoorStatus.setText(mElevator.getDoorStatus() != null ? mElevator.getDoorStatus().name() : "None");
		mSpeed.setText("" + mElevator.getSpeed());
		mAcceleration.setText("" + mElevator.getAcceleration());
		mCapacity.setText("" + mElevator.getCapacity());
		mWeight.setText("" + mElevator.getWeight());

		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("Pressed Buttons:<br>");
		for (Floor f : mBuilding.getFloors())
		{
			if (mElevator.getButton(f))
			{
				sb.append(f.getDescription());
				sb.append("<br>");
			}
		}
		sb.append("</html>");
		mElevatorButtons.setText(sb.toString());
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		showDetails();
	}

	@Override
	public void unload() {
		mElevator.removePropertyChangeListener(this);
	}
}
