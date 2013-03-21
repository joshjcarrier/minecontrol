package com.joshjcarrier.minecontrol.ui.views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;

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

import com.joshjcarrier.minecontrol.framework.input.ApplicationEvent;
import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;
import com.joshjcarrier.minecontrol.framework.input.ButtonMappingType;
import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.ui.ContentResources;
import com.joshjcarrier.minecontrol.ui.actions.SimpleAction;
import com.joshjcarrier.minecontrol.ui.controls.ButtonDescriptorPanel;
import com.joshjcarrier.minecontrol.ui.controls.ButtonMappingToReplayControl;
import com.joshjcarrier.minecontrol.ui.parts.ConfigurationPart;

public class ConfigurationView extends JDialog
{
	private static final long serialVersionUID = -424815020280281748L;
	
	public ConfigurationView(ConfigurationPart part)
	{
		setTitle(part.getTitle());
		
		ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource(ContentResources.INPUTDEVICE_XBOX360));
    	this.setIconImage(icon.getImage());    
    	
    	Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    	JPanel contentPanel = new JPanel(new GridLayout(1, 1));
    	
    	JTabbedPane tabbedPane = new JTabbedPane();
    	
    	JPanel mappingPanel = createMappingPanel(part);
    	mappingPanel.setBorder(emptyBorder);
    	tabbedPane.addTab("MAPPING", mappingPanel);
    	
    	JPanel sensitivityPanel = createSensitivityPanel(part);
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
	
	private static JPanel createMappingPanel(final ConfigurationPart dataContext)
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
		
		ButtonMapping[] mappings = new ButtonMapping[]
				{
					new ButtonMapping(ButtonMappingType.Application, ApplicationEvent.MouseMode),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_A),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_B),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_C),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_COMMA),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_D),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_DELETE),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_DOWN),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_E),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_END),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_ESCAPE),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F1),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F2),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F3),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F4),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F5),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F6),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F7),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F8),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F9),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F10),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F11),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F12),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_G),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_H),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_HOME),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_I),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_INSERT),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_J),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_K),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_L),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_LEFT),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_M),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_N),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_O),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_P),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_PAGE_DOWN),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_PAGE_UP),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_PERIOD),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_Q),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_R),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_RIGHT),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_S),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_SHIFT),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_SPACE),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_T),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_TAB),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_U),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_UP),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_V),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_W),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_X),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_Y),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_Z),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_1),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_2),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_3),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_4),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_5),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_6),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_7),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_8),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_9),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_0),
					new ButtonMapping(ButtonMappingType.Mouse, MouseEvent.BUTTON1_MASK),
					new ButtonMapping(ButtonMappingType.Mouse, MouseEvent.BUTTON2_MASK),
					new ButtonMapping(ButtonMappingType.Mouse, MouseEvent.BUTTON3_MASK),
					new ButtonMapping(ButtonMappingType.Mouse, MouseEvent.MOUSE_WHEEL, -1),
					new ButtonMapping(ButtonMappingType.Mouse, MouseEvent.MOUSE_WHEEL, 1),
				};
				
		for (final Buttons button : new Buttons[] { Buttons.A, Buttons.B, Buttons.X, Buttons.Y, Buttons.LEFT_SHOULDER, Buttons.RIGHT_SHOULDER, Buttons.LEFT_STICK, Buttons.RIGHT_STICK, Buttons.DPAD_LEFT, Buttons.DPAD_UP, Buttons.DPAD_RIGHT, Buttons.DPAD_DOWN, Buttons.BACK, Buttons.START })
		{
			gridConstraints.gridy += 1;
			
			gridConstraints.gridx = 0;
			panel.add(new ButtonDescriptorPanel(button), gridConstraints);
							
			final ButtonMappingToReplayControl buttonMappingToReplayControl = new ButtonMappingToReplayControl(Arrays.asList(mappings));
			buttonMappingToReplayControl.setAction(new SimpleAction()
			{				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (buttonMappingToReplayControl.isValid())
					{
						dataContext.setButtonMapping(button, buttonMappingToReplayControl.getSelectedButtonMapping());
					}
				}
			});
			
			buttonMappingToReplayControl.setSelectedButton(dataContext.getButtonMapping(button));
			
			gridConstraints.gridx = 1;
			panel.add(buttonMappingToReplayControl, gridConstraints);
		}
		
		gridConstraints.gridy += 1;
		
		gridConstraints.gridx = 0;
		panel.add(new ButtonDescriptorPanel("Enable triggers", ContentResources.BUTTON_XBOX360_RIGHTTRIGGER), gridConstraints);
						
		final JCheckBox enableTriggersCheckBox = new JCheckBox();
		enableTriggersCheckBox.setToolTipText("When selected, both left and right triggers will be enabled.");
		enableTriggersCheckBox.setSelected(dataContext.isTriggersEnabled());
		enableTriggersCheckBox.setAction(new SimpleAction()
		{				
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (enableTriggersCheckBox.isValid())
				{
					dataContext.setTriggersEnabled(enableTriggersCheckBox.isSelected());
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
	
	private static JPanel createSensitivityPanel(final ConfigurationPart dataContext)
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gridConstraints = new GridBagConstraints();
		gridConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridConstraints.insets = new Insets(5, 0, 0, 0);
		gridConstraints.weightx = 1;
		
		/* Sliders for look movement sensitivity */
		gridConstraints.gridy = 0;
		panel.add(new JLabel("X, Y Look sensitivity (movement mode)"), gridConstraints);
		
		final JSlider lookHorizontalSlider = new JSlider();		
		lookHorizontalSlider.setMajorTickSpacing(10);
		lookHorizontalSlider.setMinorTickSpacing(1);
		lookHorizontalSlider.setPaintTicks(true);
		lookHorizontalSlider.setPaintLabels(true);
		lookHorizontalSlider.setSnapToTicks(true);
		lookHorizontalSlider.setValue(dataContext.getMouseMode1SensitivityX());
		lookHorizontalSlider.setToolTipText("Sets how responsive looking left and right in mouse mode 1 (default mode) will be.");
		lookHorizontalSlider.setMinimum(0);
		lookHorizontalSlider.setMaximum(100);		
		lookHorizontalSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				dataContext.setMouseMode1SensitivityX(lookHorizontalSlider.getValue());
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
		lookVerticalSlider.setValue(dataContext.getMouseMode1SensitivityY());
		lookVerticalSlider.setToolTipText("Sets how responsive looking left and right in mouse mode 1 (default mode) will be.");
		lookVerticalSlider.setMinimum(0);
		lookVerticalSlider.setMaximum(100);
		lookVerticalSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				dataContext.setMouseMode1SensitivityY(lookVerticalSlider.getValue());
			}
		});
		
		gridConstraints.gridy = 2;
		panel.add(lookVerticalSlider, gridConstraints);
		
		/* Sliders for mouse movement sensitivity */
		gridConstraints.gridy = 3;
		panel.add(new JLabel("X, Y Look sensitivity (mouse mode)"), gridConstraints);
		
		final JSlider lookMouseHorizontalSlider = new JSlider();						
		lookMouseHorizontalSlider.setMajorTickSpacing(5);
		lookMouseHorizontalSlider.setMinorTickSpacing(1);
		lookMouseHorizontalSlider.setPaintTicks(true);
		lookMouseHorizontalSlider.setPaintLabels(true);
		lookMouseHorizontalSlider.setSnapToTicks(true);
		lookMouseHorizontalSlider.setValue(dataContext.getMouseMode2SensitivityX());
		lookMouseHorizontalSlider.setToolTipText("Sets how responsive looking left and right in mouse mode 2 will be. Enter this mode by mapping a button to \"<Toggle mouse mode>\".");
		lookMouseHorizontalSlider.setMinimum(0);
		lookMouseHorizontalSlider.setMaximum(40);
		lookMouseHorizontalSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				dataContext.setMouseMode2SensitivityX(lookMouseHorizontalSlider.getValue());
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
		lookMouseVerticalSlider.setValue(dataContext.getMouseMode2SensitivityY());
		lookMouseVerticalSlider.setToolTipText("Sets how responsive looking up and down in mouse mode 2 will be. Enter this mode by mapping a button to \"<Toggle mouse mode>\".");
		lookMouseVerticalSlider.setMinimum(0);
		lookMouseVerticalSlider.setMaximum(40);
		lookMouseVerticalSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				dataContext.setMouseMode2SensitivityY(lookMouseVerticalSlider.getValue());
			}
		});
		
		gridConstraints.gridy = 5;
		panel.add(lookMouseVerticalSlider, gridConstraints);
		
		final JCheckBox invertPitchCheckBox = new JCheckBox();
		invertPitchCheckBox.setAction(new SimpleAction() {			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				dataContext.setLookInverted(invertPitchCheckBox.isSelected());
			}
		});
		
		invertPitchCheckBox.setText("Invert look up/down");
		invertPitchCheckBox.setToolTipText("When selected, pushing up on the right joystick moves the mouse down.");
		invertPitchCheckBox.setSelected(dataContext.isLookInverted());
		gridConstraints.gridy = 6;
		panel.add(invertPitchCheckBox, gridConstraints);
		
		// filler at bottom    
    	JPanel fillerPanel = new JPanel();
    	Random rand = new Random(System.currentTimeMillis());
		int niceSettingsssYouHaveThere = rand.nextInt(4);	
		if(niceSettingsssYouHaveThere == 0)
		{
			JLabel iconLabel = new JLabel();
			URL iconUrl = ConfigurationView.class.getClassLoader().getResource(ContentResources.CHROME_SENSITIVITYPANEL_FILLER);
		  	
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
