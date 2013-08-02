package com.fifthdimensionsoftware.wormhole;

import javax.swing.JOptionPane;

public class LaunchEntry 
{
	public static final byte isReal = 0;
	
	public static void launch(String[] args)
	{
		JOptionPane.showMessageDialog(null,"An error occured loading the game.\nYou probably broke something.");
		System.exit(1);
	}
}
