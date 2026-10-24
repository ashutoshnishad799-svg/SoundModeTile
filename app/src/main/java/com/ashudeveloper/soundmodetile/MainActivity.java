package com.ashudeveloper.soundmodetile;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int bg = Color.rgb(25, 29, 38), fg = Color.rgb(235, 239, 250);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL); root.setGravity(Gravity.CENTER);
        root.setPadding(28, 28, 28, 28); root.setBackgroundColor(bg);
        TextView title = new TextView(this); title.setText("Sound Mode Tile"); title.setTextSize(26); title.setTextColor(fg);
        TextView desc = new TextView(this); desc.setText("Quick Settings tile cycles: Sound → Vibrate → Mute → Sound.\n\nAndroid requires Do Not Disturb access for silent mode on many ROMs."); desc.setTextSize(16); desc.setTextColor(fg); desc.setPadding(0, 20, 0, 24);
        Button grant = new Button(this); grant.setText("Grant DND access");
        grant.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)));
        root.addView(title); root.addView(desc); root.addView(grant);
        setContentView(root);
    }
}
