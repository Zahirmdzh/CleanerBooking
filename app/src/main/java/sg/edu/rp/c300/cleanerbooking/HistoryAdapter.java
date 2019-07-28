package sg.edu.rp.c300.cleanerbooking;

import android.content.Context;
import android.icu.text.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<History> {

    private ArrayList<History> history;
    private Context context;
    private TextView name;
    private ImageView iv;

    public HistoryAdapter(Context context, int resource, ArrayList<History> objects) {
        super(context, resource, objects);
        // Store the food that is passed to this adapter
        history = objects;
        // Store Context object as we would need to use it later
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.history_row, parent, false);

        name = rowView.findViewById(R.id.tvHistoryName);
        iv = rowView.findViewById(R.id.ivUse);

        History currenHistory = history.get(position);

        name.setText(currenHistory.getName());
        String use = currenHistory.getUse();

        if (use.equalsIgnoreCase("yes")){
            iv.setImageResource(R.drawable.tick);
        }else{
            iv.setImageResource(R.drawable.cross);
        }
        return rowView;
    }
}

