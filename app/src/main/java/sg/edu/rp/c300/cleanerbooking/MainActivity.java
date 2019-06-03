package sg.edu.rp.c300.cleanerbooking;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button btnlogin, btnsignup;
    TextView tvguest;
    EditText etEmail, etPass;
    DBHelper db;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnlogin = findViewById(R.id.buttonLogin);
        btnsignup = findViewById(R.id.buttonSignup);
        tvguest = findViewById(R.id.textViewGuest);
        etEmail = findViewById(R.id.editTextEmail);
        etPass = findViewById(R.id.editTextPassword);
        db = new DBHelper(this);
        session = new Session(this);

        if(session.loggedinStatus()){
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }

        tvguest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();

                Boolean result = db.checkUser(email, pass);
                if (result == true) {
                    Toast.makeText(MainActivity.this,"Welcome! You are now logged in",
                            Toast.LENGTH_LONG).show();

                    session.setLoggedIn(true);
                    session.saveEmail(email);

                    Intent i = new Intent(MainActivity.this,HomeActivity.class);

                    startActivity(i);

                } else if(email.isEmpty()){
                    etEmail.setError("Empty field");

                } else if(pass.isEmpty()){
                    etPass.setError("Empty field");

                }else {

                     Toast.makeText(MainActivity.this,"Account does not exist!",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(MainActivity.this,SignupActivity.class);
               startActivity(i);

            }
        });
    }

    private void toastMsg(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void emptyEmail() {
        etEmail.setText("");

    }
    private void emptyPass() {
        etPass.setText("");
    }
}
