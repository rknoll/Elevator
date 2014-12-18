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
import javax.swing.border.Border;

import java.awt.*;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorOverviewView extends JComponent {

	private Building mBuilding;
	private JScrollPane mMainScrollPane;

	public ElevatorOverviewView(Building building, IElevatorDetailSelectListener selectListener) {
		mBuilding = building;
		setLayout(new BorderLayout());

		mMainScrollPane = CreateMainPanel();
		add(mMainScrollPane, BorderLayout.CENTER);

		Component topPanel = CreateTopPanel(selectListener);
		add(topPanel, BorderLayout.PAGE_START);

		Component leftPanel = CreateLeftPanel();
		add(leftPanel, BorderLayout.LINE_START);
	}

	private JScrollPane CreateMainPanel() {
		JPanel mainPanel = new JPanel(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;

		gc.anchor = GridBagConstraints.CENTER;

		gc.gridx = 0;

		for (Elevator e : mBuilding.getElevators()) {
			gc.gridy = 0;
			gc.gridheight = mBuilding.getNumberOfFloors();

			mainPanel.add(new ElevatorPositionComponent(mBuilding, e), gc);

			gc.gridheight = 1;
			gc.gridx += 1;
			gc.gridy = mBuilding.getNumberOfFloors() - 1;
			for (Floor f : mBuilding.getFloors()) {
				mainPanel.add(new ElevatorFloorComponent(mBuilding, e, f), gc);
				gc.gridy -= 1;
			}

			gc.gridx += 1;
		}

		JScrollPane scroll = new JScrollPane(mainPanel);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		return scroll;
	}


	private Component CreateTopPanel(IElevatorDetailSelectListener selectListener) {
		JPanel mainPanel = new JPanel(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;

		gc.anchor = GridBagConstraints.CENTER;

		gc.gridx = 0;
		gc.gridy = 0;

		for (Elevator e : mBuilding.getElevators()) {
			Component selElevators = new ElevatorModeComponent(e, selectListener);
			mainPanel.add(selElevators, gc);
			gc.gridx += 1;
		}

		JScrollPane scroll = new JScrollPane(mainPanel);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getHorizontalScrollBar().setModel(mMainScrollPane.getHorizontalScrollBar().getModel());
		scroll.setBorder(BorderFactory.createEmptyBorder());

		JPanel p = new JPanel(new BorderLayout());
		JPanel space = new JPanel();
		space.setPreferredSize(new Dimension(150, 60));
		p.add(space, BorderLayout.LINE_START);
		p.add(scroll, BorderLayout.CENTER);

		return p;
	}


	private Component CreateLeftPanel() {
		JPanel mainPanel = new JPanel(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;

		gc.anchor = GridBagConstraints.CENTER;

		gc.gridx = 0;
		gc.gridy = mBuilding.getNumberOfFloors() - 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;

		for (Floor f : mBuilding.getFloors()) {
			mainPanel.add(new FloorStatusComponent(mBuilding, f), gc);
			gc.gridy -= 1;
		}

		JScrollPane scroll = new JScrollPane(mainPanel);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setModel(mMainScrollPane.getVerticalScrollBar().getModel());
		scroll.setBorder(BorderFactory.createEmptyBorder());

		return scroll;
	}
}
