package ucll.drijkel.soa.services;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ucll.drijkel.soa.model.Planet;
import ucll.drijkel.soa.model.RestException;
import ucll.drijkel.soa.model.Star;

import java.beans.PropertyEditorSupport;
import java.util.Collections;

@Service
public class PlanetService {
    private final String url = "https://soa-planet-api.herokuapp.com/api/planets";


    public PlanetService(){}

    public Planet getPlanet(int id){
        RestTemplate restTemplate = new RestTemplate();
        Planet result = restTemplate.getForObject(url+"/"+id +"/", Planet.class);
        assert result != null;
        return result;
    }

    public JSONObject getPlanets(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Planet[]> response = restTemplate.getForEntity(url+"/", Planet[].class);
        Planet[] planets = response.getBody();
        JSONObject wrapper = new JSONObject();
        assert planets != null;
        for (Planet planet : planets) {
            wrapper.put(Integer.toString(planet.getId()), new JSONObject(planet));
        }
        return wrapper;
    }

    public JSONObject addPlanet(Planet planet, String apiKey){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Token " + apiKey);
        HttpEntity<Planet> request = new HttpEntity<>(planet,headers);
        ResponseEntity<Planet> result = restTemplate.postForEntity(url+"/",request,Planet.class);
        if (result.getStatusCode() == HttpStatus.CREATED){
            return new JSONObject(result.getBody());
        }
        return null;
    }

    public void deletePlanet(int id, String apiKey){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Token " + apiKey);
        Planet planet = getPlanet(id);
        if (planet == null){
            throw new RestException("Planet not found");
        }
        HttpEntity<Planet> request = new HttpEntity<>(planet,headers);
        try {
            ResponseEntity<Planet> result = restTemplate.exchange(url + "/" + id +"/", HttpMethod.DELETE,request, Planet.class);
        } catch (
            HttpClientErrorException.NotFound e) {
            throw new RestException("Planet not found");
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new RestException("Unauthorized");
        }
    }

    public JSONObject updatePlanet(Planet updatedPlanet, String apiKey){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Token " + apiKey);
        HttpEntity<Planet> request = new HttpEntity<>(updatedPlanet, headers);
        ResponseEntity<Planet> result = restTemplate.exchange(url + "/" + updatedPlanet.getId() +"/",HttpMethod.PUT, request, Planet.class);
        System.out.println(result.getStatusCode());
        if (result.getStatusCode() == HttpStatus.OK){
            return new JSONObject(result.getBody());
        }
        return null;
    }

    public void register(String username, String password){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("username", username);
        map.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( "https://soa-planet-api.herokuapp.com/api/register", request , String.class );
        System.out.println(response.getStatusCode());
    }

    public JSONObject getKey(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("username", username);
        map.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( "https://soa-planet-api.herokuapp.com/api/api-token-auth", request , String.class );
        return new JSONObject(response.getBody());
    }

}
