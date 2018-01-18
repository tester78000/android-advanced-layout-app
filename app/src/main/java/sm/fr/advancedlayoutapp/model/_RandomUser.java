package sm.fr.advancedlayoutapp.model;



public class _RandomUser {

    private String name;

    private String email;

    private Double latitude;

    private Double longitude;

    public _RandomUser() {
    }

    public String getName() {
        return name;
    }

    public _RandomUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public _RandomUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public _RandomUser setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public _RandomUser setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
