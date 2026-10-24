# Sound Mode Tile

A native Android Quick Settings tile that cycles the phone ringer mode:

1. **Sound**
2. **Vibrate**
3. **Mute**

## Important limitations

- A standard Android `TileService` is one clickable tile. The app cannot place three independent buttons inside the system's expanded/resized tile; the ROM controls the tile layout. Resizing may only make this single tile wider.
- Android/ROM policy may require granting **Do Not Disturb access** before the app can switch to silent mode. Open the app and tap **Grant DND access**.
- This app does not require root on standard Android APIs. Root does not bypass every ROM-specific audio policy automatically.
- Test on your specific LineageOS build. Some ROMs may handle ringer modes differently.

## Add the tile

1. Install the APK.
2. Pull down Quick Settings twice and tap Edit.
3. Find **Sound Mode**, add it, and place it where you want.
4. If prompted, grant Do Not Disturb access in the app.

## Build locally

Requires JDK 17 and Gradle 8.7+ compatible with Android Gradle Plugin 8.6.1.

```bash
gradle assembleDebug
```

APK: `app/build/outputs/apk/debug/app-debug.apk`

## GitHub Actions

Push to `main` or `master`, or run **Build Sound Mode Tile APK** from Actions. Download the APK from the workflow run's Artifacts section.
