package sg.edu.rp.c300.cleanerbooking;

public class Profile {

    private Integer id;
    private String name;
    private Integer mobile;
    private String address;
    private String email;
    private Integer points;

    public Profile(Integer id, String name, Integer mobile, String address, String email, Integer points) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.email = email;
        this.points = points;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}