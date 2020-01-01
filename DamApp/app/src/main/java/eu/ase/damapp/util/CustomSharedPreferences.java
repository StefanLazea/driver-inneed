package eu.ase.damapp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class CustomSharedPreferences {
    private static SharedPreferences preferences;
    private static final String USER_ID = "userId";

    public static void setIdToPreferences(Context context, String sharedPrefName, long id) {
        preferences = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(USER_ID, id);
        editor.apply();
    }

    public static long getIdFromPreferences(Context context, String sharedPrefName) {
        preferences = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        long value = preferences.getLong(USER_ID, -1);
        if (value != -1) {
            return value;
        }
        return -1;
    }
}
