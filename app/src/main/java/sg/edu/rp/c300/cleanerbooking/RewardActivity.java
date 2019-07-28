package sg.edu.rp.c300.cleanerbooking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class RewardActivity extends AppCompatActivity {

    TextView tvCode,tvPoint,tvDesc;
    Button btnRedeem;
    private ActionBar titlebar;
    SharedPreferences pref;
    SharedPreferences.Editor prefedit;
    String code,desc,redeem;
    Integer id,point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        //AB.setDisplayHomeAsUpEnabled(true);
        //titlebar = getSupportActionBar();
        //titlebar.setTitle("Reward");
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);
//        titlebar.setTitle("Rewards");

        tvCode = findViewById(R.id.tvCode);
        tvPoint = findViewById(R.id.tvPoints);
        tvDesc = findViewById(R.id.tvDesc);
        btnRedeem = findViewById(R.id.btnRedeem);

        Intent i = getIntent();
        final Reward reward = (Reward)i.getSerializableExtra("reward");

        pref = getSharedPreferences("myrewards",MODE_PRIVATE);
        prefedit = pref.edit();
        prefedit.putInt("rewardId",reward.getId());
        prefedit.commit();

        pref = getSharedPreferences("pref2", MODE_PRIVATE);
        final String member_id = pref.getString("member_id", "");

        id = reward.getId();
        code = reward.getCode();
        point = reward.getPoints();
        desc = reward.getDesc();

        tvCode.setText(code);
        tvPoint.setText("POINTS REQUIRED: "+point);
        tvDesc.setText(desc);


        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //reward.setRedeem("yes");
                //redeem = reward.getRedeem();

                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.add("reward_id",id.toString());
                params.add("member_id",member_id);
                params.add("redeem","yes");
                params.add("point_required",point.toString());


                client.post("http://10.0.2.2/FYPCleanerAdmin/redeemReward.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        Log.i("JSON Results: ", response.toString());
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RewardActivity.this, HomeActivity.class);
                        //i.putExtra("rewardId", reward.getId());
                        //i.putExtra("rewardRedeem", reward.getRedeem());

                        startActivity(i);
                        Toast.makeText(getApplicationContext(),"Reward successfuly Redeemed!",Toast.LENGTH_LONG).show();

                    }
                });




            }
        });

    }
}
