package comtt.example.sobhan.amlak_admin;

import java.io.Serializable;

public class User implements Serializable {

    String username,password,family,senficode,image_url;
    int id;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFamily() {
        return family;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getSenficode() {
        return senficode;
    }

    public void setSenficode(String senficode) {
        this.senficode = senficode;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public int getId() {
        return id;
    }
}
