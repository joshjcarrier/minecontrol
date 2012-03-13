package com.joshjcarrier.minecontrol.ui.views;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.joshjcarrier.minecontrol.App;
import com.joshjcarrier.minecontrol.ui.controls.renderers.GamePadWrapperListCellRenderer;
import com.joshjcarrier.minecontrol.ui.controls.renderers.GameTitleWrapperListCellRenderer;
import com.joshjcarrier.minecontrol.ui.models.GameTitleWrapper;

/**
 * The main view.
 * @author joshjcarrier
 *
 */
public class MainView extends JFrame
{
	private static final long serialVersionUID = -46316333717547118L;

	public MainView()
	{
		this.setTitle("Minecontrol for Minecraft");
		this.setLayout(new GridLayout(3, 1));
		
		ArrayList<GameTitleWrapper> gameTitles = new ArrayList<GameTitleWrapper>();			
		Random rand = new Random(System.currentTimeMillis());
		int meatloaf = rand.nextInt(2);		
		if (meatloaf == 0)
		{
			gameTitles.add(new GameTitleWrapper("Mincecraft", "Mojang Specifications", "minecraft"));
		}
		else
		{
			gameTitles.add(new GameTitleWrapper("Minecraft", "Mojang Specifications", "minecraft"));	
		}		
		
		final JComboBox gameTitlesComboBox = new JComboBox(gameTitles.toArray());
//		controllers.addActionListener(new ActionListener() {			
//			public void actionPerformed(ActionEvent arg0) {
//				dataContext.setSelectedController((ControllerWrapper)controllers.getSelectedItem());
//			}
//		});
		
		GameTitleWrapperListCellRenderer gameTileListCellRenderer = new GameTitleWrapperListCellRenderer();
		gameTileListCellRenderer.setPreferredSize(new Dimension(400, 40));
		gameTitlesComboBox.setRenderer(gameTileListCellRenderer);		
		this.add(gameTitlesComboBox);

		JComboBox controllersComboBox = new JComboBox();
		GamePadWrapperListCellRenderer inputReaderDeviceListCellRenderer = new GamePadWrapperListCellRenderer();
		inputReaderDeviceListCellRenderer.setPreferredSize(new Dimension(400, 40));
		controllersComboBox.setRenderer(inputReaderDeviceListCellRenderer);
		this.add(controllersComboBox);
		
		this.add(new JLabel("v" + App.VERSION + ". 2012 Josh Carrier <josh@joshjcarrier.com>"));
		
		this.setSize(400, 600);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
}
