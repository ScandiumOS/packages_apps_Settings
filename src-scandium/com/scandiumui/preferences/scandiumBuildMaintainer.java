package com.scandiumui.preferences;

import android.content.Context;
import android.os.SystemProperties;
import androidx.annotation.VisibleForTesting;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

public class scandiumBuildMaintainer extends BasePreferenceController {

    @VisibleForTesting

    public scandiumBuildMaintainer(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    // scandium Build Status
    /* This code was taken from BootleggersROM
       https://github.com/BootleggersROM/packages_apps_BootlegDumpster
    */
    @Override
    public CharSequence getSummary() {

        String scandiumMaintainer = SystemProperties.get("ro.scandium.maintainer", "unofficial maintainer");
        String scandiumBuildStatusSummary;

        if(scandiumMaintainer.equalsIgnoreCase("unofficial maintainer") || scandiumMaintainer.equalsIgnoreCase(null)){
            scandiumBuildStatusSummary = mContext.getString(R.string.scandium_build_unmaintained_summary);
        } else {
            scandiumBuildStatusSummary = mContext.getString(R.string.scandium_build_maintained_summary, scandiumMaintainer);
        }
	    return scandiumBuildStatusSummary;
    }
}
