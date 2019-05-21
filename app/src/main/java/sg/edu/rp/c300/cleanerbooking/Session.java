package sg.edu.rp.c300.cleanerbooking;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;

    public Session(Context context){
        this.context = context;
        prefs = context.getSharedPreferences("app", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveEmail(String email) {
        editor.putString("email",email);
        editor.commit();

    }

    public void setLoggedIn(boolean isLoggedIn){

        editor.putBoolean("loggedInmode",isLoggedIn);
//        editor.putString("Email",email);
        editor.commit();
    }

    public boolean loggedinStatus(){
        return prefs.getBoolean("loggedInmode", false);
    }
}
