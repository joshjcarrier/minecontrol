package com.joshjcarrier.minecontrol.ui.controls;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;
import com.joshjcarrier.minecontrol.framework.input.ButtonMappingType;
import com.joshjcarrier.minecontrol.services.replayhandlers.IButtonsReplayHandler;
import com.joshjcarrier.minecontrol.ui.actions.SimpleAction;

public class ButtonMappingToReplayControl extends JPanel 
{
	private static final long serialVersionUID = -3729294863824556950L;
	
	private final JComboBox buttonMappingComboBox;
	private final JCheckBox keyToggleModeCheckBox;
	private final ButtonMapping unboundMapping = new ButtonMapping(ButtonMappingType.Unbound, 0);
	private Action updateAction;

	public ButtonMappingToReplayControl(Collection<ButtonMapping> buttonMappings) 
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints gridConstraints = new GridBagConstraints();
		
		gridConstraints.gridx = 0;
		gridConstraints.weightx = 1;
		ArrayList<ButtonMapping> selectableButtonMappings = new ArrayList<ButtonMapping>();
		selectableButtonMappings.add(unboundMapping);
		selectableButtonMappings.addAll(buttonMappings);
		this.buttonMappingComboBox = new JComboBox(selectableButtonMappings.toArray());
		this.buttonMappingComboBox.setSelectedItem(unboundMapping);
		this.buttonMappingComboBox.setAction(new SimpleAction()
			{				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					notifyItemStateChanged();
				}
			});
		this.add(this.buttonMappingComboBox, gridConstraints);
		
		gridConstraints.gridx = 1;
		gridConstraints.weighty = 0;
		this.keyToggleModeCheckBox = new JCheckBox();
		this.keyToggleModeCheckBox.setText("Toggle mode");
		this.add(this.keyToggleModeCheckBox, gridConstraints);
	}
	
	public IButtonsReplayHandler getSelectedButtonMapping()
	{
		// TODO
		return null;
	}
	
	public void setAction(Action a)
	{
		this.updateAction = a;
	}
	
	public void setSelectedButton(ButtonMapping buttonMapping)
	{
		ButtonMapping selectedButtonMapping = buttonMapping != null ? buttonMapping : unboundMapping;
		this.buttonMappingComboBox.setSelectedItem(selectedButtonMapping);	
	}
	
	private void notifyItemStateChanged()
	{
		this.keyToggleModeCheckBox.setEnabled(((ButtonMapping)this.buttonMappingComboBox.getSelectedItem()).getMappingType() == ButtonMappingType.Keyboard);
		
		if (this.updateAction != null)
		{
			this.updateAction.actionPerformed(new ActionEvent(this, ItemEvent.ITEM_STATE_CHANGED, ""));
		}
	}
}
