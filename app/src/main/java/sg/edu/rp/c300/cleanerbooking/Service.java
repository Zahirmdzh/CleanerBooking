package sg.edu.rp.c300.cleanerbooking;


import java.io.Serializable;

public class Service implements Serializable {
    private String id;
    private String name;
    private String tagline;
    private String description;


    public Service(String id, String name, String tagline, String description) {
        this.id = id;
        this.name = name;
        this.tagline = tagline;
        this.description = description;

    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTagline() {
        return tagline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
