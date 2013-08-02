package com.fifthdimensionsoftware.wormhole;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class Wormhole extends JDialog
{
	private static final long serialVersionUID = 1L;
	public static String games;
	public static Properties data;
	public static JList<String> list;
	public static JDialog frame;
	
	public static void main(String[] args) throws IOException 
	{
		new Wormhole().run(args);
	}
	
	public static void injectData(String... paths)
	{
		try {
			Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
			method.setAccessible(true);
			for(String path : paths)
			{
				method.invoke(ClassLoader.getSystemClassLoader(), new Object[]{new URL("http://www.trewindata.com/FifthData/"+path)});
			}
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private void run(final String[] args) throws IOException
	{
		frame = this;
		frame.setSize(new Dimension(640,480));
		try {
			frame.setIconImage(ImageIO.read(new File("C:/Users/Andrew/Desktop/Wormhole.png")));
		} catch (IOException e) {}
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    Properties launchable = new Properties();
	    data = launchable;
	    launchable.load(this.sendGet("http://www.trewindata.com/FifthData/list.txt"));
	    games = launchable.getProperty("games");
/*	    for(String item : launchable.getProperty("games").split(";"))
	    {
	    	try {
				this.injectData(launchable.getProperty(item).split(";"));
			} catch (Exception e) {}
	    }*/
	    frame.setLayout(null);
	    JButton launchButton = new JButton("Launch");
	    final Container contentpane = frame.getContentPane();
	    contentpane.setLayout(null);
		list = new JList<String>(games.replace("_", " ").split(";"));
		
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane pane = new JScrollPane(list);
		pane.setBounds(new Rectangle(0,0,frame.getWidth(),frame.getHeight() - 68));
		contentpane.add(pane);
		launchButton.setBounds(0, frame.getHeight() - 68, frame.getWidth(), 30);
		launchButton.addActionListener(new ActionListener()
		{
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				Wormhole.injectData(Wormhole.data.getProperty(Wormhole.list.getSelectedValue().replace(" ", "_")).split(";"));
				while(true)
				{
					try{
						if(LaunchEntry.isReal == 1)
							break;
						break;
					}catch(Throwable t)
					{
						continue;
					}
				}
				
				for(Component comp : frame.getComponents())
				{
					frame.remove(comp);
				}
				
				frame.dispose();
				frame = null;
				System.gc();
				LaunchEntry.launch(args);
			}
		});
		contentpane.add(launchButton);
	    frame.setVisible(true);
	    //launchButton.getActionListeners()[0].actionPerformed(null);
	}
	
	private InputStream sendGet(String url) 
	{
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
			// optional default is GET
			con.setRequestMethod("GET");
 
			//add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			
			return con.getInputStream();
		} catch (IOException e) {
			return null;
		}
 
	}
}
