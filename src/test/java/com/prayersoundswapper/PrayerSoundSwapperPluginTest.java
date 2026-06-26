package com.prayersoundswapper;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PrayerSoundSwapperPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PrayerSoundSwapperPlugin.class);
		RuneLite.main(args);
	}
}
