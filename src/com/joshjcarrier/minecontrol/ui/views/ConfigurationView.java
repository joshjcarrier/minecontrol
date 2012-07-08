package com.joshjcarrier.minecontrol.ui.views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.EnumSet;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;
import com.joshjcarrier.minecontrol.framework.input.ButtonMappingType;
import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.ui.ContentResources;
import com.joshjcarrier.minecontrol.ui.actions.SimpleAction;

public class ConfigurationView extends JDialog
{
	private static final long serialVersionUID = -424815020280281748L;
	
	public ConfigurationView()
	{
		ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource(ContentResources.INPUTDEVICE_XBOX360));
    	this.setIconImage(icon.getImage());    
    	
    	Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    	JPanel contentPanel = new JPanel(new GridLayout(1, 1));
    	
    	JTabbedPane tabbedPane = new JTabbedPane();
    	
    	JPanel mappingPanel = createMappingPanel();
    	mappingPanel.setBorder(emptyBorder);
    	tabbedPane.addTab("MAPPING", mappingPanel);
    	
    	JPanel sensitivityPanel = createSensitivityPanel();
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
		
		JLabel confirmLabel = new JLabel("Changes take effect immediately.");
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
	
	private static JPanel createMappingPanel()
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
		
		Object[] mappings = new Object[]
				{
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_A),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_C),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_D),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_E),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_ESCAPE),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_F5),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_I),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_Q),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_R),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_S),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_SHIFT),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_SPACE),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_T),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_TAB),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_V),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_W),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_X),
					new ButtonMapping(ButtonMappingType.Keyboard, KeyEvent.VK_Z),
					new ButtonMapping(ButtonMappingType.Mouse, MouseEvent.BUTTON1),
					new ButtonMapping(ButtonMappingType.Mouse, MouseEvent.BUTTON2),
					new ButtonMapping(ButtonMappingType.Mouse, MouseEvent.BUTTON3),
					new ButtonMapping(ButtonMappingType.Mouse, MouseEvent.MOUSE_WHEEL, 0),
					new ButtonMapping(ButtonMappingType.Mouse, MouseEvent.MOUSE_WHEEL, 1),
				};
				
		for(Buttons button : EnumSet.allOf(Buttons.class))
		{
			gridConstraints.gridy += 1;
			
			gridConstraints.gridx = 0;
			panel.add(new JLabel(button.name()), gridConstraints);
							
			final JComboBox comboBox = new JComboBox(mappings);
			comboBox.setAction(new SimpleAction()
			{				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (comboBox.isValid())
					{
						//dataContext.setButtonMapping(controllerButton, (MappedButton)comboBox.getSelectedItem());
					}
				}
			});
			
			gridConstraints.gridx = 1;
			panel.add(comboBox, gridConstraints);
		}
		
		return panel;
	}
	
	private static JPanel createSensitivityPanel()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gridConstraints = new GridBagConstraints();
		gridConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridConstraints.insets = new Insets(5, 0, 0, 0);
		gridConstraints.weightx = 1;
		JSlider lookVerticalSlider, lookHorizontalSlider, lookMouseVerticalSlider, lookMouseHorizontalSlider;
		
		/* Sliders for look movement sensitivity */
		gridConstraints.gridy = 0;
		panel.add(new JLabel("X, Y Look sensitivity (movement mode)"), gridConstraints);
		
		lookHorizontalSlider = new JSlider();		
		lookHorizontalSlider.setMajorTickSpacing(5);
		lookHorizontalSlider.setMinorTickSpacing(1);
		lookHorizontalSlider.setPaintTicks(true);
		lookHorizontalSlider.setPaintLabels(true);
		lookHorizontalSlider.setSnapToTicks(true);
		//lookHorizontalSlider.setValue(this.dataContext.getMouseMode1SensitivityX());
		lookHorizontalSlider.setMinimum(0);
		lookHorizontalSlider.setMaximum(40);		
		lookHorizontalSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				//dataContext.setMouseMode1SensitivityX(lookHorizontalSlider.getValue());
			}
		});		
		
		gridConstraints.gridy = 1;
		panel.add(lookHorizontalSlider, gridConstraints);
		
		lookVerticalSlider = new JSlider();
		lookVerticalSlider.setMajorTickSpacing(5);
		lookVerticalSlider.setMinorTickSpacing(1);
		lookVerticalSlider.setPaintTicks(true);
		lookVerticalSlider.setPaintLabels(true);
		lookVerticalSlider.setSnapToTicks(true);
		//lookVerticalSlider.setValue(this.dataContext.getMouseMode1SensitivityY());
		lookVerticalSlider.setMinimum(0);
		lookVerticalSlider.setMaximum(40);
		lookVerticalSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				//dataContext.setMouseMode1SensitivityY(lookVerticalSlider.getValue());
			}
		});
		
		gridConstraints.gridy = 2;
		panel.add(lookVerticalSlider, gridConstraints);
		
		/* Sliders for mouse movement sensitivity */
		gridConstraints.gridy = 3;
		panel.add(new JLabel("X, Y Look sensitivity (mouse mode)"), gridConstraints);
		
		lookMouseHorizontalSlider = new JSlider();						
		lookMouseHorizontalSlider.setMajorTickSpacing(5);
		lookMouseHorizontalSlider.setMinorTickSpacing(1);
		lookMouseHorizontalSlider.setPaintTicks(true);
		lookMouseHorizontalSlider.setPaintLabels(true);
		lookMouseHorizontalSlider.setSnapToTicks(true);
		//lookMouseHorizontalSlider.setValue(this.dataContext.getMouseMode2SensitivityX());
		lookMouseHorizontalSlider.setMinimum(1);
		lookMouseHorizontalSlider.setMaximum(40);
		lookMouseHorizontalSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				//dataContext.setMouseMode2SensitivityX(lookMouseHorizontalSlider.getValue());
			}
		});
		
		gridConstraints.gridy = 4;
		panel.add(lookMouseHorizontalSlider, gridConstraints);
		
		lookMouseVerticalSlider = new JSlider();
		lookMouseVerticalSlider.setMajorTickSpacing(5);
		lookMouseVerticalSlider.setMinorTickSpacing(1);
		lookMouseVerticalSlider.setPaintTicks(true);
		lookMouseVerticalSlider.setPaintLabels(true);
		lookMouseVerticalSlider.setSnapToTicks(true);
		//lookMouseVerticalSlider.setValue(this.dataContext.getMouseMode2SensitivityY());
		lookMouseVerticalSlider.setMinimum(0);
		lookMouseVerticalSlider.setMaximum(40);
		lookMouseVerticalSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				//dataContext.setMouseMode2SensitivityY(lookMouseVerticalSlider.getValue());
			}
		});
		
		gridConstraints.gridy = 5;
		panel.add(lookMouseVerticalSlider, gridConstraints);
		
		final JCheckBox invertPitchCheckBox = new JCheckBox();
		invertPitchCheckBox.setAction(new SimpleAction() {			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//dataContext.setInvertLook(invertPitchCheckBox.isSelected());
			}
		});
		
		invertPitchCheckBox.setText("Invert look up/down");
		//invertPitchCheckBox.setSelected(this.dataContext.isInvertLook());
		gridConstraints.gridy = 6;
		panel.add(invertPitchCheckBox, gridConstraints);
		
		// filler at bottom
		gridConstraints.gridy = 7;
		gridConstraints.weighty = 1;
		panel.add(new JPanel(), gridConstraints);
		
		return panel;
	}
}
