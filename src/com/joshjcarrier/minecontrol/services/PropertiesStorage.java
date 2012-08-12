package com.joshjcarrier.minecontrol.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.joshjcarrier.minecontrol.AppInfo;
import com.joshjcarrier.minecontrol.framework.input.ButtonMapping;
import com.joshjcarrier.minecontrol.framework.input.ButtonMappingType;

/**
 * Stores data within a properties file.
 * @author joshjcarrier
 *
 */
public class PropertiesStorage
{	
	private Properties cache;
	
	public PropertiesStorage() 
	{
		cache = new Properties();
	}
	
	public void commit()
	{
		String settingsFilePath = this.getSettingsFilePath();
		if (settingsFilePath == null)
		{
			return;
		}
		
		try 
		{
			this.cache.store(new FileOutputStream(settingsFilePath), "Minecontrol for Minecraft v" + AppInfo.BuildVersion);
		} 
		catch (IOException e) 
		{
			// failed to save settings -- silently fail for now.
		}
	}
	
	public Properties load()
	{
		String settingsFilePath = this.getSettingsFilePath();
	 
		FileInputStream settingsStream = null;
		try 
		{
			settingsStream =new FileInputStream(settingsFilePath); 
			cache.load(settingsStream);
		} 
		catch (FileNotFoundException e) 
		{
			// it's okay to not have an existing settings file.
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			// maybe the settings file is corrupt, so delete it
			e.printStackTrace();
		}
		finally
		{
			if (settingsStream != null)
			{
				try 
				{
					settingsStream.close();
				} 
				catch (IOException e) 
				{
					// tried to dispose of the stream nicely, oh well.
				}
			}
		}
		
		return cache;
	}
	
	public String read(String name)
	{
		return this.cache.getProperty(name);
	}
	
	public boolean readBoolean(String name)
	{
		String stringValue = this.read(name);
		return Boolean.parseBoolean(stringValue);
	}
	
	public ButtonMapping readButtonMapping(String name)
	{
		String mappingType = this.read(name + ".mappingtype");
		
		if (mappingType == null)
		{
			return null;
		}
		
		ButtonMappingType buttonMappingType = ButtonMappingType.valueOf(mappingType);
		int eventCode = this.readInt(name + ".eventcode", 0);
		int variant = this.readInt(name + ".variant", 0);
		
		return new ButtonMapping(buttonMappingType, eventCode, variant);
	}
	
	public int readInt(String name, int defaultValue)
	{
		String stringValue = this.read(name);
		try
		{
			return Integer.parseInt(stringValue);
		}
		catch (NumberFormatException ex)
		{
			return defaultValue;
		}
	}
	
	public void write(String name, String value)
	{
		this.cache.put(name, value);
	}
	
	public void writeBoolean(String name, boolean value)
	{
		this.write(name, String.valueOf(value));
	}
	
	public void writeButtonMapping(String name, ButtonMapping buttonMapping)
	{
		this.write(name + ".mappingtype", buttonMapping.getMappingType().name());
		this.writeInt(name + ".eventcode", buttonMapping.getEventCode());
		this.writeInt(name + ".variant", buttonMapping.getVariant());
	}
	
	public void writeInt(String name, int value)
	{
		this.write(name, String.valueOf(value));
	}
	
	private String getSettingsFilePath()
	{
		try 
		{
			String userHomeDir = System.getProperty("user.home");
			if(userHomeDir == null)
			{
				return null;
			}
			
			return userHomeDir + "/.minecontrol2.txt";
		}
		catch (SecurityException ioEx) {}
		
		return null;
	}
}
