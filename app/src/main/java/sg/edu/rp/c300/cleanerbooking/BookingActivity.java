package sg.edu.rp.c300.cleanerbooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BookingActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter aa;
    ArrayList<Booking> booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        lv = findViewById(R.id.lv);

        booking = new ArrayList<Booking>();
        aa = new BookingAdapter(BookingActivity.this,R.layout.booking_row,booking);
        lv.setAdapter(aa);

        //String txt = "";
        //for (int i = 0; i < booking.size(); i++) {
        //    txt += i + ". " + data.get(i) + "\n";
        //    booking.add(data.get(i));
        //}
    }
}
