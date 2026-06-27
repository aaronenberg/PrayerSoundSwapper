package com.prayersoundswapper;

import java.util.Map;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NativeSoundSwapMapTest
{
	@Test
	public void parsesNativeSoundSwapLines()
	{
		PrayerSoundSwapperPlugin plugin = new PrayerSoundSwapperPlugin();

		plugin.updateNativeSoundSwaps("2675,2\n2676,4");

		assertEquals(Map.of(2675, 2, 2676, 4), plugin.nativeSoundSwaps);
	}

	@Test
	public void ignoresEmptyLines()
	{
		PrayerSoundSwapperPlugin plugin = new PrayerSoundSwapperPlugin();

		plugin.updateNativeSoundSwaps("\n2675,2\n\n2676,4\n");

		assertEquals(Map.of(2675, 2, 2676, 4), plugin.nativeSoundSwaps);
	}

	@Test
	public void skipsDisallowedSourceIds()
	{
		PrayerSoundSwapperPlugin plugin = new PrayerSoundSwapperPlugin();

		plugin.updateNativeSoundSwaps("97,1\n2675,2\n2692,3\n2676,4");

		assertEquals(Map.of(2675, 2, 2676, 4), plugin.nativeSoundSwaps);
	}

	@Test
	public void skipsMalformedLines()
	{
		PrayerSoundSwapperPlugin plugin = new PrayerSoundSwapperPlugin();

		plugin.updateNativeSoundSwaps("2675\n2676,4,5\n2685,6");

		assertEquals(Map.of(2685, 6), plugin.nativeSoundSwaps);
	}

	@Test
	public void keepsFirstReplacementForDuplicateSourceIds()
	{
		PrayerSoundSwapperPlugin plugin = new PrayerSoundSwapperPlugin();

		plugin.updateNativeSoundSwaps("2675,1\n2675,2");

		assertEquals(Map.of(2675, 1), plugin.nativeSoundSwaps);
	}
}
