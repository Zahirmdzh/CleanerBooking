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

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ViewBookingActivity extends AppCompatActivity {

    private ActionBar titlebar;
    Button btnReschedule,btnCancel;
    TextView tvService, tvStatus, tvReq,tvStartTime;
    String bookingID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        titlebar = getSupportActionBar();
        titlebar.setTitle("Booking");

        tvService = findViewById(R.id.textViewService);
        tvStatus = findViewById(R.id.textViewStatus);
        tvReq = findViewById(R.id.textViewRequested);
        tvStartTime = findViewById(R.id.textViewStartTime);

        Intent i = getIntent();
        Booking selected = (Booking) i.getSerializableExtra("booking");

        String servicename = selected.getService();
        String status = selected.getStatus();
        String request = selected.getRequest();
        String startTime = selected.getDateTime();
        bookingID = selected.getId();


        tvService.setText(servicename);
        tvStatus.setText(status);
        tvReq.setText(request);
        tvStartTime.setText(startTime);


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
