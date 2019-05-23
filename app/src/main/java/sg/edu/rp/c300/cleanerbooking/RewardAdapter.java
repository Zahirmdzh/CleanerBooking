package sg.edu.rp.c300.cleanerbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RewardAdapter extends ArrayAdapter<Reward> {

    private ArrayList<Reward> reward;
    private Context context;
    private TextView name;

    public RewardAdapter(Context context, int resource, ArrayList<Reward> objects) {
        super(context, resource, objects);
        // Store the food that is passed to this adapter
        reward = objects;
        // Store Context object as we would need to use it later
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout._row, parent, false);

//        number = rowView.findViewById(R.id.tv_number);
        name = rowView.findViewById(R.id.tv_name);
        date = rowView.findViewById(R.id.tv_date);

        Booking currenTask = booking.get(position);

        for (int i=0;i<booking.size();i++) {
//            number.setText(i);
        }
        name.setText(currenTask.getName());
        date.setText(currenTask.getDate());

        return rowView;
    }
}

