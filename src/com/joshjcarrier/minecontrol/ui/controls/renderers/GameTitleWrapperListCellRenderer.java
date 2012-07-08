package com.joshjcarrier.minecontrol.ui.controls.renderers;

import java.awt.Image;

import javax.swing.ImageIcon;

import com.joshjcarrier.minecontrol.ui.models.GameTitleWrapper;

/**
 * List cell renderer for the {@link GameTitleWrapper} class.
 * @author joshjcarrier
 *
 */
public class GameTitleWrapperListCellRenderer extends BaseWrapperListCellRenderer<GameTitleWrapper>
{
	private static final long serialVersionUID = -7527303933458736931L;
	
	@Override
	protected void updateListCell(GameTitleWrapper value)
	{
		ImageIcon icon = new ImageIcon(value.getTileResource());
    	Image newimg = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);  
    	this.iconLabel.setIcon(new ImageIcon(newimg));
    	
    	this.titleLabel.setText(value.getTitle());
    	this.publisherLabel.setText(value.getPublisher());
	}

}
