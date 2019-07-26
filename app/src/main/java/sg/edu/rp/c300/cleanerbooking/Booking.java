package sg.edu.rp.c300.cleanerbooking;

import java.io.Serializable;

public class Booking implements Serializable {

    private String id;
    private String service;
    private String dateTime;
    private String status;
    private String request;
//    private String address;


    public Booking(String id,String service, String dateTime, String status, String request) {
        this.id = id;
        this.service = service;
        this.dateTime = dateTime;
        this.status = status;
        this.request = request;
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

    public String getRequest() {
        return request;
    }

}




