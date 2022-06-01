package com.example.duan1.activities;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import com.example.duan1.R;

public class PreferenceSetting  extends PreferenceActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
        SharedPreferences sharedPreferences =getSharedPreferences("SET_SETTING",MODE_PRIVATE);
        boolean remember = sharedPreferences.getBoolean("remember",false);
        Loadsetting();
    }
    public void Loadsetting(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        boolean chk_night = sp.getBoolean("Night",false);
        if(chk_night){
            getListView().setBackgroundColor(Color.parseColor("#222222"));
        }else {
            getListView().setBackgroundColor(Color.parseColor("#ffffff"));
        }
        CheckBoxPreference check = (CheckBoxPreference) findPreference("Night");
        check.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference prefs, Object obj) {
                boolean yes = (boolean)obj;
                if(yes){
                    getListView().setBackgroundColor(Color.parseColor("#222222"));

                }else {
                    getListView().setBackgroundColor(Color.parseColor("#ffffff"));

                }
                return true;
            }
        });
        ListPreference lp =(ListPreference) findPreference("Orientation");
        String orien = sp.getString("Orientation","");
        if("1".equals(orien)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
            lp.setSummary(lp.getEntry());
        }else if("2".equals(orien)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            lp.setSummary(lp.getEntry());
        }else if("3".equals(orien)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            lp.setSummary(lp.getEntry());
        }

        lp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference pref, Object obj) {
                String item =(String)obj;
                if(pref.getKey().equals("Orientation"))
                {
                    switch (item){
                        case "1":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
                            break;
                        case "2":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            break;
                        case "3":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                            break;
                    }
                    ListPreference lpre =(ListPreference) pref;
                    lpre.setSummary(lpre.getEntries()[lpre.findIndexOfValue(item)]);
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        Loadsetting();
        super.onResume();
    }
}
