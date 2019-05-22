package sg.edu.rp.c300.cleanerbooking;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.home_row, parent, false);

        //Match the UI components with Java variables
        TextView tvTitle = rowView.findViewById(R.id.textViewTitle);

        ImageView ivImage = rowView.findViewById(R.id.imageView);
        TextView tvDesc = rowView.findViewById(R.id.textViewDesc);
        Button btnBook = rowView.findViewById(R.id.buttonBook);


        //note position
        Service currService = services.get(position);
        String description = currService.getDescription();
        String title = currService.getTitle();
        tvDesc.setText(description);
        tvTitle.setText(title);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent zoom=new Intent(parent.getContext(), NotificationActivity.class);
                parent.getContext().startActivity(zoom);
            }
        });

        return rowView;


    }

}
