package sg.edu.rp.c300.cleanerbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookingAdapter extends ArrayAdapter<Booking> {

    private ArrayList<Booking> booking;
    private Context context;
    private TextView name;
    private TextView date;

    public BookingAdapter(Context context, int resource, ArrayList<Booking> objects) {
        super(context, resource, objects);
        // Store the food that is passed to this adapter
        booking = objects;
        // Store Context object as we would need to use it later
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.booking_row, parent, false);

//        number = rowView.findViewById(R.id.tv_number);
        name = rowView.findViewById(R.id.tv_name);
        date = rowView.findViewById(R.id.tv_date);

        Booking currenTask = booking.get(position);

        for (int i=0;i<booking.size();i++) {
//            number.setText(i);
        }
        name.setText(currenTask.getService());
        date.setText(currenTask.getDateTime());

        return rowView;
    }
}
