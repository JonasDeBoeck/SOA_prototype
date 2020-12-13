package ucll.drijkel.soa.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ucll.drijkel.soa.model.RestException;

@Service
public class NASAWeatherService {
    //TODO: delete my API key for git push
    private final String url = "https://api.nasa.gov/insight_weather/?api_key=KZkba4RGKzwkqCcGQmcBXsvEagu5a6NXk2pDBGfC&feedtype=json&ver=1.0";
    //private final String url = "https://api.nasa.gov/insight_weather/?api_key=DEMO_KEY&feedtype=json&ver=1.0";


    public NASAWeatherService() {
    }

    public JSONObject getSolData(){
        JSONArray jsonarray = getAllWeatherData();
        JSONObject result = new JSONObject();
        JSONObject jsonobject = jsonarray.getJSONObject(0);
        result.put("sols", jsonobject);
        return result;
    }

    public JSONObject getSolKeys(){
        JSONArray jsonarray = getAllWeatherData();
        JSONObject result = new JSONObject();
        JSONObject jsonobject = jsonarray.getJSONObject(1);
        result.put("sol_keys", jsonobject);
        return result;
    }

    public JSONObject getValidityChecks(){
        JSONArray jsonarray = getAllWeatherData();
        JSONObject result = new JSONObject();
        JSONObject jsonobject = jsonarray.getJSONObject(2);
        result.put("validity_checks", jsonobject);
        return result;
    }

    private JSONArray getAllWeatherData(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
        String responseString = response.getBody();
        return new  JSONArray(responseString);
    }
}
