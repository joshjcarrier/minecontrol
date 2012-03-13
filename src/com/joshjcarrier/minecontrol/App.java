package com.joshjcarrier.minecontrol;

import javax.swing.JOptionPane;

import com.joshjcarrier.minecontrol.services.RunnableGamePadInterpreter;
import com.joshjcarrier.minecontrol.ui.models.GamePadWrapper;
import com.joshjcarrier.minecontrol.ui.views.MainView;

/**
 * Application entry point.
 * @author joshjcarrier
 *
 */
public class App
{
	public final static String VERSION = "2.0.0a";
	
	public App()
	{
		// TODO sync with minecontrol runtime
		// controller reader service async
		RunnableGamePadInterpreter inputReaderService = new RunnableGamePadInterpreter();
		
		for (GamePadWrapper device : inputReaderService.getInputReaderDevices())
		{
			// TODO lowercase
			if (device.getName().contains("Xbox"))
			{
				inputReaderService.setInputReaderDevice(device.getController());
			}
		}
		
		Thread inputReaderServiceThread = new Thread(inputReaderService);
		inputReaderServiceThread.start();
		
		new MainView();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			new App();
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex + "\r\n" + ex.getStackTrace()[0] + "\r\nContact josh@joshjcarrier.com .", "Minecontrol failure", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
}
