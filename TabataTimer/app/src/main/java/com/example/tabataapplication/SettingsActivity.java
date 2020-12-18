package com.example.tabataapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.tabataapplication.DatabaseHelper.DatabaseAdapter;

import java.util.Arrays;
import java.util.Locale;

public class SettingsActivity extends PreferenceActivity {
    private SharedPreferences preferences;
    private int fontScaleIndex;
    private int languageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean("theme", true)) {
            setTheme(R.style.Theme_AppCompat);
        }
        Configuration configuration = new Configuration();
        configuration.locale = initLanguage();
        configuration.fontScale = initFontSize();

        getBaseContext().getResources().updateConfiguration(configuration, null);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new ChangeSettingsFragment()).commit();
        super.onCreate(savedInstanceState);
    }

    private Locale initLanguage() {
        String value = preferences.getString("test_lang", "en");
        Locale locale = new Locale(value);
        Locale.setDefault(locale);

        languageIndex = Arrays.asList((getResources().getStringArray(R.array.language_alias))).indexOf(value);

        return locale;
    }

    private float initFontSize() {
        String font = preferences.getString("font_size", "1.0");
        fontScaleIndex = Arrays.asList((getResources().getStringArray(R.array.text_scale_alias))).indexOf(font);

        return Float.parseFloat(font);
    }

    public static class ChangeSettingsFragment extends PreferenceFragment {
        private DatabaseAdapter databaseAdapter;

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            databaseAdapter = new DatabaseAdapter(getContext());
            databaseAdapter.open();
            addPreferencesFromResource(R.xml.preference);

            Preference theme = findPreference("theme");
            theme.setOnPreferenceChangeListener(this::onThemeChange);

            ListPreference font = (ListPreference) findPreference("font_size");
            font.setOnPreferenceChangeListener(this::onFontChange);
            font.setValueIndex(((SettingsActivity) getActivity()).fontScaleIndex);

            ListPreference language = (ListPreference) findPreference("test_lang");
            language.setOnPreferenceChangeListener(this::onLanguageChange);
            language.setValueIndex(((SettingsActivity) getActivity()).languageIndex);

            Preference button = findPreference("DeleteAll");
            button.setOnPreferenceClickListener(this::onDeleteClick);
        }

        private boolean onLanguageChange(Preference preference, Object newValue) {
            Locale locale = new Locale(newValue.toString());

            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            getActivity().getResources().updateConfiguration(configuration, null);
            getActivity().recreate();

            return true;
        }

        private boolean onFontChange(Preference preference, Object newValue) {
            Configuration configuration = getResources().getConfiguration();

            configuration.fontScale = Float.parseFloat(newValue.toString());

            DisplayMetrics metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            getActivity().getBaseContext().getResources().updateConfiguration(configuration, metrics);
            getActivity().recreate();

            return true;
        }

        private boolean onThemeChange(Preference preference, Object newValue) {
            if ((boolean) newValue) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            getActivity().recreate();

            return true;
        }

        private boolean onDeleteClick(Preference preference) {
            databaseAdapter.deleteAll();
            Intent intent = new Intent();
            getActivity().setResult(RESULT_OK, intent);
            getActivity().finish();

            return true;
        }
    }
}
