package comtt.example.sobhan.amlak_admin;
import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;
public class agahi {
    @SerializedName("name")
    private String name;

    @SerializedName("info")
    private String info;

    @SerializedName("cost")
    private String cost;

    @SerializedName("phone")
    private String phone;

    @SerializedName("area")
    private String area;

    @SerializedName("address")
    private String address;

    @SerializedName("metr")
    private int metr;

    @SerializedName("id")
    private int id;

    @SerializedName("type")
    private boolean type;

    @SerializedName("sanad")
    private boolean sanad;

    @SerializedName("image")
    private String image;

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getCost() {
        return cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMetr() {
        return metr;
    }

    public void setMetr(int metr) {
        this.metr = metr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public boolean isSanad() {
        return sanad;
    }

    public void setSanad(boolean sanad) {
        this.sanad = sanad;
    }

    public String getImage() {
        return image;
    }
}
