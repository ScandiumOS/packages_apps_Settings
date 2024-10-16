package com.android.settings.dashboard;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Build;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import static android.content.ContentValues.TAG;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
//import com.android.settingslib.DeviceInfoUtils;

public class TopMenu extends Preference {

    private long lastTouchTime = 0;
    private long currentTouchTime = 0;

    public TopMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        boolean gridStyle = Settings.System.getInt(context.getContentResolver(),
                    Settings.System.SETTINGS_STYLE, 0) == 2;
        setLayoutResource(context.getResources().
                getIdentifier(gridStyle ? "layout/scandium_top_menu_grid" : "layout/scandium_top_menu", null, context.getPackageName()));

    }


    // system prop
    public static String getSystemProperty(String key) {
        String value = null;

        try {
            value = (String) Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class).invoke(null, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    // device name
    private static void setInfo(String prop, TextView textview) {
        if (TextUtils.isEmpty(getSystemProperty(prop))) {
            textview.setText("Unknown");
        } else {
            textview.setText(getSystemProperty(prop));
        }

    }
    /**
     * public static String getDeviceModel() {
     * FutureTask<String> msvSuffixTask = new FutureTask<>(() -> DeviceInfoUtils.getMsvSuffix());
     * <p>
     * msvSuffixTask.run();
     * try {
     * // Wait for msv suffix value.
     * final String msvSuffix = msvSuffixTask.get();
     * return Build.MODEL + msvSuffix;
     * } catch (ExecutionException e) {
     * Log.e(TAG, "Execution error, so we only show model name");
     * } catch (InterruptedException e) {
     * Log.e(TAG, "Interruption error, so we only show model name");
     * }
     * // If we can't get an msv suffix value successfully,
     * // it's better to return model name.
     * return Build.MODEL;
     * }
     */

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        final boolean selectable = false;
        final Context context = getContext();

        /**TextView mBatteryText = holder.itemView.findViewById(context.getResources().
                getIdentifier("id/battery_text", null, context.getPackageName()));

        //TextView deviceName = holder.itemView.findViewById(context.getResources().
              //  getIdentifier("id/device_name", null, context.getPackageName()));

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get the battery scale
                int mProgressStatus = 0;
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
                int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
                boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;

                // get the battery level
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

                // Calculate the battery charged percentage
                float percentage = level / (float) scale;
                // Update the progress bar to display current battery charged percentage
                mProgressStatus = (int) ((percentage) * 100);

                // Show the battery charged percentage text
                if (isCharging) {
                    if (usbCharge) {
                        mBatteryText.setText("⚡ USB " + mProgressStatus + "%");
                    } else if (acCharge) {
                        mBatteryText.setText("⚡ AC " + mProgressStatus + "%");
                    } else {
                        mBatteryText.setText("Battery " + mProgressStatus + "%");
                    }
                } else {
                    mBatteryText.setText("Battery " + mProgressStatus + "%");

                }
            }

        }, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        **/

        holder.itemView.setFocusable(selectable);
        
        holder.setDividerAllowedAbove(false);
        holder.setDividerAllowedBelow(false);

        LinearLayout mAbout = holder.itemView.findViewById(context.getResources().
                getIdentifier("id/about", null, context.getPackageName()));
        
        mAbout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$MyDeviceInfoActivity"));
                context.startActivity(intent);
            }
        });

        LinearLayout mScandium = holder.itemView.findViewById(context.getResources().
                getIdentifier("id/scandiumparts", null, context.getPackageName()));
        
        mScandium.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$ScandiumPartsActivity"));
                context.startActivity(intent);
            }
        });

        /**LinearLayout mWifi = holder.itemView.findViewById(context.getResources().
                getIdentifier("id/wifi", null, context.getPackageName()));
        mWifi.setClickable(true);
        mWifi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$NetworkDashboardActivity"));
                context.startActivity(intent);
            }
        });*/

        LinearLayout mSystem = holder.itemView.findViewById(context.getResources().
                getIdentifier("id/system", null, context.getPackageName()));
        
        mSystem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$SystemDashboardActivity"));
                context.startActivity(intent);
            }
        });

        LinearLayout mScandiumTop = holder.itemView.findViewById(context.getResources().
                getIdentifier("id/scandium", null, context.getPackageName()));

        boolean blurStyleEnable = Settings.System.getIntForUser(context.getContentResolver(),
                Settings.System.BLUR_STYLE_PREFERENCE_KEY, 0, UserHandle.USER_CURRENT) == 1;
        if (blurStyleEnable) {
            Drawable ScandiumTop = mScandiumTop.getBackground();
            if (ScandiumTop != null) ScandiumTop.setAlpha(100);
            Drawable system = mSystem.getBackground();
            if (system != null) system.setAlpha(100);
            Drawable about = mAbout.getBackground();
            if (about != null) about.setAlpha(100);
        }

        boolean gridStyle = Settings.System.getInt(context.getContentResolver(),
                Settings.System.SETTINGS_STYLE, 0) == 2;

        ImageView aboutIcon = holder.itemView.findViewById(context.getResources().
                getIdentifier("id/about_icon", null, context.getPackageName()));
        aboutIcon.setVisibility(gridStyle ? View.INVISIBLE : View.VISIBLE);
        ImageView systemIcon = holder.itemView.findViewById(context.getResources().
                getIdentifier("id/system_icon", null, context.getPackageName()));
        systemIcon.setVisibility(gridStyle ? View.INVISIBLE : View.VISIBLE);
        LinearLayout textFrame = holder.itemView.findViewById(context.getResources().
                getIdentifier("id/text_frame", null, context.getPackageName()));
        textFrame.setSelected(true);
        mAbout.setSelected(true);
        mSystem.setSelected(true);

        //deviceName.setText(getDeviceModel());
        //setInfo("ro.product.model", deviceName);
    }

}
