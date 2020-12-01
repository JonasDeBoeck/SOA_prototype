package ucll.drijkel.soa.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ucll.drijkel.soa.model.Planet;
import ucll.drijkel.soa.model.RestException;
import ucll.drijkel.soa.services.PlanetService;

import javax.validation.Valid;

@Controller
public class PlanetController {

    private PlanetService planetService;

    @Autowired
    public PlanetController(PlanetService service){ this.planetService = service; }

    @GetMapping("/planets")
    public String showPlanetsIndex(){
        return "planet/planets_index";
    }


    @GetMapping("/planets/getplanets")
    public String getAllPlanets(Model model ){
        JSONObject result = planetService.getPlanets();
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/planets/showGetPlanet")
    public String showGetPlanet(){
        return "planet/get_planet";
    }

    @GetMapping("/planets/getPlanet")
    public String getPlanet(Model model, @RequestParam("id") int id){
        JSONObject planet = new JSONObject(planetService.getPlanet(id));
        model.addAttribute("result", planet);
        return "result";
    }

    @GetMapping("/planets/showRequestKey")
    public String showRequestKey(){
        return "planet/request_key_planet";
    }

    @GetMapping("/planets/showRegister")
    public String showRegister(){
        return "planet/register_planet";
    }

    @PostMapping("/planets/register")
    public String register(Model model, WebRequest webRequest){
        String username = webRequest.getParameter("username");
        String password = webRequest.getParameter("password");
        planetService.register(username,password);
        model.addAttribute("info", "Account created succesfully, now you can go and request your api-key");
        return "planet/planets_index";
    }

    @PostMapping("/planets/requestKey")
    public String requestKey(Model model, WebRequest webRequest){
        String username = webRequest.getParameter("username");
        String password = webRequest.getParameter("password");
        JSONObject key = planetService.getKey(username,password);
        model.addAttribute("result",key);
        return "result";
    }

    @GetMapping("/planets/showAddPlanet")
    public String showAddPlanet(Model model){
        Planet planet = new Planet();
        model.addAttribute("planet",planet);
        return "planet/add_planet";
    }

    @PostMapping("/planets/addPlanet")
    public String addPlanet(@ModelAttribute("planet") @Valid Planet planet, BindingResult binding, Model model, WebRequest webrequest){
        if (binding.hasErrors()){
            model.addAttribute("planet",planet);
            return "planet/add_planet";
        }else{
            JSONObject result = planetService.addPlanet(planet,webrequest.getParameter("api_key"));
            if (result != null){
                model.addAttribute("result",result);
                return "result";
            }
            else{
                model.addAttribute("error","An error occured");
                return "planet/planets_index";
            }
        }
    }

    @GetMapping("/planets/showEditPlanet")
    public String showEditPlanet(){
        return "planet/select_planet";
    }

    @GetMapping("/planets/editPlanet")
    public String showEditPlanet(Model model, @RequestParam("id") int id){
        Planet planet = planetService.getPlanet(id);
        if (planet == null){
            model.addAttribute("error","Planet not found");
            return "planet/select_planet";
        }
        model.addAttribute("planet",planet);
        return "planet/edit_planet";
    }

    @PostMapping("/planets/editPlanet")
    public String editPlanet(@ModelAttribute("planet") @Valid Planet planet, BindingResult bindingResult, Model model, WebRequest webRequest){
        if (bindingResult.hasErrors()){
            return "planet/edit_planet";
        }
        JSONObject result = planetService.updatePlanet(planet,webRequest.getParameter("api_key"));
        if (result != null){
            model.addAttribute("result",result);
            return "result";
        }else{
            model.addAttribute("error","An error occured");
            return "planet/planets_index";
        }
    }

    @GetMapping("/planets/showDeletePlanet")
    public String showDeletePlanet(){
        return "planet/delete_planet";
    }

    @PostMapping("/planets/deletePlanet")
    public String deleteStar(WebRequest webRequest, Model model){
        int id = Integer.parseInt(webRequest.getParameter("id"));
        String api_key = webRequest.getParameter("api_key");
        try{
            planetService.deletePlanet(id,api_key);
            model.addAttribute("info","Planet deleted succesfully");
            return "planet/planets_index";
        }catch (RestException e){
            model.addAttribute("error",e.getMessage());
            return "planet/planets_index";
        }
    }

    @GetMapping("/planets/{id}")
    public String getPlanet(@PathVariable("id") int id, Model model){
        JSONObject planet = new JSONObject(planetService.getPlanet(id));
        model.addAttribute("result", planet);
        return "result";
    }
}
