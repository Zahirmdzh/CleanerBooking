package sg.edu.rp.c300.cleanerbooking;

import java.io.Serializable;

public class Booking implements Serializable {

    private String id;
    private String service;
    private String dateTime;
    private String status;

    public Booking(String id,String service, String dateTime, String status) {
        this.id = id;
        this.service = service;
        this.dateTime = dateTime;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getService() {
        return service;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getStatus() {
        return status;
    }
}




