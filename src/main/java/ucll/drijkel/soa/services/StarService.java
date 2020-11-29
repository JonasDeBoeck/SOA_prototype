package ucll.drijkel.soa.services;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ucll.drijkel.soa.model.RestException;
import ucll.drijkel.soa.model.Star;
import ucll.drijkel.soa.model.StarDetailed;

import java.util.Collections;

@Service
public class StarService {
    private final String url = "http://localhost:8000/stars";

    public StarService() {}

    public Star getStar(int id) {
        RestTemplate restTemplate = new RestTemplate();
        Star result = restTemplate.getForObject(url + "/" + id, Star.class);
        assert result != null;
        return result;
    }

    public StarDetailed getStarDetailed(int id) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            StarDetailed result = restTemplate.getForObject(url + "/" + id + "/detailed", StarDetailed.class);
            return result;
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public JSONObject getStars() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Star[]> response = restTemplate.getForEntity(url, Star[].class);
        Star[] stars = response.getBody();
        JSONObject wrapper = new JSONObject();
        assert stars != null;
        for (Star star : stars) {
            wrapper.put(Integer.toString(star.getId()), new JSONObject(star));
        }
        return wrapper;
    }

    public JSONObject getStarsDetailed() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<StarDetailed[]> response = restTemplate.getForEntity(url + "/detailed", StarDetailed[].class);
        StarDetailed[] stars = response.getBody();
        JSONObject wrapper = new JSONObject();
        assert stars != null;
        for (StarDetailed star : stars) {
            wrapper.put(Integer.toString(star.getId()), new JSONObject(star));
        }
        return wrapper;
    }

    public JSONObject addStar(StarDetailed star, String apiKey) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Token " + apiKey);
        HttpEntity<StarDetailed> request = new HttpEntity<>(star, headers);
        ResponseEntity<StarDetailed> result = restTemplate.postForEntity(url + "/detailed", request, StarDetailed.class);
        if (result.getStatusCode() == HttpStatus.CREATED) {
            return new JSONObject(result.getBody());
        } else {
            return null;
        }
    }

    public void deleteStar(int id, String apiKey) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Token " + apiKey);
        StarDetailed star = getStarDetailed(id);
        if (star == null) {
            throw new RestException("Star not found");
        }
        HttpEntity<StarDetailed> request = new HttpEntity<>(star, headers);
        try {
            ResponseEntity<StarDetailed> result = restTemplate.exchange(url + "/" + id + "/detailed", HttpMethod.DELETE, request, StarDetailed.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RestException("Star not found");
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new RestException("Unauthorized");
        }
    }

    public JSONObject editStar(StarDetailed star, String apiKey) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Token " + apiKey);
        HttpEntity<StarDetailed> request = new HttpEntity<>(star, headers);
        ResponseEntity<StarDetailed> result = restTemplate.exchange(url + "/" + star.getId() + "/detailed", HttpMethod.PUT, request, StarDetailed.class);
        if (result.getStatusCode() == HttpStatus.OK) {
            return new JSONObject(result.getBody());
        } else {
            return null;
        }
    }

    public void register(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("username", username);
        map.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( "http://localhost:8000/account/register", request , String.class );
    }

    public JSONObject getKey(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("username", username);
        map.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( "http://localhost:8000/api-token-auth", request , String.class );
        return new JSONObject(response.getBody());
    }
}
