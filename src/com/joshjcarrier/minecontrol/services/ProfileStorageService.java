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
		readKeyAnalogHandler(section, "mouse.mode1.sensitivity.x", this.propertiesStorage); // TODO profile.getLeftThumbStickXHandler()
		readKeyAnalogHandler(section, "mouse.mode1.sensitivity.y", this.propertiesStorage); // TODO profile.getLeftThumbStickYHandler()
		readMouseMoveAnalogHandler(section, "mouse", this.propertiesStorage); // TODO profile.getRightThumbStickHandler()
		
		for (Entry<Buttons, IButtonsReplayHandler> mapping : profile.getButtonMappingReplayHandlers().entrySet())
		{
			IButtonsReplayHandler handler = readHandler(section, "button." + mapping.getKey().name(), mapping.getKey(), mapping.getValue(), this.propertiesStorage);
			profile.getButtonMappingReplayHandlers().put(mapping.getKey(), handler);
		}
		
		return profile;
	}
	
	public void store(ControllerProfile profile)
	{
		// TODO commit button mapping value instead of persisting hash value
		String identifier = profile.getIdentifier();
		
		String section = getControllerProfileSectionName(identifier);
		writeHandler(section, "mouse.mode1.sensitivity.x", profile.getLeftThumbStickXHandler(), this.propertiesStorage);
		writeHandler(section, "mouse.mode1.sensitivity.y", profile.getLeftThumbStickYHandler(), this.propertiesStorage);
		writeHandler(section, "mouse", profile.getRightThumbStickHandler(), this.propertiesStorage);
		
//		this.propertiesStorage.writeInt(getConfigurationKey(identifier, "mouse.mode2.sensitivity.x"), profile.getMouseMode2SensitivityX());
//		this.propertiesStorage.writeInt(getConfigurationKey(identifier, "mouse.mode2.sensitivity.y"), profile.getMouseMode2SensitivityY());
		
		for (Entry<Buttons, IButtonsReplayHandler> mapping : profile.getButtonMappingReplayHandlers().entrySet())
		{
			writeHandler(section, "button." + mapping.getKey().name(), mapping.getValue(), this.propertiesStorage);
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
		int eventCode = propertiesStorage.readInt(section, handlerIdentifier + ".eventcode", 0);
		int variant = propertiesStorage.readInt(section, handlerIdentifier + ".variant", 0);
		
		ButtonMapping buttonMapping = new ButtonMapping(buttonMappingType, eventCode, variant);						
		return this.replayHandlerFactory.create(activationButton, buttonMapping, false);
	}
	
	private static VirtualKeyAnalogReplayHandler readKeyAnalogHandler(String section, String handlerIdentifier, IStorage propertiesStorage)
	{
		// TODO propertiesStorage.writeInt(getConfigurationKey(handlerIdentifierPrefix, ""));
		return null;
	}
	
	private static VirtualMouseMoveAnalogReplayHandler readMouseMoveAnalogHandler(String section, String handlerIdentifier, IStorage propertiesStorage)
	{
		// TODO
//		propertiesStorage.writeInt(getConfigurationKey(handlerIdentifierPrefix, ""), handler.hashCode());
//		propertiesStorage.writeBoolean(getConfigurationKey(handlerIdentifierPrefix, "invert"), handler.isInvertY());
		return null;
	}
	
	private static void writeHandler(String section, String handlerIdentifier, IButtonsReplayHandler handler, IStorage propertiesStorage)
	{
		ButtonMapping buttonMapping = handler != null ? handler.getButtonMapping() : ButtonMapping.UNBOUND;
		propertiesStorage.write(section, handlerIdentifier + ".mappingtype", buttonMapping.getMappingType().name());
		propertiesStorage.writeInt(section, handlerIdentifier + ".eventcode", buttonMapping.getEventCode());
		propertiesStorage.writeInt(section, handlerIdentifier + ".variant", buttonMapping.getVariant());
	}
	
	private static void writeHandler(String section, String handlerIdentifier, VirtualKeyAnalogReplayHandler handler, IStorage propertiesStorage)
	{
		propertiesStorage.writeInt(section, handlerIdentifier, handler.hashCode());
	}
	
	private static void writeHandler(String section, String handlerIdentifier, VirtualMouseMoveAnalogReplayHandler handler, IStorage propertiesStorage)
	{
		propertiesStorage.writeInt(section, handlerIdentifier, handler.hashCode());
		propertiesStorage.writeBoolean(section, handlerIdentifier + ".invert", handler.isInvertY());
	}
}
