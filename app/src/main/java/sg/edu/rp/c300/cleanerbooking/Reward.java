package sg.edu.rp.c300.cleanerbooking;

import java.io.Serializable;

public class Reward implements Serializable {

    private Integer id;
    private String code;
    private String desc;
    private Integer points;

    public Reward(Integer id, String code, String desc, Integer points) {
        this.id = id;
        this.code = code;
        this.desc = desc;
        this.points = points;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

}