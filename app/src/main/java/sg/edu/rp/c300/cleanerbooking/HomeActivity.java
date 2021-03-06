package sg.edu.rp.c300.cleanerbooking;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;

import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;

public class HomeActivity extends AppCompatActivity {
    private TextView mTextMessage;


    private ActionBar titlebar;
    ListView lv;

    ArrayList<Service> alService;
    ArrayAdapter aaService;

    ArrayAdapter book;
    ArrayList<Booking> alBooking;

    ArrayAdapter aaReward;
    ArrayList<Reward> alReward;

    ArrayAdapter aaHistory;
    ArrayList<History> alHistory;

    SharedPreferences pref;


    private Session session;

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

                    alBooking = new ArrayList<Booking>();

   /*                 Booking itemB1 = new Booking("Home Booking", "22/04/2019");
                    Booking itemB2 = new Booking("Home Booking", "12/06/2021");
                    alBooking.add(itemB1);
                    alBooking.add(itemB2);
*/
                    book = new BookingAdapter(HomeActivity.this, R.layout.booking_row, alBooking);
                    lv.setAdapter(book);

                    AsyncHttpClient client = new AsyncHttpClient();
                    //FOR REGISTERED MEMBER
                    if (session.loggedinStatus() == true) {
                        pref = getSharedPreferences("pref2", MODE_PRIVATE);
                        String member_id = pref.getString("member_id", "");
                        Log.d("member_id", member_id);
//                        String email = pref.getString("email","");


                        RequestParams params = new RequestParams();

                        params.put("member_id", member_id);
//                        params.put("email",email);

                        String url2 = "https://nogoodcodes.000webhostapp.com/getBookingAndroid.php";
                        //String ur12 = "http://10.0.2.2/FYPCleanerAdmin/getBookingAndroid.php";

                        client.post(url2, params, new JsonHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                                try {
                                    Log.i("RESPONSE", response.toString());
                                    for (int i = 0; i < response.length(); i++) {

                                        JSONObject booking = response.getJSONObject(i);
                                        String bokingId = booking.getString("booking_id");
                                        String serviceName = booking.getString("booking_service");
                                        String dateTime = booking.getString("booking_date_time");
                                        String status = booking.getString("booking_status");
                                        String request = booking.getString("booking_request");
                                        String address = booking.getString("booking_address");
                                        Booking b = new Booking(bokingId, serviceName, dateTime, status, request, address);
                                        alBooking.add(b);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                book.notifyDataSetChanged();
                            }
                        });

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                                Booking bookingSelected = alBooking.get(pos);  // Get the selected Category
                                Intent intent = new Intent(HomeActivity.this, ViewBookingActivity.class);
                                intent.putExtra("booking", bookingSelected);
                                startActivity(intent);
                            }
                        });
                    }


                    //FOR GUEST MEMBER
                    if (session.loggedinStatus() == false) {

                        pref = getSharedPreferences("APP", MODE_PRIVATE);
                        String key = pref.getString("uniquekey", "");

                        RequestParams params = new RequestParams();

                        params.put("uniquekey", key);

                        String url1 = "https://nogoodcodes.000webhostapp.com/getBookingToAndroidForGuest.php";
                        //String url = "http://10.0.2.2/FYPCleanerAdmin/getBookingToAndroidForGuest.php";

                        client.post(url1, params, new JsonHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                alBooking.clear();

                                try {
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject booking = response.getJSONObject(i);
                                        String id = booking.getString("booking_id");
                                        String name = booking.getString("booking_service");
                                        String date = booking.getString("booking_date_time");
                                        String status = booking.getString("booking_status");
                                        String request = booking.getString("booking_request");
                                        String address = booking.getString("booking_address");
                                        Log.d("DATEFOR", date);

                                        Booking b = new Booking(id, name, date, status, request, address);
                                        alBooking.add(b);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                book.notifyDataSetChanged();
                            }
                        });

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                                Booking bookingSelected = alBooking.get(pos);  // Get the selected Booking
                                Intent intent = new Intent(HomeActivity.this, ViewBookingActivity.class);


                                intent.putExtra("booking", bookingSelected);
                                startActivity(intent);
                            }
                        });

                    }
                    return true;

                case R.id.navigation_redeem:
//                    mTextMessage.setText("Choose from a range of offers");
                    titlebar.setTitle("Redeem");


                    alReward = new ArrayList<Reward>();
                    aaReward = new RewardAdapter(HomeActivity.this, R.layout.reward_row, alReward);
                    lv.setAdapter(aaReward);

                    AsyncHttpClient client1 = new AsyncHttpClient();

                    String url = "https://nogoodcodes.000webhostapp.com/getRewards.php";
                    //String url = "http://10.0.2.2/FYPCleanerAdmin/getRewards.php";

                    client1.get(url, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            alReward.clear();
                            Log.i("REWARD", response.toString());
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject reward = response.getJSONObject(i);

                                    Integer id = reward.getInt("reward_id");
                                    String code = reward.getString("reward_code");
                                    String desc = reward.getString("reward_description");
                                    Integer points = reward.getInt("point_required");
                                    //String redeem = reward.getString("redeem");

                                    Reward s = new Reward(id, code, desc, points);
                                    alReward.add(s);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            aaReward.notifyDataSetChanged();
                        }
                    });

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                            Reward rewardSelected = alReward.get(pos);  // Get the selected Category
                            if (session.loggedinStatus() == true) {
                                Intent intent = new Intent(HomeActivity.this, RewardActivity.class);
                                intent.putExtra("reward", rewardSelected);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Rewards can be redeemed by registered member only!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                    return true;

                case R.id.navigation_history:
//                    mTextMessage.setText("History of redeemed offers");
                    titlebar.setTitle("Redeem History");

                 /*   alReward = new ArrayList<Reward>();
                    addToRedeem();*/

                    alHistory = new ArrayList<History>();
                    aaHistory = new HistoryAdapter(HomeActivity.this, R.layout.history_row, alHistory);
                    lv.setAdapter(aaHistory);

                    if (session.loggedinStatus() == true) {

                        pref = getSharedPreferences("pref2", MODE_PRIVATE);
                        final String member_id = pref.getString("member_id", "");

                        RequestParams params = new RequestParams();
                        params.put("member_id", member_id);

                        AsyncHttpClient client2 = new AsyncHttpClient();

                        String url1 = "https://nogoodcodes.000webhostapp.com/redeemHistory.php";
                        //String url = "http://10.0.2.2/FYPCleanerAdmin/redeemHistory.php";
                        client2.post(url1,params, new JsonHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                alHistory.clear();
                                Log.i("RedeemHistory", response.toString());
                                try {
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject history = response.getJSONObject(i);

                                        String name = history.getString("reward_code");
                                        String used = history.getString("used");

                                        History h = new History(name,used);
                                        alHistory.add(h);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                aaHistory.notifyDataSetChanged();
                            }
                        });

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                                History redeemSelected = alHistory.get(pos);  // Get the selected Category
                                //String name = redeemSelected.getName();
                                String use = redeemSelected.getUse();
                                if (use.equalsIgnoreCase("yes")) {
                                    Toast.makeText(getApplicationContext(), "This reward has been used", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(getApplicationContext(), "This reward has not been used", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else{
                        Toast.makeText(getApplicationContext(),"Points are rewarded only for registered members only",Toast.LENGTH_LONG).show();
                    }

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


        session = new Session(this);
        Boolean stat = session.loggedinStatus();
        Log.d("STATUSS", String.valueOf(stat));


        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        titlebar = getSupportActionBar();
        titlebar.setTitle("Services");

        alService = new ArrayList<Service>();
        aaService = new HomeAdapter(HomeActivity.this, R.layout.home_row, alService);
        lv.setAdapter(aaService);

        String url = "https://nogoodcodes.000webhostapp.com/getServices.php";
        //String url = "http://10.0.2.2/FYPCleanerAdmin/getServices.php";


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject service = response.getJSONObject(i);
                        String serviceId = service.getString("service_id");
                        String name = service.getString("service_name");
                        String tagline = service.getString("service_tagline");
                        String desc = service.getString("service_description");
                        Service s = new Service(serviceId, name,tagline, desc);
                        alService.add(s);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aaService.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Service serviceSelected = alService.get(pos);  // Get the selected Category
                Intent intent = new Intent(HomeActivity.this, ServiceBookingActivity.class);
                intent.putExtra("service", serviceSelected);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_info) {
            Intent i = new Intent(this, ContactActivity.class);
            startActivity(i);

        }

        if (id == R.id.action_profile) {
            SharedPreferences pref = getSharedPreferences("app", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            if (session.loggedinStatus() == true) {
                Intent i = new Intent(this, ProfileActivity.class);
                startActivity(i);
            }else{
                item.setVisible(false);
                Toast.makeText(getApplicationContext(),"Profiles exist for registered member only.",Toast.LENGTH_LONG).show();
            }
        }
        if (id == R.id.action_logout) {
            SharedPreferences pref = getSharedPreferences("app", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


    public void addToService() {

        alService = new ArrayList<Service>();
        aaService = new HomeAdapter(HomeActivity.this, R.layout.home_row, alService);
        lv.setAdapter(aaService);

        String url = "https://nogoodcodes.000webhostapp.com/getServices.php";
        //String url = "http://10.0.2.2/FYPCleanerAdmin/getServices.php";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {


                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject service = response.getJSONObject(i);
                        String serviceId = service.getString("service_id");
                        String name = service.getString("service_name");
                        String tagline = service.getString("service_tagline");
                        String desc = service.getString("service_description");
                        Service s = new Service(serviceId, name, tagline, desc);
                        alService.add(s);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aaService.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Service serviceSelected = alService.get(pos);  // Get the selected Category
                Intent intent = new Intent(HomeActivity.this, ServiceBookingActivity.class);
                intent.putExtra("service", serviceSelected);
                startActivity(intent);
            }
        });
    }
}





