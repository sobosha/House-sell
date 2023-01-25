package comtt.example.sobhan.amlak_admin;

import com.google.gson.annotations.SerializedName;

public class message {

    @SerializedName("id")
    private int id;

    @SerializedName("shoper_name")
    private String shoper;

    @SerializedName("message")
    private String message;

    @SerializedName("read_message")
    private boolean read;

    public int getId() {
        return id;
    }

    public String getShoper() {
        return shoper;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return read;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShoper(String shoper) {
        this.shoper = shoper;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
