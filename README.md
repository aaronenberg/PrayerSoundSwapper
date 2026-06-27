# Prayer Sound Swapper

Prayer Sound Swapper allows the user to replace prayer-related sound effects.

## Replaceable sound IDs

Unlike the original Sound Swapper plugin, Prayer Sound Swapper only replaces prayer sounds. This is
to allow this plugin to be compliant when enabled in areas where the original SoundSwapper plugin
cannot due to RuneLite guidelines around swapping arbitrary sounds.

The plugin also includes Interface Select 1 (`2266`) because it is used by the quick prayer orb.
RuneLite's sound event does not identify which widget triggered interface sounds, so swapping or
muting Interface Select 1 applies to every source of that same sound ID.

## Prayer Sound Swaps

Each prayer sound has its own dropdown. Choose:

- `Original` to leave it unchanged.
- `Mute` to prevent that prayer sound from playing.
- `Custom Sound` to use a `.wav` file named after the original sound ID.
- Any listed prayer sound to play that native prayer sound instead.

## Custom Sound Swaps

To replace a sound with a custom `.wav` file:

1. Set that prayer sound's dropdown to `Custom Sound` in the plugin configuration.
2. Place a `.wav` file with the same name in the `PrayerSoundSwapper` folder in your root RuneLite folder.

For example, setting `Protect from Magic (2675)` to `Custom Sound` uses `2675.wav`.
