/*
 * Copyright (c) 2023, petertalbanese <https://github.com/petertalbanese>
 * Copyright (c) 2023, damencs <https://github.com/damencs>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.prayersoundswapper;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;

@ConfigGroup("prayer-sound-swapper")
public interface PrayerSoundSwapperConfig extends Config
{
	@ConfigSection(
		name = "Prayer Sound Swaps",
		description = "Settings for replacing prayer sounds with custom or native sounds",
		position = 1
	)
	String PRAYER_SOUND_SWAPS_SECTION = "prayerSoundSwaps";

	@ConfigItem(
		keyName = "protectItem",
		name = "Protect Item",
		description = "Sound to play instead of Protect Item (1982)",
		position = 1,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap protectItem()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "improvedReflexes",
		name = "Improved Reflexes",
		description = "Sound to play instead of Improved Reflexes (2662)",
		position = 2,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap improvedReflexes()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "cancelPrayer",
		name = "Cancel Prayer",
		description = "Sound to play instead of Cancel Prayer (2663)",
		position = 3,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap cancelPrayer()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "clarityOfThought",
		name = "Clarity of Thought",
		description = "Sound to play instead of Clarity of Thought (2664)",
		position = 4,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap clarityOfThought()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "eagleEye",
		name = "Eagle Eye",
		description = "Sound to play instead of Eagle Eye (2665)",
		position = 5,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap eagleEye()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "hawkEye",
		name = "Hawk Eye",
		description = "Sound to play instead of Hawk Eye (2666)",
		position = 6,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap hawkEye()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "incredibleReflexes",
		name = "Incredible Reflexes",
		description = "Sound to play instead of Incredible Reflexes (2667)",
		position = 7,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap incredibleReflexes()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "mysticLore",
		name = "Mystic Lore",
		description = "Sound to play instead of Mystic Lore (2668)",
		position = 8,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap mysticLore()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "mysticMight",
		name = "Mystic Might",
		description = "Sound to play instead of Mystic Might (2669)",
		position = 9,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap mysticMight()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "mysticWillAugury",
		name = "Augury",
		description = "Sound to play instead of Mystic Will / Augury (2670)",
		position = 10,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap mysticWillAugury()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "prayerBoost",
		name = "Prayer Boost",
		description = "Sound to play instead of Prayer Boost (2671)",
		position = 11,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap prayerBoost()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "prayerDrain",
		name = "Prayer Drain",
		description = "Sound to play instead of Prayer Drain (2672)",
		position = 12,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap prayerDrain()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "prayerOff",
		name = "Prayer Off",
		description = "Sound to play instead of Prayer Off (2673)",
		position = 13,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap prayerOff()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "prayerRecharge",
		name = "Prayer Recharge",
		description = "Sound to play instead of Prayer Recharge (2674)",
		position = 14,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap prayerRecharge()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "protectFromMagic",
		name = "Protect Magic",
		description = "Sound to play instead of Protect from Magic (2675)",
		position = 15,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap protectFromMagic()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "protectFromMelee",
		name = "Protect Melee",
		description = "Sound to play instead of Protect from Melee (2676)",
		position = 16,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap protectFromMelee()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "protectFromMissiles",
		name = "Protect Missiles",
		description = "Sound to play instead of Protect from Missiles (2677)",
		position = 17,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap protectFromMissiles()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "rapidHeal",
		name = "Rapid Heal",
		description = "Sound to play instead of Rapid Heal (2678)",
		position = 18,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap rapidHeal()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "rapidRestorePreserve",
		name = "Preserve",
		description = "Sound to play instead of Rapid Restore / Preserve (2679)",
		position = 19,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap rapidRestorePreserve()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "redemption",
		name = "Redemption",
		description = "Sound to play instead of Redemption (2680)",
		position = 20,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap redemption()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "redemptionHeal",
		name = "Redemption Heal",
		description = "Sound to play instead of Redemption Heal (2681)",
		position = 21,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap redemptionHeal()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "retribution",
		name = "Retribution",
		description = "Sound to play instead of Retribution (2682)",
		position = 22,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap retribution()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "retributionTest",
		name = "Retrib. Test",
		description = "Sound to play instead of Retribution Test (2683)",
		position = 23,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap retributionTest()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "rockSkin",
		name = "Rock Skin",
		description = "Sound to play instead of Rock Skin (2684)",
		position = 24,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap rockSkin()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "sharpEyeRigour",
		name = "Rigour",
		description = "Sound to play instead of Sharp Eye / Rigour (2685)",
		position = 25,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap sharpEyeRigour()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "smite",
		name = "Smite",
		description = "Sound to play instead of Smite (2686)",
		position = 26,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap smite()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "steelSkin",
		name = "Steel Skin",
		description = "Sound to play instead of Steel Skin (2687)",
		position = 27,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap steelSkin()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "burstOfStrength",
		name = "Burst of Strength",
		description = "Sound to play instead of Burst of Strength (2688)",
		position = 28,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap burstOfStrength()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "superhumanStrength",
		name = "Superhuman Strength",
		description = "Sound to play instead of Superhuman Strength (2689)",
		position = 29,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap superhumanStrength()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "thickSkin",
		name = "Thick Skin",
		description = "Sound to play instead of Thick Skin (2690)",
		position = 30,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap thickSkin()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "ultimateStrength",
		name = "Ultimate Strength",
		description = "Sound to play instead of Ultimate Strength (2691)",
		position = 31,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap ultimateStrength()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "piety",
		name = "Piety",
		description = "Sound to play instead of Piety (3825)",
		position = 32,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap piety()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "chivalry",
		name = "Chivalry",
		description = "Sound to play instead of Chivalry (3826)",
		position = 33,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap chivalry()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "mysticVigour",
		name = "Mystic Vigour",
		description = "Sound to play instead of Mystic Vigour (10100)",
		position = 34,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap mysticVigour()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "deadeye",
		name = "Deadeye",
		description = "Sound to play instead of Deadeye (10194)",
		position = 35,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default PrayerSoundSwap deadeye()
	{
		return PrayerSoundSwap.ORIGINAL;
	}

	@ConfigItem(
		keyName = "enableCustomSoundsVolume",
		name = "Enable Custom Volume",
		description = "Enable the ability to set the volume of custom sound effects",
		position = 100,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default boolean enableCustomSoundsVolume()
	{
		return false;
	}

	@Range(max = 100)
	@ConfigItem(
		keyName = "customSoundsVolume",
		name = "Custom Volume",
		description = "Sets the volume of the sound clips for custom sound effects",
		position = 101,
		section = PRAYER_SOUND_SWAPS_SECTION
	)
	default int customSoundsVolume()
	{
		return 65;
	}
}
