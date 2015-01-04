package at.fhhagenberg.sqe.project.ui.views;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.ui.DynamicUIComponent;
import at.fhhagenberg.sqe.project.ui.components.ElevatorButtonsComponent;
import at.fhhagenberg.sqe.project.ui.views.listeners.IElevatorOverviewSelectListener;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Detail View of an Elevator
 */
public class ElevatorDetailView extends DynamicUIComponent implements PropertyChangeListener {

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

    private ElevatorButtonsComponent mElevatorButtons;

    public ElevatorDetailView(Building building, Elevator elevator, IElevatorOverviewSelectListener selectListener) {
        mElevator = elevator;

        setLayout(new BorderLayout());

        JScrollPane scroll = new JScrollPane(CreateMainPanel(building, selectListener));
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);

        elevator.addPropertyChangeListener(this);
    }

    private JPanel CreateMainPanel(Building building, IElevatorOverviewSelectListener selectListener) {
        GridBagLayout gbl = new GridBagLayout();
        JPanel mainPanel = new JPanel(gbl);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.CENTER;

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(event -> selectListener.selectOverview());
        gc.insets = new Insets(10,0,0,10);
        mainPanel.add(returnButton, gc);

        gc.gridx += 1;
        mDescriptionLabel = new JLabel();
        Font font = mDescriptionLabel.getFont();
        mDescriptionLabel.setFont(new Font(font.getName(), Font.PLAIN, 16));
        gc.insets = new Insets(10,0,0,0);
        mainPanel.add(mDescriptionLabel, gc);

        gc.gridx = 1;
        gc.gridy = 1;

        Component detailInfo = CreateComponentDetailInfo();
        gc.insets = new Insets(0,0,0,10);
        mainPanel.add(detailInfo, gc);

        gc.gridx = 2;
        gc.gridy = 1;
        mElevatorButtons = new ElevatorButtonsComponent(building, mElevator);
        gc.insets = new Insets(0,0,0,0);
        mainPanel.add(mElevatorButtons, gc);

        // add space to bottom so that all controls get to the top of the window
        gc.gridx = 0;
        gc.gridy = 2;
        gc.gridwidth = 2;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;
        mainPanel.add(Box.createVerticalGlue(), gc);

        showDetails();

        return mainPanel;
    }

    private Component CreateComponentDetailInfo() {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 0;

        gc.anchor = GridBagConstraints.WEST;

        gc.insets = new Insets(0,0,0,10);
        infoPanel.add(new JLabel("Current Mode:"), gc);
        gc.gridx += 1;
        mAutomaticMode = new JLabel();
        gc.insets = new Insets(0,0,0,0);
        infoPanel.add(mAutomaticMode, gc);
        gc.gridx -= 1;
        gc.gridy += 1;

        gc.insets = new Insets(0,0,0,10);
        infoPanel.add(new JLabel("Position:"), gc);
        gc.gridx += 1;
        mCurrentFloor = new JLabel();
        gc.insets = new Insets(0,0,0,0);
        infoPanel.add(mCurrentFloor, gc);
        gc.gridx -= 1;
        gc.gridy += 1;

        gc.insets = new Insets(0,0,0,10);
        infoPanel.add(new JLabel("Position:"), gc);
        gc.gridx += 1;
        mPosition = new JLabel();
        gc.insets = new Insets(0,0,0,0);
        infoPanel.add(mPosition, gc);
        gc.gridx -= 1;
        gc.gridy += 1;

        gc.insets = new Insets(0,0,0,10);
        infoPanel.add(new JLabel("Target:"), gc);
        gc.gridx += 1;
        mTarget = new JLabel();
        gc.insets = new Insets(0,0,0,0);
        infoPanel.add(mTarget, gc);
        gc.gridx -= 1;
        gc.gridy += 1;

        gc.insets = new Insets(0,0,0,10);
        infoPanel.add(new JLabel("Direction:"), gc);
        gc.gridx += 1;
        mDirection = new JLabel();
        gc.insets = new Insets(0,0,0,0);
        infoPanel.add(mDirection, gc);
        gc.gridx -= 1;
        gc.gridy += 1;

        gc.insets = new Insets(0,0,0,10);
        infoPanel.add(new JLabel("Door State:"), gc);
        gc.gridx += 1;
        mDoorStatus = new JLabel();
        gc.insets = new Insets(0,0,0,0);
        infoPanel.add(mDoorStatus, gc);
        gc.gridx -= 1;
        gc.gridy += 1;

        gc.insets = new Insets(0,0,0,10);
        infoPanel.add(new JLabel("Speed:"), gc);
        gc.gridx += 1;
        mSpeed = new JLabel();
        gc.insets = new Insets(0,0,0,0);
        infoPanel.add(mSpeed, gc);
        gc.gridx -= 1;
        gc.gridy += 1;

        gc.insets = new Insets(0,0,0,10);
        infoPanel.add(new JLabel("Acceleration:"), gc);
        gc.gridx += 1;
        mAcceleration = new JLabel();
        gc.insets = new Insets(0,0,0,0);
        infoPanel.add(mAcceleration, gc);
        gc.gridx -= 1;
        gc.gridy += 1;

        gc.insets = new Insets(0,0,0,10);
        infoPanel.add(new JLabel("Capacity:"), gc);
        gc.gridx += 1;
        mCapacity = new JLabel();
        gc.insets = new Insets(0,0,0,0);
        infoPanel.add(mCapacity, gc);
        gc.gridx -= 1;
        gc.gridy += 1;

        gc.insets = new Insets(0,0,0,10);
        infoPanel.add(new JLabel("Weight:"), gc);
        gc.gridx += 1;
        mWeight = new JLabel();
        gc.insets = new Insets(0,0,0,0);
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
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        showDetails();
    }

    @Override
    public void unload() {
        mElevator.removePropertyChangeListener(this);
        mElevatorButtons.unload();
    }
}
