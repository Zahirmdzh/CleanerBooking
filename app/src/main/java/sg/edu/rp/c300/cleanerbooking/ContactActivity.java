package sg.edu.rp.c300.cleanerbooking;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.net.URI;

public class ContactActivity extends AppCompatActivity {

    ImageView ivPhone,ivMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar myTB = findViewById(R.id.my_toolbar);
        setSupportActionBar(myTB);
        ActionBar AB = getSupportActionBar();
        AB.setDisplayHomeAsUpEnabled(true);

        ivPhone = findViewById(R.id.ivPhone);
        ivMail = findViewById(R.id.ivEmail);

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


    }
}
