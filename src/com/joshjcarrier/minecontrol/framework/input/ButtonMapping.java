package com.joshjcarrier.minecontrol.framework.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class ButtonMapping
{
	private ButtonMappingType mappingType;	
	private int eventCode;
	private int variant;
	
	public ButtonMapping(ButtonMappingType mappingType, int eventCode, int variant)
	{
		this.mappingType = mappingType;
		this.eventCode = eventCode;
		this.variant = variant;
	}
	
	public ButtonMapping(ButtonMappingType mappingType, int eventCode)
	{
		this(mappingType, eventCode, 0);
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
					case MouseEvent.BUTTON1:
						return "Mouse left click (button 1)";
					case MouseEvent.BUTTON2:
						return "Mouse middle click (button 2)";
					case MouseEvent.BUTTON3:
						return "Mouse right click (button 3)";
					case MouseEvent.MOUSE_WHEEL:
						if (variant > 0)
						{
							return "Mouse scroll up";
						}
						
						return "Mouse scroll down";
				}
			case Unbound:
				return "none";
		}
		
		return this.mappingType.name() + " code " + this.eventCode;		
	}
}
