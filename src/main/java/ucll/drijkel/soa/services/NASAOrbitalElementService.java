package ucll.drijkel.soa.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ucll.drijkel.soa.model.RestException;

@Service
public class NASAOrbitalElementService {
    private final String url = "https://data.nasa.gov/resource/b67r-rgxc.json";

    public NASAOrbitalElementService() {
    }

    public JSONObject getAllOrbitalElements(){
        JSONArray jsonarray = getAllOrbitalElementsArray();
        JSONObject result = new JSONObject();
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            result.put(Integer.toString(i), jsonobject);
        }
        return result;
    }

    public JSONObject getOrbitalElementByObject(String object){
        JSONArray list = getAllOrbitalElementsArray();
        for (int i = 0; i < list.length(); i++) {
            JSONObject jsonobject = list.getJSONObject(i);
            String object_temp = (String) jsonobject.get("object");
            if(object_temp.equalsIgnoreCase(object)){
                return jsonobject;
            }
        }
        throw new RestException("Didn't find an object with that name.");
    }

    public JSONObject getOrbitalElementById(int id){
        JSONArray list = getAllOrbitalElementsArray();
        try {
            return list.getJSONObject(id);
        } catch (JSONException e){
            throw new RestException("Couldn't find an object with that id.");
        }
    }

    private JSONArray getAllOrbitalElementsArray(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
        String responseString = response.getBody();
        return new  JSONArray(responseString);
    }
}
