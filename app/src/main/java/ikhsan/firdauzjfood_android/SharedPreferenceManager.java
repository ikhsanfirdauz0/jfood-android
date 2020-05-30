package ikhsan.firdauzjfood_android;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager
{
    public static final String SP_JFOOD = "spJfood";
    public static final String SP_NAME = "spNama";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_ALREADY_LOGIN = "spAlreadyLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPreferenceManager(Context context)
    {
        sp = context.getSharedPreferences(SP_JFOOD, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPNama(){
        return sp.getString(SP_NAME, "");
    }

    public String getSPEmail(){
        return sp.getString(SP_EMAIL, "");
    }

    public Boolean getSPAlreadyLogin(){
        return sp.getBoolean(SP_ALREADY_LOGIN, false);
    }
}
