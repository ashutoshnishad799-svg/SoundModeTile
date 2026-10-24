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


## Release APK and system-app module
GitHub Actions builds a signed release APK and a Magisk/KernelSU-compatible module ZIP. Install the APK normally for testing. To make it a system app, install the module ZIP from your root manager and reboot. The module places the app in `/system/app` (not `priv-app`, because it does not request privileged permissions). A system app still needs to be tested on the target ROM.

The release build uses the Android debug signing key for convenience/testing. For a public production release, configure a private release keystore and protect its secrets in GitHub Actions.


## Important Quick Settings limitation

Android's public `TileService` API does not expose drag/swipe touch events inside a Quick Settings tile, so a custom tile cannot contain a true left/right slider like a built-in system tile. This build uses one tap to cycle Sound → Vibrate → Mute. On Android versions/ROMs that require Notification Policy Access for silent mode, grant it in the app and tap the tile again.


## Android 14+ crash fix

The tile now uses `TileService.startActivityAndCollapse(PendingIntent)` on API 34+; the legacy Intent overload is used only on API 26–33. The manifest declares `ACCESS_NOTIFICATION_POLICY`, which is required before the user can grant Do Not Disturb policy access for Silent/Mute mode. After granting access, return to Quick Settings and tap the tile again.
