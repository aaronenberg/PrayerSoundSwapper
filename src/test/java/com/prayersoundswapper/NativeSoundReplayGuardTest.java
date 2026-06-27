package com.prayersoundswapper;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NativeSoundReplayGuardTest
{
	@Test
	public void bypassesMarkedReplayOnce()
	{
		PrayerSoundSwapperPlugin plugin = new PrayerSoundSwapperPlugin();

		plugin.markReplayedNativeSound(PrayerSoundSwap.PROTECT_FROM_MAGIC.getSoundId());

		assertTrue(plugin.consumeReplayedNativeSound(PrayerSoundSwap.PROTECT_FROM_MAGIC.getSoundId()));
		assertFalse(plugin.consumeReplayedNativeSound(PrayerSoundSwap.PROTECT_FROM_MAGIC.getSoundId()));
	}

	@Test
	public void doesNotBypassUnmarkedSound()
	{
		PrayerSoundSwapperPlugin plugin = new PrayerSoundSwapperPlugin();

		assertFalse(plugin.consumeReplayedNativeSound(PrayerSoundSwap.PROTECT_FROM_MAGIC.getSoundId()));
	}

	@Test
	public void tracksReplayPerSoundId()
	{
		PrayerSoundSwapperPlugin plugin = new PrayerSoundSwapperPlugin();

		plugin.markReplayedNativeSound(PrayerSoundSwap.PROTECT_FROM_MELEE.getSoundId());

		assertFalse(plugin.consumeReplayedNativeSound(PrayerSoundSwap.PROTECT_FROM_MAGIC.getSoundId()));
		assertTrue(plugin.consumeReplayedNativeSound(PrayerSoundSwap.PROTECT_FROM_MELEE.getSoundId()));
	}
}
