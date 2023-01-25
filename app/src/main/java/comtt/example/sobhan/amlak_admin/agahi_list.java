package comtt.example.sobhan.amlak_admin;
import com.google.gson.annotations.SerializedName;

public class agahi_list {

    @SerializedName("agahiha")
    private agahi agahis[];

    public agahi[] getAgahis() {
        return agahis;
    }

    public void setAgahis(agahi[] agahis) {
        this.agahis = agahis;
    }
}
