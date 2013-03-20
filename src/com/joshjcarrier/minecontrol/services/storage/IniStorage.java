package com.joshjcarrier.minecontrol.services.storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.ini4j.Ini;

import com.joshjcarrier.minecontrol.AppInfo;

public class IniStorage implements IStorage 
{
	private Ini cache;
	
	public IniStorage() 
	{
		this.cache = new Ini();
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
			this.cache.setComment(AppInfo.ProductName + "Minecontrol v" + AppInfo.BuildVersion);
			this.cache.store(new FileOutputStream(settingsFilePath));
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
			settingsStream = new FileInputStream(settingsFilePath); 
			this.cache.load(settingsStream);
		} 
		catch (FileNotFoundException e) 
		{
			// it's okay to not have an existing settings file.
		} 
		catch (IOException e) 
		{
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
		return this.cache.fetch(section, name);
	}

	public boolean readBoolean(String section, String name) 
	{
		try
		{
			return this.cache.fetch(section, name, Boolean.class);
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public float read(String section, String name, float defaultValue) 
	{
		try
		{
			return this.cache.fetch(section, name, Float.class);
		}
		catch (Exception e)
		{
			return defaultValue;
		}
	}

	public int read(String section, String name, int defaultValue) 
	{
		try
		{
			return this.cache.fetch(section, name, Integer.class);
		}
		catch (Exception e)
		{
			return defaultValue;
		}
	}

	public void write(String section, String name, String value) 
	{
		this.cache.put(section, name, value);
	}

	public void write(String section, String name, boolean value) 
	{
		this.cache.put(section, name, value);
	}

	public void write(String section, String name, float value) 
	{
		this.cache.put(section, name, value);
	}
	
	public void write(String section, String name, int value) 
	{
		this.cache.put(section, name, value);
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
