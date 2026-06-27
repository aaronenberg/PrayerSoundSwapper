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
import java.util.HashMap;
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
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Prayer Sound Swapper",
	description = "Replace prayer sounds with either custom or native old school runescape sounds."
)
public class PrayerSoundSwapperPlugin extends Plugin
{
	private static final File SOUND_DIR = new File(RuneLite.RUNELITE_DIR, "PrayerSoundSwapper");
	private static final String CONFIG_GROUP = "prayer-sound-swapper";

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private PrayerSoundSwapperConfig config;

	public HashMap<Integer, Sound> customSounds = new HashMap<>();
	public Map<Integer, PrayerSoundSwap> configuredSoundSwaps = new HashMap<>();

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

		updateLists();
	}

	@Override
	protected void shutDown() throws Exception
	{
		reset();
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!CONFIG_GROUP.equals(event.getGroup()))
		{
			return;
		}

		updateConfiguredSoundSwaps();
	}

	void updateLists()
	{
		updateConfiguredSoundSwaps();
	}

	@Subscribe
	public void onSoundEffectPlayed(SoundEffectPlayed event)
	{
		int soundId = event.getSoundId();

		PrayerSoundSwap soundSwap = configuredSoundSwaps.get(soundId);
		if (soundSwap == PrayerSoundSwap.MUTE)
		{
			log.debug("muted prayer sound effect: {}", soundId);
			event.consume();
			return;
		}

		if (soundSwap == PrayerSoundSwap.CUSTOM_SOUND)
		{
			Sound customSound = customSounds.get(soundId);
			if (customSound != null)
			{
				event.consume();
				playCustomSound(customSound, config.enableCustomSoundsVolume() ? config.customSoundsVolume() : -1);
			}
			return;
		}

		if (soundSwap != null && soundSwap.isPrayerSound())
		{
			int replacementSoundId = soundSwap.getSoundId();
			log.debug("native prayer sound swap: {} -> {}", soundId, replacementSoundId);
			event.consume();
			clientThread.invokeLater(() -> client.playSoundEffect(replacementSoundId));
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

	private void updateConfiguredSoundSwaps()
	{
		customSounds.clear();
		Map<Integer, PrayerSoundSwap> updatedSoundSwaps = new HashMap<>();

		for (PrayerSoundSwap sourceSound : PrayerSoundSwap.prayerSounds())
		{
			PrayerSoundSwap selectedSound = getConfiguredSoundSwap(sourceSound);
			if (selectedSound == PrayerSoundSwap.ORIGINAL)
			{
				continue;
			}

			int sourceSoundId = sourceSound.getSoundId();
			updatedSoundSwaps.put(sourceSoundId, selectedSound);

			if (selectedSound == PrayerSoundSwap.CUSTOM_SOUND)
			{
				tryLoadSound(customSounds, Integer.toString(sourceSoundId), sourceSoundId);
			}
		}

		configuredSoundSwaps = updatedSoundSwaps;
	}

	PrayerSoundSwap getConfiguredSoundSwap(int soundId)
	{
		PrayerSoundSwap sourceSound = PrayerSoundSwap.fromSoundId(soundId);
		return sourceSound == null ? PrayerSoundSwap.ORIGINAL : getConfiguredSoundSwap(sourceSound);
	}

	private PrayerSoundSwap getConfiguredSoundSwap(PrayerSoundSwap sourceSound)
	{
		PrayerSoundSwap selectedSound;
		switch (sourceSound)
		{
			case PROTECT_ITEM:
				selectedSound = config.protectItem();
				break;
			case IMPROVED_REFLEXES:
				selectedSound = config.improvedReflexes();
				break;
			case CANCEL_PRAYER:
				selectedSound = config.cancelPrayer();
				break;
			case CLARITY_OF_THOUGHT:
				selectedSound = config.clarityOfThought();
				break;
			case EAGLE_EYE:
				selectedSound = config.eagleEye();
				break;
			case HAWK_EYE:
				selectedSound = config.hawkEye();
				break;
			case INCREDIBLE_REFLEXES:
				selectedSound = config.incredibleReflexes();
				break;
			case MYSTIC_LORE:
				selectedSound = config.mysticLore();
				break;
			case MYSTIC_MIGHT:
				selectedSound = config.mysticMight();
				break;
			case MYSTIC_WILL_AUGURY:
				selectedSound = config.mysticWillAugury();
				break;
			case PRAYER_BOOST:
				selectedSound = config.prayerBoost();
				break;
			case PRAYER_DRAIN:
				selectedSound = config.prayerDrain();
				break;
			case PRAYER_OFF:
				selectedSound = config.prayerOff();
				break;
			case PRAYER_RECHARGE:
				selectedSound = config.prayerRecharge();
				break;
			case PROTECT_FROM_MAGIC:
				selectedSound = config.protectFromMagic();
				break;
			case PROTECT_FROM_MELEE:
				selectedSound = config.protectFromMelee();
				break;
			case PROTECT_FROM_MISSILES:
				selectedSound = config.protectFromMissiles();
				break;
			case RAPID_HEAL:
				selectedSound = config.rapidHeal();
				break;
			case RAPID_RESTORE_PRESERVE:
				selectedSound = config.rapidRestorePreserve();
				break;
			case REDEMPTION:
				selectedSound = config.redemption();
				break;
			case REDEMPTION_HEAL:
				selectedSound = config.redemptionHeal();
				break;
			case RETRIBUTION:
				selectedSound = config.retribution();
				break;
			case RETRIBUTION_TEST:
				selectedSound = config.retributionTest();
				break;
			case ROCK_SKIN:
				selectedSound = config.rockSkin();
				break;
			case SHARP_EYE_RIGOUR:
				selectedSound = config.sharpEyeRigour();
				break;
			case SMITE:
				selectedSound = config.smite();
				break;
			case STEEL_SKIN:
				selectedSound = config.steelSkin();
				break;
			case BURST_OF_STRENGTH:
				selectedSound = config.burstOfStrength();
				break;
			case SUPERHUMAN_STRENGTH:
				selectedSound = config.superhumanStrength();
				break;
			case THICK_SKIN:
				selectedSound = config.thickSkin();
				break;
			case ULTIMATE_STRENGTH:
				selectedSound = config.ultimateStrength();
				break;
			case PIETY:
				selectedSound = config.piety();
				break;
			case CHIVALRY:
				selectedSound = config.chivalry();
				break;
			case MYSTIC_VIGOUR:
				selectedSound = config.mysticVigour();
				break;
			case DEADEYE:
				selectedSound = config.deadeye();
				break;
			default:
				selectedSound = PrayerSoundSwap.ORIGINAL;
				break;
		}

		return selectedSound == null ? PrayerSoundSwap.ORIGINAL : selectedSound;
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
		configuredSoundSwaps = new HashMap<>();
	}
}
