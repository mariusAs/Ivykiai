package com.example.ivykiai.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ivykiai.models.User;

public class SharedPreferenceManager {

    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPreferenceManager mInstance;
    private Context mCtx;

    private SharedPreferenceManager(Context mCtx) {
        this.mCtx = mCtx;
    }


    public static synchronized SharedPreferenceManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPreferenceManager(mCtx);
        }
        return mInstance;
    }


    public void saveUser(User user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("name", user.getName());
        editor.putString("last_name", user.getLast_Name());
        editor.putString("phone_number", user.getPhone_number());
        editor.putString("personal_code", user.getPersonal_code());

        editor.apply();

    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("last_name", null),
                sharedPreferences.getString("phone_number", null),
                sharedPreferences.getString("personal_code", null)

        );
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}