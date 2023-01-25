package comtt.example.sobhan.amlak_admin;
import com.google.gson.annotations.SerializedName;

public class agahi_list_rent {
    @SerializedName("agahiha")
    private agahi_rent agahis[];

    public agahi_rent[] getAgahis() {
        return agahis;
    }

    public void setAgahis(agahi_rent[] agahis) {
        this.agahis = agahis;
    }
}
