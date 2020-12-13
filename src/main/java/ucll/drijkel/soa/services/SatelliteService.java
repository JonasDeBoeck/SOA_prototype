package ucll.drijkel.soa.services;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ucll.drijkel.soa.model.RestException;
import ucll.drijkel.soa.model.Satellite;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Service
public class SatelliteService {
    // URL of heroku hosted REST service
    private final String url = "https://satellites-api-soa.herokuapp.com/satellites";

    // Constructor
    public SatelliteService() {}

    // GET call for the Satellite with id={id}
    public Satellite getSatellite(int id) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url + "/" + id, String.class);
            JSONObject json = new JSONObject(response.getBody());
            Satellite satellite = new Satellite();
            satellite.setId((long) json.getInt("id"));
            satellite.setName(json.getString("name"));
            satellite.setLaunch_location(json.getString("launch_location"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(json.getString("launch_date"), formatter);
            satellite.setLaunch_date(date);
            return satellite;
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    // GET ALL Satellites
    public JSONObject getSatellites() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return new JSONObject(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    // POST call to add new Satellite
    public JSONObject addSatellite(Satellite Satellite) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Satellite> request = new HttpEntity<>(Satellite, headers);
        try {
            ResponseEntity<Satellite> result = restTemplate.postForEntity(url, request, Satellite.class);
            if (result.getStatusCode() == HttpStatus.CREATED) {
                return new JSONObject(result.getBody());
            } else {
                return null;
            }
        } catch (RestClientException e) {
            throw new RestException("Something went wrong");
        }
    }

    // DELETE call to delete Satellite with id={id}
    public void deleteSatellite(int id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Satellite Satellite = getSatellite(id);
        if (Satellite == null) {
            throw new RestException("Satellite not found");
        }
        HttpEntity<Satellite> request = new HttpEntity<>(Satellite, headers);
        try {
            ResponseEntity<Satellite> result = restTemplate.exchange(url + "/" + id , HttpMethod.DELETE, request, Satellite.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RestException("Satellite not found");
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new RestException("Something went wrong");
        }
    }

    // UPDATE call to update satellite based on id
    public JSONObject editSatellite(Satellite Satellite) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Satellite> request = new HttpEntity<>(Satellite, headers);
        try {
            ResponseEntity<Satellite> result = restTemplate.exchange(url + "/" + Satellite.getId(), HttpMethod.PUT, request, Satellite.class);
            if (result.getStatusCode() == HttpStatus.CREATED) {
                return new JSONObject(result.getBody());
            } else {
                return null;
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new RestException("Satellite not found");
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new RestException("Something went wrong");
        }
    }
}
