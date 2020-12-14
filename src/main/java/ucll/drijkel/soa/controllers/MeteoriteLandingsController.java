package ucll.drijkel.soa.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ucll.drijkel.soa.model.Meteorite;
import ucll.drijkel.soa.model.Planet;
import ucll.drijkel.soa.model.RestException;
import ucll.drijkel.soa.services.MeteoriteLandingsService;

import javax.validation.Valid;

@Controller
public class MeteoriteLandingsController {

    private MeteoriteLandingsService meteoriteLandingsService;

    @Autowired
    public MeteoriteLandingsController(MeteoriteLandingsService service){ this.meteoriteLandingsService = service; }

    @GetMapping("/meteorites")
    public String showMeteoritesIndex(){
        return "meteorites/meteorites_index";
    }


    @GetMapping("/meteorites/getmeteorites")
    public String getAllMeteorites(Model model ){
        JSONObject result = meteoriteLandingsService.getAllMeteorites();
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/meteorites/showGetMeteorite")
    public String showGetMeteorite(){
        return "meteorites/get_meteorite";
    }

    @GetMapping("/meteorites/getMeteorite")
    public String getMeteorite(Model model, @RequestParam("id") int id){
        JSONObject meteorite = new JSONObject(meteoriteLandingsService.getMeteorite(id));
        model.addAttribute("result", meteorite);
        return "result";
    }

    @GetMapping("/meteorites/showRequestKey")
    public String showRequestKey(){
        return "meteorites/request_key_meteorite";
    }

    @GetMapping("/meteorites/showRegister")
    public String showRegister(){
        return "meteorites/register_meteorite";
    }

    @PostMapping("/meteorites/register")
    public String register(Model model, WebRequest webRequest){
        String username = webRequest.getParameter("username");
        String password1 = webRequest.getParameter("password1");
        String password2 = webRequest.getParameter("password2");
        meteoriteLandingsService.register(username,password1, password2);
        model.addAttribute("info", "Account created succesfully, now you can go and request your api-key");
        return "meteorites/meteorites_index";
    }

    @PostMapping("/meteorites/requestKey")
    public String requestKey(Model model, WebRequest webRequest){
        String username = webRequest.getParameter("username");
        String password = webRequest.getParameter("password");
        JSONObject key = meteoriteLandingsService.getKey(username,password);
        model.addAttribute("result",key);
        return "result";
    }

    @GetMapping("/meteorites/showAddMeteorite")
    public String showAddMeteorite(Model model){
        Meteorite meteorite = new Meteorite();
        model.addAttribute("meteorite",meteorite);
        return "meteorites/add_meteorite";
    }

    @PostMapping("/meteorites/addMeteorite")
    public String addMeteorite(@ModelAttribute("meteorite") @Valid Meteorite meteorite, BindingResult binding, Model model, WebRequest webrequest){
        if (binding.hasErrors()){
            model.addAttribute("meteorite",meteorite);
            return "meteorites/add_meteorite";
        }else{
            try {
                JSONObject result = meteoriteLandingsService.addMeteorite(meteorite,webrequest.getParameter("api_key"));
                if (result != null){
                    model.addAttribute("result",result);
                    return "result";
                }
                else{
                    return "meteorites/meteorites_index";
                }
            }catch (RestException e){
                model.addAttribute("error",e.getMessage());
                return "meteorites/meteorites_index";
            }
        }
    }

    @GetMapping("/meteorites/showEditMeteorite")
    public String showEditMeteorite(){
        return "meteorites/select_meteorite";
    }

    @GetMapping("/meteorites/editMeteorite")
    public String showEditMeteorite(Model model, @RequestParam("id") int id){
        Meteorite meteorite = meteoriteLandingsService.getMeteorite(id);
        if (meteorite == null){
            model.addAttribute("error","Meteorite Landing not found");
            return "meteorites/select_meteorite";
        }
        model.addAttribute("meteorite",meteorite);
        return "meteorites/edit_meteorite";
    }

    @PostMapping("/meteorites/editMeteorite")
    public String editMeteorite(@ModelAttribute("meteorite") @Valid Meteorite meteorite, BindingResult bindingResult, Model model, WebRequest webRequest){
        if (bindingResult.hasErrors()){
            return "meteorites/edit_meteorite";
        }
        try {
            JSONObject result = meteoriteLandingsService.updateMeteorite(meteorite, webRequest.getParameter("api_key"));
            if (result != null){
                model.addAttribute("result",result);
                return "result";
            }else{
                model.addAttribute("error","An error occured");
                return "meteorites/meteorites_index";
            }
        }catch (RestException rest){
            model.addAttribute("error", rest.getMessage());
            return "meteorites/meteorites_index";
        }
    }

    @GetMapping("/meteorites/showDeleteMeteorite")
    public String showDeleteMeteorite(){
        return "meteorites/delete_meteorite";
    }

    @PostMapping("/meteorites/deleteMeteorite")
    public String deleteMeteorite(WebRequest webRequest, Model model){
        int id = Integer.parseInt(webRequest.getParameter("id"));
        String api_key = webRequest.getParameter("api_key");
        try{
            meteoriteLandingsService.deleteMeteorite(id,api_key);
            model.addAttribute("info","Meteorite landing deleted succesfully");
            return "meteorites/meteorites_index";
        }catch (RestException e){
            model.addAttribute("error",e.getMessage());
            return "meteorites/meteorites_index";
        }
    }

    @GetMapping("/meteorites/{id}")
    public String getMeteorite(@PathVariable("id") int id, Model model){
        JSONObject meteorite = new JSONObject(meteoriteLandingsService.getMeteorite(id));
        model.addAttribute("result", meteorite);
        return "result";
    }
}
