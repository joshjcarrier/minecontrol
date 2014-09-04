package com.joshjcarrier.minecontrol.ui.controls;

import com.joshjcarrier.minecontrol.ui.actions.SimpleAction;
import com.joshjcarrier.minecontrol.ui.models.AutomationBindingWrapper;
import rx.util.functions.Action1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;

public class AutomationBindingsControl extends JPanel {
    private static final long serialVersionUID = -3729294863824556950L;

    private final JComboBox buttonMappingComboBox;
    private final JCheckBox keyToggleModeCheckBox;
    private Action1<AutomationBindingWrapper> bindingChangedAction;
    private Action1<Boolean> projectionChangedAction;

    public AutomationBindingsControl(boolean isToggled, AutomationBindingWrapper selectedBinding, Collection<AutomationBindingWrapper> bindings) {
        this(isToggled, selectedBinding, bindings, true);
    }

    public AutomationBindingsControl(boolean isToggled, AutomationBindingWrapper selectedBinding, Collection<AutomationBindingWrapper> bindings, boolean isToggleEnabled) {
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
                AutomationBindingWrapper binding = (AutomationBindingWrapper)buttonMappingComboBox.getSelectedItem();

                if (bindingChangedAction != null)
                {
                    bindingChangedAction.call(binding);
                }
            }
        });
        this.add(this.buttonMappingComboBox, gridConstraints);

        gridConstraints.gridx = 1;
        gridConstraints.weighty = 0;
        this.keyToggleModeCheckBox = new JCheckBox();
        this.keyToggleModeCheckBox.setSelected(isToggled);
        this.keyToggleModeCheckBox.setAction(new SimpleAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (projectionChangedAction != null)
                {
                    projectionChangedAction.call(keyToggleModeCheckBox.isSelected());
                }
            }
        });
        this.keyToggleModeCheckBox.setText("Toggle mode");
        this.keyToggleModeCheckBox.setToolTipText("When selected, the mapped key will continue to be held until the button is pressed again.");
        this.keyToggleModeCheckBox.setEnabled(isToggleEnabled);
        this.add(this.keyToggleModeCheckBox, gridConstraints);
    }

    public void onBindingChanged(Action1<AutomationBindingWrapper> a)
    {
        this.bindingChangedAction = a;
    }

    public void onProjectionChanged(Action1<Boolean> a)
    {
        this.projectionChangedAction = a;
    }
}
