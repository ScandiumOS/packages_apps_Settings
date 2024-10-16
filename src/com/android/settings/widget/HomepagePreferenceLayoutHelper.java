/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.widget;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/** Helper for homepage preference to manage layout. */
public class HomepagePreferenceLayoutHelper {

    private Preference mPreference;
    private View mIcon;
    private View mText;
    private boolean mIconVisible = true;
    private int mIconPaddingStart = -1;
    private int mTextPaddingStart = -1;
    private int mSettingsStyle;
    private boolean mBlurStyleEnable;

    /** The interface for managing preference layouts on homepage */
    public interface HomepagePreferenceLayout {
        /** Returns a {@link HomepagePreferenceLayoutHelper}  */
        HomepagePreferenceLayoutHelper getHelper();
    }

    public HomepagePreferenceLayoutHelper(Preference preference) {
    	mPreference = preference;
    }

    public void setSettingsStyle(int style) {
    	mSettingsStyle = style;
    }

    public void setSettingsBlur(boolean blur) {
    	mBlurStyleEnable = blur;
    }

    /** Sets whether the icon should be visible */
    public void setIconVisible(boolean visible) {
        mIconVisible = visible;
        if (mIcon != null) {
            mIcon.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    /** Sets the icon padding start */
    public void setIconPaddingStart(int paddingStart) {
        mIconPaddingStart = paddingStart;
        if (mIcon != null && paddingStart >= 0) {
            mIcon.setPaddingRelative(paddingStart, mIcon.getPaddingTop(), mIcon.getPaddingEnd(),
                    mIcon.getPaddingBottom());
        }
    }

    /** Sets the text padding start */
    public void setTextPaddingStart(int paddingStart) {
        mTextPaddingStart = paddingStart;
        if (mText != null && paddingStart >= 0) {
            mText.setPaddingRelative(paddingStart, mText.getPaddingTop(), mText.getPaddingEnd(),
                    mText.getPaddingBottom());
        }
    }

    void onBindViewHolder(PreferenceViewHolder holder) {
        mIcon = holder.findViewById(R.id.icon_frame);
        mText = holder.findViewById(R.id.text_frame);
        mText.setSelected(true);
        View bg = holder.findViewById(R.id.nad_layout);
        if (bg instanceof LinearLayout && mBlurStyleEnable) {
            ((LinearLayout) bg).getBackground().setAlpha(100);
        }
        setIconVisible(mIconVisible);
        setIconPaddingStart(mIconPaddingStart);
        setTextPaddingStart(mTextPaddingStart);
        String key = mPreference.getKey();
        boolean showSummary = key.equals("main_toggle_wifi") || key.equals("tether_settings")
                || key.equals("top_level_location");
        TextView summary = (TextView) holder.findViewById(android.R.id.summary);
        if (mSettingsStyle == 3 && !showSummary) summary.setVisibility(View.GONE);
    }
}
