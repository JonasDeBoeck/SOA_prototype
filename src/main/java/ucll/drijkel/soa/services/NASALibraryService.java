package ucll.drijkel.soa.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ucll.drijkel.soa.model.RestException;

@Service
public class NASALibraryService {
    private final String url = "https://images-api.nasa.gov/";

    public NASALibraryService() {}

    public JSONObject getOperation(String operation) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url + "/search?q=" + operation, String.class);
            JSONObject res = new JSONObject(response.getBody());
            if (res.getJSONObject("collection").getJSONObject("metadata").getInt("total_hits") == 0) {
                return null;
            }
            return res;
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public JSONObject getAsset(String asset) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url + "/asset/" + asset, String.class);
            return new JSONObject(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public JSONObject getMetadata(String asset) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url + "/metadata/" + asset, String.class);
            JSONObject res = new JSONObject(response.getBody());
            ResponseEntity<String> result = restTemplate.getForEntity(res.get("location").toString(), String.class);
            return new JSONObject(result.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public JSONObject getCaptions(String asset) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url + "/captions/" + asset, String.class);
            return new JSONObject(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (HttpClientErrorException.BadRequest ex) {
            throw new RestException("The file you requested captions for is not a video");
        }
    }

    public JSONObject getNasaId(String operation) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url + "/search?q=" + operation, String.class);
            JSONObject result = new JSONObject(response.getBody());
            JSONArray operations = result.getJSONObject("collection").getJSONArray("items");
            JSONObject ids = new JSONObject();
            for (int i = 0; i < operations.length(); i++) {
                JSONObject data = operations.getJSONObject(i);
                JSONArray d = data.getJSONArray("data");
                JSONObject item = new JSONObject();
                item.put("title", d.getJSONObject(0).getString("title"));
                item.put("nasa_id", d.getJSONObject(0).getString("nasa_id"));
                ids.put(Integer.toString(i), item);
            }
            if (ids.length() == 0) {
                return null;
            }
            return ids;
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
