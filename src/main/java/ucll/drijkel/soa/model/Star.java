package ucll.drijkel.soa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Star {
    private int id;
    private String name, star_system;
    private double distance;

    public Star() {}

    public String toString() {
        return  "{" +
                "id:" + id +
                "name:" + name +
                "star_system:" + star_system +
                "distance:" + distance +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStar_system() {
        return star_system;
    }

    public void setStar_system(String star_system) {
        this.star_system = star_system;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
