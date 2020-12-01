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
    private Integer year_of_discovery;
    private Integer id;
    @NotNull (message = "distance cannot be empty")
    private Double distance;
    private Double apparent_magnitude;
    private Double absolut_magnitude;
    private Double parallax;

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

    public Integer getYear_of_discovery() {
        return year_of_discovery;
    }

    public void setYear_of_discovery(Integer year_of_discovery) {
        this.year_of_discovery = year_of_discovery;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getApparent_magnitude() {
        return apparent_magnitude;
    }

    public void setApparent_magnitude(Double apparent_magnitude) {
        this.apparent_magnitude = apparent_magnitude;
    }

    public Double getAbsolut_magnitude() {
        return absolut_magnitude;
    }

    public void setAbsolut_magnitude(Double absolut_magnitude) {
        this.absolut_magnitude = absolut_magnitude;
    }

    public Double getParallax() {
        return parallax;
    }

    public void setParallax(Double parallax) {
        this.parallax = parallax;
    }
}
