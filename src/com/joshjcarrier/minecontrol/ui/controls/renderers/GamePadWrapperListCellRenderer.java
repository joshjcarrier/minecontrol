package com.joshjcarrier.minecontrol.ui.controls.renderers;

import java.awt.Component;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.joshjcarrier.minecontrol.ui.models.GamePadWrapper;

/**
 * List cell renderer for the {@link GamePadWrapper} class.
 * @author joshjcarrier
 *
 */
public class GamePadWrapperListCellRenderer extends JLabel implements ListCellRenderer 
{
	private static final long serialVersionUID = -5353456488841978539L;

	public GamePadWrapperListCellRenderer() 
	{
        this.setOpaque(true);
        this.setHorizontalAlignment(LEFT);
        this.setVerticalAlignment(CENTER);
    }

    /*
     * This method finds the image and text corresponding
     * to the selected value and returns the label, set up
     * to display the text and image.
     */
    public Component getListCellRendererComponent(
                                       JList list,
                                       Object value,
                                       int index,
                                       boolean isSelected,
                                       boolean cellHasFocus) 
    {
    	
        // regular combobox list behavior
        if (isSelected) 
        {
            this.setBackground(list.getSelectionBackground());
            this.setForeground(list.getSelectionForeground());
        } 
        else 
        {
            this.setBackground(list.getBackground());
            this.setForeground(list.getForeground());
        }
        
        if (value == null)
        {
        	return this;
        }
        
        this.setText(value.toString());
        this.setFont(list.getFont());

        // custom imaging
        if (value instanceof GamePadWrapper)
        {
        	GamePadWrapper controller = ((GamePadWrapper)value);
        	
    	  	this.setText(controller.getName());
        	
    	  	URL iconUrl = controller.getTileResource();
    	  	
        	// TODO consider resizing resources in advance
        	ImageIcon icon = new ImageIcon(iconUrl);
        	Image newimg = icon.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);  
        	this.setIcon(new ImageIcon(newimg));        	
        }
        
        return this;
    }
}