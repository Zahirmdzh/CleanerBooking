package sg.edu.rp.c300.cleanerbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileAdapter extends ArrayAdapter<Profile> {

    private ArrayList<Profile> profile;
    private Context context;
    private TextView name,address,phone,email,point;

    public ProfileAdapter(Context context, int resource, ArrayList<Profile> objects) {
        super(context, resource, objects);
        // Store the food that is passed to this adapter
        profile = objects;
        // Store Context object as we would need to use it later
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.profile_row, parent, false);



        Profile currenTask = profile.get(position);

        for (int i=0;i<profile.size();i++) {
//            number.setText(i);
        }
        name.setText(currenTask.getName());

        return rowView;
    }
}