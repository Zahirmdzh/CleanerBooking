package sg.edu.rp.c300.cleanerbooking;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


public class RewardActivity extends AppCompatActivity {

    TextView tv;
    private ActionBar titlebar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar AB = getSupportActionBar();
        AB.setDisplayHomeAsUpEnabled(true);
        titlebar = getSupportActionBar();
        titlebar.setTitle("Reward");


        //tv=findViewById(R.id.textViewRedeem);
        Intent i = getIntent();
        Reward reward = (Reward)i.getSerializableExtra("reward");
        //tv.setText(reward.getName() + "\n" + reward.getDesc());


    }
}
