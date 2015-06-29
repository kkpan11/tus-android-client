package io.tus.android.client;

import android.content.SharedPreferences;

import java.net.MalformedURLException;
import java.net.URL;

public class TusPreferencesURLStore implements TusURLStore {
    private SharedPreferences preferences;

    public TusPreferencesURLStore(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public URL get(String fingerprint) {
        // Ignore empty fingerprints
        if(fingerprint.length() == 0) {
            return null;
        }

        String urlStr = preferences.getString(fingerprint, "");

        // No entry was found
        if(urlStr.length() == 0) {
            return null;
        }

        // Ignore invalid URLs
        try {
            return new URL(urlStr);
        } catch(MalformedURLException e) {
            remove(fingerprint);
            return null;
        }
    }

    public void set(String fingerprint, URL url) {
        String urlStr = url.toString();

        // Ignore empty fingerprints
        if(fingerprint.length() == 0) {
            return;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(fingerprint, urlStr);
        editor.commit();
    }

    public void remove(String fingerprint) {
        // Ignore empty fingerprints
        if(fingerprint.length() == 0) {
            return;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(fingerprint);
        editor.commit();
    }
}