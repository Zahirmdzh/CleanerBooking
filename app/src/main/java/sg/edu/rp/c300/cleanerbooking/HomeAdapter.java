package sg.edu.rp.c300.cleanerbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeAdapter extends ArrayAdapter<Service> {
    Context context;
    ArrayList<Service> services;



    public HomeAdapter(Context context, int resource, ArrayList<Service> objects) {
        super(context, resource, objects);
        this.context = context;
        services = objects;

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.home_row, parent, false);

        //Match the UI components with Java variables
        TextView tvTitle = rowView.findViewById(R.id.textViewTitle);

        ImageView ivImage = rowView.findViewById(R.id.imageView);
        TextView tvTagline = rowView.findViewById(R.id.textViewTagline);



        //note position
        final Service currService = services.get(position);
        String tagline = currService.getTagline();
        String name = currService.getName();
        tvTagline.setText(tagline);
        tvTitle.setText(name);

        if (currService.getName().equalsIgnoreCase("Office Cleaning")) {
            ivImage.setImageResource(R.drawable.office);

        } else if (currService.getName().equalsIgnoreCase("Factory Cleaning")) {

            ivImage.setImageResource(R.drawable.factory);

        } else if (currService.getName().equalsIgnoreCase("Warehouse Cleaning")) {

            ivImage.setImageResource(R.drawable.general);

        }



        return rowView;


    }



}
