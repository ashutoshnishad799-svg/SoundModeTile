package com.ashudeveloper.soundmodetile;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int bg = Color.rgb(25, 29, 38);
        int fg = Color.rgb(235, 239, 250);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(28, 28, 28, 28);
        root.setBackgroundColor(bg);

        TextView title = new TextView(this);
        title.setText("Sound Mode Tile");
        title.setTextSize(26);
        title.setTextColor(fg);

        TextView desc = new TextView(this);
        desc.setText("Tap the Quick Settings tile to cycle through:\\n\\nSound  →  Vibrate  →  Mute\\n\\nIf Mute does not work, grant Do Not Disturb access below, return to Quick Settings, and tap the tile again.");
        desc.setTextSize(16);
        desc.setTextColor(fg);
        desc.setPadding(0, 20, 0, 24);

        Button grant = new Button(this);
        grant.setText("Grant Do Not Disturb access");
        grant.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NotificationManager nm =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (nm != null && nm.isNotificationPolicyAccessGranted()) {
                    Toast.makeText(this, "Access already granted", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS));
                }
            }
        });

        root.addView(title);
        root.addView(desc);
        root.addView(grant);
        setContentView(root);
    }
}
