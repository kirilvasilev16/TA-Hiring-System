package authentication.entities;

public class Authentication {
    private String netID;
    private String name;

    public Authentication(String netID, String name) {
        this.netID = netID;
        this.name = name;
    }

    public String getNetID() {
        return netID;
    }

    public void setNetID(String netID) {
        this.netID = netID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
