package ucll.drijkel.soa.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Planet {

    private int id;
    @NotEmpty(message = "Naam mag niet leeg zijn")
    private String name;
    @NotNull
    private int distance_from_sun;
    @NotNull
    private int period_of_revolution;
    @NotEmpty(message = "Rotation speed mag niet leeg zijn")
    private String rotation_speed;
    @NotNull
    private int diameter;
    @NotEmpty(message = "Atmosphere main components mag niet leeg zijn")
    private String atmosphere_main_components;
    @NotNull
    private int moons;
    @NotNull
    private int rings;

    public Planet(){}

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", name:'" + name + '\'' +
                ", distance_from_sun:" + distance_from_sun +
                ", period_of_revolution:" + period_of_revolution +
                ", rotation_speed:'" + rotation_speed + '\'' +
                ", diameter:" + diameter +
                ", atmosphere_main_components:'" + atmosphere_main_components + '\'' +
                ", moons:" + moons +
                ", rings:" + rings +
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

    public int getDistance_from_sun() {
        return distance_from_sun;
    }

    public void setDistance_from_sun(int distance_from_sun) {
        this.distance_from_sun = distance_from_sun;
    }

    public int getPeriod_of_revolution() {
        return period_of_revolution;
    }

    public void setPeriod_of_revolution(int period_of_revolution) {
        this.period_of_revolution = period_of_revolution;
    }

    public String getRotation_speed() {
        return rotation_speed;
    }

    public void setRotation_speed(String rotation_speed) {
        this.rotation_speed = rotation_speed;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public String getAtmosphere_main_components() {
        return atmosphere_main_components;
    }

    public void setAtmosphere_main_components(String atmosphere_main_components) {
        this.atmosphere_main_components = atmosphere_main_components;
    }

    public int getMoons() {
        return moons;
    }

    public void setMoons(int moons) {
        this.moons = moons;
    }

    public int getRings() {
        return rings;
    }

    public void setRings(int rings) {
        this.rings = rings;
    }
}
