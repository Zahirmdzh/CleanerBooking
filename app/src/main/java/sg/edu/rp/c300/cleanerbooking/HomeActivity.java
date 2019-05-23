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

    ArrayAdapter book;
    ArrayList<Booking> booking;

//    SharedPreferences pref = getSharedPreferences("app",MODE_PRIVATE);
//    String msg = pref.getString("email","");


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_services:
//                    mTextMessage.setText(R.string.title_activity_services);
                    titlebar.setTitle("Services");
                    mTextMessage.setText("");
                    alService = new ArrayList<Service>();

                    Service item1 = new Service("Home Cleaning","We provide household services");
                    alService.add(item1);

                    aa = new HomeAdapter(HomeActivity.this,R.layout.home_row,alService);
                    lv.setAdapter(aa);
                    return true;

                case R.id.navigation_booking:
//                    mTextMessage.setText(R.string.title_booking);
                    titlebar.setTitle("Booking");
                    mTextMessage.setText("");
//                    Intent i = new Intent(HomeActivity.this, BookingActivity.class);
//                    startActivity(i);
                    booking = new ArrayList<Booking>();

                    Booking itemB1 = new Booking("Home Cleaning","22/04/1987");
                    Booking itemB2 = new Booking("Home Booking","12/06/2021");

                    booking.add(itemB1);
                    booking.add(itemB2);

                    book = new BookingAdapter(HomeActivity.this,R.layout.booking_row,booking);
                    lv.setAdapter(book);
                    return true;

                case R.id.navigation_redeem:
//                    mTextMessage.setText(R.string.title_redeem);
                    titlebar.setTitle("Redeem");
                    //mTextMessage.setText("You have 300 Loyalty Points");
                    return true;

                case R.id.navigation_profile:
//                    mTextMessage.setText(R.string.title_profile);
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
        lv = findViewById(R.id.listViewService);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        titlebar = getSupportActionBar();
        titlebar.setTitle("Services");

//        Intent i = getIntent();
//        String msg = i.getStringExtra("username");
        alService = new ArrayList<Service>();

        Service item1 = new Service("Home Cleaning","We provide household services");
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


