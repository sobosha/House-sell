package comtt.example.sobhan.amlak_admin;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class user_ret {

    @SerializedName("name")
    private String name;

    @SerializedName("image_url")
    private String image_url;

    @SerializedName("correct")
    private boolean correct;

    @SerializedName("senfi_code")
    private String senfi_code;

    @SerializedName("family")
    private String family;

    @SerializedName("password")
    private String  password;

    @SerializedName("id")
    private int  id;

    public user_ret(String name,boolean correct,String image_url,String password,String family,String senfi_code,int id) {
        this.name = name;
        this.correct=correct;
        this.image_url=image_url;
        this.password = password;
        this.family=family;
        this.senfi_code=senfi_code;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getSenfi_code() {
        return senfi_code;
    }

    public void setSenfi_code(String senfi_code) {
        this.senfi_code = senfi_code;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
