package at.fhhagenberg.sqe.project.ui.views;

import at.fhhagenberg.sqe.project.model.Building;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;
import at.fhhagenberg.sqe.project.ui.IDynamicUIControl;
import at.fhhagenberg.sqe.project.ui.components.ElevatorFloorComponent;
import at.fhhagenberg.sqe.project.ui.components.ElevatorModeComponent;
import at.fhhagenberg.sqe.project.ui.components.ElevatorPositionComponent;
import at.fhhagenberg.sqe.project.ui.components.FloorStatusComponent;
import at.fhhagenberg.sqe.project.ui.views.listeners.IElevatorDetailSelectListener;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorOverviewView extends JComponent implements PropertyChangeListener, IDynamicUIControl {

	private Building mBuilding;
	private JScrollPane mMainScrollPane;
	private IElevatorDetailSelectListener mSelectListener;
	private JPanel mVerticalScrollBarSpace;
	private JPanel mHorizontalScrollBarSpace;

	private List<IDynamicUIControl> mChildControls;

	public ElevatorOverviewView(Building building, IElevatorDetailSelectListener selectListener) {
		mBuilding = building;
		mSelectListener = selectListener;
		mChildControls = new ArrayList<IDynamicUIControl>();
		setLayout(new BorderLayout());
		addComponentListener(new ResizeListener());

		refreshView();

		building.addPropertyChangeListener(this);
	}

	private void refreshView() {
		removeAll();

		mMainScrollPane = CreateMainPanel();
		add(mMainScrollPane, BorderLayout.CENTER);

		Component topPanel = CreateTopPanel(mSelectListener);
		add(topPanel, BorderLayout.PAGE_START);

		Component leftPanel = CreateLeftPanel();
		add(leftPanel, BorderLayout.LINE_START);

		revalidate();
		repaint();
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

			ElevatorPositionComponent component = new ElevatorPositionComponent(mBuilding, e);
			mChildControls.add(component);
			mainPanel.add(component, gc);

			gc.gridheight = 1;
			gc.gridx += 1;
			gc.gridy = mBuilding.getNumberOfFloors() - 1;
			for (Floor f : mBuilding.getFloors()) {
				ElevatorFloorComponent efComponent = new ElevatorFloorComponent(e, f);
				mChildControls.add(efComponent);
				mainPanel.add(efComponent, gc);
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
			ElevatorModeComponent component = new ElevatorModeComponent(e, selectListener);
			mChildControls.add(component);
			mainPanel.add(component, gc);
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

		mHorizontalScrollBarSpace = new JPanel();
		mHorizontalScrollBarSpace.setPreferredSize(new Dimension((int)mMainScrollPane.getVerticalScrollBar().getPreferredSize().getWidth(), 60));
		mHorizontalScrollBarSpace.setVisible(false);

		p.add(mHorizontalScrollBarSpace, BorderLayout.LINE_END);

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
			FloorStatusComponent component = new FloorStatusComponent(f);
			mChildControls.add(component);
			mainPanel.add(component, gc);
			gc.gridy -= 1;
		}

		JScrollPane scroll = new JScrollPane(mainPanel);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setModel(mMainScrollPane.getVerticalScrollBar().getModel());
		scroll.setBorder(BorderFactory.createEmptyBorder());

		JPanel p = new JPanel(new BorderLayout());
		mVerticalScrollBarSpace = new JPanel();
		mVerticalScrollBarSpace.setPreferredSize(new Dimension(150, (int)mMainScrollPane.getHorizontalScrollBar().getPreferredSize().getHeight()));
		mVerticalScrollBarSpace.setVisible(false);

		p.add(mVerticalScrollBarSpace, BorderLayout.PAGE_END);
		p.add(scroll, BorderLayout.CENTER);

		return p;
	}

	@Override
	public void unload() {
		mBuilding.removePropertyChangeListener(this);
		for (IDynamicUIControl control : mChildControls) {
			control.unload();
		}
	}

	class ResizeListener extends ComponentAdapter {
		@Override
		public void componentResized(ComponentEvent e) {
			if (mMainScrollPane.getHorizontalScrollBar().isVisible()) {
				mVerticalScrollBarSpace.setVisible(true);
			} else {
				mVerticalScrollBarSpace.setVisible(false);
			}
			if (mMainScrollPane.getVerticalScrollBar().isVisible()) {
				mHorizontalScrollBarSpace.setVisible(true);
			} else {
				mHorizontalScrollBarSpace.setVisible(false);
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		refreshView();
	}
}
