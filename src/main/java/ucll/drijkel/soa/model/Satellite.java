package ucll.drijkel.soa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Satellite {
    private Long id;
    private String name;
    private String launch_location;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate launch_date;

    public Satellite() {}

    public String toString() {
        return  "{" +
                "\"id\":\"" + id + "\", " +
                "\"name\":\"" + name + "\", " +
                "\"launch_location\":\"" + launch_location + "\", " +
                "\"launch_date\":\"" + launch_date +
                "\"}";
    }

    public Satellite(String name, String launch_location, LocalDate launch_date) {
        this.name = name;
        this.launch_location = launch_location;
        this.launch_date = launch_date;
    }

    public String getLaunch_location() { return launch_location; }

    public void setLaunch_location(String launch_location) { this.launch_location = launch_location; }

    public LocalDate getLaunch_date() { return launch_date; }

    public void setLaunch_date(LocalDate launch_date) { this.launch_date = launch_date; }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}