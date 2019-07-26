package sg.edu.rp.c300.cleanerbooking;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

import static android.view.View.GONE;

public class ServiceBookingActivity4 extends AppCompatActivity {

    TextView tvTime, tvDate, tvAddress, tvContact, tvFname, tvLname, tvEmail, tvServicename, tvType, tvReq, tv11;
    SharedPreferences prefKey;
    SharedPreferences.Editor prefedit;
    Button btnConfirm;
    String time, date, dateString, address, contact, fname, lname, fullname, email, servicename, type, request, key;
    private AsyncHttpClient client;
    Date d;
    private Session session;
    LinearLayout layoutContact,layoutFname,layoutLname,layoutEmail;
    int requestCode = 123;
    int notificationID = 888;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_booking4);

        Toolbar myTB = findViewById(R.id.my_toolbar);
        setSupportActionBar(myTB);
        ActionBar AB = getSupportActionBar();
        AB.setDisplayHomeAsUpEnabled(false);

        layoutContact = findViewById(R.id.layoutC);
        layoutFname = findViewById(R.id.layoutF);
        layoutLname = findViewById(R.id.layoutL);
        layoutEmail = findViewById(R.id.layoutE);

        prefKey = getSharedPreferences("APP", Context.MODE_PRIVATE);
        key = prefKey.getString("uniquekey", "");


        SharedPreferences prefMember = getSharedPreferences("pref2", MODE_PRIVATE);


        client = new AsyncHttpClient();

        tvServicename = findViewById(R.id.textViewServiceName);
        tvTime = findViewById(R.id.textViewStartTime);
        tvDate = findViewById(R.id.textViewDate);
        tvAddress = findViewById(R.id.textViewAddress);
        tvContact = findViewById(R.id.textViewContact);
        tvFname = findViewById(R.id.textViewFname);
        tvLname = findViewById(R.id.textViewLname);
        tvEmail = findViewById(R.id.textViewEmail);
        tvType = findViewById(R.id.textView65);
        tvReq = findViewById(R.id.textViewRequest);

        session = new Session(this);
        //text view type
        tv11 = findViewById(R.id.textView11);


        File f = new File(
                "/data/data/sg.edu.rp.c300.cleanerbooking/shared_prefs/mybooking.xml");
        if (f.exists()) {
            SharedPreferences pref = getSharedPreferences("mybooking", MODE_PRIVATE);

            time = pref.getString("mytime", "");
            date = pref.getString("mydate", "");
            Log.d("TIME",time);
            Log.d("DATE",date);

            address = pref.getString("address", "");
            contact = pref.getString("contact", "");
            fname = pref.getString("fname", "");
            lname = pref.getString("lname", "");
            servicename = pref.getString("servicename", "Not Selected");
            type = pref.getString("type", "");
            request = pref.getString("request", "");
            fullname = fname + " " + lname;

            Log.d("STATUSLOGIN",String.valueOf(session.loggedinStatus()));


            tvServicename.setText(servicename);
            tvTime.setText(time);
            tvDate.setText(date);
            tvAddress.setText(address);
            tvContact.setText(contact);
            tvFname.setText(fname);
            tvLname.setText(lname);

            tvReq.setText(request);
            tvType.setText(type);

            if (session.loggedinStatus() == false) {
                tv11.setVisibility(GONE);
                tvType.setVisibility(GONE);

                email = pref.getString("email", "");
                tvEmail.setText(email);
                Log.d("GUEST EMAIL", email);


            } else {
                layoutEmail.setVisibility(GONE);
                layoutContact.setVisibility(GONE);
                layoutFname.setVisibility(GONE);
                layoutLname.setVisibility(GONE);


                email = prefMember.getString("email", "");
                Log.d("REGISTERED EMAIL", email);

            }

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


                        prefedit = getApplicationContext().getSharedPreferences("mybooking", MODE_PRIVATE).edit();
                        prefedit.clear();
                        prefedit.commit();

                        Intent intent = new Intent(ServiceBookingActivity4.this,FinishBookingActivity.class);
                        startActivity(intent);

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
        params.add("uniquekey", key);
        params.add("repeated", type);
        params.add("request",request);

        client.post("http://10.0.2.2/FYPCleanerAdmin/addBookingAndroid.php", params, new JsonHttpResponseHandler() {
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

        NotificationManager notificationManager =
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel("default", "Default Channel",
                    NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(ServiceBookingActivity4.this,HomeActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(
                ServiceBookingActivity4.this,requestCode,intent,
                PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(ServiceBookingActivity4.this,"default");
        builder.setContentTitle("Booking has been made!");
        builder.setContentText("Details of the assigned cleaner will be sent to your Email Address");
        builder.setSmallIcon(android.R.drawable.btn_star_big_off);
        builder.setContentIntent(pIntent);
        builder.setAutoCancel(true);

        Uri uri = RingtoneManager.getDefaultUri(
                RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);
        builder.setPriority(Notification.PRIORITY_HIGH);


        Notification n  = builder.build();

        notificationManager.notify(notificationID, n);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ServiceBookingActivity4.this);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                prefedit = getApplicationContext().getSharedPreferences("mybooking", MODE_PRIVATE).edit();
                prefedit.clear();
                prefedit.commit();

                Intent intent = new Intent(ServiceBookingActivity4.this,HomeActivity.class);
                startActivity(intent);

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog myDialog = builder.create();

        myDialog.setMessage("Pressing yes will bring you back to the Home Page");
        myDialog.setTitle("Restart Booking?");

        myDialog.show();


    }
}
