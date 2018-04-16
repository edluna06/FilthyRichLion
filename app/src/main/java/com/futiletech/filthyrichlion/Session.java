package com.futiletech.filthyrichlion;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusername(String username) {
        prefs.edit().putString("username", username).commit();
    }

    public String getusername() {
        String username = prefs.getString("username","");
        return username;
    }

    public void setuserpic(String userpic) {
        prefs.edit().putString("userpic", userpic).commit();
    }

    public String getuserpic() {
        String userpic = prefs.getString("userpic","");
        return userpic;
    }

}
