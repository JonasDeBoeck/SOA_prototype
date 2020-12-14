package ucll.drijkel.soa.model;


public class Meteorite {
    private String name;
    private int id_model;
    private int id;
    private String nametype;
    private String recclass;
    private double mass;
    private String fall;
    private int year;
    private double reclat;
    private double reclong;
    private String geoLocation;

    public Meteorite(int id, String name, int id_model, String nametype, String recclass, double mass, String fall, int year, double reclat, double reclong, String geoLocation) {
        this.name = name;
        this.id_model = id_model;
        this.nametype = nametype;
        this.recclass = recclass;
        this.mass = mass;
        this.fall = fall;
        this.year = year;
        this.reclat = reclat;
        this.reclong = reclong;
        this.geoLocation = geoLocation;
        this.id = id;
    }

    public Meteorite(){}

    public int getId_model() {
        return id_model;
    }

    public void setId_model(int id_model) {
        this.id_model = id_model;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id+
                "id_model:" + id_model +
                ", name: " + name +
                ", nametype: " + nametype +
                ", recclass: " + recclass +
                ", mass: " + mass +
                ", fall: " + fall +
                ", year: " + year +
                ", reclat: " + reclat +
                ", reclong: " + reclong +
                ", geoLocation: " + geoLocation +
                "}";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNametype() {
        return nametype;
    }

    public void setNametype(String nametype) {
        this.nametype = nametype;
    }

    public String getRecclass() {
        return recclass;
    }

    public void setRecclass(String recclass) {
        this.recclass = recclass;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public String getFall() {
        return fall;
    }

    public void setFall(String fall) {
        this.fall = fall;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getReclat() {
        return reclat;
    }

    public void setReclat(double reclat) {
        this.reclat = reclat;
    }

    public double getReclong() {
        return reclong;
    }

    public void setReclong(double reclong) {
        this.reclong = reclong;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }
}