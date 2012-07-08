package com.joshjcarrier.minecontrol.ui.controls.renderers;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 * List cell renderer for the {@link BaseWrapperListCellRenderer} class.
 * @author joshjcarrier
 *
 */
public abstract class BaseWrapperListCellRenderer<T> extends JPanel implements ListCellRenderer
{
	private static final long serialVersionUID = -2297820527959875027L;
	
	protected final JLabel iconLabel;
	protected final JLabel publisherLabel;
	protected final JLabel titleLabel;
	
	protected BaseWrapperListCellRenderer()
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
	
	public final Component getListCellRendererComponent(
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
       
        // custom update
		@SuppressWarnings("unchecked")
		T typedValue = (T) value;
		if (typedValue != null)
		{
			updateListCell(typedValue);
		}
		
		return this;
	}
	
	protected abstract void updateListCell(T value);
}
