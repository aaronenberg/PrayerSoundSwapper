package com.prayersoundswapper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PrayerSoundSwapTest
{
	@Test
	public void findsPrayerSoundById()
	{
		assertEquals(PrayerSoundSwap.PROTECT_FROM_MAGIC, PrayerSoundSwap.fromSoundId(2675));
		assertEquals(PrayerSoundSwap.MYSTIC_VIGOUR, PrayerSoundSwap.fromSoundId(10100));
		assertNull(PrayerSoundSwap.fromSoundId(97));
	}

	@Test
	public void excludesSpecialOptionsFromPrayerSoundList()
	{
		assertTrue(PrayerSoundSwap.prayerSounds().contains(PrayerSoundSwap.PROTECT_FROM_MAGIC));
		assertTrue(!PrayerSoundSwap.prayerSounds().contains(PrayerSoundSwap.ORIGINAL));
		assertTrue(!PrayerSoundSwap.prayerSounds().contains(PrayerSoundSwap.MUTE));
		assertTrue(!PrayerSoundSwap.prayerSounds().contains(PrayerSoundSwap.CUSTOM_SOUND));
	}
}
