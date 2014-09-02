package com.joshjcarrier.minecontrol.ui.controls.renderers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import com.joshjcarrier.minecontrol.ui.models.GamePadWrapper;

/**
 * List cell renderer for the {@link GamePadWrapper} class.
 * @author joshjcarrier
 *
 */
public class GamePadWrapperListCellRenderer extends BaseWrapperListCellRenderer<GamePadWrapper>
{
	private static final long serialVersionUID = -5353456488841978539L;

	public GamePadWrapperListCellRenderer()
	{
		titleLabel.setFont(new Font("Verdana", Font.BOLD, 12));
		publisherLabel.setFont(new Font("Verdana", Font.ITALIC, 10));
	}
	
	@Override
	protected void updateListCell(GamePadWrapper value)
	{	
	  	URL iconUrl = value.getTileResource();
	  	
    	ImageIcon icon = new ImageIcon(iconUrl);
    	Image newimg = icon.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);  
    	this.iconLabel.setIcon(new ImageIcon(newimg));    
    	    
    	String normalizedControllerName = value.getName().toLowerCase();
    	if (normalizedControllerName.contains("xbox"))
    	{
    		this.titleLabel.setText("Microsoft Xbox Controller");
    		this.titleLabel.setForeground(Color.BLACK);
    		this.publisherLabel.setText(value.getName());
    	}
    	else if (normalizedControllerName.contains("keyboard"))
    	{
    		this.titleLabel.setText("Keyboard");
    		this.titleLabel.setForeground(Color.GRAY);
    		this.publisherLabel.setText(value.getName());
    	}
    	else if (normalizedControllerName.contains("mouse"))
    	{
    		this.titleLabel.setText("Mouse");
    		this.titleLabel.setForeground(Color.GRAY);
    		this.publisherLabel.setText(value.getName());
    	}
    	else
    	{
    		this.titleLabel.setText(value.getName());
    		this.titleLabel.setForeground(Color.BLACK);
    		this.publisherLabel.setText("");
    	}
	}
}