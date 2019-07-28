package sg.edu.rp.c300.cleanerbooking;

public class History {
    private String name;
    private String use;

    public History(String name, String use) {
        this.name = name;
        this.use = use;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }
}
