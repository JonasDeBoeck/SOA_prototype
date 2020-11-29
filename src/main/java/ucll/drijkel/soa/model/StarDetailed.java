package ucll.drijkel.soa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StarDetailed {
    @NotEmpty (message = "Name cannot be empty")
    private String name;
    @NotEmpty (message = "Star system cannot be empty")
    private String star_system;
    @NotEmpty(message = "Spectral class cannot be empty")
    private String spectral_class;
    private int year_of_discovery;
    private int id;
    @NotNull (message = "distance cannot be empty")
    private double distance;
    private double apparent_magnitude;
    private double absolut_magnitude;
    private double parallax;

    public StarDetailed() {}

    public String toString() {
        return  "{" +
                "id:" + id +
                "name:" + name +
                "star_system:" + star_system +
                "distance:" + distance +
                "year_of_discovery:" + year_of_discovery +
                "apparent_magnitude:" + apparent_magnitude +
                "absolut_magnitude:" + absolut_magnitude +
                "spectral_class:" + spectral_class  +
                "parallax:" + parallax +
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

    public String getSpectral_class() {
        return spectral_class;
    }

    public void setSpectral_class(String spectral_class) {
        this.spectral_class = spectral_class;
    }

    public int getYear_of_discovery() {
        return year_of_discovery;
    }

    public void setYear_of_discovery(int year_of_discovery) {
        this.year_of_discovery = year_of_discovery;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getApparent_magnitude() {
        return apparent_magnitude;
    }

    public void setApparent_magnitude(double apparent_magnitude) {
        this.apparent_magnitude = apparent_magnitude;
    }

    public double getAbsolut_magnitude() {
        return absolut_magnitude;
    }

    public void setAbsolut_magnitude(double absolut_magnitude) {
        this.absolut_magnitude = absolut_magnitude;
    }

    public double getParallax() {
        return parallax;
    }

    public void setParallax(double parallax) {
        this.parallax = parallax;
    }
}
