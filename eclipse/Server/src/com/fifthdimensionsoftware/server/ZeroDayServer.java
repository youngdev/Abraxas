package com.fifthdimensionsoftware.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ZeroDayServer 
{
	private static ZeroDayServer instance;
	private static final String version = "1.0.0";
	
	public static ZeroDayServer getInstance()
	{
		return instance;
	}
	
	public static void main(String[] args) throws Exception
	{
		UPnP.mapPort(8080, "TCP", "Zero Day Exploit");
		System.out.println("Zero Day Exploit server "+version);
		instance = new ZeroDayServer();
		instance.run();
	}
	
		public void run() throws Exception
	{
		@SuppressWarnings("resource")
		//It wants me to close it :/
		ServerSocket serverSocket = new ServerSocket(8080);
		while(true)
		{
			Socket clientSocket = serverSocket.accept();
		    DataInputStream iStream = new DataInputStream(clientSocket.getInputStream());
		    this.handlePacket(iStream.readByte(), iStream, clientSocket);
		}
	}
	
	public void handlePacket(int id, DataInputStream iStream, Socket socket) throws IOException
	{
		DataOutputStream oStream = new DataOutputStream(socket.getOutputStream());
		switch(id)
		{
		case 1:
			oStream.writeBoolean(true);
		}
	}
	
	public void loadUserMap()
	{
		
	}
}
