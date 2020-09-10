package dev.muhammadsabeelahmed.expenses;


import android.content.Context;
import android.content.SharedPreferences;


public class PreferencesHandler {

    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    private static final String CURRENT_DATE = "14-04-2020";
    private static final String APP_FIRST_TIME = "first_time";
    private static final String UEMAIL = "uemail";
    private static final String UCONTACT = "ucontact";
    private static final String UNAME = "uname";

    public PreferencesHandler(Context context) {
        pref = context.getSharedPreferences("expenses_manager", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public String getCurrentDate() {
        return pref.getString(CURRENT_DATE, "14-04-2020");
    }

    public void setCurrentDate(String currentDate) {
        editor.putString(CURRENT_DATE, currentDate);
        editor.apply();
        editor.commit();
    }

    public String getAppFirstTime() {
        return pref.getString(APP_FIRST_TIME, "true");
    }

    public void setAppFirstTime(String appFirstTime) {
        editor.putString(APP_FIRST_TIME, appFirstTime);
        editor.apply();
        editor.commit();
    }

    public String getUemail() {
        return pref.getString(UEMAIL, "");
    }

    public void setUemail(String uemail) {
        editor.putString(UEMAIL, uemail);
        editor.apply();
        editor.commit();
    }

    public String getUname() {
        return pref.getString(UNAME, "");
    }

    public void setUname(String uname) {
        editor.putString(UNAME, uname);
        editor.apply();
        editor.commit();
    }


    public String getUcontact() {
        return pref.getString(UCONTACT, "");
    }

    public void setUcontact(String ucontact) {
        editor.putString(UCONTACT, ucontact);
        editor.apply();
        editor.commit();
    }
}
