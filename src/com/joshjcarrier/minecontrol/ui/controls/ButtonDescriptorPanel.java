package com.joshjcarrier.minecontrol.ui.controls;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.ui.ContentResources;
import com.joshjcarrier.minecontrol.ui.views.GamePadProfileView;

public class ButtonDescriptorPanel extends JPanel 
{
	private static final long serialVersionUID = -390648248728862955L;

	public ButtonDescriptorPanel(String canonicalName, String iconResource) 
	{
		this.setLayout(new GridLayout());
		
		Font headerFont = new Font("Verdana", Font.BOLD, 10);
		JLabel nameLabel = new JLabel(canonicalName);
		nameLabel.setFont(headerFont);
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(nameLabel);
				
		JLabel iconLabel = new JLabel();
		URL iconUrl = GamePadProfileView.class.getClassLoader().getResource(iconResource);
    	ImageIcon icon = new ImageIcon(iconUrl);
    	Image newimg = icon.getImage().getScaledInstance(24, 24,  java.awt.Image.SCALE_SMOOTH);  
    	iconLabel.setIcon(new ImageIcon(newimg));
    	iconLabel.setToolTipText(canonicalName);
    	iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(iconLabel);
	}
	
	public ButtonDescriptorPanel(Buttons button) 
	{		
		this(getCanonicalName(button), getButtonResource(button));
	}
	
	private static String getCanonicalName(Buttons button)
	{
		switch(button)
		{
			case BACK:
				return "Back";
			case DPAD_DOWN:
				return "D-Pad down";
			case DPAD_LEFT:
				return "D-Pad left";
			case DPAD_RIGHT:
				return "D-Pad right";
			case DPAD_UP:
				return "D-Pad up";
			case LEFT_SHOULDER:
				return "Left shoulder";
			case LEFT_STICK:
				return "Left joystick press";
			case LEFT_TRIGGER:
				return "Left trigger";
			case RIGHT_SHOULDER:
				return "Right shoulder";
			case RIGHT_STICK:
				return "Right joystick press";
			case RIGHT_TRIGGER:
				return "Right trigger";
			case START:
				return "Start";
			default:
				return button.name();
		}
	}
	
	private static String getButtonResource(Buttons button)
	{
		switch(button)
		{
			case A:
				return ContentResources.BUTTON_XBOX360_A;
			case B:
				return ContentResources.BUTTON_XBOX360_B;
			case BACK:
				return ContentResources.BUTTON_XBOX360_BACK;
			case DPAD_DOWN:
				return ContentResources.BUTTON_XBOX360_DPAD;
			case DPAD_LEFT:
				return ContentResources.BUTTON_XBOX360_DPAD;
			case DPAD_RIGHT:
				return ContentResources.BUTTON_XBOX360_DPAD;
			case DPAD_UP:
				return ContentResources.BUTTON_XBOX360_DPAD;
			case LEFT_SHOULDER:
				return ContentResources.BUTTON_XBOX360_LEFTSHOULDER;
			case LEFT_STICK:
				return ContentResources.BUTTON_XBOX360_LEFTSTICK;
			case LEFT_TRIGGER:
				return ContentResources.BUTTON_XBOX360_LEFTTRIGGER;
			case RIGHT_SHOULDER:
				return ContentResources.BUTTON_XBOX360_RIGHTSHOULDER;
			case RIGHT_STICK:
				return ContentResources.BUTTON_XBOX360_RIGHTSTICK;
			case RIGHT_TRIGGER:
				return ContentResources.BUTTON_XBOX360_RIGHTTRIGGER;
			case START:
				return ContentResources.BUTTON_XBOX360_START;
			case X:
				return ContentResources.BUTTON_XBOX360_X;
			case Y:
				return ContentResources.BUTTON_XBOX360_Y;
			default:
				return ContentResources.INPUTDEVICE_XBOX360;
		}
	}
}
