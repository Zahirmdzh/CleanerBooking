package sg.edu.rp.c300.cleanerbooking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private ActionBar titlebar;
    ListView lv;

    ArrayList<Service> alService;
    ArrayAdapter aa;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_services:
                    mTextMessage.setText(R.string.title_activity_services);
                    titlebar.setTitle("Services");
                    return true;
                case R.id.navigation_booking:
                    mTextMessage.setText(R.string.title_booking);
                    titlebar.setTitle("Booking");
                    return true;
                case R.id.navigation_redeem:
                    mTextMessage.setText(R.string.title_redeem);
                    titlebar.setTitle("Redeem");
                    return true;
                case R.id.navigation_profile:
                    mTextMessage.setText(R.string.title_profile);
                    titlebar.setTitle("Profile");
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        titlebar = getSupportActionBar();
        titlebar.setTitle("Services");

//        Intent i = getIntent();
//        String msg = i.getStringExtra("username");
        SharedPreferences pref = getSharedPreferences("app",MODE_PRIVATE);
        String msg = pref.getString("email","");
        mTextMessage.setText("Welcome " + msg);

        lv = findViewById(R.id.listViewService);
        alService = new ArrayList<Service>();

        Service item1 = new Service("Home Cleaaning","We provide household services");
        alService.add(item1);

        aa = new HomeAdapter(HomeActivity.this,R.layout.home_row,alService);
        lv.setAdapter(aa);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_info) {
            Intent i = new Intent(this, ContactActivity.class);
            startActivity(i);

        }
        if(id == R.id.action_notification) {
            Intent i = new Intent(this, NotificationActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}


