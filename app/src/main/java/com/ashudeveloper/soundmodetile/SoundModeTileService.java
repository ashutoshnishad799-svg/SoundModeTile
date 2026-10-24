package com.ashudeveloper.soundmodetile;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

public class SoundModeTileService extends TileService {
    @Override
    public void onStartListening() {
        super.onStartListening();
        refreshTile();
    }

    @Override
    public void onClick() {
        super.onClick();

        AudioManager audio = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audio == null) {
            Toast.makeText(this, "Audio service unavailable", Toast.LENGTH_SHORT).show();
            return;
        }

        int current = audio.getRingerMode();
        int next;
        if (current == AudioManager.RINGER_MODE_NORMAL) {
            next = AudioManager.RINGER_MODE_VIBRATE;
        } else if (current == AudioManager.RINGER_MODE_VIBRATE) {
            next = AudioManager.RINGER_MODE_SILENT;
        } else {
            next = AudioManager.RINGER_MODE_NORMAL;
        }

        // Android 6+ requires Notification Policy Access before an app can
        // switch the phone into silent mode on many ROMs.
        if (next == AudioManager.RINGER_MODE_SILENT
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationManager nm =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (nm != null && !nm.isNotificationPolicyAccessGranted()) {
                Toast.makeText(this,
                        "Grant Do Not Disturb access in Sound Mode Tile, then tap again.",
                        Toast.LENGTH_LONG).show();
                launchMainActivity();
                return;
            }
        }

        try {
            audio.setRingerMode(next);
        } catch (SecurityException e) {
            Toast.makeText(this,
                    "Permission denied. Grant Do Not Disturb access and try again.",
                    Toast.LENGTH_LONG).show();
            launchMainActivity();
            return;
        }

        refreshTile();
    }

    /**
     * Android 14+ (API 34+) rejects startActivityAndCollapse(Intent) for apps
     * targeting API 34 or later. Use the PendingIntent overload on those versions.
     */
    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this,
                    1001,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            startActivityAndCollapse(pendingIntent);
        } else {
            startActivityAndCollapse(intent);
        }
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        refreshTile();
    }

    private void refreshTile() {
        Tile tile = getQsTile();
        if (tile == null) return;

        AudioManager audio = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audio == null) return;

        int mode = audio.getRingerMode();
        if (mode == AudioManager.RINGER_MODE_NORMAL) {
            tile.setLabel("Sound Mode");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                tile.setSubtitle("Sound");
            }
            tile.setState(Tile.STATE_ACTIVE);
        } else if (mode == AudioManager.RINGER_MODE_VIBRATE) {
            tile.setLabel("Sound Mode");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                tile.setSubtitle("Vibrate");
            }
            tile.setState(Tile.STATE_ACTIVE);
        } else {
            tile.setLabel("Sound Mode");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                tile.setSubtitle("Mute");
            }
            tile.setState(Tile.STATE_INACTIVE);
        }
        tile.updateTile();
    }
}
