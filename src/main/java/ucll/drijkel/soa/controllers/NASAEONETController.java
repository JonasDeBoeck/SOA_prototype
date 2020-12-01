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

@Controller
public class NASAEONETController {

    private NASAEONETService service;

    @Autowired
    public NASAEONETController(NASAEONETService service) { this.service = service; }

    @GetMapping("/nasa/EONET")
    public String showIndex(){
        return "nasa/EONET/index";
    }

    @GetMapping("/nasa/EONET/showEventSelect")
    public String showEventSelect(){
        return "nasa/EONET/eventSelect";
    }

    @GetMapping("/nasa/EONET/event")
    public String getEvent(Model model, WebRequest webRequest){
        String sourceID = webRequest.getParameter("source");
        String status = webRequest.getParameter("status");
        String limit = webRequest.getParameter("limit");
        String days = webRequest.getParameter("days");
        Event event = new Event();
        event.setSource(sourceID);
        try{
            event.setStatus(status);
        }catch (RestException restException){
            model.addAttribute("error",restException.getMessage());
            return "nasa/EONET/index";
        }
        if(limit != null && !limit.equals(""))
            event.setLimit(Integer.parseInt(limit));
        if (days != null && !days.equals(""))
            event.setDays(Integer.parseInt(days));
        if (!sourceID.equals("") && service.checkSource(sourceID)){
            JSONObject events = service.getEvent(event);
            model.addAttribute("result",events);
            return "result";
        }
        else{
            if (sourceID.equals("")){
                JSONObject json_event = service.getEvent(event);
                model.addAttribute("result",json_event);
                return "result";
            }
            model.addAttribute("error", "SourceID does not exist");
        }
        return "nasa/EONET/index";
    }

    @GetMapping("nasa/EONET/showSourceId")
    public String getSources(Model model){
        JSONObject object = service.getSources();
        model.addAttribute("result",object);
        return "result";
    }

}
