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
			this.cache.setComment("Minecontrol for Minecraft v" + AppInfo.BuildVersion);
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
		return this.cache.fetch(section, name);
	}

	public boolean readBoolean(String section, String name) 
	{
		return this.cache.fetch(section, name, Boolean.class);
	}

	public int readInt(String section, String name, int defaultValue) 
	{
		return this.cache.fetch(section, name, Integer.class);
	}

	public void write(String section, String name, String value) 
	{
		this.cache.put(section, name, value);
	}

	public void writeBoolean(String section, String name, boolean value) 
	{
		this.cache.put(section, name, value);
	}

	public void writeInt(String section, String name, int value) 
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
