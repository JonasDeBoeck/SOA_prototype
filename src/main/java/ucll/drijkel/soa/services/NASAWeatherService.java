package ucll.drijkel.soa.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ucll.drijkel.soa.model.RestException;

import java.util.ArrayList;

@Service
public class NASAWeatherService {
    //TODO: delete my API key for git push
    //private final String url = "https://api.nasa.gov/insight_weather/?api_key=secret&feedtype=json&ver=1.0";
    private final String url = "https://api.nasa.gov/insight_weather/?api_key=DEMO_KEY&feedtype=json&ver=1.0";


    public NASAWeatherService() {
    }

    public JSONObject getAllData(){
        JSONObject jsonarray = getAllWeatherData();
        return jsonarray;
    }

    public JSONObject getValidSolKeys(){
        JSONObject jsonarray = getAllWeatherData();
        JSONObject keys = new JSONObject();
        keys.put("Valid keys", jsonarray.get("sol_keys"));
        return keys;
    }

    /*

    public JSONObject getSolKeys(){
        JSONObject jsonarray = getAllWeatherData();
        JSONObject result = new JSONObject();
        JSONObject jsonobject = jsonarray.getJSONObject(1);
        result.put("sol_keys", jsonobject);
        return result;
    }

    public JSONObject getValidityChecks(){
        JSONObject jsonarray = getAllWeatherData();
        JSONObject result = new JSONObject();
        JSONObject jsonobject = jsonarray.getJSONObject(2);
        result.put("validity_checks", jsonobject);
        return result;
    }*/

    private JSONObject getAllWeatherData(){
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return new JSONObject(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
