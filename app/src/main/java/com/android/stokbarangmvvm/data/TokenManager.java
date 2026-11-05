package com.android.stokbarangmvvm.data;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private final SharedPreferences prefs;

    public TokenManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
    }

    public void save(String token, String name) {
        prefs.edit().putString("token", token).putString("name", name).apply();
    }

    public String getToken() { return prefs.getString("token", null); }
    public boolean isLoggedIn() { return getToken() != null; }
    public void clear() { prefs.edit().clear().apply(); }
}