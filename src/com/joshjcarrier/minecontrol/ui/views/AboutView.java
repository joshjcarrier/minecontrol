package com.joshjcarrier.minecontrol.ui.views;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.joshjcarrier.minecontrol.AppInfo;

public class AboutView extends JDialog
{
	private static final long serialVersionUID = 3018102427542810766L;

	public AboutView() 
	{
		this.setTitle("About " + AppInfo.ProductName);
		
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
		GridBagConstraints gridConstraints = new GridBagConstraints();
		gridConstraints.fill = GridBagConstraints.BOTH;
		gridConstraints.insets = new Insets(5, 0, 0, 5);
		
		Font headerFont = new Font("Verdana", Font.BOLD | Font.ITALIC, 16);
		JLabel headerLabel = new JLabel(AppInfo.ProductName);
		headerLabel.setFont(headerFont);
		gridConstraints.gridy = 0;
		gridConstraints.gridx = 0;
		panel.add(headerLabel, gridConstraints);
		
		gridConstraints.gridy += 1;
		panel.add(new JLabel("For the latest update information visit www.joshjcarrier.com."), gridConstraints);
		
		gridConstraints.gridy += 1;
		panel.add(new JLabel(""), gridConstraints);
		
		gridConstraints.gridy += 1;
		panel.add(new JLabel(AppInfo.ProductName + " is released under the BSD license and made possibly by these open source works:"), gridConstraints);
		
		gridConstraints.gridy += 1;
		panel.add(new JLabel("ini4j the Java .ini library <http://ini4j.sourceforge.net> (Apache 2)"), gridConstraints);
		
		gridConstraints.gridy += 1;
		panel.add(new JLabel("Jinput Java game controller API <http://java.net/projects/jinput> (BSD 2)"), gridConstraints);
		
		gridConstraints.gridy += 1;
		panel.add(new JLabel("RxJava Reactive Extensions <https://github.com/Netflix/RxJava> (Apache 2)"), gridConstraints);
		
		gridConstraints.gridy += 1;
		panel.add(new JLabel("XNA Button Pack – Jeff Jenkins <http://sinnix.net> (CCA3.0 Unported)"), gridConstraints);
		
		gridConstraints.gridy += 1;
		panel.add(new JLabel(""), gridConstraints);
		
		gridConstraints.gridy += 1;
		panel.add(new JLabel("Minecraft and the Minecraft cube are registered trademarks of Mojang Specifications and/or its affiliates."), gridConstraints);
		
		this.add(panel);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	this.setResizable(false);
    	this.pack();
	}
}
