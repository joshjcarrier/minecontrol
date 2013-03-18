package com.joshjcarrier.minecontrol.services.replayhandlers;

import java.awt.Robot;

import com.joshjcarrier.minecontrol.services.ReplayState;

/**
 * Replays the "positive" or "negative" keyboard events if the analog input reaches a threshold value.
 * @author joshjcarrier
 *
 */
public class VirtualKeyAnalogReplayHandler 
{
	public static final float DefaultTolerance = 1.5f;
	
	private final int positiveVirtualKeyMask;
	private final int negativeVirtualKeyMask;
	private final float tolerance;
	private final int maxDutyCycle = 10;
	private int currentDutyCycle = 0;
	private boolean isActive;
	
	public VirtualKeyAnalogReplayHandler(int positiveVirtualKeyMask, int negativeVirtualKeyMask, float tolerance)
	{
		this.positiveVirtualKeyMask = positiveVirtualKeyMask;
		this.negativeVirtualKeyMask = negativeVirtualKeyMask;
		this.tolerance = tolerance;
	}
	
	public int getNegativeVirtualKeyMask()
	{
		return this.negativeVirtualKeyMask;
	}
	
	public int getPositiveVirtualKeyMask()
	{
		return this.positiveVirtualKeyMask;
	}
	
	public float getTolerance()
	{
		return this.tolerance;
	}
	
	public ReplayState replay(ReplayState replayState, float analogInput, Robot humanInterfaceDeviceService)
	{
		if (analogInput > 0)
		{
			// if outside of duty cycle, turn off
			if(this.currentDutyCycle >= analogInput * this.maxDutyCycle * this.tolerance)
			{
				if (this.isActive)
				{
					humanInterfaceDeviceService.keyRelease(this.positiveVirtualKeyMask);
					this.isActive = false;
				}
			}
			else if (!this.isActive)
			{
				humanInterfaceDeviceService.keyPress(this.positiveVirtualKeyMask);
				humanInterfaceDeviceService.keyRelease(this.negativeVirtualKeyMask);
				this.isActive = true;
			}		
		}
		else if (analogInput < 0)
		{
			if(this.currentDutyCycle >= -analogInput * this.maxDutyCycle * this.tolerance)
			{
				if (this.isActive)
				{
					humanInterfaceDeviceService.keyRelease(this.negativeVirtualKeyMask);
					this.isActive = false;
				}
			}
			else if (!this.isActive)
			{
				humanInterfaceDeviceService.keyPress(this.negativeVirtualKeyMask);
				humanInterfaceDeviceService.keyRelease(this.positiveVirtualKeyMask);
				this.isActive = true;
			}			
		}
		else
		{
			if (this.isActive)
			{
				humanInterfaceDeviceService.keyRelease(this.positiveVirtualKeyMask);
				humanInterfaceDeviceService.keyRelease(this.negativeVirtualKeyMask);
				this.isActive = false;
			}
		}
		
		this.currentDutyCycle += 1;
		this.currentDutyCycle %= this.maxDutyCycle + 1;
		
		return replayState;
	}
}
