package sg.edu.rp.c300.cleanerbooking;


public class Service {
    private String id;
    private String name;
    private String description;


    public Service(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;

    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
