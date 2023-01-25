package comtt.example.sobhan.amlak_admin;

import com.google.gson.annotations.SerializedName;

public class test_get_sale {
    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    @SerializedName("title")
    private String title;

    @SerializedName("info")
    private String info;

    @SerializedName("cost")
    private int cost;

    @SerializedName("phone")
    private String phone;

    @SerializedName("area")
    private String area;

    @SerializedName("address")
    private String address;

    @SerializedName("metr")
    private int metr;

    @SerializedName("sanad")
    private boolean sanad;

    @SerializedName("vila")
    private boolean vila;

    @SerializedName("shoper")
    private String shoper;

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public int getCost() {
        return cost;
    }

    public String getPhone() {
        return phone;
    }

    public String getArea() {
        return area;
    }

    public String getAddress() {
        return address;
    }

    public int getMetr() {
        return metr;
    }

    public boolean isSanad() {
        return sanad;
    }

    public boolean isVila() {
        return vila;
    }

    public String isShoper() {
        return shoper;
    }
}
