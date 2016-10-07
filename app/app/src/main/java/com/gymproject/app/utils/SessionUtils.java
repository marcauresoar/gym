package com.gymproject.app.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.gymproject.app.activities.InicioActivity;
import com.gymproject.app.dao.UsuarioDao;
import com.gymproject.app.models.Usuario;

import io.realm.Realm;

public class SessionUtils {
    private static SessionUtils instance;

    // Shared Preferences
    private static SharedPreferences pref;

    // Editor for Shared preferences
    private static SharedPreferences.Editor editor;

    // Context
    private static Context context;

    // Shared pref mode
    private static int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "SessionPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "isLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_ID = "id";

    protected SessionUtils(Context context){
        context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static SessionUtils getInstance(Context context){
        if(instance == null) {
            instance = new SessionUtils(context);
        }
        return instance;
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String id){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_ID, id);

        // commit changes
        editor.commit();
    }

    public String getIdUsuario(){
        return pref.getString(KEY_ID, "");
    }

    public String getNomeUsuario(){
        return getUsuario().getNome();
    }

    public String getEmailUsuario(){
        return getUsuario().getEmail();
    }

    public Usuario getUsuario(){
        return UsuarioDao.getById(Realm.getDefaultInstance(), getIdUsuario());
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}