package com.joshjcarrier.minecontrol.framework.modes;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.joshjcarrier.minecontrol.framework.input.ApplicationEvent;
import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;

public class MinecraftGameMode 
{

	public String getButtonMappingName(ButtonMapping mapping)
	{
		switch (mapping.getMappingType())
		{
			case Keyboard:
				switch(mapping.getEventCode())
				{
					case KeyEvent.VK_1:
						return "Item 1";
					case KeyEvent.VK_2:
						return "Item 2";
					case KeyEvent.VK_3:
						return "Item 3";
					case KeyEvent.VK_4:
						return "Item 4";
					case KeyEvent.VK_5:
						return "Item 5";
					case KeyEvent.VK_6:
						return "Item 6";
					case KeyEvent.VK_7:
						return "Item 7";
					case KeyEvent.VK_8:
						return "Item 8";
					case KeyEvent.VK_9:
						return "Item 9";
					case KeyEvent.VK_ESCAPE:
						return "Menu/Pause";
					case KeyEvent.VK_F1:
						return "Heads-up display";
					case KeyEvent.VK_F2:
						return "Screenshot";
					case KeyEvent.VK_F3:
						return "Debug overlay";
					case KeyEvent.VK_F5:
						return "Change perspective";
					case KeyEvent.VK_F8:
						return "Mouse smoothing (cinematic)";
					case KeyEvent.VK_F11:
						return "Full-screen mode";
					case KeyEvent.VK_SHIFT:
						return "Sneak";
					case KeyEvent.VK_W:
						return "Forward";
					case KeyEvent.VK_A:
						return "Left";
					case KeyEvent.VK_S:
						return "Back";
					case KeyEvent.VK_D:
						return "Right";
					case KeyEvent.VK_SPACE:
						return "Jump";
					case KeyEvent.VK_E:
						return "Inventory";
					case KeyEvent.VK_Q:
						return "Drop";
					case KeyEvent.VK_T:
						return "Chat";
					case KeyEvent.VK_SLASH:
						return "Command";
					case KeyEvent.VK_TAB:
						return "List players";
				}
			case Mouse:
				switch(mapping.getEventCode())
				{
					case MouseEvent.BUTTON1_MASK:
						return "Action";
					case MouseEvent.BUTTON2_MASK:
						return "Pick block";
					case MouseEvent.BUTTON3_MASK:
						return "Use item";
					case MouseEvent.MOUSE_WHEEL:
						if (mapping.getVariant() > 0)
						{
							return "Next item";
						}
						
						return "Previous item";
				}
		}
		
		return "";		
	}
}
