package com.joshjcarrier.minecontrol;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.joshjcarrier.minecontrol.services.RunnableGamePadInterpreter;
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
		Thread inputReaderServiceThread = new Thread(inputReaderService);
		inputReaderServiceThread.start();
		
		new MainView(inputReaderService);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			// Get the native look and feel class name
			String nativeLF = UIManager.getSystemLookAndFeelClassName();

			// Install the native look and feel, allowing startup to proceed with default look and feel if native not possible
			try {
			    UIManager.setLookAndFeel(nativeLF);
			} catch (InstantiationException e) {
			} catch (ClassNotFoundException e) {
			} catch (UnsupportedLookAndFeelException e) {
			} catch (IllegalAccessException e) {
			}
			
			new App();
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex + "\r\n" + ex.getStackTrace()[0] + "\r\nContact josh@joshjcarrier.com .", "Minecontrol failure", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
}
