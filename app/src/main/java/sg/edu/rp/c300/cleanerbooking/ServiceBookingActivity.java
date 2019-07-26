package sg.edu.rp.c300.cleanerbooking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ServiceBookingActivity extends AppCompatActivity {

    TextView tvName,tvDesc;
    private ActionBar titlebar;

    Button btn;
    SharedPreferences pref;
    SharedPreferences.Editor prefedit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_booking);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar AB = getSupportActionBar();
        AB.setDisplayHomeAsUpEnabled(true);
        titlebar = getSupportActionBar();
        titlebar.setTitle("Service");




        tvName=findViewById(R.id.textViewName);
        tvDesc=findViewById(R.id.textViewDesc);
        Intent i = getIntent();
        final Service service = (Service)i.getSerializableExtra("service");
        tvName.setText(service.getName());
        tvDesc.setText(service.getDescription());

        pref = getSharedPreferences("mybooking",MODE_PRIVATE);
        prefedit = pref.edit();


        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefedit.putString("servicename",service.getName());
                prefedit.commit();
                startActivity(new Intent(ServiceBookingActivity.this, ServiceBookingActivity2.class));
            }
        });

    }
}
