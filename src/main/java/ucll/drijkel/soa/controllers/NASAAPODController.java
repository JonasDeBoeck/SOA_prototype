package ucll.drijkel.soa.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import ucll.drijkel.soa.model.RestException;
import ucll.drijkel.soa.services.NASAAPODService;import org.json.JSONObject;


@Controller
public class NASAAPODController {
    private NASAAPODService service;

    @Autowired
    public NASAAPODController(NASAAPODService service) {this.service = service;}

    @GetMapping("/nasa/APOD")
    public String showIndexAPOD(){
        return "nasa/APOD/index.html";
    }

    @GetMapping("nasa/APOD/showAPOD")
    public String getAPOD(Model model){
        try{
            JSONObject object = service.getAPOD();
            model.addAttribute("result", object);
            return "result";
        } catch (RestException e){
            model.addAttribute("error", e.getMessage());
            return "nasa/APOD/index";
        }

    }

    @GetMapping("nasa/APOD/showAPODByDate")
    public String showAPODByDate(){return "nasa/APOD/selectByDate";}

    @GetMapping("/nasa/APOD/search")
    public String getAPODByDate(Model model, @RequestParam("date") String date){
        try {
            JSONObject result = service.getAPODByDate(date);
            model.addAttribute("result", result);
            return "result";
        }catch (RestException e){
            model.addAttribute("error", e.getMessage());
            return "nasa/APOD/index";
        }
    }
}
