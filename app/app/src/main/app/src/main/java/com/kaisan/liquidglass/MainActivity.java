
package com.kaisan.liquidglass;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {

    LinearLayout root;

    GradientDrawable shape(int color, int radius) {
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(color);
        bg.setCornerRadius(radius);
        bg.setStroke(2, Color.argb(35,255,255,255));
        return bg;
    }

    TextView text(String value, int size) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(Color.WHITE);
        return t;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.rgb(20,35,60));
        getWindow().setNavigationBarColor(Color.rgb(15,25,45));

        ScrollView scroll = new ScrollView(this);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(22,35,22,30);

        GradientDrawable background = new GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            new int[]{
                Color.rgb(20,35,65),
                Color.rgb(65,75,115),
                Color.rgb(25,35,65)
            }
        );

        root.setBackground(background);

        TextView date = text("FRIDAY  •  YOUR SPACE", 13);
        root.addView(date);

        TextView clock = text("09:41", 58);
        clock.setTypeface(null, Typeface.BOLD);
        root.addView(clock);

        TextView greeting = text("Welcome back.", 25);
        greeting.setPadding(0,0,0,25);
        root.addView(greeting);

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(20,20,20,20);
        card.setBackground(shape(
            Color.argb(75,255,255,255),30
        ));

        TextView title = text("LIQUID GLASS", 18);
        title.setTypeface(null,Typeface.BOLD);

        TextView subtitle = text(
            "A fresh, beautiful Android experience.",15
        );

        card.addView(title);
        card.addView(subtitle);
        root.addView(card);

        TextView heading = text("Your applications",22);
        heading.setTypeface(null,Typeface.BOLD);
        heading.setPadding(0,30,0,15);
        root.addView(heading);

        PackageManager pm = getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> apps =
            pm.queryIntentActivities(intent,0);

        Collections.sort(apps,
            (a,b) -> a.loadLabel(pm).toString()
                .compareToIgnoreCase(b.loadLabel(pm).toString())
        );

        for (ResolveInfo app : apps) {

            String name = app.loadLabel(pm).toString();

            LinearLayout appCard = new LinearLayout(this);
            appCard.setGravity(Gravity.CENTER_VERTICAL);
            appCard.setPadding(15,12,15,12);
            appCard.setBackground(shape(
                Color.argb(55,255,255,255),22
            ));

            LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                    -1, -2
                );
            params.setMargins(0,5,0,5);
            appCard.setLayoutParams(params);

            ImageView icon = new ImageView(this);
            icon.setImageDrawable(app.loadIcon(pm));
            appCard.addView(icon,
                new LinearLayout.LayoutParams(48,48));

            TextView appName = text(name,17);
            appName.setPadding(18,0,0,0);
            appCard.addView(appName);

            appCard.setOnClickListener(v -> {
                Intent launch = pm.getLaunchIntentForPackage(
                    app.activityInfo.packageName
                );
                if (launch != null) startActivity(launch);
            });

            root.addView(appCard);
        }

        scroll.addView(root);
        setContentView(scroll);
    }
             }
