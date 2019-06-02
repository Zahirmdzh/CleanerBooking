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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class HomeActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private ActionBar titlebar;
    ListView lv;

    ArrayList<Service> alService;
    ArrayAdapter aaService;

    ArrayAdapter book;
    ArrayList<Booking> alBooking;

    ArrayAdapter point;
    ArrayList<Reward> alReward;

    ArrayAdapter profile;
    ArrayList<Profile> alRecord;
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
                    alService = new ArrayList<Service>();

                    addToService();
                    return true;

                case R.id.navigation_booking:
//                    mTextMessage.setText(R.string.title_booking);
                    titlebar.setTitle("Booking");
//                    Intent i = new Intent(HomeActivity.this, BookingActivity.class);
//                    startActivity(i);
                    alBooking = new ArrayList<Booking>();

                    Booking itemB1 = new Booking("Home Booking","22/04/2019");
                    Booking itemB2 = new Booking("Home Booking","12/06/2021");

                    alBooking.add(itemB1);
                    alBooking.add(itemB2);

                    book = new BookingAdapter(HomeActivity.this,R.layout.booking_row,alBooking);
                    lv.setAdapter(book);
                    return true;

                case R.id.navigation_redeem:
//                    mTextMessage.setText(R.string.title_redeem);
                    titlebar.setTitle("Redeem");

                    alReward= new ArrayList<Reward>();

                    Reward reward1 = new Reward("Discount 1","50% Discount");
                    Reward reward2 = new Reward("Discount 2","Free Service");

                    alReward.add(reward1);
                    alReward.add(reward2);

                    point = new RewardAdapter(HomeActivity.this,R.layout.profile_row,alReward);
                    lv.setAdapter(point);
                    return true;

                case R.id.navigation_profile:
//                    mTextMessage.setText(R.string.title_profile);
                    titlebar.setTitle("Profile");

                    alRecord= new ArrayList<Profile>();

                    Profile record1 = new Profile("Loyalty Points");
                    Profile record2 = new Profile("Inbox");

                    alRecord.add(record1);
                    alRecord.add(record2);

                    profile = new ProfileAdapter(HomeActivity.this,R.layout.profile_row,alRecord);
                    lv.setAdapter(profile);
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

        alService = new ArrayList<Service>();
        aaService = new HomeAdapter(HomeActivity.this,R.layout.home_row,alService);
        lv.setAdapter(aaService);
//        addToService();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.2.2/FYPCleanerAdmin/getServices.php", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {


                try {
                    for (int i = 0; i <response.length();i++) {

                        JSONObject service = response.getJSONObject(i);
                        String serviceId = service.getString("service_id");
                        String name = service.getString("service_name");
                        String desc = service.getString("service_description");
                        Service s = new Service(serviceId, name, desc);
                        alService.add(s);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aaService.notifyDataSetChanged();
            }
        });


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

    public void addToService() {
        Service item1 = new Service("H111","Home Cleaning","We provide household services");
        Service item2 = new Service("H222","Office Cleaning","We provide office services");
        Service item3 = new Service("H333","Public area Cleaning","We provide household services");
        Service item4 = new Service("H444","Misc Cleaning","We provide household services");
        alService.add(item1);
        alService.add(item2);
        alService.add(item3);
        alService.add(item4);

        aaService = new HomeAdapter(HomeActivity.this,R.layout.home_row,alService);
        lv.setAdapter(aaService);
    }
}


