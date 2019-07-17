package sg.edu.rp.c300.cleanerbooking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ViewBookingActivity extends AppCompatActivity {

    private ActionBar titlebar;
    private TextView tvStatus, tvCleaner, tvstartTime, tvService;
    private AsyncHttpClient client;

    SharedPreferences pref2;
    SharedPreferences.Editor prefEdit2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);

        client = new AsyncHttpClient();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        titlebar = getSupportActionBar();
        titlebar.setTitle("Booking");

        tvstartTime = findViewById(R.id.textViewStartTime);
        tvStatus = findViewById(R.id.textViewStatus);
        tvCleaner = findViewById(R.id.textViewCleaner);
        tvService=findViewById(R.id.textViewService);

        Intent i = getIntent();
        Booking booking = (Booking)i.getSerializableExtra("booking");
        tvService.setText(booking.getName());
        tvstartTime.setText(booking.getDate());
        tvStatus.setText(booking.getStatus());

        pref2 = getSharedPreferences("pref2", Context.MODE_PRIVATE);

        String email = pref2.getString("email","");
        Log.d("EMAIL",email);

        RequestParams params = new RequestParams();
        params.put("email",email);

        client.post("http://10.0.2.2/FYPCleanerAdmin/getBookingToAndroidByEmail.php",params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {


                try {
                    for (int i = 0; i <response.length();i++) {
                        JSONObject booking = response.getJSONObject(i);
                        int id = booking.getInt("booking_id");
                        String name = booking.getString("booking_service");
                        String date = booking.getString("booking_date_time");
                        String status = booking.getString("booking_status");
                        Log.d("DATEFOR",date);

                        Booking b = new Booking(id,name,date,status);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });



    }
}
