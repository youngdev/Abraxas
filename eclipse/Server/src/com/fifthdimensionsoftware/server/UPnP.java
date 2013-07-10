package com.fifthdimensionsoftware.server;

import java.io.IOException;
import java.net.InetAddress;

import javax.xml.parsers.ParserConfigurationException;

import org.wetorrent.upnp.GatewayDevice;
import org.wetorrent.upnp.GatewayDiscover;
import org.wetorrent.upnp.PortMappingEntry;
import org.xml.sax.SAXException;

public class UPnP 
{
	public static GatewayDevice router;
	public static String externalIP;
	
	static
	{
		GatewayDiscover discover = new GatewayDiscover();
			try {
				discover.discover();
			} catch (IOException
					| SAXException | ParserConfigurationException e1) {
				e1.printStackTrace();
			}
		router = discover.getValidGateway();
		try {
			externalIP = router.getExternalIPAddress();
		} catch (IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean mapPort(int port, String protocol, String desc) throws Exception
	{
		GatewayDevice d = router;
		
		if(d == null)
			return false;

		InetAddress localAddress = d.getLocalAddress();
		PortMappingEntry portMapping = new PortMappingEntry();

		if (!d.getSpecificPortMappingEntry(port,protocol,portMapping)) {

		    return d.addPortMapping(port,port,localAddress.getHostAddress(),protocol,desc);
		    
		} else {
			return false;
		}
	}
}
