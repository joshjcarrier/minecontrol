package com.joshjcarrier.minecontrol.services.storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.joshjcarrier.minecontrol.AppInfo;

/**
 * Stores data within a properties file.
 * @author joshjcarrier
 *
 */
public class PropertiesStorage implements IStorage
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
	
	public void load()
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
	}
	
	public String read(String section, String name)
	{
		return this.cache.getProperty(section + "." + name);
	}
	
	public boolean readBoolean(String section, String name)
	{
		String stringValue = this.read(section, name);
		return Boolean.parseBoolean(stringValue);
	}
		
	public int readInt(String section, String name, int defaultValue)
	{
		String stringValue = this.read(section, name);
		try
		{
			return Integer.parseInt(stringValue);
		}
		catch (NumberFormatException ex)
		{
			return defaultValue;
		}
	}
	
	public void write(String section, String name, String value)
	{
		this.cache.put(section + "." + name, value);
	}
	
	public void writeBoolean(String section, String name, boolean value)
	{
		this.write(section, name, String.valueOf(value));
	}
		
	public void writeInt(String section, String name, int value)
	{
		this.write(section, name, String.valueOf(value));
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
