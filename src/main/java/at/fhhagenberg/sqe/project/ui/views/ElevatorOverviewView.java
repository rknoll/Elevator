package at.fhhagenberg.sqe.project.ui.views;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.services.IElevatorPositionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.media.jfxmediaimpl.platform.java.JavaPlatform;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorOverviewView extends JComponent implements IElevatorPositionListener {

    private Building mBuilding;

    public ElevatorOverviewView(Building building, IElevatorDetailSelectListener selectListener) {
        mBuilding = building;

        setLayout(new FlowLayout());
        JPanel mainPanel = CreateMainPanel(selectListener);
        add(mainPanel);
        
        building.addListener(this);
    }
    
    private JPanel CreateMainPanel(IElevatorDetailSelectListener selectListener)
    {
    	JPanel mainPanel = new JPanel(new GridBagLayout());
    	GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.CENTER;
    	
    	gc.gridx = 1;
    	gc.gridy = 0;    	
    	gc.gridwidth = 2;
    	
    	for (Elevator e : mBuilding.getElevators())
    	{
    		Component selElevatorBtn = CreateComponentSelectElevator(selectListener, e);
    		mainPanel.add(selElevatorBtn, gc);
    		gc.gridy += 1;
    		Component elevatorMode = CreateComponentElevatorMode(e);
    		mainPanel.add(elevatorMode, gc);
    		
    		gc.gridx += 2;
    		gc.gridy -= 1;
    	}
    	
    	gc.gridx = 0;
    	gc.gridy = 2;
    	gc.gridwidth = 1;
    	
    	for (Floor f : mBuilding.getFloors())
    	{
    		Component floorButtonInfo = CreateFloorButtonInfo(f);
    		mainPanel.add(floorButtonInfo, gc);
    		gc.gridy += 1;
    	}
    	
    	gc.gridx = 1;    	
    	for (Elevator e : mBuilding.getElevators())
    	{
    		gc.gridy = 2;
    		
    		// Erstelle Panel für Positionsanzeige
    		Component positionPanel = CreateComponentElevatorPosition();
    		gc.gridheight = mBuilding.GetNumberOfFloors();
    		mainPanel.add(positionPanel, gc);
    		
    		gc.gridx += 1;
    		
    		// Erstelle Call und Serve
    		gc.gridheight = 1;
    		for (Floor f : mBuilding.getFloors())
    		{
    			Component elevatorSetting = CreateComponentElevatorSettings(e, f);
    			mainPanel.add(elevatorSetting, gc);
    			gc.gridy += 1;
    		}
    		
    		gc.gridx += 1;
    	}
    	
    	return mainPanel;
    }
    
    private JPanel CreateFloorButtonInfo(Floor floor)
    {
    	JPanel pnlFloorBtn = new JPanel(new GridBagLayout());
    	GridBagConstraints gc = new GridBagConstraints();
    	
		gc.gridx = 0;
		gc.gridy = 0;
		
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridheight = 2;		
		pnlFloorBtn.add(new JLabel(floor.getDescription()), gc);
		
		// -- Spalte 2 -----------------------------------------
		gc.anchor = GridBagConstraints.WEST;
		gc.gridheight = 1;
		gc.gridx = 1;
		pnlFloorBtn.add(new JCheckBox("Up"), gc);
		gc.gridy = 1;
		pnlFloorBtn.add(new JCheckBox("Down"), gc);
		
		return pnlFloorBtn;		
    }
    
    private Component CreateComponentSelectElevator(IElevatorDetailSelectListener selectListener, Elevator elevator)
    {
    	JButton button = new JButton(elevator.getDescription());
        button.addActionListener(event -> {
            mBuilding.removeListener(this);
            selectListener.elevatorSelected(elevator);
        });
        
        return button;
    }
    
    private Component CreateComponentElevatorMode(Elevator elevator)
    {
    	JCheckBox checkBox = new JCheckBox("automatic");
        checkBox.setName(elevator.getDescription() + " AutomaticMode");
        checkBox.setSelected(elevator.isAutomaticMode());
        
        checkBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				AbstractButton abstractButton = (AbstractButton)e.getSource();
				ButtonModel buttonModel = abstractButton.getModel();
				boolean selected = buttonModel.isSelected();
				
				if (selected != elevator.isAutomaticMode())
				{
					System.out.println(abstractButton.getName() + " Changed: " + selected);
					elevator.setAutomaticMode(selected);
				}				
			}
        });

    	// TODO: Der Listener funktioniert so nicht. 
        // 		 Im Building muss es für jeden Elevator einen ModeChangedListener geben, das dieser Funktion übergeben wird.
        
        return checkBox;
    }
    
    private Component CreateComponentElevatorSettings(Elevator elevator, Floor floor)
    {
    	JPanel pnlElevatorSettings = new JPanel(new GridBagLayout());
    	GridBagConstraints gc = new GridBagConstraints();
    	
		gc.gridx = 0;
		gc.gridy = 0;
		
		gc.anchor = GridBagConstraints.CENTER;
    	
    	JButton callButton = new JButton("Call");
    	callButton.setName(elevator.getDescription() + " Call " + floor.getDescription());
    	pnlElevatorSettings.add(callButton, gc);
    	
    	gc.gridy = 1;
    	JCheckBox serveFloorCheckBox = new JCheckBox("Serve");
    	serveFloorCheckBox.setName(elevator.getDescription() + " Serve " + floor.getDescription());
    	serveFloorCheckBox.setSelected(elevator.getService(floor)); 
    	
    	// TODO: Add Listener!
    	pnlElevatorSettings.add(serveFloorCheckBox, gc);    	
    	
        return pnlElevatorSettings;
    }
    
    private Component CreateComponentElevatorPosition()
    {
    	JPanel pnlFloorPosition = new JPanel();
    	pnlFloorPosition.setBackground(Color.BLUE);
    	
    	return pnlFloorPosition;
    }
    

    @Override
    public void update() {

    }
}
