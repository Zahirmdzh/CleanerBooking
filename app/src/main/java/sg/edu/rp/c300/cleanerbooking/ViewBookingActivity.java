package sg.edu.rp.c300.cleanerbooking;

import android.content.DialogInterface;
import android.content.Intent;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ViewBookingActivity extends AppCompatActivity {

    private ActionBar titlebar;
    Button btnReschedule,btnCancel;
    TextView tvCleaner, tvService, tvStatus, tvReq,tvStartTime,tvAddress,tvID;
    String bookingID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        titlebar = getSupportActionBar();
        titlebar.setTitle("Booking");
        ActionBar AB = getSupportActionBar();
        AB.setDisplayHomeAsUpEnabled(true);

        tvID = findViewById(R.id.textViewID);
        tvService = findViewById(R.id.textViewService);
        tvStatus = findViewById(R.id.textViewStatus);
        tvReq = findViewById(R.id.textViewRequested);
        tvStartTime = findViewById(R.id.textViewStartTime);
        tvCleaner = findViewById(R.id.textViewCleaner);
        tvAddress = findViewById(R.id.textViewBookingAddress);

        Intent i = getIntent();
        Booking selected = (Booking) i.getSerializableExtra("booking");

        String servicename = selected.getService();
        String status = selected.getStatus();
        String request = selected.getRequest();
        String startTime = selected.getDateTime();
        String address = selected.getAddress();
        bookingID = selected.getId();


        tvID.setText(bookingID);
        tvService.setText(servicename);
        tvStatus.setText(status);
        tvReq.setText(request);
        tvStartTime.setText(startTime);
        tvAddress.setText(address);



        btnReschedule = findViewById(R.id.buttonReschedule);

        btnReschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewBookingActivity.this,RescheduleBookingActivity.class);
                i.putExtra("bookingId",bookingID);
                startActivity(i);
            }
        });

        btnCancel = findViewById(R.id.buttonCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewBookingActivity.this);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        btnDeleteOnClick(v);

                        Intent intent = new Intent(ViewBookingActivity.this,HomeActivity.class);
                        startActivity(intent);

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog myDialog = builder.create();

                myDialog.setMessage("Are you sure you want to cancel this booking?");
                myDialog.setTitle("Confirm");

                myDialog.show();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("booking_id",bookingID);


        client.post("http://10.0.2.2/FYPCleanerAdmin/getAssignedCleanersAndroid.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                try {
                    Log.i("JSON Results: ", response.toString());
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject data = response.getJSONObject(i);
                        String cleanername = data.getString("cleaner_name");
                        tvCleaner.setText(cleanername);
                        Log.d("CLEANERNAME", cleanername);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void btnDeleteOnClick(View v) {


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("booking_id",bookingID);


        client.post("http://10.0.2.2/FYPCleanerAdmin/deleteBookingAndroid.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Log.i("JSON Results: ", response.toString());


                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
