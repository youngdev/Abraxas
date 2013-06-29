package com.fifthdimensionsoftware.fbpl;

import java.io.*;
import java.util.*;

//Object for facilitating the execution of a single method
public class Method {
	public File codeBase;
	public String type = "null";
	public Stack<String> location = new Stack<String>();
	public Object[] fileCache;
	public Object[] croppedCache;
	private boolean hasBegun;
	public String terminal;
	private boolean ignoreGroup;
	public HashMap<String, Object> variables = new HashMap<String,Object>();
	
	public Method(File fbplParam, String type, int index, Object[] cache, String terminal)
	{
		this.codeBase = fbplParam;
		if(!this.type.isEmpty())
			this.type = type;
		else
			this.type = "null";
		this.fileCache = cache;
		this.croppedCache = new Object[fileCache.length-index-1];
		this.terminal = terminal;
		System.arraycopy(this.fileCache, index+1, this.croppedCache, 0, this.croppedCache.length);
	}
	//Execute this method
	public Object execute(Object[] args)
	{
		Object toReturn = null;
		int index = 0;
		outer : for(Object item : this.croppedCache)
		{
			String line = ((String)item).trim();
			if(index > 0)
			{
				if(!this.ignoreGroup)
				{
					switch(line.split(" ")[0])
					{
					case "{":
						this.moveIn(((String)croppedCache[index-1]).split(" |\\(")[0].trim(), ((String)croppedCache[index-1]).trim());
						break;
					case "}":
						if(this.hasBegun && this.location.isEmpty())
							break outer;
						else
							this.moveOut();
						break;
					case "print":
						TTY.getTerminal(this.terminal).println(line.split("\"")[1].replace("\\n", "\n"));
						break;
					case "err":
						TTY.getTerminal(this.terminal).println("[ERROR]"+line.split("\"")[1].replace("\\n", "\n"));
						break;
					case "return":
						switch(this.type)
						{
						case "null":
							return null;
						case "num":
							return Double.parseDouble(line.split(" ")[1]);
						case "boolean":
							return this.evaluateBoolean(line.split(" ")[1]);
						case "string":
							return line.split("\"")[1];
						case "array":
							return null;
						case "var":
							return null;
						default:
							System.err.println("[FBPL]"+type+" is not a valid type, ignoring");
							break;
						}
						break;
					}
					if(line.split(" ")[0] == "}")
					{
						if(this.hasBegun && this.location.isEmpty())
							break outer;
						else
							this.moveOut();
					}
				}
				this.hasBegun = true;
			}
			
			index++;
		}
		try {
			System.err.println(TTY.getContents(TTY.getTerminal(this.terminal)));
		} catch (Exception e) {e.printStackTrace();}
		return toReturn;
	}
	
	//Get the next not-blank line from the scanner (also treats comments as blank)
	public String nextValidLine(Scanner fbplCode)
	{
		if(fbplCode.hasNext())
		{
			String nextLine = fbplCode.nextLine();
			while((nextLine.trim().isEmpty() || nextLine.trim().startsWith("//")) && fbplCode.hasNext())
			{
				nextLine = fbplCode.nextLine();
			}
			return nextLine.trim();
		}else
			return "";
	}
	
	//Exit a code block
	public void moveOut()
	{
		this.location.pop();
		this.ignoreGroup = false;
	}
	
	//Enter a code block
	public void moveIn(String loc, String line)
	{
		switch(loc)
		{
		default:
			this.hasBegun = true;
			this.location.push(loc);
			break;
		case "if":
			String expression;
			try {
				expression = line.split("\\(|\\)")[1];
			} catch (Exception e) {break;}
			this.ignoreGroup = !this.evaluateBoolean(expression);
			break;
		}
	}
	
	//Retieve current execution location
	public String getLocation()
	{
		return this.location.peek();
	}
	
	public boolean evaluateBoolean(String expression)
	{
		if(expression.equals("false"))
		{
			return false;
		}
		else if(expression.equals("true"))
		{
			return true;
		}else
		{
			Object[] args = null;
			return (boolean) this.executeMethod(expression.split("\\(")[0], args);
		}
	}
	
	public Object executeMethod(String fullName, Object[] args)
	{
		String region;
		String method;
		if(fullName.contains("."))
		{
			region = fullName.split("\\.")[fullName.split("\\.").length-2];
			method = fullName.split("\\.")[fullName.split("\\.").length-1];
			return ExecutionCore.coreMap.get(this.codeBase.getName()).areaMethods.get(region).get(method).execute(args);
		}else
		{
			return ExecutionCore.coreMap.get(this.codeBase.getName()).methods.get(fullName).execute(args);
		}
	}
}
