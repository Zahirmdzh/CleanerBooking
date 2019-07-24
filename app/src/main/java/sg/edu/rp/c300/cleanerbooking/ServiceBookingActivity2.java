package sg.edu.rp.c300.cleanerbooking;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ServiceBookingActivity2 extends AppCompatActivity {

    EditText etReq;
    TextView tvTime, tvDate, tvType, tvErrorTime, tvErrorDate;
    int theHour, theMin, theYear, theMonth, theDay;
    Button btnNext;
    SharedPreferences pref;
    SharedPreferences.Editor prefedit;
    Spinner spn;
    RadioGroup rg;
    RadioButton rb1, rb2, rb3;
    ArrayList<String> aLItems;
    ArrayAdapter<String> aaItems;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_booking2);

        Toolbar myTB = findViewById(R.id.my_toolbar);
        setSupportActionBar(myTB);
        ActionBar AB = getSupportActionBar();
        AB.setDisplayHomeAsUpEnabled(true);

        tvType = findViewById(R.id.textViewType);


        //create mybooking.xml file
        pref = getSharedPreferences("mybooking", MODE_PRIVATE);
        prefedit = pref.edit();

        btnNext = findViewById(R.id.buttonNext);
        tvTime = findViewById(R.id.textViewStartTime);
        spn = findViewById(R.id.spinnerDate);
        rg = findViewById(R.id.rg);
        rb1 = findViewById(R.id.radioButton);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        etReq = findViewById(R.id.editTextRequests);

        session = new Session(this);
        if (session.loggedinStatus() == false) {
            rg.setVisibility(GONE);
            tvType.setVisibility(GONE);
        }

        aLItems = new ArrayList<>();
        // create an array adapter using default spinner layout
        aaItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, aLItems);

        spn.setAdapter(aaItems);

        spn.setVisibility(GONE);
        Log.d("RB1ONCREATE", rb1.getText().toString());
        prefedit.putString("type", rb1.getText().toString());
        prefedit.commit();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                aLItems.clear();

                if (rg.getCheckedRadioButtonId() == rb1.getId()) {
                    spn.setVisibility(GONE);
                    Log.d("RB1TEXT", rb1.getText().toString());
                    prefedit.putString("type", rb1.getText().toString());
                    prefedit.commit();

                } else {
                    spn.setVisibility(VISIBLE);


                    String text = "";
                    if (rg.getCheckedRadioButtonId() == rb2.getId()) {
                        spn.setSelection(0);
                        String[] strItems = getResources().getStringArray(R.array.weekItem);
                        aLItems.addAll(Arrays.asList(strItems));

                    } else if (rg.getCheckedRadioButtonId() == rb3.getId()) {
                        spn.setSelection(0);
                        String[] strItems = getResources().getStringArray(R.array.monthItem);
                        aLItems.addAll(Arrays.asList(strItems));

                    } else {
                        return;
                    }
                }
                aaItems.notifyDataSetChanged();


            }
        });

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                String item = String.valueOf(spn.getItemAtPosition(position));
                Log.d("ITEM", item);
                prefedit.putString("type", item);
                prefedit.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub)

            }
        });


        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = hourOfDay + ":" + minute;
                        prefedit.putString("mytime", time);
                        prefedit.commit();

                        tvTime.setText(time);
                        theHour = hourOfDay;
                        theMin = minute;

                    }
                };

                TimePickerDialog myTimeDialog = new TimePickerDialog(ServiceBookingActivity2.this,
                        myTimeListener, theHour, theMin, true);
                myTimeDialog.show();
            }
        });


        final Calendar c = Calendar.getInstance();
        theYear = c.get(Calendar.YEAR);
        theMonth = c.get(Calendar.MONTH);
        theDay = c.get(Calendar.DAY_OF_MONTH);


        tvDate = findViewById(R.id.textViewDate);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String monthformat = String.format("%02d", monthOfYear + 1);
                        String dayformat = String.format("%02d", dayOfMonth);
                        String date = year + "-" + monthformat + "-" + dayformat;

                        prefedit.putString("mydate", date);
                        prefedit.commit();

                        tvDate.setText(date);
                        theYear = year;
                        theMonth = monthOfYear;
                        theDay = dayOfMonth;

                    }
                };

                DatePickerDialog myDateDialog = new DatePickerDialog(ServiceBookingActivity2.this,
                        myDateListener, theYear, theMonth, theDay);

                myDateDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                myDateDialog.show();
            }
        });

     // Error Checking
        tvErrorDate = findViewById(R.id.textViewErrorDate);
        tvErrorTime = findViewById(R.id.textViewErrorTime);

        tvTime.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (!tvTime.getText().toString().isEmpty() && s.length() > 0)
                {

                    tvErrorTime.setText("");
                    tvTime.setError(null);
                }
                else
                {

                    tvErrorTime.setText("Time required. Please select the date");
                    tvTime.setError("Required");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });


        tvDate.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (!tvDate.getText().toString().isEmpty() && s.length() > 0)
                {

                    tvErrorDate.setText("");
                    tvDate.setError(null);
                }
                else
                {

                    tvErrorDate.setText("Date required. Please select the date");
                    tvDate.setError("Required");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pref.contains("mytime") && pref.contains("mydate")) {
                    String request = etReq.getText().toString();

                    prefedit.putString("request", request);

                    prefedit.commit();

                    startActivity(new Intent(ServiceBookingActivity2.this, ServiceBookingActivity3.class));
                }

                else if (!pref.contains("mytime")) {

                    tvTime.setError("Select Time");
                    tvErrorTime.setText("Time required. Please select the time");

                } else if (!pref.contains("mydate")) {

                    tvDate.setError("Select Date");
                    tvErrorDate.setText("Date required. Please select the date");

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        prefedit.clear();
        prefedit.commit();
        super.onBackPressed();
    }

/*    @Override
    protected void onRestart() {
        prefedit.clear();
        prefedit.commit();
        tvTime.setText("");
        tvDate.setText("");
        super.onRestart();
    }

    @Override
    protected void onStop() {
        prefedit.clear();
        prefedit.commit();
        tvTime.setText("");
        tvDate.setText("");
        super.onStop();
    }*/

    @Override
    protected void onDestroy() {

     prefedit.clear();
     prefedit.commit();
     super.onDestroy();
    }
}
