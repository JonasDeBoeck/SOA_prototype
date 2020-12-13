package ucll.drijkel.soa.services;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ucll.drijkel.soa.model.Meteorite;
import ucll.drijkel.soa.model.RestException;

import java.util.Collection;
import java.util.Collections;

@Service
public class MeteoriteLandingsService {
    private final String url = "https://meteorite-landings-api.herokuapp.com/";

    public MeteoriteLandingsService() {}

    public Meteorite getMeteorite(int id) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            Meteorite result = restTemplate.getForObject(url+"api/"+id, Meteorite.class);
            assert result != null;
            return result;
        } catch (HttpClientErrorException e) {
            throw new RestException("Didn't find that specific meteorite landing.");
        }
    }

    public JSONObject getAllMeteorites() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Meteorite[]> response = restTemplate.getForEntity(url+"api/"+"getmeteorites", Meteorite[].class);
        Meteorite[] meteorites = response.getBody();
        JSONObject wrapper = new JSONObject();
        assert meteorites != null;
        for(Meteorite meteorite : meteorites) {
            wrapper.put(Integer.toString(meteorite.getId()), new JSONObject(meteorite));
        }
        return wrapper;
    }

    public JSONObject addMeteorite(Meteorite meteorite, String apiKey){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization","Token " + apiKey);
        HttpEntity<Meteorite> request = new HttpEntity<>(meteorite, headers);
        try {
            ResponseEntity<Meteorite> result = restTemplate.exchange(url+"api/"+"addmeteorite", HttpMethod.POST,request, Meteorite.class);
            if(result.getStatusCode() == HttpStatus.CREATED){
                return new JSONObject(result.getBody());
            } else{
                return null;
            }
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new RestException("Unauthorized");
        }
    }

    public void deleteMeteorite(int id, String apiKey){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Token " + apiKey);
        Meteorite meteorite = getMeteorite(id);
        if (meteorite == null){
            throw new RestException("Meteorite landing not found");
        }
        HttpEntity<Meteorite> request = new HttpEntity<>(meteorite,headers);
        try {
            ResponseEntity<Meteorite> result = restTemplate.exchange(url + "api/"+"deletemeteorite/" + id, HttpMethod.DELETE,request, Meteorite.class);
        } catch (
                HttpClientErrorException.NotFound e) {
            throw new RestException("Meteorite landing not found");
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new RestException("Unauthorized");
        }
    }

    public JSONObject updateMeteorite(Meteorite updatedMeteorite, String apiKey){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Token " + apiKey);
        HttpEntity<Meteorite> request = new HttpEntity<>(updatedMeteorite, headers);
        try {
            ResponseEntity<Meteorite> result = restTemplate.exchange(url + "api/"+"updatemeteorite/" + updatedMeteorite.getId(),HttpMethod.PUT, request, Meteorite.class);
            if (result.getStatusCode() == HttpStatus.OK){
                return new JSONObject(result.getBody());
            }
            return null;
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new RestException("Unauthorized");
        } catch (HttpClientErrorException.NotFound ex) {
            throw new NotFoundException("Didn't find that meteorite landing");
        }

    }

    public void register(String username, String password){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("username", username);
        map.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url+"registration/", request , String.class );
    }

    public JSONObject getKey(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("username", username);
        map.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url+"login/", request , String.class );
        return new JSONObject(response.getBody());
    }
}