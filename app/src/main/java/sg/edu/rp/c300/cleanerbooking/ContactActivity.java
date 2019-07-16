package sg.edu.rp.c300.cleanerbooking;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import cz.msebera.android.httpclient.Header;

public class ContactActivity extends AppCompatActivity {

    ImageView ivPhone,ivMail;
    EditText etEmail, etEnquiry;
    Button btnSend;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        client = new AsyncHttpClient();

        Toolbar myTB = findViewById(R.id.my_toolbar);
        setSupportActionBar(myTB);
        ActionBar AB = getSupportActionBar();
        AB.setDisplayHomeAsUpEnabled(true);

        ivPhone = findViewById(R.id.ivPhone);
        ivMail = findViewById(R.id.ivEmail);
        etEmail = findViewById(R.id.editTextEmail);
        etEnquiry = findViewById(R.id.editTextEnquiry);
        btnSend = findViewById(R.id.buttonSend);

        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent. ACTION_DIAL, Uri.parse("tel:" +96982298));
                startActivity(i);

            }
        });

        ivMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ACTION_SEND is used to indicate sending text
                Intent email = new Intent(Intent.ACTION_SEND);

                // Put essentials like email address, subject & body text
                email.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{"greentacpteltd@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT,
                        "Enquiry#");

                // This MIME type indicates email
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email,"Choose an Email client: "));
            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           btnSendOnClick(v);


            }
        });

    }

    private void btnSendOnClick(View v) {
        String url = "http://10.0.2.2/FYPCleanerAdmin/addEnquiry.php";
        String email = etEmail.getText().toString();
        String enquiry = etEnquiry.getText().toString();

        RequestParams params = new RequestParams();
        params.add("email", email);
        params.add("enquiry", enquiry);


        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Log.i("JSON Results: ", response.toString());


                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Enquiry received", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
