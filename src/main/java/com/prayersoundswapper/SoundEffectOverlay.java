/*
 * Copyright (c) 2018, WooxSolo <https://github.com/WooxSolo>
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.SoundEffectPlayed;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;

@Slf4j
class SoundEffectOverlay extends OverlayPanel
{
	private static final int MAX_LINES = 10;
	private static final Color COLOR_SOUND_EFFECT = Color.WHITE;
	private static final Color COLOR_ALLOWED = Color.GREEN;
	private static final Color COLOR_BLOCKED = Color.ORANGE;
	private static final Color COLOR_CUSTOM = Color.PINK;
	private static final Color COLOR_NATIVE = Color.CYAN;
	private static final Color COLOR_NOT_REPLACEABLE = Color.LIGHT_GRAY;

	private static final String ALLOWED = "Allowed";
	private static final String BLOCKED = "Blocked";
	private static final String CUSTOM = "Custom";
	private static final String NOT_REPLACEABLE = "Not replaceable";

	private final PrayerSoundSwapperPlugin plugin;
	private final PrayerSoundSwapperConfig config;

	@Inject
	SoundEffectOverlay(PrayerSoundSwapperPlugin plugin, PrayerSoundSwapperConfig config)
	{
		this.plugin = plugin;
		this.config = config;

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Sound Effects")
			.leftColor(Color.CYAN)
			.build());

		setClearChildren(false);
		setPosition(OverlayPosition.TOP_LEFT);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!config.debugSoundEffects())
		{
			return null;
		}

		return super.render(graphics);
	}

	@Subscribe
	public void onSoundEffectPlayed(SoundEffectPlayed event)
	{
		if (!config.debugSoundEffects())
		{
			return;
		}

		int soundId = event.getSoundId();
		String text = "G: " + soundId;
		String action = ALLOWED;
		Color actionColor = COLOR_ALLOWED;

		if (plugin.blockedSounds.contains(soundId))
		{
			action = BLOCKED;
			actionColor = COLOR_BLOCKED;
		}
		else if (config.soundEffects() && plugin.customSounds.containsKey(soundId))
		{
			action = CUSTOM;
			actionColor = COLOR_CUSTOM;
		}
		else if (config.nativeSoundIDSwapEnable() && plugin.nativeSoundSwaps.containsKey(soundId))
		{
			action = soundId + " -> " + plugin.nativeSoundSwaps.get(soundId);
			actionColor = COLOR_NATIVE;
		}
		else if (!PrayerSoundIds.isReplaceable(soundId))
		{
			action = NOT_REPLACEABLE;
			actionColor = COLOR_NOT_REPLACEABLE;
		}

		panelComponent.getChildren().add(LineComponent.builder()
			.left(text)
			.leftColor(COLOR_SOUND_EFFECT)
			.right(action)
			.rightColor(actionColor)
			.build());

		checkMaxLines();
	}

	private void checkMaxLines()
	{
		while (panelComponent.getChildren().size() > MAX_LINES)
		{
			panelComponent.getChildren().remove(1);
		}
	}

	public void resetLines()
	{
		panelComponent.getChildren().clear();
		panelComponent.getChildren().add(LineComponent.builder()
			.left("Sound Effects")
			.leftColor(Color.CYAN)
			.build());
	}
}
