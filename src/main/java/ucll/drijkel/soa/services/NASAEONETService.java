package ucll.drijkel.soa.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ucll.drijkel.soa.model.Event;

import java.nio.file.ClosedDirectoryStreamException;

@Service
public class NASAEONETService {

    private final String url = "https://eonet.sci.gsfc.nasa.gov/api/v3/events";
    private final String sources = "https://eonet.sci.gsfc.nasa.gov/api/v3/sources";

    public NASAEONETService(){}

    public JSONObject getSources(){
        RestTemplate restTemplate = new RestTemplate();
        try{
            ResponseEntity<String> response = restTemplate.getForEntity(sources, String.class);
            return new JSONObject(response.getBody());
        }catch (Exception e){
            return null;
        }
    }

    public JSONObject getEvent(Event event){
        RestTemplate restTemplate = new RestTemplate();
        try{
            String queryUrl = constructEventUrl(event);
            ResponseEntity<String> response = restTemplate.getForEntity(queryUrl, String.class );
            return new JSONObject(response.getBody());
        }catch (Exception e){
            return null;
        }
    }

    private String constructEventUrl(Event event){
        String queryUrl = url+"?";
        boolean addedQuery = false;
        if (event.getSource() != null){
            queryUrl += "source=" + event.getSource();
            addedQuery = true;
        }
        if (event.getStatus() != null){
            if (addedQuery)
                queryUrl += "&";
            queryUrl += "status=" + event.getStatus();
            addedQuery = true;
        }
        if (event.getLimit() != -1) {
            if (addedQuery)
                queryUrl += "&";
            queryUrl += "limit=" + event.getLimit();
            addedQuery = true;
        }
        if (event.getDays() != -1) {
            if (addedQuery)
                queryUrl += "&";
            queryUrl += "days=" + event.getDays();
        }
        if (!addedQuery)
            queryUrl = queryUrl.substring(0,queryUrl.length()-1);
        return queryUrl;
    }

    public boolean checkSource(String sourceID) {
        JSONObject sources_json = getSources();
        JSONArray sources = sources_json.getJSONArray("sources");
        for (int i=0;i<sources.length(); i++){
            JSONObject source = sources.getJSONObject(i);
            if (source.get("id").equals(sourceID)){
                return true;
            }
        }
        return false;
    }
}


