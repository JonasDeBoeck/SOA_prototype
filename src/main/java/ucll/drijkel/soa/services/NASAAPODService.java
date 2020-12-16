package ucll.drijkel.soa.services;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
@Service
public class NASAAPODService {
    private final String url = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY";

    public NASAAPODService(){}

    public JSONObject getAPOD(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
        String responseString = response.getBody();
        return new JSONObject(responseString);
    }

    public JSONObject getAPODByDate(String date){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url+"&date="+date,String.class);
        String responseString = response.getBody();
        return new JSONObject(responseString);
    }
}
