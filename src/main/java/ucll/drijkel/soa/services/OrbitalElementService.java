package ucll.drijkel.soa.services;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ucll.drijkel.soa.model.OrbitalElement;
import ucll.drijkel.soa.model.RestException;

import java.util.Collection;
import java.util.Collections;

@Service
public class OrbitalElementService {
    private final String url = "http://localhost:8002/orbitalelements";

    public OrbitalElementService() {}

    public OrbitalElement getOrbitalElement(int id) {
        RestTemplate restTemplate = new RestTemplate();
        try{
            OrbitalElement result = restTemplate.getForObject(url+"/"+id, OrbitalElement.class);
            assert result != null;
            return result;
        } catch (HttpClientErrorException e){
            throw new RestException("Didn't find that orbitalelement sorry lad...");
        }

    }

    public JSONObject getOrbitalElements(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OrbitalElement[]> response = restTemplate.getForEntity(url, OrbitalElement[].class);
        OrbitalElement[] orbitalElements = response.getBody();
        JSONObject wrapper = new JSONObject();
        assert orbitalElements != null;
        for(OrbitalElement orbitalElement : orbitalElements) {
            wrapper.put(Integer.toString(orbitalElement.getId()), new JSONObject(orbitalElement));
        }
        return wrapper;
    }

    public JSONObject addOrbitalElement(OrbitalElement orbitalElement, String apiKey){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization","Token " + apiKey);
        HttpEntity<OrbitalElement> request = new HttpEntity<>(orbitalElement, headers);
        try {
            ResponseEntity<OrbitalElement> result = restTemplate.exchange(url+"/", HttpMethod.POST,request, OrbitalElement.class);
            if(result.getStatusCode() == HttpStatus.CREATED){
                return new JSONObject(result.getBody());
            } else{
                return null;
            }
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new RestException("Unauthorized");
        }
    }

    public void deleteOrbitalElement(int id, String apiKey){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Token " + apiKey);
        OrbitalElement orbitalElement = getOrbitalElement(id);
        if (orbitalElement == null){
            throw new RestException("Orbitalelement not found");
        }
        HttpEntity<OrbitalElement> request = new HttpEntity<>(orbitalElement,headers);
        try {
            ResponseEntity<OrbitalElement> result = restTemplate.exchange(url + "/" + id +"/", HttpMethod.DELETE,request, OrbitalElement.class);
        } catch (
                HttpClientErrorException.NotFound e) {
            throw new RestException("Orbitalelement not found");
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new RestException("Unauthorized");
        }
    }

    public JSONObject updateOrbitalElement(OrbitalElement updatedOrbitalElement, String apiKey){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Token " + apiKey);
        HttpEntity<OrbitalElement> request = new HttpEntity<>(updatedOrbitalElement, headers);
        try {
            ResponseEntity<OrbitalElement> result = restTemplate.exchange(url + "/" + updatedOrbitalElement.getId() +"/",HttpMethod.PUT, request, OrbitalElement.class);
            if (result.getStatusCode() == HttpStatus.OK){
                return new JSONObject(result.getBody());
            }
            return null;
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new RestException("Unauthorized");
        } catch (HttpClientErrorException.NotFound ex) {
            throw new NotFoundException("Didn't find that orbitalelement sorry lad...");
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
        ResponseEntity<String> response = restTemplate.postForEntity( "http://localhost:8002/account/register", request , String.class );
    }

    public JSONObject getKey(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("username", username);
        map.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( "http://localhost:8002/api-token-auth/", request , String.class );
        return new JSONObject(response.getBody());
    }
}
