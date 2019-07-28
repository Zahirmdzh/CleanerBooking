package sg.edu.rp.c300.cleanerbooking;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity{

    private ActionBar titlebar;
    SharedPreferences pref;
    EditText name,address,phone,email,point;
    Button btnHome;
    ToggleButton edit;
    String memName,memAddress,memEmail;
    Integer memId,memMobile,memPoint;

    String newName,newAddress,newEmail;
    Integer newMobile,newPoint;

    RequestParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar myTB = findViewById(R.id.my_toolbar);
        setSupportActionBar(myTB);
        ActionBar AB = getSupportActionBar();
        AB.setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.etName);
        address = findViewById(R.id.etAddress);
        phone = findViewById(R.id.etMobile);
        email = findViewById(R.id.etEmail);
        point = findViewById(R.id.etPoints);
        edit = findViewById(R.id.edit);
        btnHome = findViewById(R.id.btnHome);

        name.setEnabled(false);
        address.setEnabled(false);
        phone.setEnabled(false);
        email.setEnabled(false);
        point.setEnabled(false);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        titlebar = getSupportActionBar();
        titlebar.setTitle("Profile");

            AsyncHttpClient client = new AsyncHttpClient();

            RequestParams params = new RequestParams();

            pref = getSharedPreferences("pref2", MODE_PRIVATE);
            final String member_id = pref.getString("member_id", "");

            params.put("member_id", member_id);

        //String url = "http://10.0.2.2/FYPCleanerAdmin/getMemberById.php";
        String url = "https://nogoodcodes.000webhostapp.com/getMemberById.php";

            client.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                    try {
                        for (int i = 0; i < response.length(); i++) {
                            Log.i("PROFILE", response.toString());

                            JSONObject profile = response.getJSONObject(i);
                            memId = profile.getInt("member_id");
                            memName = profile.getString("full_name");
                            memAddress = profile.getString("address");
                            memMobile = profile.getInt("mobile");
                            memEmail = profile.getString("email");
                            memPoint = profile.getInt("accumulated_points");
                            //    Profile profile1 = new Profile(memId,memName, memMobile, memAddress, memEmail, memPoint);

                            name.setText(memName);
                            address.setText(memAddress);
                            phone.setText(String.valueOf(memMobile));
                            email.setText(memEmail);
                            point.setText(String.valueOf(memPoint));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit.isChecked()){
                    //Button is ON
                    name.setEnabled(true);
                    name.requestFocus();

                    address.setEnabled(true);
                    address.requestFocus();

                    phone.setEnabled(true);
                    phone.requestFocus();

                    email.setEnabled(true);
                    email.requestFocus();

                    Toast.makeText(getApplicationContext(),"You can edit your fields now",Toast.LENGTH_LONG).show();
                    newName = name.getText().toString();
                    newAddress = address.getText().toString();
                    newEmail = email.getText().toString();
                    newMobile = Integer.parseInt(phone.getText().toString());
                    //Profile profile1 = new Profile(memId,newName, newMobile, newAddress, newEmail, memPoint);

                    name.setText(newName);
                    address.setText(newAddress);
                    phone.setText(String.valueOf(newMobile));
                    email.setText(newEmail);
                }
                else{
                    //Button is OFF


                    Toast.makeText(getApplicationContext(),"Your changes are saved now",Toast.LENGTH_LONG).show();

                    name.setEnabled(false);
                    address.setEnabled(false);
                    phone.setEnabled(false);
                    email.setEnabled(false);

                    String newName1 = name.getText().toString();
                    String newAddress1 = address.getText().toString();
                    String newEmail1 = email.getText().toString();
                    Integer newMobile1 = Integer.parseInt(phone.getText().toString());

                    AsyncHttpClient client = new AsyncHttpClient();

                    pref = getSharedPreferences("pref2", MODE_PRIVATE);
                    final String member_id = pref.getString("member_id", "");

                    RequestParams params = new RequestParams();
                    params.put("member_id",member_id);
                    params.put("full_name",newName1);
                    params.put("mobile",newMobile1.toString());
                    params.put("address",newAddress1);
                    params.put("email",newEmail1);


                    //String url = "http://10.0.2.2/FYPCleanerAdmin/editMemberDetails.php";
                    String url = "https://nogoodcodes.000webhostapp.com/editMemberDetails.php";
                    client.post(url,params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                Boolean authenticated = response.getBoolean("authenticated");
                                if (authenticated == true) {
                                    Log.i("PROFILE", response.toString());
                                    Toast.makeText(getApplicationContext(),"Credentials have been successfully edited",Toast.LENGTH_LONG).show();
//                                    JSONObject profile = response.getJSONObject(response.toString());
//                                    memName = profile.getString("full_name");
//                                    memAddress= profile.getString("address");
//                                    memMobile = profile.getInt("mobile");
//                                    memEmail = profile.getString("email");
                                    //Profile profile1 = new Profile(memId,newName, newMobile, newAddress, newEmail, memPoint);
//
//                                    memPoint = profile.getInt("accumulated_points");
//                                    name.setText(memName);
//                                    address.setText(memAddress);
//                                    phone.setText(String.valueOf(memMobile));
//                                    email.setText(memEmail);
//                                    point.setText(String.valueOf(memPoint));

                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }

            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

    }
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB) // API 11
//    void startMyTask(AsyncTask asyncTask) {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
//            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
//        else
//            asyncTask.execute(params);
//    }
}