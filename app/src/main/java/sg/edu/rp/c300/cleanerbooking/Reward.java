package sg.edu.rp.c300.cleanerbooking;

import java.io.Serializable;

public class Reward implements Serializable {

    private String name;
    private String desc;


    public Reward(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
