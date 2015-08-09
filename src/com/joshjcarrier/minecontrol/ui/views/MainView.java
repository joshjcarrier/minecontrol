package com.joshjcarrier.minecontrol.ui.views;

import com.joshjcarrier.minecontrol.AppInfo;
import com.joshjcarrier.minecontrol.ui.ContentResources;
import com.joshjcarrier.minecontrol.ui.controllers.MainController;
import com.joshjcarrier.minecontrol.ui.controls.renderers.GamePadWrapperListCellRenderer;
import com.joshjcarrier.minecontrol.ui.controls.renderers.GameTitleWrapperListCellRenderer;
import com.joshjcarrier.minecontrol.ui.models.GamePadProfileWrapper;
import com.joshjcarrier.minecontrol.ui.models.GamePadWrapper;
import com.joshjcarrier.minecontrol.ui.models.GameTitleWrapper;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The main view.
 * @author joshjcarrier
 *
 */
public class MainView extends JFrame
{
	private static final long serialVersionUID = -46316333717547118L;

	public MainView(final MainController mainController)
	{
		this.setTitle(AppInfo.ProductName);
		
		ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource(ContentResources.INPUTDEVICE_XBOX_ONE));
    	this.setIconImage(icon.getImage());    
    	
		JPanel contentPanel = createContentPanel(mainController);
		contentPanel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
		this.add(contentPanel);
		
		this.setResizable(false);		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
	}
	
	private static JPanel createContentPanel(final MainController mainController)
	{
		JPanel contentPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gridConstraints = new GridBagConstraints();
		gridConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		MatteBorder panelBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY);			
		
		JPanel gameTitleSelectionPanel = createGameTitleSelectionPanel();
		gameTitleSelectionPanel.setBorder(BorderFactory.createTitledBorder(panelBorder, "MODE"));
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 0;
		contentPanel.add(gameTitleSelectionPanel, gridConstraints);
		
		JPanel controllerSelectionPanel = createControllerSelectionPanel(mainController);
		controllerSelectionPanel.setBorder(BorderFactory.createTitledBorder(panelBorder, "DEVICE"));
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 1;		
		contentPanel.add(controllerSelectionPanel, gridConstraints);
								
		JPanel profilePanel = createProfilePanel(mainController);
		profilePanel.setBorder(BorderFactory.createTitledBorder(panelBorder, "PROFILE"));
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 2;
		contentPanel.add(profilePanel, gridConstraints);
		
		JPanel footerPanel = createFooterPanel();
		footerPanel.setBorder(BorderFactory.createEmptyBorder(6, 4, 0, 4));
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 3;
		contentPanel.add(footerPanel, gridConstraints);
		
		return contentPanel;
	}
	
	private static JPanel createGameTitleSelectionPanel()
	{
		JPanel panel = new JPanel(new GridLayout(1, 1));
		
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
		GameTitleWrapperListCellRenderer gameTileListCellRenderer = new GameTitleWrapperListCellRenderer();
		gameTileListCellRenderer.setPreferredSize(new Dimension(300, 35));
		gameTitlesComboBox.setRenderer(gameTileListCellRenderer);
		
		panel.add(gameTitlesComboBox);
		
		return panel;
	}
	
	private static JPanel createControllerSelectionPanel(final MainController mainController)
	{
		JPanel panel = new JPanel(new GridLayout(1, 1));

		List<GamePadWrapper> gamePads = mainController.getGamePads();
		final JComboBox controllersComboBox = new JComboBox(gamePads.toArray());
		GamePadWrapperListCellRenderer inputReaderDeviceListCellRenderer = new GamePadWrapperListCellRenderer();
		inputReaderDeviceListCellRenderer.setPreferredSize(new Dimension(300, 35));
		controllersComboBox.setRenderer(inputReaderDeviceListCellRenderer);
		controllersComboBox.addActionListener(new ActionListener()
		{			
			public void actionPerformed(ActionEvent event)
			{
				GamePadWrapper gamePad = (GamePadWrapper)controllersComboBox.getSelectedItem();
				mainController.setActiveGamePad(gamePad);
			}
		});
		
		panel.add(controllersComboBox);
		
		// fire initial selection logic
		if(controllersComboBox.getItemCount() > 0)
		{
			controllersComboBox.setSelectedIndex(0);
		}
		
		// default gamepad selection
		for (GamePadWrapper device : gamePads)
		{
			if (device.getName().toLowerCase().contains("xbox"))
			{
				controllersComboBox.setSelectedItem(device);
				break;
			}
		}
				
		return panel;
	}
	
	private static JPanel createProfilePanel(final MainController mainController)
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gridConstraints = new GridBagConstraints();
		gridConstraints.fill = GridBagConstraints.HORIZONTAL;


		GamePadWrapper activeGamePad = mainController.getActiveGamePad();
		JComboBox profilesComboBox = new JComboBox(activeGamePad == null ? new GamePadProfileWrapper[0] : activeGamePad.getProfiles().toArray());
		gridConstraints.gridx = 0;
		gridConstraints.weightx = 1;
		gridConstraints.insets = new Insets(0, 0, 0, 10);
		panel.add(profilesComboBox, gridConstraints);
		
		JButton configurationButton = new JButton("Configure");
		configurationButton.addActionListener(new ActionListener()
		{			
			public void actionPerformed(ActionEvent event)
			{
				GamePadProfileView view = mainController.navigateToGamePadProfile();
                if (view != null) {
                    view.setModal(true);
                    view.setVisible(true);
                }
			}
		});
		
		gridConstraints.gridx = 1;
		gridConstraints.weightx = 0;
		gridConstraints.insets = new Insets(0, 0, 0, 0);
		panel.add(configurationButton, gridConstraints);
		
		return panel;
	}
	
	private static JPanel createFooterPanel()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gridConstraints = new GridBagConstraints();
		
		JLabel copyrightLabel = new JLabel("v" + AppInfo.BuildVersion + ". 2014 Josh Carrier <josh@joshjcarrier.com>  ");
		copyrightLabel.setFont(new Font("Verdana", Font.PLAIN, 10));
		copyrightLabel.setForeground(Color.DARK_GRAY);
		panel.add(copyrightLabel, gridConstraints);
		
		JButton aboutButton = new JButton(":)");
		aboutButton.setToolTipText("About " + AppInfo.ProductName);
		aboutButton.addActionListener(new ActionListener()
		{			
			public void actionPerformed(ActionEvent event)
			{
				AboutView aboutView = new AboutView();
				aboutView.setModal(true);
				aboutView.setVisible(true);
			}
		});
		panel.add(aboutButton);
		
		return panel;
	}
}
