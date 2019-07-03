package sg.edu.rp.c300.cleanerbooking;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;


public class SignupActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^" +
                    "(?=.*(8|9))" +
                    "$");

    EditText etFullName, etEmail, etPass, etCfmpass;
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar myTB = findViewById(R.id.my_toolbar);
        setSupportActionBar(myTB);
        ActionBar AB = getSupportActionBar();
        AB.setDisplayHomeAsUpEnabled(true);

        etFullName = findViewById(R.id.editTextFullname);
        etEmail = findViewById(R.id.editTextEmail);
        etPass = findViewById(R.id.editTextPassword);
        etCfmpass = findViewById(R.id.editTextPasswordCfm);
        btnSubmit = findViewById(R.id.buttonSubmit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etFullName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                String cfmpass = etCfmpass.getText().toString();

                if (validateFullname() && validateEmail() && validatePassword() && pass.equals(cfmpass)) {
                    //long value = db.registerUser(user, phone, email, pass);
                    String type="reg";
                    BackgroundTask backgroundTask= new BackgroundTask(getApplicationContext());
                    backgroundTask.execute(type, name, email, pass);

                        toastMsg("Sign Up successful!");
                        Intent i = new Intent(SignupActivity.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        toastMsg("There are incompleted fields");
                    }
                }

        });
    }

    private void toastMsg(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private boolean validateEmail() {
        String emailInput = etEmail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            etEmail.setError("Field is required");
            return false;

            // use regex   Cursor on EMAIL_ADDRESS press ctrl + B
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            etEmail.setError("Please enter a valid email address");
            return false;

        } else {
            etEmail.setError(null);
            return true;

        }

    }

    private boolean validatePassword() {
        String passwordInput = etPass.getText().toString().trim();
        String cfmpassword = etCfmpass.getText().toString().trim();

        if (passwordInput.isEmpty() || cfmpassword.isEmpty()) {
            etPass.setError("Field is required");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            etPass.setError("At least 4 characters");
            return false;
        } else if (!cfmpassword.equals(passwordInput)) {

            etCfmpass.setError("Password does not match");
            return false;
        } else {
            etPass.setError(null);
            return true;
        }
    }

    private boolean validateFullname() {
        String usernameInput = etFullName.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            etFullName.setError("Field is required");
            return false;
        } else {
            etFullName.setError(null);
            return true;
        }
    }
//
//    private boolean validatePhone() {
//        String phone = etPhone.getText().toString().trim();
//
//        if (phone.isEmpty()) {
//            etPhone.setError("Field is required");
//            return false;
//        } else if (!PHONE_PATTERN.matcher(phone).matches() && phone.length() != 8) { // change
//            etPhone.setError("Must start with 8 or 9 with 8 digits ");
//            return false;
//        } else {
//            etPhone.setError(null);
//            return true;
//        }
//    }



}






















