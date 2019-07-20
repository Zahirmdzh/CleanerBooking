package sg.edu.rp.c300.cleanerbooking;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class RescheduleBookingActivity extends AppCompatActivity {

    TextView tvDate,tvTime;
    int theHour, theMin, theYear,theMonth,theDay;
    Date d;
    String date,time,bookingID,dateString;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reschedule_booking);

        tvDate = findViewById(R.id.textViewDate);
        tvTime = findViewById(R.id.textViewStartTime);
        btnConfirm = findViewById(R.id.buttonConfirm);

        final Calendar c = Calendar.getInstance();
        theYear = c.get(Calendar.YEAR);
        theMonth= c.get(Calendar.MONTH);
        theDay = c.get(Calendar.DAY_OF_MONTH);

        Intent i = getIntent();
        bookingID = i.getStringExtra("bookingId");
        Log.d("BOOKINGID",bookingID);



        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time = hourOfDay + ":" + minute;


                        tvTime.setText(time);
                        theHour = hourOfDay;
                        theMin = minute;

                    }
                };

                TimePickerDialog myTimeDialog = new TimePickerDialog(RescheduleBookingActivity.this,
                        myTimeListener,theHour,theMin,true);
                myTimeDialog.show();
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String monthformat = String.format("%02d" , monthOfYear + 1);
                        String dayformat = String.format("%02d" , dayOfMonth);
                        date = year + "-" + monthformat + "-" + dayformat;



                        tvDate.setText(date);
                        theYear = year;
                        theMonth = monthOfYear;
                        theDay = dayOfMonth;

                    }
                };

                DatePickerDialog myDateDialog = new DatePickerDialog(RescheduleBookingActivity.this,
                        myDateListener,theYear,theMonth,theDay);

                myDateDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                myDateDialog.show();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(RescheduleBookingActivity.this);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    btnRescheduleOnClick(v);



                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog myDialog = builder.create();

                myDialog.setMessage("Are you sure you want to Reschedule this booking?");
                myDialog.setTitle("Confirm");

                myDialog.show();



            }
        });

    }

    private void btnRescheduleOnClick(View v) {

        if (tvDate.getText().toString().isEmpty() || tvTime.getText().toString().isEmpty()) {
            tvDate.setError("no input");
        } else {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            try {
                d = sdf.parse(date + time);

                Log.d("DATETIME", String.valueOf(d));
            } catch (ParseException ex) {
                Log.v("Exception", ex.getLocalizedMessage());
            }

            if (!date.isEmpty() && !time.isEmpty()) {
                // Create the MySQL datetime string
                dateString = date + " " + time + ":00";
                Log.d("DATETIME", dateString);
            }

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.add("booking_id", bookingID);
            params.add("booking_date_time", dateString);

            client.post("http://10.0.2.2/FYPCleanerAdmin/rescheduleBooking.php", params, new JsonHttpResponseHandler() {
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
}
