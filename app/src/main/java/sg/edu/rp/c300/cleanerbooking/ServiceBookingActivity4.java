package sg.edu.rp.c300.cleanerbooking;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.io.File;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class ServiceBookingActivity4 extends AppCompatActivity {

    TextView tvTime,tvDate, tvAddress,tvContact,tvFname,tvLname,tvEmail, tvServicename, tvType,tvReq;
    SharedPreferences pref;
    SharedPreferences.Editor prefedit;
    Button btnConfirm;
    String time,date,dateString,address,contact,fname,lname,fullname, email,servicename, type,request;
    private AsyncHttpClient client;
    Date d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_booking4);

        Toolbar myTB = findViewById(R.id.my_toolbar);
        setSupportActionBar(myTB);
        ActionBar AB = getSupportActionBar();
        AB.setDisplayHomeAsUpEnabled(true);

        client = new AsyncHttpClient();

        tvServicename = findViewById(R.id.textViewServiceName);
        tvTime = findViewById(R.id.textViewTime);
        tvDate = findViewById(R.id.textViewDate);
        tvAddress = findViewById(R.id.textViewAddress);
        tvContact = findViewById(R.id.textViewContact);
        tvFname = findViewById(R.id.textViewFname);
        tvLname = findViewById(R.id.textViewLname);
        tvEmail = findViewById(R.id.textViewEmail);
        tvType = findViewById(R.id.textViewType);
        tvReq = findViewById(R.id.textViewRequest);

        File f = new File(
                "/data/data/sg.edu.rp.c300.cleanerbooking/shared_prefs/mybooking.xml");
        if (f.exists()) {
            pref = getSharedPreferences("mybooking", MODE_PRIVATE);

            time = pref.getString("mytime","");
            date = pref.getString("mydate","");

            address = pref.getString("address","");
            contact = pref.getString("contact","");
            fname = pref.getString("fname","");
            lname = pref.getString("lname","");
            email = pref.getString("email","");
            servicename = pref.getString("servicename","");
            type = pref.getString("type","");
            request = pref.getString("request","");
            fullname = fname + " " + lname;


            tvServicename.setText(servicename);
            tvTime.setText(time);
            tvDate.setText(date);
            tvAddress.setText(address);
            tvContact.setText(contact);
            tvFname.setText(fname);
            tvLname.setText(lname);
            tvEmail.setText(email);
            tvType.setText(type);
            tvReq.setText(request);


            if (!date.isEmpty() && !time.isEmpty()) {
                // Create the MySQL datetime string
                dateString = date + " " + time + ":00";
                Log.d("DATETIME", dateString);
            }
            Log.d("TAG", "SharedPreferences mybooking.xml : exist");

        } else {
            Log.d("TAG", "Setup default preferences");
        }


        btnConfirm = findViewById(R.id.buttonConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(ServiceBookingActivity4.this);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        btnCreateOnClick(v);


                        prefedit = getApplicationContext().getSharedPreferences("mybooking",MODE_PRIVATE).edit();
                        prefedit.clear();
                        prefedit.commit();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog myDialog = builder.create();

                myDialog.setMessage("Are you sure you want to make this booking?");
                myDialog.setTitle("Confirm");

                myDialog.show();





            }
        });
    }

    private void btnCreateOnClick(View v) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            d = sdf.parse(date + time);

            Log.d("DATETIME", String.valueOf(d));
        } catch (ParseException ex) {
            Log.v("Exception", ex.getLocalizedMessage());
        }

        RequestParams params = new RequestParams();
        params.add("fullname", fullname);
        params.add("mobile", contact);
        params.add("address_postal_code", address);
        params.add("email", email);
        params.add("booking_date_time", dateString);
        params.add("booking_service_name", servicename);

        client.post("http://10.0.2.2/FYPCleanerAdmin/addMember.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Log.i("JSON Results: ", response.toString());


                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


}
