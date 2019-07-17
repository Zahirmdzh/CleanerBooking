package sg.edu.rp.c300.cleanerbooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RescheduleBookingActivity extends AppCompatActivity {

    TextView tvDate,tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reschedule_booking);

        tvDate = findViewById(R.id.textViewDate);
        tvTime = findViewById(R.id.textViewStartTime);

    }
}
