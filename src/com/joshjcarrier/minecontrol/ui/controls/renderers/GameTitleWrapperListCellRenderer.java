package com.joshjcarrier.minecontrol.ui.controls.renderers;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.joshjcarrier.minecontrol.ui.models.GameTitleWrapper;

/**
 * List cell renderer for the {@link GameTitleWrapper} class.
 * @author joshjcarrier
 *
 */
public class GameTitleWrapperListCellRenderer extends JPanel implements ListCellRenderer
{
	private static final long serialVersionUID = -7527303933458736931L;
	
	private final JLabel iconLabel;
	private final JLabel publisherLabel;
	private final JLabel titleLabel;
	
	public GameTitleWrapperListCellRenderer()
	{
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gridConstraints = new GridBagConstraints();
		
		iconLabel = new JLabel();
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 0;
		gridConstraints.gridheight = 2;
		gridConstraints.gridwidth = 1;
		gridConstraints.weightx = 0;
		gridConstraints.ipadx = 4;
		gridConstraints.fill = GridBagConstraints.NONE;
		this.add(iconLabel, gridConstraints);
		
		titleLabel = new JLabel();	
		titleLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 0;
		gridConstraints.gridheight = 1;
		gridConstraints.gridwidth = 1;
		gridConstraints.weightx = 1;
		gridConstraints.ipadx = 0;
		gridConstraints.fill = GridBagConstraints.HORIZONTAL;
		this.add(titleLabel, gridConstraints);
		
		publisherLabel = new JLabel();
		publisherLabel.setFont(new Font("Verdana", Font.ITALIC, 12));
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 1;
		gridConstraints.gridheight = 1;
		gridConstraints.gridwidth = 1;
		gridConstraints.weightx = 1;
		gridConstraints.ipadx = 0;
		gridConstraints.fill = GridBagConstraints.HORIZONTAL;
		this.add(publisherLabel, gridConstraints);
	}

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

		this.setFont(list.getFont());
        
        // custom imaging
        if(value instanceof GameTitleWrapper)
        {
        	GameTitleWrapper gameTitle = ((GameTitleWrapper)value);
        
        	ImageIcon icon = new ImageIcon(gameTitle.getTileResource());
        	Image newimg = icon.getImage().getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);  
        	this.iconLabel.setIcon(new ImageIcon(newimg));
        	
        	this.titleLabel.setText(gameTitle.getTitle());
        	this.publisherLabel.setText(gameTitle.getPublisher());
        	
//        	if(controller.getController() != null)
//        	{
//	        	URL iconUrl = controller.getThumbnail();
//	        	
//	        	// TODO consider resizing resources in advance
//	        	ImageIcon icon = new ImageIcon(iconUrl);
//	        	Image newimg = icon.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);  
//	        	this.setIcon(new ImageIcon(newimg));
//        	}
//        	else
//        	{
//        		setIcon(null);
//        	}
        }
		
		return this;
	}

}
