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
    private TextView name,desc;

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
        View rowView = inflater.inflate(R.layout.reward_row, parent, false);

//        number = rowView.findViewById(R.id.tv_number);
        name = rowView.findViewById(R.id.tvName);
        desc = rowView.findViewById(R.id.tvDesc);


        Reward currenTask = reward.get(position);


        name.setText(currenTask.getName());
        desc.setText(currenTask.getDesc());

        return rowView;
    }
}

