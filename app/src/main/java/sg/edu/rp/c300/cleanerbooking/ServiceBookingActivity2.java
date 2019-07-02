package sg.edu.rp.c300.cleanerbooking;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class ServiceBookingActivity2 extends AppCompatActivity {

    TextView tvTime,tvDate;
    int theHour, theMin, theYear,theMonth,theDay;
    Button btnNext;
    SharedPreferences pref;
    SharedPreferences.Editor prefedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_booking2);

        Toolbar myTB = findViewById(R.id.my_toolbar);
        setSupportActionBar(myTB);
        ActionBar AB = getSupportActionBar();
        AB.setDisplayHomeAsUpEnabled(true);

        pref = getSharedPreferences("mybooking",MODE_PRIVATE);
        prefedit = pref.edit();

        btnNext = findViewById(R.id.buttonConfirm);
        tvTime = findViewById(R.id.textViewTime);

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = hourOfDay + ":" + minute;
                        prefedit.putString("mytime",time);
                        prefedit.commit();

                        tvTime.setText(time);
                        theHour = hourOfDay;
                        theMin = minute;

                    }
                };
                //Create the Date Picker Dialog
                //if theYear theMonth theDay not initialize, use the fields in onDateSet() line 186
                TimePickerDialog myTimeDialog = new TimePickerDialog(ServiceBookingActivity2.this,
                        myTimeListener,theHour,theMin,true);
                myTimeDialog.show();
            }
        });

        tvDate = findViewById(R.id.textViewDate);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String monthformat = String.format("%02d" , monthOfYear + 1);
                        String dayformat = String.format("%02d" , dayOfMonth);
                        String date = year + "-" + monthformat + "-" + dayformat;

                        prefedit.putString("mydate",date);
                        prefedit.commit();

                        tvDate.setText(date);
                        theYear = year;
                        theMonth = monthOfYear;
                        theDay = dayOfMonth;
                    }
                };
                //Create the Date Picker Dialog
                // if theYear theMonth theDay not initialize, use the fields in onDateSet() line 186
                DatePickerDialog myDateDialog = new DatePickerDialog(ServiceBookingActivity2.this,
                        myDateListener,theYear,theMonth,theDay);
                myDateDialog.show();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceBookingActivity2.this, ServiceBookingActivity3.class));
            }
        });
    }
}
