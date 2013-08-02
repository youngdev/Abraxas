package com.fifthdimensionsoftware.game;

import java.io.File;

public class ExitThread extends Thread
{
	public void run()
	{
		//Save MailSystem
		ZeroDayExploit.globalMail.saveMail(new File(System.getProperty("java.io.tmpdir")+"/mail.bin"));
		System.err.println("I must go. My planet needs me.");
	}
}
