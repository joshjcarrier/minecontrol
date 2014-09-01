package com.joshjcarrier.minecontrol.ui.views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.joshjcarrier.minecontrol.ui.ContentResources;
import com.joshjcarrier.minecontrol.ui.actions.SimpleAction;
import com.joshjcarrier.minecontrol.ui.controllers.GamePadProfileController;
import com.joshjcarrier.minecontrol.ui.controls.AutomationBindingsControl;
import com.joshjcarrier.minecontrol.ui.controls.ButtonDescriptorPanel;
import com.joshjcarrier.minecontrol.ui.models.AutomationBindingWrapper;
import com.joshjcarrier.minecontrol.ui.models.GamePadProfileWrapper;
import com.joshjcarrier.minecontrol.ui.models.MouseProfileWrapper;
import net.java.games.input.Component;
import rx.util.functions.Action0;
import rx.util.functions.Action1;

public class GamePadProfileView extends JDialog
{
	private static final long serialVersionUID = -424815020280281748L;
	
	public GamePadProfileView(GamePadProfileController gamePadProfileController)
	{
		setTitle(gamePadProfileController.getTitle());
		
		ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource(ContentResources.INPUTDEVICE_XBOX360));
    	this.setIconImage(icon.getImage());    
    	
    	Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    	JPanel contentPanel = new JPanel(new GridLayout(1, 1));
    	
    	JTabbedPane tabbedPane = new JTabbedPane();
    	
    	JPanel mappingPanel = createMappingPanel(gamePadProfileController);
    	mappingPanel.setBorder(emptyBorder);
    	tabbedPane.addTab("MAPPING", mappingPanel);
    	
    	JPanel sensitivityPanel = createSensitivityPanel(gamePadProfileController.getGamePadProfile());
    	sensitivityPanel.setBorder(emptyBorder);
    	tabbedPane.addTab("LOOK/SENSITIVITY", sensitivityPanel);
    	
    	contentPanel.add(tabbedPane);    	
    	this.add(contentPanel, BorderLayout.CENTER);
    	
    	JPanel footerPanel = createFooterPanel();
    	footerPanel.setBorder(emptyBorder);
    	this.add(footerPanel, BorderLayout.SOUTH);
    	
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	this.setResizable(false);
    	this.pack();
	}
	
	private JPanel createFooterPanel()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gridConstraints = new GridBagConstraints();
		gridConstraints.fill = GridBagConstraints.BOTH;
		
		JLabel confirmLabel = new JLabel("Changes automatically save and apply.");
		confirmLabel.setFont(new Font("Verdana", Font.ITALIC, 10));
		gridConstraints.gridx = 0;
		gridConstraints.weightx = 1;
		panel.add(confirmLabel, gridConstraints);
		
		JButton closeButton = new JButton();
		closeButton.setAction(new SimpleAction()
		{			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				dispose();
			}
		});
		closeButton.setText("Close");
		gridConstraints.gridx = 1;
		gridConstraints.weightx = 0;
		gridConstraints.insets = new Insets(0, 10, 0, 0);
		panel.add(closeButton, gridConstraints);
		
		return panel;
	}
	
	private static JPanel createMappingPanel(final GamePadProfileController gamePadProfileController)
	{		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gridConstraints = new GridBagConstraints();
		gridConstraints.fill = GridBagConstraints.BOTH;
		gridConstraints.insets = new Insets(5, 0, 0, 0);
		
		Font headerFont = new Font("Verdana", Font.BOLD, 12);
		JLabel controllerHeader = new JLabel("Controller Input");
		controllerHeader.setFont(headerFont);
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 0;
		gridConstraints.weightx = 1;
		gridConstraints.ipadx = 60;
		panel.add(controllerHeader, gridConstraints);
		
		JLabel mappingHeader = new JLabel("Mapping");
		mappingHeader.setFont(headerFont);
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 0;
		gridConstraints.weightx = 0;
		gridConstraints.ipadx = 0;
		panel.add(mappingHeader, gridConstraints);

        List<AutomationBindingWrapper> automationBindingWrappers = gamePadProfileController.getAutomationBindings();
        final GamePadProfileWrapper gamePadProfile = gamePadProfileController.getGamePadProfile();
        HashMap<Component.Identifier, String> gamePadButtonIcons = gamePadProfile.getGamePadButtonIcons();
        List<Map.Entry<Component.Identifier, String>> gamePadLabels = new ArrayList<Map.Entry<Component.Identifier, String>>(gamePadProfile.getGamePadButtonLabels().entrySet());
        Collections.sort(gamePadLabels, new Comparator<Map.Entry<Component.Identifier, String>>() {
            @Override
            public int compare(Map.Entry<Component.Identifier, String> identifierStringEntry, Map.Entry<Component.Identifier, String> identifierStringEntry2) {
                return identifierStringEntry.getKey().getName().compareTo(identifierStringEntry2.getKey().getName());
            }
        });

        for (final Map.Entry<Component.Identifier, String> gamePadButtonLabel : gamePadLabels)
		{
			gridConstraints.gridy += 1;
			
			gridConstraints.gridx = 0;
			panel.add(new ButtonDescriptorPanel(gamePadButtonLabel.getValue(), gamePadButtonIcons.get(gamePadButtonLabel.getKey())), gridConstraints);
							
			final AutomationBindingsControl automationBindingsControl = new AutomationBindingsControl(gamePadProfile.isBufferAutomationProjection(gamePadButtonLabel.getKey()), gamePadProfile.getAutomationBinding(gamePadButtonLabel.getKey()), automationBindingWrappers);
            automationBindingsControl.onBindingChanged(new Action1<AutomationBindingWrapper>() {
                @Override
                public void call(AutomationBindingWrapper selectedAutomationBinding) {
                    gamePadProfile.setAutomationBinding(gamePadButtonLabel.getKey(), selectedAutomationBinding);
                }
            });
            automationBindingsControl.onProjectionChanged(new Action1<Boolean>() {
                @Override
                public void call(Boolean isToggled) {
                    if (isToggled) {
                        gamePadProfile.setBufferAutomationProjection(gamePadButtonLabel.getKey());
                    } else {
                        gamePadProfile.setThresholdAutomationProjection(gamePadButtonLabel.getKey());
                    }
                }
            });

			gridConstraints.gridx = 1;
			panel.add(automationBindingsControl, gridConstraints);
		}
		
		gridConstraints.gridy += 1;
		
		gridConstraints.gridx = 0;
		panel.add(new ButtonDescriptorPanel("Enable triggers", ContentResources.BUTTON_XBOX360_RIGHTTRIGGER), gridConstraints);
						
		final JCheckBox enableTriggersCheckBox = new JCheckBox();
		enableTriggersCheckBox.setToolTipText("When selected, both left and right triggers will be enabled.");
		// TODO enableTriggersCheckBox.setSelected(dataContext.isTriggersEnabled());
		enableTriggersCheckBox.setAction(new SimpleAction()
		{				
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (enableTriggersCheckBox.isValid())
				{
					// TODO dataContext.setTriggersEnabled(enableTriggersCheckBox.isSelected());
				}
			}
		});
				
		gridConstraints.gridx = 1;
		panel.add(enableTriggersCheckBox, gridConstraints);
		
		gridConstraints.gridy = 1;
		gridConstraints.gridx = 2;
		panel.add(new JLabel("                  ")); // REVIEW: real cheap way of adding a spacer between the columns
		
		JLabel readonlyButtonsHeader = new JLabel("Read-only Mappings");
		readonlyButtonsHeader.setFont(headerFont);
		gridConstraints.gridy = 0;
		gridConstraints.gridx = 3;
		panel.add(readonlyButtonsHeader, gridConstraints);
		
		gridConstraints.gridy = 1;
		gridConstraints.gridx = 3;
		panel.add(new ButtonDescriptorPanel("Left joystick: up", ContentResources.BUTTON_XBOX360_LEFTSTICK), gridConstraints);
		gridConstraints.gridx = 4;
		panel.add(new JLabel("W (pulse mode) [Forward]"), gridConstraints);
		
		gridConstraints.gridy += 1;
		gridConstraints.gridx = 3;
		panel.add(new ButtonDescriptorPanel("Left joystick: down", ContentResources.BUTTON_XBOX360_LEFTSTICK), gridConstraints);
		gridConstraints.gridx = 4;
		panel.add(new JLabel("S (pulse mode) [Back]"), gridConstraints);
		
		gridConstraints.gridy += 1;
		gridConstraints.gridx = 3;
		panel.add(new ButtonDescriptorPanel("Left joystick: left", ContentResources.BUTTON_XBOX360_LEFTSTICK), gridConstraints);
		gridConstraints.gridx = 4;
		panel.add(new JLabel("A (pulse mode) [Left]"), gridConstraints);
		
		gridConstraints.gridy += 1;
		gridConstraints.gridx = 3;
		panel.add(new ButtonDescriptorPanel("Left joystick: right", ContentResources.BUTTON_XBOX360_LEFTSTICK), gridConstraints);
		gridConstraints.gridx = 4;
		panel.add(new JLabel("D (pulse mode) [Right]"), gridConstraints);
		
		gridConstraints.gridy += 1;
		gridConstraints.gridx = 3;
		panel.add(new ButtonDescriptorPanel("Right joystick", ContentResources.BUTTON_XBOX360_RIGHTSTICK), gridConstraints);
		gridConstraints.gridx = 4;
		panel.add(new JLabel("Mouse [Turn/Aim]"), gridConstraints);
		
		gridConstraints.gridy += 1;
		gridConstraints.gridx = 3;
		panel.add(new ButtonDescriptorPanel("Left trigger", ContentResources.BUTTON_XBOX360_LEFTTRIGGER), gridConstraints);
		gridConstraints.gridx = 4;
		panel.add(new JLabel("Mouse right press/release (button 3) [Use item]"), gridConstraints);
		
		gridConstraints.gridy += 1;
		gridConstraints.gridx = 3;
		panel.add(new ButtonDescriptorPanel("Right trigger", ContentResources.BUTTON_XBOX360_RIGHTTRIGGER), gridConstraints);
		gridConstraints.gridx = 4;
		panel.add(new JLabel("Mouse left press/release (button 1) [Attack/Dig]"), gridConstraints);
		
		return panel;
	}
	
	private static JPanel createSensitivityPanel(final GamePadProfileWrapper gamePadProfile)
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gridConstraints = new GridBagConstraints();
		gridConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridConstraints.insets = new Insets(5, 0, 0, 0);
		gridConstraints.weightx = 1;
		
		/* Sliders for look movement sensitivity */
		gridConstraints.gridy = 0;
		panel.add(new JLabel("X, Y Look sensitivity (movement mode)"), gridConstraints);

        final MouseProfileWrapper primaryMouseProfile = gamePadProfile.getPrimaryMouseProfile();
		final JSlider lookHorizontalSlider = new JSlider();		
		lookHorizontalSlider.setMajorTickSpacing(10);
		lookHorizontalSlider.setMinorTickSpacing(1);
		lookHorizontalSlider.setPaintTicks(true);
		lookHorizontalSlider.setPaintLabels(true);
		lookHorizontalSlider.setSnapToTicks(true);
		lookHorizontalSlider.setValue(primaryMouseProfile.getSensitivityX());
		lookHorizontalSlider.setToolTipText("Sets how responsive looking left and right in mouse mode 1 (default mode) will be.");
		lookHorizontalSlider.setMinimum(0);
		lookHorizontalSlider.setMaximum(100);		
		lookHorizontalSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
                primaryMouseProfile.setSensitivityX(lookHorizontalSlider.getValue());
			}
		});		
		
		gridConstraints.gridy = 1;
		panel.add(lookHorizontalSlider, gridConstraints);
		
		final JSlider lookVerticalSlider = new JSlider();
		lookVerticalSlider.setMajorTickSpacing(10);
		lookVerticalSlider.setMinorTickSpacing(1);
		lookVerticalSlider.setPaintTicks(true);
		lookVerticalSlider.setPaintLabels(true);
		lookVerticalSlider.setSnapToTicks(true);
		lookVerticalSlider.setValue(primaryMouseProfile.getSensitivityY());
		lookVerticalSlider.setToolTipText("Sets how responsive looking left and right in mouse mode 1 (default mode) will be.");
		lookVerticalSlider.setMinimum(0);
		lookVerticalSlider.setMaximum(100);
		lookVerticalSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
                primaryMouseProfile.setSensitivityY(lookVerticalSlider.getValue());
			}
		});
		
		gridConstraints.gridy = 2;
		panel.add(lookVerticalSlider, gridConstraints);
		
		/* Sliders for mouse movement sensitivity */
		gridConstraints.gridy = 3;
		panel.add(new JLabel("X, Y Look sensitivity (mouse mode)"), gridConstraints);

        final MouseProfileWrapper secondaryMouseProfile = gamePadProfile.getSecondaryMouseProfile();
		final JSlider lookMouseHorizontalSlider = new JSlider();						
		lookMouseHorizontalSlider.setMajorTickSpacing(5);
		lookMouseHorizontalSlider.setMinorTickSpacing(1);
		lookMouseHorizontalSlider.setPaintTicks(true);
		lookMouseHorizontalSlider.setPaintLabels(true);
		lookMouseHorizontalSlider.setSnapToTicks(true);
		lookMouseHorizontalSlider.setValue(secondaryMouseProfile.getSensitivityX());
		lookMouseHorizontalSlider.setToolTipText("Sets how responsive looking left and right in mouse mode 2 will be. Enter this mode by mapping a button to \"<Toggle mouse mode>\".");
		lookMouseHorizontalSlider.setMinimum(0);
		lookMouseHorizontalSlider.setMaximum(40);
		lookMouseHorizontalSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
                secondaryMouseProfile.setSensitivityX(lookMouseHorizontalSlider.getValue());
			}
		});
		
		gridConstraints.gridy = 4;
		panel.add(lookMouseHorizontalSlider, gridConstraints);
		
		final JSlider lookMouseVerticalSlider = new JSlider();
		lookMouseVerticalSlider.setMajorTickSpacing(5);
		lookMouseVerticalSlider.setMinorTickSpacing(1);
		lookMouseVerticalSlider.setPaintTicks(true);
		lookMouseVerticalSlider.setPaintLabels(true);
		lookMouseVerticalSlider.setSnapToTicks(true);
		lookMouseVerticalSlider.setValue(secondaryMouseProfile.getSensitivityY());
		lookMouseVerticalSlider.setToolTipText("Sets how responsive looking up and down in mouse mode 2 will be. Enter this mode by mapping a button to \"<Toggle mouse mode>\".");
		lookMouseVerticalSlider.setMinimum(0);
		lookMouseVerticalSlider.setMaximum(40);
		lookMouseVerticalSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
                secondaryMouseProfile.setSensitivityY(lookMouseVerticalSlider.getValue());
			}
		});
		
		gridConstraints.gridy = 5;
		panel.add(lookMouseVerticalSlider, gridConstraints);
		
		final JCheckBox invertPitchCheckBox = new JCheckBox();
		invertPitchCheckBox.setAction(new SimpleAction() {			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
                primaryMouseProfile.setInvertY(invertPitchCheckBox.isSelected());
			}
		});
		
		invertPitchCheckBox.setText("Invert look up/down");
		invertPitchCheckBox.setToolTipText("When selected, pushing up on the right joystick moves the mouse down.");
		invertPitchCheckBox.setSelected(primaryMouseProfile.isInvertY());
		gridConstraints.gridy = 6;
		panel.add(invertPitchCheckBox, gridConstraints);
		
		// filler at bottom    
    	JPanel fillerPanel = new JPanel();
    	Random rand = new Random(System.currentTimeMillis());
		int niceSettingsssYouHaveThere = rand.nextInt(4);	
		if(niceSettingsssYouHaveThere == 0)
		{
			JLabel iconLabel = new JLabel();
			URL iconUrl = GamePadProfileView.class.getClassLoader().getResource(ContentResources.CHROME_SENSITIVITYPANEL_FILLER);
		  	
	    	ImageIcon icon = new ImageIcon(iconUrl);
	    	Image newimg = icon.getImage().getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);  
	    	iconLabel.setIcon(new ImageIcon(newimg));
			fillerPanel.add(iconLabel, BorderLayout.CENTER);
		}
		
		gridConstraints.gridy = 7;
		gridConstraints.weighty = 1;
		panel.add(fillerPanel, gridConstraints);
		
		return panel;
	}
}
