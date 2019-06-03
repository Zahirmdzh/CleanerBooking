package sg.edu.rp.c300.cleanerbooking;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class ServiceBookingActivity extends AppCompatActivity {

    TextView tv;
    private ActionBar titlebar;
    ImageView iv;

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

        iv = findViewById(R.id.imageView2);

        tv=findViewById(R.id.textViewName);
        Intent i = getIntent();
        Service service = (Service)i.getSerializableExtra("service");
        tv.setText(service.getName() + "\n" + service.getDescription());

    }
}
