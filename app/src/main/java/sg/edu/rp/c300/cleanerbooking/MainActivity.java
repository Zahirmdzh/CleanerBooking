package sg.edu.rp.c300.cleanerbooking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Random;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    Button btnlogin, btnsignup;
    TextView tvguest ,tvForgot;
    EditText etEmail, etPass;
    private Session session;
    private AsyncHttpClient client;
    private String member_id;

//    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";


    private static final String UKEY = "APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnlogin = findViewById(R.id.buttonLogin);
        btnsignup = findViewById(R.id.buttonSignup);
        tvguest = findViewById(R.id.textViewGuest);
        tvForgot = findViewById(R.id.tvForgot);
        etEmail = findViewById(R.id.editTextEmail);
        etPass = findViewById(R.id.editTextPassword);
        session = new Session(this);
        client = new AsyncHttpClient();



        if(session.loggedinStatus()){
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }

        tvguest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPrefs = getSharedPreferences(
                        UKEY, Context.MODE_PRIVATE);

                String androidId = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                Log.d("UKEY", androidId);

                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("uniquekey", androidId);
                editor.commit();


                Intent i = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });

        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ForgotPassword.class);
                startActivity(i);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString();
                final String pass = etPass.getText().toString();
               /* Intent intent = new Intent();
                intent.putExtra("email",email);
                startActivity(intent);
*/

                if (email.equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Login failed. Please enter email.", Toast.LENGTH_LONG).show();

                } else if (pass.equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Login failed. Please enter password.", Toast.LENGTH_LONG).show();

                } else {
                    // proceed to authenticate user
                    // TODO: call doLogin web service to authenticate use
                    //String url = "http://10.0.2.2/FYPCleanerAdmin/login.php";
                    String url = "https://nogoodcodes.000webhostapp.com/login.php";

                    RequestParams params = new RequestParams();
                    params.add("email", email);
                    params.add("password", pass);

                    client.post(url, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                Boolean authenticated = response.getBoolean("authenticated");

                                Log.d("AUTHENTIC",authenticated.toString());
                                if (authenticated == true) {

                                    Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                                    member_id = response.getString("member_id");
                                    Log.d("member_id",member_id);
                                    String email = response.getString("email");
                                    //intent.putExtra("email",email);
                                    //intent.putExtra("password",pass);


                                    SharedPreferences pref = getSharedPreferences("pref2",MODE_PRIVATE);
                                    SharedPreferences.Editor prefEdit = pref.edit();
                                    prefEdit.putString("member_id",member_id);
                                    prefEdit.putString("email",email);
                                    prefEdit.commit();


                                    Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_LONG).show();

                                    session.setLoggedIn(true);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(MainActivity.this, "Login failed. Please check your login credentials", Toast.LENGTH_LONG);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            }
        });


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignupActivity.class);
                startActivity(i);

            }
        });

    }
    @Override
    public void onBackPressed() {
        this.finishAffinity();
        super.onBackPressed();
    }

}



