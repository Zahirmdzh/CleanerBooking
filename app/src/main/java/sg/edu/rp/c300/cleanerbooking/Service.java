package sg.edu.rp.c300.cleanerbooking;

import android.media.Image;
import android.widget.ImageView;

public class Service {
    private String title;
    private String description;


    public Service(String title, String description) {
        this.title = title;
        this.description = description;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
