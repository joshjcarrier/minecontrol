package com.joshjcarrier.minecontrol.ui.parts;

import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;
import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.minecontrol.framework.input.ControllerProfile;
import com.joshjcarrier.minecontrol.services.ProfileStorageService;
import com.joshjcarrier.minecontrol.services.replayhandlers.IButtonsReplayHandler;
import com.joshjcarrier.minecontrol.services.replayhandlers.ReplayHandlerFactory;
import com.joshjcarrier.minecontrol.ui.views.ConfigurationView;

public class ConfigurationPart extends BasePart
{
	private final ControllerProfile controllerProfile;
	private final ProfileStorageService profileStorageService;
	private final ReplayHandlerFactory replayHandlerFactory;
	
	public ConfigurationPart(
			ControllerProfile controllerProfile, 
			ReplayHandlerFactory replayHandlerFactory,
			ProfileStorageService profileStorageService) 
	{
		this.controllerProfile = controllerProfile;
		this.replayHandlerFactory = replayHandlerFactory;
		this.profileStorageService = profileStorageService;
	}
	
	public ConfigurationPart(ControllerProfile controllerProfile)
	{
		this(controllerProfile, new ReplayHandlerFactory(), new ProfileStorageService());
	}

	public ConfigurationView createView()
	{
		return new ConfigurationView(this);
	}
	
	public boolean isLookInverted() 
	{
		return this.controllerProfile.getRightThumbStickHandler().isInvertY();
	}
	
	public ButtonMapping getButtonMapping(Buttons button)
	{
		IButtonsReplayHandler handler = this.controllerProfile.getButtonMappingReplayHandlers().get(button);
		return handler != null ? handler.getButtonMapping() : null;
	}
	
	public void setButtonMapping(Buttons button, ButtonMapping buttonMapping)
	{
		IButtonsReplayHandler handler = this.replayHandlerFactory.create(button, buttonMapping, false);
		this.controllerProfile.getButtonMappingReplayHandlers().put(button, handler);
		this.profileStorageService.store(this.controllerProfile);
	}
	
	public int getMouseMode1SensitivityX() {
		return this.controllerProfile.getRightThumbStickHandler().getSensitivityX();
	}

	public void setMouseMode1SensitivityX(int mouseMode1SensitivityX) {
		this.controllerProfile.getRightThumbStickHandler().setSensitivityX(mouseMode1SensitivityX);
		this.profileStorageService.store(this.controllerProfile);
	}

	public int getMouseMode1SensitivityY() {
		return this.controllerProfile.getRightThumbStickHandler().getSensitivityY();
	}

	public void setMouseMode1SensitivityY(int mouseMode1SensitivityY) {
		this.controllerProfile.getRightThumbStickHandler().setSensitivityY(mouseMode1SensitivityY);
		this.profileStorageService.store(this.controllerProfile);
	}
	
	public void setLookInverted(boolean invertY)
	{
		this.controllerProfile.getRightThumbStickHandler().setInvertY(invertY);
		this.profileStorageService.store(this.controllerProfile);
	}
}
