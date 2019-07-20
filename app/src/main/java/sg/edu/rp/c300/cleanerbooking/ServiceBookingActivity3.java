package sg.edu.rp.c300.cleanerbooking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.view.View.GONE;

public class ServiceBookingActivity3 extends AppCompatActivity {

    Button btnNext;
    EditText etFname, etLname,etEmail,etPhone,etAddress;
    SharedPreferences pref;
    SharedPreferences.Editor prefedit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_booking3);

        Toolbar myTB = findViewById(R.id.my_toolbar);
        setSupportActionBar(myTB);
        ActionBar AB = getSupportActionBar();
        AB.setDisplayHomeAsUpEnabled(true);

        etFname = findViewById(R.id.editTextFirst);
        etLname = findViewById(R.id.editTextLast);
        etEmail = findViewById(R.id.editTextEmail);
        etPhone = findViewById(R.id.editTextPhone);
        etAddress = findViewById(R.id.editTextAddress);

        Session session = new Session(this);
        if (session.loggedinStatus() == true) {
            etFname.setVisibility(GONE);
            etLname.setVisibility(GONE);
            etEmail.setVisibility(GONE);
            etPhone.setVisibility(GONE);
        }



        pref = getSharedPreferences("mybooking",MODE_PRIVATE);
        prefedit = pref.edit();

        btnNext = findViewById(R.id.buttonConfirm);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = etFname.getText().toString().trim();
                String lname = etLname.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String address = etAddress.getText().toString().trim();

                prefedit.putString("fname",fname);
                prefedit.putString("lname",lname);
                prefedit.putString("email",email);
                prefedit.putString("contact",phone);
                prefedit.putString("address",address);

                prefedit.commit();

                startActivity(new Intent(ServiceBookingActivity3.this, ServiceBookingActivity4.class));
            }
        });
    }
}
