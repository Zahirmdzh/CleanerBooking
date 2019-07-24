package sg.edu.rp.c300.cleanerbooking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.view.View.GONE;

public class ServiceBookingActivity3 extends AppCompatActivity {

    Button btnNext;
    EditText etFname, etLname, etEmail, etPhone, etAddress;
    TextView tvErrorFname, tvErrorLname, tvErrorEmail, tvErrorPhone, tvErrorAddress;
    SharedPreferences pref;
    SharedPreferences.Editor prefedit;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_booking3);



        Toolbar myTB = findViewById(R.id.my_toolbar);
        setSupportActionBar(myTB);
        ActionBar AB = getSupportActionBar();
        AB.setDisplayHomeAsUpEnabled(true);
        //Error Fields
        tvErrorFname = findViewById(R.id.textViewErrorFirst);
        tvErrorLname = findViewById(R.id.textViewErrorLast);
        tvErrorEmail = findViewById(R.id.textViewErrorEmail);
        tvErrorPhone = findViewById(R.id.textViewErrorPhone);
        tvErrorAddress = findViewById(R.id.textViewErrorAddress);

        etFname = findViewById(R.id.editTextFirst);
        etLname = findViewById(R.id.editTextLast);
        etEmail = findViewById(R.id.editTextEmail);
        etPhone = findViewById(R.id.editTextPhone);
        etAddress = findViewById(R.id.editTextAddress);

        session = new Session(this);
        if (session.loggedinStatus() == true) {
            etFname.setVisibility(GONE);
            etLname.setVisibility(GONE);
            etEmail.setVisibility(GONE);
            etPhone.setVisibility(GONE);

        }


        pref = getSharedPreferences("mybooking", MODE_PRIVATE);
        prefedit = pref.edit();


        checkET();


        btnNext = findViewById(R.id.buttonConfirm);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = etFname.getText().toString().trim();
                String lname = etLname.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String address = etAddress.getText().toString().trim();


                if (fname.isEmpty() && session.loggedinStatus() == false) {
                    etFname.setError("Required");
                    tvErrorFname.setText("First Name required. Please enter your first name");

                } else if (lname.isEmpty() && session.loggedinStatus() == false) {
                    etLname.setError("Required");
                    tvErrorLname.setText("Last Name required. Please enter your last name");


                } else if (email.isEmpty() && !isValidEmail(email) && session.loggedinStatus() == false) {
                    etEmail.setError("Required");
                    tvErrorEmail.setText("Email required. Please enter your email address");


                } else if (phone.isEmpty() && phone.length() != 8 && session.loggedinStatus() == false  ) {
                    etPhone.setError("Required");
                    tvErrorPhone.setText("Phone required. Please enter your phone number");


                } else if (address.isEmpty() && address.length() < 120) {
                    etAddress.setError("Required");
                    tvErrorAddress.setText("Address required. Please enter your Address");

                } else {


                    prefedit.putString("fname", fname);
                    prefedit.putString("lname", lname);
                    prefedit.putString("email", email);
                    prefedit.putString("contact", phone);
                    prefedit.putString("address", address);

                    prefedit.commit();

                    startActivity(new Intent(ServiceBookingActivity3.this, ServiceBookingActivity4.class));
                }
            }
        });
    }

    private void checkET() {

        etFname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (!etFname.getText().toString().isEmpty() && s.length() > 0)
                {
                    tvErrorFname.setText("");
                    etFname.setError(null);
                }
                else
                {
                    tvErrorFname.setText("First Name required. Please enter your first name");
                    etFname.setError("Required");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });


        etLname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (!etLname.getText().toString().isEmpty() && s.length() > 0)
                {
                    tvErrorLname.setText("");
                    etLname.setError(null);
                }
                else
                {
                    tvErrorLname.setText("Last name required. Please enter your last name");
                    etLname.setError("Required");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (!etEmail.getText().toString().isEmpty() && isValidEmail(etEmail.getText().toString()) && s.length() > 0)
                {
                    tvErrorEmail.setText("");
                    etEmail.setError(null);
                }
                else
                {
                    tvErrorEmail.setText("Email needs to have @ and end with .com");
                    etEmail.setError("Invalid");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });


        etPhone.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (!etPhone.getText().toString().isEmpty() && etPhone.length() ==8 && s.length() > 0 && (etPhone.getText().toString().startsWith("9") || etPhone.getText().toString().startsWith("9")))
                {
                    tvErrorPhone.setText("");
                    etPhone.setError(null);
                }
                else
                {
                    tvErrorPhone.setText("Phone number must have 8 digits");
                    etPhone.setError("Invalid");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });

        etAddress.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (!etAddress.getText().toString().isEmpty() && etAddress.length() < 120 && s.length() > 0)
                {
                    tvErrorAddress.setText("");
                    etAddress.setError(null);
                }
                else
                {
                    tvErrorAddress.setText("Max 120 characters");
                    etAddress.setError("Chars Exceeded");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });




    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    public void onBackPressed() {
        prefedit.clear();
        prefedit.commit();
        super.onBackPressed();
    }
/*
    @Override
    protected void onRestart() {
        prefedit.clear();
        prefedit.commit();
        etFname.setText("");
        etLname.setText("");
        etEmail.setText("");
        etPhone.setText("");
        etAddress.setText("");
        super.onRestart();
    }

    @Override
    protected void onStop() {
        prefedit.clear();
        prefedit.commit();

        super.onStop();
    }
*/

    @Override
    protected void onDestroy() {
        prefedit.clear();
        prefedit.commit();
        super.onDestroy();
    }
}
