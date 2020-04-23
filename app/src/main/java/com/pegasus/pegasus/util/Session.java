package com.pegasus.pegasus.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.pegasus.pegasus.view.viewholders.ScreenNames;

import java.util.concurrent.TimeUnit;

public class Session {
    public static void SaveSession(Context ctx, String name, String password, String billNo) {
        SharedPreferences loginPreferences = ctx.getSharedPreferences(ScreenNames.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginPreferences.edit();
        editor.clear();
        editor.apply();

        editor = loginPreferences.edit();

        editor.putString(ScreenNames.Name, name);
        editor.putString(ScreenNames.Pass, password);
        editor.putString(ScreenNames.UserBillNo, billNo);
        editor.putLong("ExpiredDate", System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30));
        editor.apply();
    }

    public static String GetString(Context ctx, String key) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(ScreenNames.MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedpreferences.getLong("ExpiredDate", -1) > System.currentTimeMillis()) {
            SharedPreferences loginPreferences = ctx.getSharedPreferences(ScreenNames.MyPREFERENCES, Context.MODE_PRIVATE);
            return loginPreferences.getString(key, null);
        } else {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
        }
        return null;
    }
}
