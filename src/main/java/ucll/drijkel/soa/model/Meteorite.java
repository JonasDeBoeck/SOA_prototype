package ucll.drijkel.soa.model;


public class Meteorite {
    private String name;
    private int id;
    private String nametype;
    private double recclass;
    private double mass;
    private String fall;
    private int year;
    private double reclat;
    private double reclong;
    private String geoLocation;

    public Meteorite(){}

    @Override
    public String toString() {
        return "{" + 
                "id:" + id +
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

    public void setId(int name) {
        this.id = id;
    }

    public String getNametype() {
        return nametype;
    }

    public void setNametype(String nametype) {
        this.nametype = nametype;
    }

    public double getRecclass() {
        return recclass;
    }

    public void setRecclass(double recclass) {
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