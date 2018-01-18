package sm.fr.advancedlayoutapp.model;

/**
 * Created by Farid on 18/01/2018.
 */

public class StationMetro {


    private String nom;

    private String ville;

    private Double latitude;

    private Double longitude;



    private String id;
    private String insee;
    private String ligne;

    public StationMetro() {
    }

    public String getNom() {
        return nom;
    }

    public StationMetro setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public String getVille() {
        return ville;
    }

    public StationMetro setVille(String ville) {
        this.ville = ville;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public StationMetro setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public StationMetro setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getId() {
        return id;
    }

    public StationMetro setId(String id) {
        this.id = id;
        return this;
    }

    public String getInsee() {
        return insee;
    }

    public StationMetro setInsee(String insee) {
        this.insee = insee;
        return this;
    }

    public String getLigne() {
        return ligne;

    }

    public StationMetro setLigne(String ligne) {
        ligne = ligne;
        return this;
    }

    @Override
    public String toString() {
        return this.nom;
    }
}
