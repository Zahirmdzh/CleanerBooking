package sg.edu.rp.c300.cleanerbooking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private ActionBar titlebar;
    ArrayAdapter aa;
    ArrayList<Profile> alProfile;

    ListView lv;
    TextView tvName;
    Button btnEditProfile, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar myTB = findViewById(R.id.my_toolbar);
        setSupportActionBar(myTB);
        ActionBar AB = getSupportActionBar();
        AB.setDisplayHomeAsUpEnabled(true);


        lv = findViewById(R.id.lv);
        btnLogout = findViewById(R.id.btnLogout);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        titlebar = getSupportActionBar();
        titlebar.setTitle("Profile");

        tvName=findViewById(R.id.textViewName);
        tvName.setText(R.string.title_profile);
        titlebar.setTitle("Profile");

        alProfile = new ArrayList<Profile>();

        Profile record1 = new Profile("Loyalty Points");
        Profile record2 = new Profile("Inbox");

        alProfile.add(record1);
        alProfile.add(record2);

        aa = new ProfileAdapter(ProfileActivity.this, R.layout.profile_row, alProfile);
        lv.setAdapter(aa);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
