package com.prayersoundswapper;

import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BlockedSoundsTest
{
	@Test
	public void blockedSoundsAreLimitedToPrayerSoundIds()
	{
		PrayerSoundSwapperPlugin plugin = new PrayerSoundSwapperPlugin();

		plugin.blockedSounds = plugin.getReplaceableIds("97,2675,2692,3825", "Blocked Sounds");

		assertEquals(List.of(2675, 3825), plugin.blockedSounds);
	}
}
