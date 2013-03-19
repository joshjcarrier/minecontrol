package com.joshjcarrier.minecontrol.framework.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class ButtonMapping implements Serializable
{
	private static final long serialVersionUID = 7306882809094286108L;
	public static final ButtonMapping UNBOUND = new ButtonMapping(ButtonMappingType.Unbound, 0);
	
	private ButtonMappingType mappingType;	
	private int eventCode;
	private int variant;
	private boolean isToggleMode;
	
	public ButtonMapping(ButtonMappingType mappingType, int eventCode, int variant, boolean isToggleMode)
	{
		this.mappingType = mappingType;
		this.eventCode = eventCode;
		this.variant = variant;
		this.isToggleMode = isToggleMode;
	}
	
	public ButtonMapping(ButtonMappingType mappingType, int eventCode, int variant)
	{
		this(mappingType, eventCode, variant, false);
	}
	
	public ButtonMapping(ButtonMappingType mappingType, int eventCode)
	{
		this(mappingType, eventCode, 0, false);
	}
	
	@Override
	public boolean equals(Object b)
	{
		ButtonMapping other = (ButtonMapping) b;
		if (other == null)
		{
			return false;
		}
		
		return this.eventCode == other.eventCode && this.mappingType == other.mappingType && this.variant == other.variant;
	}
	
	public boolean isToggleMode()
	{
		return this.isToggleMode;
	}
	
	public void setIsToggleMode(boolean isToggleMode)
	{
		this.isToggleMode = isToggleMode;
	}
	
	public ButtonMappingType getMappingType()
	{
		return this.mappingType;
	}

	public int getEventCode()
	{
		return this.eventCode;
	}
	
	public int getVariant()
	{
		return this.variant;
	}

	public String toString()
	{
		switch (this.mappingType)
		{
			case Keyboard:
				return KeyEvent.getKeyText(this.eventCode);
			case Mouse:
				switch(this.eventCode)
				{
					case MouseEvent.BUTTON1_MASK:
						return "Mouse left click (button 1)";
					case MouseEvent.BUTTON2_MASK:
						return "Mouse middle click (button 2)";
					case MouseEvent.BUTTON3_MASK:
						return "Mouse right click (button 3)";
					case MouseEvent.MOUSE_WHEEL:
						if (variant > 0)
						{
							return "Mouse scroll up";
						}
						
						return "Mouse scroll down";
					default:
						return "Mouse event unknown";
				}
			case Application:
				switch(this.eventCode)
				{
					case ApplicationEvent.MouseMode:
						return "<Toggle mouse sensitivity>";
					default:
						return "Application event unknown";
				}
			case Unbound:
				return "<none>";
		}
		
		return this.mappingType.name() + " code " + this.eventCode;		
	}
}
