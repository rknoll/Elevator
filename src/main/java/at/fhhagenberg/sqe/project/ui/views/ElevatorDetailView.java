package at.fhhagenberg.sqe.project.ui.views;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.IElevatorInfoListener;

import javax.swing.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorDetailView extends JComponent implements IElevatorInfoListener {

    private Building mBuilding;
    private Elevator mElevator;

    private JLabel mDescriptionLabel;

    public ElevatorDetailView(Building building, Elevator elevator, IElevatorOverviewSelectListener selectListener) {
        mBuilding = building;
        mElevator = elevator;
        mDescriptionLabel = new JLabel();

        setLayout(new FlowLayout());
        JPanel mainPanel = CreateMainPanel(selectListener);
        add(mainPanel);

//        JButton returnButton = new JButton("Return");
//        returnButton.addActionListener(event -> {
//            building.removeListener(this);
//            selectListener.selectOverview();
//        });
//        add(returnButton);
//
//        mDescriptionLabel = new JLabel();
//        add(mDescriptionLabel);
//        
//        Component detailInfo = CreateComponentDetailInfo();
//        add(detailInfo);

        building.addListener(this);
    }
    
    private JPanel CreateMainPanel(IElevatorOverviewSelectListener selectListener)
    {
    	JPanel mainPanel = new JPanel(new GridBagLayout());
    	GridBagConstraints gc = new GridBagConstraints();

    	gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.CENTER;

		JButton returnButton = new JButton("Return");
		returnButton.addActionListener(event -> {
			mBuilding.removeListener(this);
			selectListener.selectOverview();
		});
		mainPanel.add(returnButton);
		
		gc.gridx += 1;
		mainPanel.add(mDescriptionLabel, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		
		Component detailInfo = CreateComponentDetailInfo();
		mainPanel.add(detailInfo, gc);
		
		gc.gridx += 1;
		gc.gridy = 1;
		Component pressedButtons = CreateComponentButtonPessedOverview();
		mainPanel.add(pressedButtons, gc);		
		
		return mainPanel;
    }
    
    private Component CreateComponentDetailInfo()
    {
    	JPanel infoPanel = new JPanel(new GridBagLayout());
    	GridBagConstraints gc = new GridBagConstraints();
    	
		gc.gridx = 0;
		gc.gridy = 0;
		
		gc.anchor = GridBagConstraints.WEST;
		
		try {
			Map<String, String> lines = new HashMap<String, String>();
			lines.put("Cureent Mode:", mElevator.isAutomaticMode() ? "automatic" : "manual");
            if (mElevator.getCurrentFloor() != null) {
                lines.put("Current Floor:", mElevator.getCurrentFloor().getDescription());
            } else {
                lines.put("Current Floor:", "None");
            }
			lines.put("Position:", mElevator.getPosition() + " feet");
            if (mElevator.getTarget() != null) {
                lines.put("Target:", mElevator.getTarget().getDescription());
            } else {
                lines.put("Target:", "None");
            }
            if (mElevator.getDirection() != null) {
                lines.put("Direction:", mElevator.getDirection().name());
            } else {
                lines.put("Direction:", "None");
            }
            if (mElevator.getDoorStatus() != null) {
                lines.put("Door State:", mElevator.getDoorStatus().name());
            } else {
                lines.put("Door State:", "None");
            }
			lines.put("Speed:", mElevator.getSpeed() + " feet/s");
			lines.put("Acceleration: ", mElevator.getAcceleration() + "feet/s^2");
			lines.put("Capacity:", mElevator.getCapacity() + "");
			lines.put("Weight:", mElevator.getWeight() + "");
			
			
			for (Map.Entry<String, String> entry : lines.entrySet()) {
			    String key = entry.getKey();
			    String value = entry.getValue();
			    
			    JLabel keyLabel = new JLabel(key);
			    JLabel valueLabel = new JLabel(value);
			    
			    infoPanel.add(keyLabel, gc);
			    gc.gridx += 1;	// Eine Spalte weiter
			    infoPanel.add(valueLabel, gc);
			    gc.gridx -= 1;	// Eine Spalte zur�ck
			    gc.gridy += 1;	// Eine Zeile weiter	    	
			}
		}
		catch (Exception e)
		{
			// TODO: Exception Handling
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		    	
    	return infoPanel;
    }
    
    private Component CreateComponentButtonPessedOverview()
    {
    	JPanel buttonPressed = new JPanel(new FlowLayout());
    	for (Floor f : mBuilding.getFloors())
    	{
    		if (mElevator.getButton(f) == true)
    		{
    			// Add floor
    			JLabel floorLabel = new JLabel(f.getDescription());
    			buttonPressed.add(floorLabel);
    		}
    	}
    	return buttonPressed;
    }

    @Override
    public Elevator getElevator() {
        return mElevator;
    }

    @Override
    public void update() {
        mDescriptionLabel.setText(mElevator.getDescription());
    }
}
