package com.joshjcarrier.minecontrol.services;

import java.util.Map.Entry;

import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;
import com.joshjcarrier.minecontrol.framework.input.ButtonMappingType;
import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.framework.input.ControllerProfile;
import com.joshjcarrier.minecontrol.services.replayhandlers.IButtonsReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.ReplayHandlerFactory;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualKeyAnalogReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.VirtualMouseMoveAnalogReplayHandler;
import com.joshjcarrier.minecontrol.services.storage.IStorage;
import com.joshjcarrier.minecontrol.services.storage.IniStorage;

public class ProfileStorageService 
{
	private final IStorage propertiesStorage;
	private final ReplayHandlerFactory replayHandlerFactory;
	
	public ProfileStorageService(ReplayHandlerFactory replayHandlerFactory, IStorage propertiesStorage)
	{
		this.replayHandlerFactory = replayHandlerFactory;
		this.propertiesStorage = propertiesStorage;
	}
	
	public ProfileStorageService() 
	{
		this(new ReplayHandlerFactory(), new IniStorage());
	}
	
	public ControllerProfile load(String identifier)
	{
		this.propertiesStorage.load();
		ControllerProfile profile = new ControllerProfile();

		String section = getControllerProfileSectionName(identifier);
		profile.setLeftThumbStickXHandler(readKeyAnalogHandler(section, "mode1.lts.x", ControllerProfile.DefaultLeftThumbStickXPositiveMask, ControllerProfile.DefaultLeftThumbStickXNegativeMask));
		profile.setLeftThumbStickYHandler(readKeyAnalogHandler(section, "mode1.lts.y", ControllerProfile.DefaultLeftThumbStickYPositiveMask, ControllerProfile.DefaultLeftThumbStickYNegativeMask));
		profile.setRightThumbStickHandler(readMouseMoveAnalogHandler(section, "mode1.rts"));
		
		for (Entry<Buttons, IButtonsReplayHandler> mapping : profile.getButtonMappingReplayHandlers().entrySet())
		{
			IButtonsReplayHandler handler = readHandler(section, "button." + mapping.getKey().name(), mapping.getKey(), mapping.getValue(), this.propertiesStorage);
			profile.getButtonMappingReplayHandlers().put(mapping.getKey(), handler);
		}
		
		return profile;
	}
	
	public void store(ControllerProfile profile)
	{
		String identifier = profile.getIdentifier();
		
		String section = getControllerProfileSectionName(identifier);
		writeHandler(section, "mode1.lts.x", profile.getLeftThumbStickXHandler());
		writeHandler(section, "mode1.lts.y", profile.getLeftThumbStickYHandler());
		writeHandler(section, "mode1.rts", profile.getRightThumbStickHandler());
				
		for (Entry<Buttons, IButtonsReplayHandler> mapping : profile.getButtonMappingReplayHandlers().entrySet())
		{
			writeHandler(section, "button." + mapping.getKey().name(), mapping.getValue());
		}
		
		this.propertiesStorage.commit();
	}
	
	private static String getControllerProfileSectionName(String identifier)
	{
		return "controllers." + identifier;
	}
	
	private IButtonsReplayHandler readHandler(String section, String handlerIdentifier, Buttons activationButton, IButtonsReplayHandler fallbackValue, IStorage propertiesStorage)
	{
		String mappingType = propertiesStorage.read(section, handlerIdentifier + ".mappingtype");
		
		if (mappingType == null)
		{
			return fallbackValue;
		}
		
		ButtonMappingType buttonMappingType = ButtonMappingType.valueOf(mappingType);
		int eventCode = propertiesStorage.read(section, handlerIdentifier + ".eventcode", 0);
		int variant = propertiesStorage.read(section, handlerIdentifier + ".variant", 0);
		
		ButtonMapping buttonMapping = new ButtonMapping(buttonMappingType, eventCode, variant);						
		return this.replayHandlerFactory.create(activationButton, buttonMapping, false);
	}
	
	private VirtualKeyAnalogReplayHandler readKeyAnalogHandler(String section, String handlerIdentifier, int defaultPositiveMask, int defaultNegativeMask)
	{
		int positiveVirtualKeyMask = this.propertiesStorage.read(section, handlerIdentifier + ".positive.eventcode", defaultPositiveMask);
		int negativeVirtualKeyMask = this.propertiesStorage.read(section, handlerIdentifier + ".negative.eventcode", defaultNegativeMask);
		float tolerance = this.propertiesStorage.read(section, handlerIdentifier + ".tolerance", VirtualKeyAnalogReplayHandler.DefaultTolerance);
		return new VirtualKeyAnalogReplayHandler(positiveVirtualKeyMask, negativeVirtualKeyMask, tolerance);
	}
	
	private VirtualMouseMoveAnalogReplayHandler readMouseMoveAnalogHandler(String section, String handlerIdentifier)
	{
		int sensitivityX = this.propertiesStorage.read(section, handlerIdentifier + ".x.sensitivity", VirtualMouseMoveAnalogReplayHandler.DefaultSensitivity);
		int sensitivityY = this.propertiesStorage.read(section, handlerIdentifier + ".x.sensitivity", VirtualMouseMoveAnalogReplayHandler.DefaultSensitivity);
		int sensitivitySecondaryX = this.propertiesStorage.read(section, handlerIdentifier + ".x.sensitivitysecondary", VirtualMouseMoveAnalogReplayHandler.DefaultSecondarySensitivity);
		int sensitivitySecondaryY = this.propertiesStorage.read(section, handlerIdentifier + ".x.sensitivitysecondary", VirtualMouseMoveAnalogReplayHandler.DefaultSecondarySensitivity);
		boolean invertY = this.propertiesStorage.readBoolean(section, handlerIdentifier + ".y.invert"); 
		return new VirtualMouseMoveAnalogReplayHandler(sensitivityX, sensitivityY, sensitivitySecondaryX, sensitivitySecondaryY, invertY);
	}
	
	private void writeHandler(String section, String handlerIdentifier, IButtonsReplayHandler handler)
	{
		ButtonMapping buttonMapping = handler != null ? handler.getButtonMapping() : ButtonMapping.UNBOUND;
		this.propertiesStorage.write(section, handlerIdentifier + ".mappingtype", buttonMapping.getMappingType().name());
		this.propertiesStorage.write(section, handlerIdentifier + ".eventcode", buttonMapping.getEventCode());
		this.propertiesStorage.write(section, handlerIdentifier + ".variant", buttonMapping.getVariant());
	}
	
	private void writeHandler(String section, String handlerIdentifier, VirtualKeyAnalogReplayHandler handler)
	{
		this.propertiesStorage.write(section, handlerIdentifier + ".positive.eventcode", handler.getPositiveVirtualKeyMask());
		this.propertiesStorage.write(section, handlerIdentifier + ".negative.eventcode", handler.getNegativeVirtualKeyMask());
		this.propertiesStorage.write(section, handlerIdentifier + ".tolerance", Float.toString(handler.getTolerance()));
	}
	
	private void writeHandler(String section, String handlerIdentifier, VirtualMouseMoveAnalogReplayHandler handler)
	{
		this.propertiesStorage.write(section, handlerIdentifier + ".x.sensitivity", handler.getSensitivityX());
		this.propertiesStorage.write(section, handlerIdentifier + ".y.sensitivity", handler.getSensitivityY());
		this.propertiesStorage.write(section, handlerIdentifier + ".x.sensitivitysecondary", handler.getSensitivitySecondaryX());
		this.propertiesStorage.write(section, handlerIdentifier + ".y.sensitivitysecondary", handler.getSensitivitySecondaryY());
		this.propertiesStorage.write(section, handlerIdentifier + ".y.invert", handler.isInvertY());
	}
}
