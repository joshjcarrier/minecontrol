package com.joshjcarrier.minecontrol.ui.views;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.joshjcarrier.minecontrol.App;
import com.joshjcarrier.minecontrol.services.RunnableGamePadInterpreter;
import com.joshjcarrier.minecontrol.ui.controls.renderers.GamePadWrapperListCellRenderer;
import com.joshjcarrier.minecontrol.ui.controls.renderers.GameTitleWrapperListCellRenderer;
import com.joshjcarrier.minecontrol.ui.models.GamePadWrapper;
import com.joshjcarrier.minecontrol.ui.models.GameTitleWrapper;

/**
 * The main view.
 * @author joshjcarrier
 *
 */
public class MainView extends JFrame
{
	private static final long serialVersionUID = -46316333717547118L;

	public MainView(final RunnableGamePadInterpreter gamePadInterpreter)
	{
		this.setTitle("Minecontrol for Minecraft");
		this.setLayout(new GridLayout(3, 1));
		
		ArrayList<GameTitleWrapper> gameTitles = new ArrayList<GameTitleWrapper>();			
		Random rand = new Random(System.currentTimeMillis());
		int meatloaf = rand.nextInt(10);		
		if (meatloaf == 0)
		{
			gameTitles.add(new GameTitleWrapper("Mincecraft", "Mojang Specifications", "mincecraft"));
		}
		else
		{
			gameTitles.add(new GameTitleWrapper("Minecraft", "Mojang Specifications", "minecraft"));	
		}		
		
		final JComboBox gameTitlesComboBox = new JComboBox(gameTitles.toArray());
		gameTitlesComboBox.setEnabled(false);	
		GameTitleWrapperListCellRenderer gameTileListCellRenderer = new GameTitleWrapperListCellRenderer();
		gameTileListCellRenderer.setPreferredSize(new Dimension(400, 40));
		gameTitlesComboBox.setRenderer(gameTileListCellRenderer);		
		this.add(gameTitlesComboBox);

		ArrayList<GamePadWrapper> gamePads = gamePadInterpreter.getInputReaderDevices();
		final JComboBox controllersComboBox = new JComboBox(gamePads.toArray());
		GamePadWrapperListCellRenderer inputReaderDeviceListCellRenderer = new GamePadWrapperListCellRenderer();
		inputReaderDeviceListCellRenderer.setPreferredSize(new Dimension(400, 40));
		controllersComboBox.setRenderer(inputReaderDeviceListCellRenderer);
		controllersComboBox.addActionListener(new ActionListener()
		{			
			public void actionPerformed(ActionEvent event)
			{
				GamePadWrapper gamePad = (GamePadWrapper)controllersComboBox.getSelectedItem();
				gamePadInterpreter.setInputReaderDevice(gamePad.getController());
			}
		});
		
		this.add(controllersComboBox);
					
		// default gamepad selection
		for (GamePadWrapper device : gamePads)
		{
			if (device.getName().toLowerCase().contains("xbox"))
			{
				controllersComboBox.setSelectedItem(device);
				break;
			}
		}
		
		// TODO controller customization
		
		this.add(new JLabel("v" + App.VERSION + ". 2012 Josh Carrier <josh@joshjcarrier.com>"));
		
		this.setSize(400, 600);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
}
