package sg.edu.rp.c300.cleanerbooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RewardActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter aaReward;
    ArrayList<Reward> alReward;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        lv = findViewById(R.id.listViewReward);
        alReward = new ArrayList<Reward>();
        aaReward = new RewardAdapter(RewardActivity.this,R.layout.reward_row,alReward);
        lv.setAdapter(aaReward);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.2.2/FYPCleanerAdmin/getRewards.php", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {


                try {
                    for (int i = 0; i <response.length();i++) {

                        JSONObject reward = response.getJSONObject(i);
                        String rewardId = reward.getString("reward_code");
                        String name = reward.getString("reward_name");
                        String desc = reward.getString("reward_description");
                        Reward s = new Reward(name, desc);
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
                Intent intent = new Intent(RewardActivity.this, ServiceBookingActivity.class);
                intent.putExtra("reward",rewardSelected);
                startActivity(intent);
            }
        });
    }
}
