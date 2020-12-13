package ucll.drijkel.soa.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;
import ucll.drijkel.soa.model.Event;
import ucll.drijkel.soa.model.RestException;
import ucll.drijkel.soa.services.NASAEONETService;
import ucll.drijkel.soa.services.NASAWeatherService;

@Controller
public class NASAWeatherController {

    private NASAWeatherService service;

    @Autowired
    public NASAWeatherController(NASAWeatherService service) { this.service = service; }

    @GetMapping("/nasa/weather")
    public String showIndex(){
        return "nasa/EONET/index";
    }


}
