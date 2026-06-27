# Prayer Sound Swapper

Prayer Sound Swapper allows the user to replace prayer-related sound effects.

## Replaceable sound IDs

Unlike the original Sound Swapper plugin, Prayer Sound Swapper only replaces prayer sound IDs from the
static allowlist in `src/main/java/com/prayersoundswapper/PrayerSoundIds.java`. This is to allow
this plugin to be compliant when enabled in areas where the original SoundSwapper plugin cannot due to RuneLite
guidelines around swapping arbitrary sounds.

## Custom Sound Swaps

To replace a sound with a custom `.wav` file:

1. Add the sound ID to the appropriate list in the plugin configuration.
2. Place a `.wav` file with the sound ID as the file name in the `PrayerSoundSwapper` folder under your root RuneLite folder.

For example, `2675,2676,2677` would have files named `2675.wav`, `2676.wav`, and `2677.wav` in the folder.


## Native Sound Swaps

To replace one native sound ID with another native sound ID, enable the Native Sound Swaps section and populate `Native Sound Swaps`.

Each line represents one swap. The line format is a CSV with two values:

`originalPrayerSoundID,newSoundID`

Empty lines are ignored.

Example:

```text
2675,2676
2676,2677
2677,2675
```

## Blocked Sounds

Use `Blocked Sounds` to prevent specific prayer sounds from playing at all. Blocked sounds are limited to the same static allowlist above; non-prayer sound IDs in this setting are ignored.

## Setting Priority

In case the same Prayer sound ID is specified in custom swaps and native swaps and/or blocked, the priority is as follows:

1. Blocked
2. Custom Swaps
3. Native swaps

For example, if a sound is blocked, but also specified in custom swaps, it will be blocked. 

For another example, if a sound has a native swap, and also a custom swap, then the custom swap wins and the custom sound will play instead of the native swap sound.