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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class PrayerSoundIds
{
	public static final List<Integer> REPLACEABLE_SOUND_IDS = List.of(
		1982, // protect_items
		2662, // improved_reflexes
		2663, // cancel_prayer
		2664, // clarity
		2665, // eagle_eye
		2666, // hawk_eye
		2667, // incredible_reflexes
		2668, // mystic_lore
		2669, // mystic_might
		2670, // mystic_will; also used for Augury
		2671, // prayer_boost
		2672, // prayer_drain
		2673, // prayer_off
		2674, // prayer_recharge
		2675, // protect_from_magic
		2676, // protect_from_melee
		2677, // protect_from_missiles
		2678, // rapid_heal
		2679, // rapid_restore; also used for Preserve
		2680, // redemption
		2681, // redemption_heal
		2682, // retribution
		2683, // retribution_test
		2684, // rock_skin
		2685, // sharp_eye; also used for Rigour
		2686, // smite
		2687, // steel_skin
		2688, // strength_burst
		2689, // superhuman_strength
		2690, // thick_skin
		2691, // ultimate_strength
		3825, // kr_piety
		3826, // kr_chivalry
		10100, // mystic_vigour
		10194 // deadeye
	);

	private static final Set<Integer> REPLACEABLE_SOUND_ID_SET =
		Collections.unmodifiableSet(new HashSet<>(REPLACEABLE_SOUND_IDS));

	private PrayerSoundIds()
	{
	}

	public static boolean isReplaceable(int soundId)
	{
		return REPLACEABLE_SOUND_ID_SET.contains(soundId);
	}

	public static String toCsv()
	{
		return REPLACEABLE_SOUND_IDS.stream()
			.map(String::valueOf)
			.collect(Collectors.joining(","));
	}
}
