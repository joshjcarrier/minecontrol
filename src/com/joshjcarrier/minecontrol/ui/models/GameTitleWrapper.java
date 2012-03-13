package com.joshjcarrier.minecontrol.ui.models;

import java.net.URL;

public class GameTitleWrapper
{
	private final String title;
	private final String publisher;
	private final String tileResourceUri;
	
	public GameTitleWrapper(String title, String publisher, String tileResourceUri)
	{
		this.title = title;
		this.publisher = publisher;
		this.tileResourceUri = tileResourceUri;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getPublisher()
	{
		return publisher;
	}
	
	public URL getTileResource()
	{
		return this.getClass().getClassLoader().getResource("content/gametitles/" + this.tileResourceUri + ".png"); 
	}
}
