package ucll.drijkel.soa.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import ucll.drijkel.soa.model.OrbitalElement;
import ucll.drijkel.soa.model.RestException;
import ucll.drijkel.soa.services.NotFoundException;
import ucll.drijkel.soa.services.OrbitalElementService;

import javax.validation.Valid;
import java.sql.SQLOutput;

@Controller
public class OrbitalElementController {

    private OrbitalElementService orbitalElementService;

    @Autowired
    public OrbitalElementController(OrbitalElementService orbitalElementService){
        this.orbitalElementService = orbitalElementService;
    }

    @GetMapping("/orbitalelements")
    public String showOrbitalElementsIndex(){return "orbitalelement/orbitalelements_index";}

    @GetMapping("/orbitalelements/getorbitalelements")
    public String getAllOrbitalElements(Model model){
        JSONObject result = orbitalElementService.getOrbitalElements();
        model.addAttribute("result",result);
        return "result";
    }

    @GetMapping("/orbitalelements/showGetOrbitalElement")
    public String showGetPlanet(){
        return "orbitalelement/get_orbitalelement";
    }

    @GetMapping("/orbitalelements/getOrbitalElement")
    public String getOrbitalElement(Model model, @RequestParam("id") int id){
        try {
            JSONObject orbitalElement = new JSONObject(orbitalElementService.getOrbitalElement(id));
            model.addAttribute("result", orbitalElement);
            return "result";
        } catch (RestException e){
            model.addAttribute("error", e.getMessage());
            return "orbitalelement/orbitalelements_index";
        }
    }

    @GetMapping("/orbitalelements/showRequestKey")
    public String showRequestKey(){return "orbitalelement/request_key_orbitalelement";}

    @GetMapping ("/orbitalelements/showRegister")
    public String showRegister(){
        return "orbitalelement/register_orbitalelement";
    }

    @PostMapping("/orbitalelements/register")
    public String register(Model model, WebRequest webRequest){
        try {
            String username = webRequest.getParameter("username");
            String password = webRequest.getParameter("password");
            orbitalElementService.register(username, password);
            model.addAttribute("info", "Account created succesfully, now you can go and request your api-key");
            return "orbitalelement/orbitalelements_index";
        }catch (HttpClientErrorException e){
            model.addAttribute("error", "A user with that username already exists!");
            return "orbitalelement/orbitalelements_index";
        }
    }

    @PostMapping("/orbitalelements/requestKey")
    public String requestKey(Model model, WebRequest webRequest){
        String username = webRequest.getParameter("username");
        String password = webRequest.getParameter("password");
        JSONObject key = orbitalElementService.getKey(username,password);
        model.addAttribute("result",key);
        return "result";
    }

    @GetMapping("/orbitalelements/showAddOrbitalElement")
    public String showAddOrbitalElement(Model model){
        OrbitalElement orbitalElement = new OrbitalElement();
        model.addAttribute("orbitalelement",orbitalElement);
        return "orbitalelement/add_orbitalelement";
    }

    @PostMapping("/orbitalelements/addOrbitalElement")
    public String addOrbitalElement(@ModelAttribute("orbitalelement") @Valid OrbitalElement orbitalElement, BindingResult binding, Model model, WebRequest webRequest){
        if(binding.hasErrors()){
            model.addAttribute("orbitalelement", orbitalElement);
            return "orbitalelement/add_orbitalelement";
        }else{
            try{
                JSONObject result = orbitalElementService.addOrbitalElement(orbitalElement, webRequest.getParameter("api_key"));

                if(result != null){
                    model.addAttribute("result", result);
                    return "result";
                }
                else{
                    model.addAttribute("error", "An error occured");
                    return "orbitalelement/orbitalelements_index";
                }
            }
            catch (RestException e){
                model.addAttribute("error", "Unauthorized");
                return "orbitalelement/orbitalelements_index";
            }
        }
    }

    @GetMapping("/orbitalelements/showEditOrbitalElement")
    public String showEditOrbitalElement(){return "orbitalelement/select_orbitalelement";}

    @GetMapping("/orbitalelements/editOrbitalElement")
    public String showEditOrbitalElement(Model model, @RequestParam("id") int id){
        try {
            OrbitalElement orbitalElement = orbitalElementService.getOrbitalElement(id);
            if(orbitalElement == null){
                model.addAttribute("error", "OrbitalElement not found");
                return "orbitalelement/select_orbitalelement";
            }
            model.addAttribute("orbitalelement", orbitalElement);
            return "orbitalelement/edit_orbitalelement";
        }catch (RestException e){
            model.addAttribute("error", e.getMessage());
            return "orbitalelement/orbitalelements_index";
        }
    }

    @PostMapping("/orbitalelements/editOrbitalElement")
    public String editPlanet(@ModelAttribute("orbitalelement") @Valid OrbitalElement orbitalElement, BindingResult bindingResult, Model model, WebRequest webRequest){
        if(bindingResult.hasErrors()){
            return "orbitalelement/edit_orbitalelement";
        }
        try {
            JSONObject result = orbitalElementService.updateOrbitalElement(orbitalElement, webRequest.getParameter("api_key"));
            if(result != null){
                model.addAttribute("result", result);
                return "result";
            }else{
                model.addAttribute("error", "An error occured");
                return "orbitalelement/orbitalelements_index";
            }
        } catch (RestException e){
            model.addAttribute("error", "Unauthorised");
            return "orbitalelement/orbitalelements_index";
        }

    }

    @GetMapping("/orbitalelements/showDeleteOrbitalElement")
    public String showDeleteOrbitalElement(){ return "orbitalelement/delete_orbitalelement";}

    @PostMapping("/orbitalelements/deleteOrbitalElement")
    public String deleteOrbitalElement(WebRequest webRequest, Model model){
        int id = Integer.parseInt(webRequest.getParameter("id"));
        String api_key = webRequest.getParameter("api_key");
        try{
            orbitalElementService.deleteOrbitalElement(id, api_key);
            model.addAttribute("info", "OrbitalElement deleted succesfully");
            return "orbitalelement/orbitalelements_index";
        }catch (RestException e){
            model.addAttribute("error", e.getMessage());
            return "orbitalelement/orbitalelements_index";
        }
    }

    @GetMapping("/orbitalelements/{id}")
    public String getOrbitalElement(@PathVariable("id") int id, Model model){
        JSONObject orbitalelement = new JSONObject(orbitalElementService.getOrbitalElement(id));
        model.addAttribute("result", orbitalelement);
        return "result";
    }
}
