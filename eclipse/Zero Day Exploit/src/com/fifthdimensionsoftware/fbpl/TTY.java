package com.fifthdimensionsoftware.fbpl;

import java.io.*;
import java.util.*;

public class TTY 
{
	private static HashMap<String,PrintStream> terminals = new HashMap<String,PrintStream>();
	private static HashMap<PrintStream,ByteArrayOutputStream> streams = new HashMap<PrintStream,ByteArrayOutputStream>();
	
	public static PrintStream getTerminal(String name)
	{
		if(terminals.containsKey(name))
			return terminals.get(name);
		else
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			terminals.put(name, new PrintStream(os));
			streams.put(getTerminal(name), os);
			return getTerminal(name);
		}
	}
	
	public static void destroyTerminal(String name)
	{
		terminals.remove(name);
	}
	
	public static String getContents(PrintStream terminal) throws Exception
	{
		return streams.get(terminal).toString("UTF8");
	}
}
