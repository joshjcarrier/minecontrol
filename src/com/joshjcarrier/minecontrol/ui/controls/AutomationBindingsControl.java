package com.joshjcarrier.minecontrol.ui.controls;

import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;
import com.joshjcarrier.minecontrol.ui.actions.SimpleAction;
import com.joshjcarrier.minecontrol.ui.models.AutomationBindingWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collection;

public class AutomationBindingsControl extends JPanel {
    private static final long serialVersionUID = -3729294863824556950L;

    private final JComboBox buttonMappingComboBox;
    private final JCheckBox keyToggleModeCheckBox;
    private Action updateAction;

    public AutomationBindingsControl(AutomationBindingWrapper selectedBinding, Collection<AutomationBindingWrapper> bindings) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints = new GridBagConstraints();

        gridConstraints.gridx = 0;
        gridConstraints.weightx = 1;
        ArrayList<AutomationBindingWrapper> selectableButtonMappings = new ArrayList<AutomationBindingWrapper>();
        selectableButtonMappings.addAll(bindings);
        this.buttonMappingComboBox = new JComboBox(selectableButtonMappings.toArray());
        this.buttonMappingComboBox.setSelectedItem(selectedBinding);
        this.buttonMappingComboBox.setAction(new SimpleAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                AutomationBindingWrapper mapping = (AutomationBindingWrapper)buttonMappingComboBox.getSelectedItem();

                // toggle mode only enabled for keyboard
//                if(mapping.getMappingType() != ButtonMappingType.Keyboard)
//                {
//                    keyToggleModeCheckBox.setEnabled(false);
//                    keyToggleModeCheckBox.setSelected(false);
//                }
//                else
//                {
//                    // resync toggle mode state
//                    keyToggleModeCheckBox.setSelected(mapping.isToggleMode());
//                    keyToggleModeCheckBox.setEnabled(true);
//                }

                notifyItemStateChanged();
            }
        });
        this.add(this.buttonMappingComboBox, gridConstraints);

        gridConstraints.gridx = 1;
        gridConstraints.weighty = 0;
        this.keyToggleModeCheckBox = new JCheckBox();
        this.keyToggleModeCheckBox.setAction(new SimpleAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //((AutomationBindingWrapper)buttonMappingComboBox.getSelectedItem()).setIsToggleMode(keyToggleModeCheckBox.isSelected());
                notifyItemStateChanged();
            }
        });
        this.keyToggleModeCheckBox.setText("Toggle mode");
        this.keyToggleModeCheckBox.setToolTipText("When selected, the mapped key will continue to be held until the button is pressed again.");
        this.add(this.keyToggleModeCheckBox, gridConstraints);
    }

    public AutomationBindingWrapper getSelectedAutomationBinding()
    {
        return (AutomationBindingWrapper)this.buttonMappingComboBox.getSelectedItem();
    }

    public void setAction(Action a)
    {
        this.updateAction = a;
    }

    public void setSelectedAutomationBinding(AutomationBindingWrapper automationBinding)
    {
        this.buttonMappingComboBox.setSelectedItem(automationBinding);
        //this.keyToggleModeCheckBox.setSelected(automationBinding.isToggleMode());
    }

    private void notifyItemStateChanged()
    {
        if (this.updateAction != null)
        {
            this.updateAction.actionPerformed(new ActionEvent(this, ItemEvent.ITEM_STATE_CHANGED, ""));
        }
    }
}
