package main;

import javax.swing.UIManager;

import gui.View;

public class Run
{

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		View v = new View();
		v.setLocationRelativeTo(null);
		v.setResizable(false);
		v.setVisible(true);
	}

}
