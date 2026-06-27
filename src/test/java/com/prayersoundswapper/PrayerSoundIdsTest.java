package com.prayersoundswapper;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PrayerSoundIdsTest
{
	@Test
	public void allowsKnownPrayerSoundIds()
	{
		assertTrue(PrayerSoundIds.isReplaceable(1982));
		assertTrue(PrayerSoundIds.isReplaceable(2675));
		assertTrue(PrayerSoundIds.isReplaceable(2685));
		assertTrue(PrayerSoundIds.isReplaceable(3825));
		assertTrue(PrayerSoundIds.isReplaceable(10100));
	}

	@Test
	public void rejectsNonPrayerSoundIds()
	{
		assertFalse(PrayerSoundIds.isReplaceable(97));
		assertFalse(PrayerSoundIds.isReplaceable(2692));
	}
}
