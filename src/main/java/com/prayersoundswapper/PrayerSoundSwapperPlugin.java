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

import com.google.inject.Provides;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.SoundEffectPlayed;
import net.runelite.client.RuneLite;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.Text;

@Slf4j
@PluginDescriptor(
	name = "Prayer Sound Swapper",
	description = "Replace prayer sounds with either custom or native old school runescape sounds."
)
public class PrayerSoundSwapperPlugin extends Plugin
{
	private static final File SOUND_DIR = new File(RuneLite.RUNELITE_DIR, "PrayerSoundSwapper");
	private static final String CONFIG_GROUP = "prayer-sound-swapper";
	private static final String CONFIG_KEY_CUSTOM_SOUNDS = "customSounds";
	private static final String CONFIG_KEY_BLOCKED_SOUNDS = "blockedSounds";
	private static final String CONFIG_KEY_NATIVE_SOUND_SWAPS = "nativeSoundSwaps";
	private static final String LEGACY_CONFIG_KEY_NATIVE_SOUND_IDS_TO_REPLACE = "nativeSoundIDsToReplace";
	private static final String LEGACY_CONFIG_KEY_NATIVE_SOUND_ID_REPLACEMENTS = "nativeSoundIDReplacements";

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private EventBus eventBus;

	@Inject
	private PrayerSoundSwapperConfig config;

	@Inject
	private ConfigManager configManager;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private SoundEffectOverlay soundEffectOverlay;

	public HashMap<Integer, Sound> customSounds = new HashMap<>();
	public List<Integer> blockedSounds = new ArrayList<>();
	public Map<Integer, Integer> nativeSoundSwaps = new HashMap<>();

	@Provides
	PrayerSoundSwapperConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PrayerSoundSwapperConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		try
		{
			Files.createDirectories(SOUND_DIR.toPath());
		}
		catch (SecurityException securityException)
		{
			log.error("Attempted to create PrayerSoundSwapper directory and a security exception prompted a fault", securityException);
		}

		migrateNativeSoundSwapConfig();
		updateLists();

		overlayManager.add(soundEffectOverlay);
		eventBus.register(soundEffectOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		eventBus.unregister(soundEffectOverlay);
		overlayManager.remove(soundEffectOverlay);
		reset();
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!CONFIG_GROUP.equals(event.getGroup()))
		{
			return;
		}

		switch (event.getKey())
		{
			case CONFIG_KEY_CUSTOM_SOUNDS:
				updateSoundList(customSounds, event.getNewValue(), "Custom Sounds");
				break;
			case CONFIG_KEY_BLOCKED_SOUNDS:
				blockedSounds = getReplaceableIds(event.getNewValue(), "Blocked Sounds");
				break;
			case CONFIG_KEY_NATIVE_SOUND_SWAPS:
				updateNativeSoundSwaps(event.getNewValue());
				break;
			default:
				break;
		}

		soundEffectOverlay.resetLines();
	}

	void updateLists()
	{
		if (!config.customSounds().isEmpty())
		{
			updateSoundList(customSounds, config.customSounds(), "Custom Sounds");
		}

		if (!config.blockedSounds().isEmpty())
		{
			blockedSounds = getReplaceableIds(config.blockedSounds(), "Blocked Sounds");
		}

		updateNativeSoundSwaps(config.nativeSoundSwaps());
	}

	@Subscribe
	public void onSoundEffectPlayed(SoundEffectPlayed event)
	{
		int soundId = event.getSoundId();

		if (blockedSounds.contains(soundId))
		{
			log.debug("blocked prayer sound effect: {}", soundId);
			event.consume();
			return;
		}

		if (config.soundEffects() && customSounds.containsKey(soundId))
		{
			log.debug("playing custom sound effect for prayer: {}", soundId);
			event.consume();
			playCustomSound(customSounds.get(soundId), config.enableCustomSoundsVolume() ? config.customSoundsVolume() : -1);
			return;
		}

		if (config.nativeSoundIDSwapEnable())
		{
			Integer replacementSoundId = nativeSoundSwaps.get(soundId);

			if (replacementSoundId != null)
			{
				log.debug("swapping prayer sound effect: {} with native sound effect: {}", soundId, replacementSoundId);
				event.consume();
				clientThread.invokeLater(() -> client.playSoundEffect(replacementSoundId));
			}
		}
	}

	private boolean tryLoadSound(Map<Integer, Sound> sounds, String soundName, Integer soundId)
	{
		File soundFile = new File(SOUND_DIR, soundName + ".wav");

		if (!soundFile.exists())
		{
			return false;
		}

		try (
			InputStream fileStream = new BufferedInputStream(new FileInputStream(soundFile));
			AudioInputStream stream = AudioSystem.getAudioInputStream(fileStream)
		)
		{
			int streamLen = (int) stream.getFrameLength() * stream.getFormat().getFrameSize();
			byte[] bytes = new byte[streamLen];
			int bytesRead = 0;
			while (bytesRead < streamLen)
			{
				int read = stream.read(bytes, bytesRead, streamLen - bytesRead);
				if (read == -1)
				{
					break;
				}

				bytesRead += read;
			}

			if (bytesRead != streamLen)
			{
				log.warn("Unable to fully read custom sound {}", soundName);
				return false;
			}

			sounds.put(soundId, new Sound(bytes, stream.getFormat(), streamLen));
			return true;
		}
		catch (UnsupportedAudioFileException | IOException e)
		{
			log.warn("Unable to load custom sound " + soundName, e);
		}

		return false;
	}

	private void updateSoundList(Map<Integer, Sound> sounds, String configText, String settingName)
	{
		sounds.clear();

		for (int id : getReplaceableIds(configText, settingName))
		{
			tryLoadSound(sounds, Integer.toString(id), id);
		}
	}

	List<Integer> getReplaceableIds(String configText, String settingName)
	{
		List<Integer> ids = getIds(configText);
		List<Integer> replaceableIds = new ArrayList<>();

		for (int id : ids)
		{
			if (PrayerSoundIds.isReplaceable(id))
			{
				replaceableIds.add(id);
			}
			else
			{
				log.warn("Ignoring sound ID {} in {} because it is not in the PrayerSoundIds allowlist", id, settingName);
			}
		}

		return replaceableIds;
	}

	void updateNativeSoundSwaps(String configText)
	{
		Map<Integer, Integer> updatedNativeSoundSwaps = new HashMap<>();

		if (configText == null || configText.isEmpty())
		{
			nativeSoundSwaps = updatedNativeSoundSwaps;
			return;
		}

		configText.lines()
			.map(String::trim)
			.filter(line -> !line.isEmpty())
			.forEach(line -> addNativeSoundSwap(updatedNativeSoundSwaps, line));

		nativeSoundSwaps = updatedNativeSoundSwaps;
	}

	private void addNativeSoundSwap(Map<Integer, Integer> updatedNativeSoundSwaps, String line)
	{
		List<String> values = Text.fromCSV(line);
		if (values.size() != 2)
		{
			log.warn("Invalid native sound swap line '{}'. Expected exactly two comma-separated IDs", line);
			return;
		}

		Integer id = parseId(values.get(0), line);
		Integer replacementId = parseId(values.get(1), line);
		if (id == null || replacementId == null)
		{
			return;
		}

		if (!PrayerSoundIds.isReplaceable(id))
		{
			log.warn("Ignoring sound ID {} in Native Sound Swaps because it is not in the PrayerSoundIds allowlist", id);
			return;
		}

		if (updatedNativeSoundSwaps.containsKey(id))
		{
			log.warn("Ignoring duplicate sound ID {} in Native Sound Swaps", id);
			return;
		}

		updatedNativeSoundSwaps.put(id, replacementId);
	}

	private Integer parseId(String value, String configText)
	{
		try
		{
			return Integer.parseInt(value.trim());
		}
		catch (NumberFormatException e)
		{
			log.warn("Invalid id when parsing {}: {}", configText, value);
			return null;
		}
	}

	private List<Integer> getIds(String configText)
	{
		if (configText == null || configText.isEmpty())
		{
			return List.of();
		}

		List<Integer> ids = new ArrayList<>();
		for (String s : Text.fromCSV(configText))
		{
			try
			{
				ids.add(Integer.parseInt(s));
			}
			catch (NumberFormatException e)
			{
				log.warn("Invalid id when parsing {}: {}", configText, s);
			}
		}

		return ids;
	}

	private void migrateNativeSoundSwapConfig()
	{
		String nativeSoundSwaps = configManager.getConfiguration(CONFIG_GROUP, CONFIG_KEY_NATIVE_SOUND_SWAPS);
		if (nativeSoundSwaps != null && !nativeSoundSwaps.trim().isEmpty())
		{
			return;
		}

		String legacyIdsToReplace = configManager.getConfiguration(CONFIG_GROUP, LEGACY_CONFIG_KEY_NATIVE_SOUND_IDS_TO_REPLACE);
		String legacyReplacementIds = configManager.getConfiguration(CONFIG_GROUP, LEGACY_CONFIG_KEY_NATIVE_SOUND_ID_REPLACEMENTS);
		if (isBlank(legacyIdsToReplace) || isBlank(legacyReplacementIds))
		{
			return;
		}

		List<Integer> idsToReplace = getIds(legacyIdsToReplace);
		List<Integer> replacementIds = getIds(legacyReplacementIds);
		List<String> migratedSwaps = new ArrayList<>();

		for (int i = 0; i < idsToReplace.size() && i < replacementIds.size(); i++)
		{
			migratedSwaps.add(idsToReplace.get(i) + "," + replacementIds.get(i));
		}

		if (!migratedSwaps.isEmpty())
		{
			configManager.setConfiguration(CONFIG_GROUP, CONFIG_KEY_NATIVE_SOUND_SWAPS, String.join(System.lineSeparator(), migratedSwaps));
		}
	}

	private static boolean isBlank(String value)
	{
		return value == null || value.trim().isEmpty();
	}

	private void playCustomSound(Sound sound, int volume)
	{
		Clip clip = null;
		boolean started = false;

		try
		{
			clip = AudioSystem.getClip();
			Clip soundClip = clip;
			soundClip.addLineListener(event ->
			{
				if (event.getType() == LineEvent.Type.STOP)
				{
					soundClip.close();
				}
			});
			soundClip.open(sound.getFormat(), sound.getBytes(), 0, sound.getNumBytes());

			if (volume != -1 && soundClip.isControlSupported(FloatControl.Type.MASTER_GAIN))
			{
				FloatControl control = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);
				float gain = Math.max(control.getMinimum(), Math.min(control.getMaximum(), (float) (volume / 2 - 45)));
				control.setValue(gain);
			}

			soundClip.setFramePosition(0);
			soundClip.start();
			started = true;
		}
		catch (IllegalArgumentException | LineUnavailableException e)
		{
			if (!started && clip != null)
			{
				clip.close();
			}

			log.warn("Failed to play custom sound", e);
		}
	}

	private void reset()
	{
		customSounds = new HashMap<>();
		blockedSounds = new ArrayList<>();
		nativeSoundSwaps = new HashMap<>();
		soundEffectOverlay.resetLines();
	}
}
