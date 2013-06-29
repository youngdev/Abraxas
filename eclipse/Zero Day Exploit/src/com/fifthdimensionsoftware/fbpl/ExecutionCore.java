package com.fifthdimensionsoftware.fbpl;

import java.io.*;
import java.util.*;

public class ExecutionCore {
	
	public static HashMap<String, ExecutionCore> coreMap = new HashMap<String, ExecutionCore>();
	public HashMap<String,Scanner> areaFiles = new HashMap<String,Scanner>();
	public HashMap<String, String> areaVals = new HashMap<String, String>();
	public HashMap<String, Method> methods = new HashMap<String, Method>();
	public HashMap<String, HashMap<String,Method>> areaMethods = new HashMap<String, HashMap<String,Method>>();
	public String[] types = {"null","num","boolean","string","array"};
	
	public void execute(File fbplCode, Object[] args) throws Exception
	{
		ExecutionCore.coreMap.put(fbplCode.getName(), this);
		String tmpLine = this.nextValidLine(this.getFile(fbplCode));
		if(tmpLine.startsWith("region "))
		{
			areaVals.put("region", tmpLine.substring(7));
		}
		tmpLine = this.nextValidLine(this.getFile(fbplCode));
		while(tmpLine.startsWith("extern "))
		{
			try {
				this.getFile(new File(tmpLine.substring(7).replace(".", "\\")));
				this.discoverMethods(new File(tmpLine.substring(7).replace(".", "\\")));
				if(this.methods.size() > 1 || this.methods.size() == 0)
					System.out.println("[FBPL]Loaded "+this.methods.size()+" methods from "+tmpLine.substring(7).replace(".", "\\"));
				else
					System.out.println("[FBPL]Loaded "+this.methods.size()+" method from "+tmpLine.substring(7).replace(".", "\\"));
				areaMethods.put(new File(tmpLine.substring(7).replace(".", "\\")).getName(), this.methods);
				this.methods = new HashMap<String, Method>();
			} catch (Exception e) {
				System.err.println("[FBPL]Could not load external library "+tmpLine.substring(7).replace(".", "\\"));
			}
			tmpLine = this.nextValidLine(this.getFile(fbplCode));
		}
		this.discoverMethods(fbplCode);
		if(this.methods.containsKey("main"))
		{
			this.methods.get("main").execute(args);
		}else
		{
			System.err.println("[FBPL]"+fbplCode.getName()+" does not contain a valid main method!");
			throw new Exception(fbplCode.getName()+" does not contain a valid main method!");
		}
	}
	
	public Scanner getFile(File file) throws FileNotFoundException
	{
		if(!file.getName().endsWith(".fbpl"))
		{
			file = new File(file.getAbsolutePath()+".fbpl");
		}
		if(areaFiles.containsKey(file.getName()))
		{
			return areaFiles.get(file.getName());
		}else
		{
			areaFiles.put(file.getName(), new Scanner(file));
			return this.getFile(file);
		}
	}
	
	public void discoverMethods(File fbplCode) throws Exception
	{
		Scanner cacheScanner = new Scanner(new File(fbplCode.getAbsolutePath()+".fbpl"));
		ArrayList<String> cache = new ArrayList<String>();
		while(cacheScanner.hasNextLine())
		{
			String line = cacheScanner.nextLine();
			cache.add(line);
			//System.err.println(line);
		}
		Object[] cacheArray = cache.toArray();
		int index = 0;
		for(Object line : cacheArray)
		{
			if(Arrays.asList(this.types).contains(((String) line).split(" ")[0]))
			{
				String type = ((String) line).split(" ")[0];
				String name = ((String) line).split(" |\\(")[1];
				switch(type)
				{
				case "null":
					this.methods.put(name, new Method(fbplCode, type, index, cacheArray, fbplCode.getName()));
					break;
				case "num":
					this.methods.put(name, new Method(fbplCode, type, index, cacheArray, fbplCode.getName()));
					break;
				case "boolean":
					this.methods.put(name, new Method(fbplCode, type, index, cacheArray, fbplCode.getName()));
					break;
				case "string":
					this.methods.put(name, new Method(fbplCode, type, index, cacheArray, fbplCode.getName()));
					break;
				case "array":
					this.methods.put(name, new Method(fbplCode, type, index, cacheArray, fbplCode.getName()));
					break;
				case "var":
					System.out.println("Variable "+name+" found");
					break;
				default:
					System.err.println("[FBPL]"+type+" is not a valid type, ignoring");
					break;
				}
			}
			index++;
		}
		cacheScanner.close();
	}
	
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
	
	public static void main(String[] args) throws Exception
	{
		new ExecutionCore().execute(new File("C:\\Users\\Andrew\\Desktop\\fbpl"), args);
	}
}
