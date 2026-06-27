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
		name = "Custom Sound Swaps",
		description = "Settings for replacing prayer sounds with custom sounds",
		position = 1
	)
	String SOUND_EFFECTS_SECTION = "soundEffects";

	@ConfigSection(
		name = "Native Sound Swaps",
		description = "Settings for replacing prayer sounds with native, in-game sounds",
		position = 2
	)
	String SIMPLE_ID_SWAPS_SECTION = "nativeSoundIdSwaps";

	@ConfigSection(
		name = "Blocked Sounds",
		description = "Settings for blocking prayer sounds entirely",
		position = 3
	)
	String BLOCKED_SOUNDS_SECTION = "blockedSounds";

	@ConfigItem(
		keyName = "soundEffects",
		name = "Enable Custom Sound Swaps",
		description = "Swap prayer sounds with custom sounds",
		position = 1,
		section = SOUND_EFFECTS_SECTION
	)
	default boolean soundEffects()
	{
		return false;
	}

	@ConfigItem(
		keyName = "customSounds",
		name = "Custom Sounds",
		description = "Prayer sounds to replace with your own custom sounds from .wav files. (Comma separated IDs)",
		position = 2,
		section = SOUND_EFFECTS_SECTION
	)
	default String customSounds()
	{
		return "";
	}

	@ConfigItem(
		keyName = "blockedSounds",
		name = "Blocked Sounds",
		description = "Prayer sounds that should not play at all. (Comma-separated IDs)<br><br>"
			+ "If a sound is set to be blocked and swapped, it will be blocked.",
		position = 1,
		section = BLOCKED_SOUNDS_SECTION
	)
	default String blockedSounds()
	{
		return "";
	}

	@ConfigItem(
		keyName = "enableCustomSoundsVolume",
		name = "Enable Custom Volume",
		description = "Enable the ability to set the volume of custom sound effects",
		position = 3,
		section = SOUND_EFFECTS_SECTION
	)
	default boolean enableCustomSoundsVolume()
	{
		return false;
	}

	@Range(max = 100)
	@ConfigItem(
		keyName = "customSoundsVolume",
		name = "Custom Volume",
		description = "Sets the volume of the sound clips for sound effects",
		position = 4,
		section = SOUND_EFFECTS_SECTION
	)
	default int customSoundsVolume()
	{
		return 65;
	}

	@ConfigItem(
		keyName = "debugSoundEffects",
		name = "Debug Sounds Effects",
		description = "Display the sound effects that play (max 10 lines displayed)<br><br>" +
			"White: Sound Effect (G)",
		position = 98
	)
	default boolean debugSoundEffects()
	{
		return false;
	}

	@ConfigItem(
		keyName = "nativeSoundIDSwapEnable",
		name = "Enable Native Sound Swaps",
		description = "Enables replacement of prayer sounds with another native sound",
		position = 1,
		section = SIMPLE_ID_SWAPS_SECTION
	)
	default boolean nativeSoundIDSwapEnable()
	{
		return false;
	}

	@ConfigItem(
		keyName = "nativeSoundSwaps",
		name = "Native Sound Swaps",
		description = "One native sound swap per line. Each line must contain two comma-separated IDs:<br>"
			+ "original prayer sound ID, native sound ID to play<br><br>"
			+ "Example:<br>2675,2676",
		position = 2,
		section = SIMPLE_ID_SWAPS_SECTION
	)
	default String nativeSoundSwaps()
	{
		return "";
	}
}
