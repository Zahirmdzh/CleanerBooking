package sg.edu.rp.c300.cleanerbooking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookingActivity extends AppCompatActivity {

    private ActionBar titlebar;
    ListView lv;
    ArrayAdapter aa;
    ArrayList<Booking> booking;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_services:
                    titlebar.setTitle("Services");
                    return true;
                case R.id.navigation_booking:
                    titlebar.setTitle("Booking");
                    return true;
                case R.id.navigation_redeem:
                    titlebar.setTitle("Redeem");
                    return true;
                case R.id.navigation_profile:
                    titlebar.setTitle("Profile");
                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        lv = findViewById(R.id.lv);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        titlebar = getSupportActionBar();
        titlebar.setTitle("Booking");


        //String txt = "";
        //for (int i = 0; i < booking.size(); i++) {
        //    txt += i + ". " + data.get(i) + "\n";
        //    booking.add(data.get(i));
        //}
    }
}
