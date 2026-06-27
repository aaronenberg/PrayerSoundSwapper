/*
 * Copyright (c) 2026, Rikten X <https://github.com/riktenx>
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PrayerSoundSwap
{
	ORIGINAL(-1, "Original"),
	CUSTOM_SOUND(-1, "Custom Sound"),
	PROTECT_ITEM(1982, "Protect Item"),
	IMPROVED_REFLEXES(2662, "Improved Reflexes"),
	CANCEL_PRAYER(2663, "Cancel Prayer"),
	CLARITY_OF_THOUGHT(2664, "Clarity of Thought"),
	EAGLE_EYE(2665, "Eagle Eye"),
	HAWK_EYE(2666, "Hawk Eye"),
	INCREDIBLE_REFLEXES(2667, "Incredible Reflexes"),
	MYSTIC_LORE(2668, "Mystic Lore"),
	MYSTIC_MIGHT(2669, "Mystic Might"),
	MYSTIC_WILL_AUGURY(2670, "Mystic Will / Augury"),
	PRAYER_BOOST(2671, "Prayer Boost"),
	PRAYER_DRAIN(2672, "Prayer Drain"),
	PRAYER_OFF(2673, "Prayer Off"),
	PRAYER_RECHARGE(2674, "Prayer Recharge"),
	PROTECT_FROM_MAGIC(2675, "Protect from Magic"),
	PROTECT_FROM_MELEE(2676, "Protect from Melee"),
	PROTECT_FROM_MISSILES(2677, "Protect from Missiles"),
	RAPID_HEAL(2678, "Rapid Heal"),
	RAPID_RESTORE_PRESERVE(2679, "Rapid Restore / Preserve"),
	REDEMPTION(2680, "Redemption"),
	REDEMPTION_HEAL(2681, "Redemption Heal"),
	RETRIBUTION(2682, "Retribution"),
	RETRIBUTION_TEST(2683, "Retribution Test"),
	ROCK_SKIN(2684, "Rock Skin"),
	SHARP_EYE_RIGOUR(2685, "Sharp Eye / Rigour"),
	SMITE(2686, "Smite"),
	STEEL_SKIN(2687, "Steel Skin"),
	BURST_OF_STRENGTH(2688, "Burst of Strength"),
	SUPERHUMAN_STRENGTH(2689, "Superhuman Strength"),
	THICK_SKIN(2690, "Thick Skin"),
	ULTIMATE_STRENGTH(2691, "Ultimate Strength"),
	PIETY(3825, "Piety"),
	CHIVALRY(3826, "Chivalry"),
	MYSTIC_VIGOUR(10100, "Mystic Vigour"),
	DEADEYE(10194, "Deadeye");

	private static final List<PrayerSoundSwap> PRAYER_SOUNDS = Arrays.stream(values())
		.filter(PrayerSoundSwap::isPrayerSound)
		.collect(Collectors.toUnmodifiableList());

	private final int soundId;
	private final String displayName;

	PrayerSoundSwap(int soundId, String displayName)
	{
		this.soundId = soundId;
		this.displayName = displayName;
	}

	public int getSoundId()
	{
		return soundId;
	}

	public boolean isPrayerSound()
	{
		return soundId != -1;
	}

	public static List<PrayerSoundSwap> prayerSounds()
	{
		return PRAYER_SOUNDS;
	}

	public static PrayerSoundSwap fromSoundId(int soundId)
	{
		for (PrayerSoundSwap prayerSound : PRAYER_SOUNDS)
		{
			if (prayerSound.soundId == soundId)
			{
				return prayerSound;
			}
		}

		return null;
	}

	@Override
	public String toString()
	{
		if (!isPrayerSound())
		{
			return displayName;
		}

		return displayName + " (" + soundId + ")";
	}
}
