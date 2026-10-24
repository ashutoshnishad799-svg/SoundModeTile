package com.ashudeveloper.soundmodetile;

import android.app.StatusBarManager;
import android.content.Intent;
import android.media.AudioManager;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.app.NotificationManager;
import android.os.Build;

public class SoundModeTileService extends TileService {
    private AudioManager audio;

    @Override public void onStartListening() {
        super.onStartListening();
        audio = (AudioManager) getSystemService(AUDIO_SERVICE);
        refreshTile();
    }

    @Override public void onClick() {
        super.onClick();
        audio = (AudioManager) getSystemService(AUDIO_SERVICE);
        int current = audio.getRingerMode();
        int next = current == AudioManager.RINGER_MODE_NORMAL
                ? AudioManager.RINGER_MODE_VIBRATE
                : current == AudioManager.RINGER_MODE_VIBRATE
                    ? AudioManager.RINGER_MODE_SILENT
                    : AudioManager.RINGER_MODE_NORMAL;
        try {
            if (next == AudioManager.RINGER_MODE_SILENT && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (!nm.isNotificationPolicyAccessGranted()) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityAndCollapse(intent);
                    return;
                }
            }
            audio.setRingerMode(next);
        } catch (SecurityException ignored) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityAndCollapse(intent);
            return;
        }
        refreshTile();
    }

    private void refreshTile() {
        if (getQsTile() == null) return;
        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        int mode = am.getRingerMode();
        Tile tile = getQsTile();
        if (mode == AudioManager.RINGER_MODE_NORMAL) {
            tile.setLabel("Sound");
            tile.setSubtitle("Ringer on");
            tile.setState(Tile.STATE_ACTIVE);
        } else if (mode == AudioManager.RINGER_MODE_VIBRATE) {
            tile.setLabel("Vibrate");
            tile.setSubtitle("Vibration only");
            tile.setState(Tile.STATE_ACTIVE);
        } else {
            tile.setLabel("Mute");
            tile.setSubtitle("Silent mode");
            tile.setState(Tile.STATE_INACTIVE);
        }
        tile.updateTile();
    }
}
