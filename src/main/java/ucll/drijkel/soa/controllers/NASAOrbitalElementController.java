package ucll.drijkel.soa.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ucll.drijkel.soa.model.RestException;
import ucll.drijkel.soa.services.NASAOrbitalElementService;

@Controller
public class NASAOrbitalElementController {

    private NASAOrbitalElementService service;

    @Autowired
    public NASAOrbitalElementController(NASAOrbitalElementService service){ this.service = service; }

    @GetMapping("/nasa/orbitalelements")
    public String showOrbitalElements() {
        return "nasa/orbitalElement/index.html";
    }

    @GetMapping("/nasa/orbitalelements/showAllOrbitalElements")
    public String getAllOrbitalElements(Model model) {
        JSONObject result = service.getAllOrbitalElements();
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/nasa/orbitalelements/showGetOrbitalElementByObject")
    public String showGetByObject() {
        return "nasa/orbitalElement/get_orbitalelement";
    }

    @GetMapping("/nasa/orbitalelements/getOribitalElementByObject")
    public String getOrbitalElement(Model model, @RequestParam("object") String object){
        try {
            JSONObject result = service.getOrbitalElementByObject(object);
            model.addAttribute("result", result);
            return "result";
        } catch (RestException e){
            model.addAttribute("error", e.getMessage());
            return "nasa/orbitalelement/index";
        }
    }

    @GetMapping("/nasa/orbitalelements/showGetOrbitalElementById")
    public String showGetOrbitalElementId(){
        return "nasa/orbitalElement/get_orbitalelementid";
    }

    @GetMapping("/nasa/orbitalelements/getOrbitalElementById")
    public String getOrbitalElementById(Model model, @RequestParam("id") int id){
        try {
            JSONObject result = service.getOrbitalElementById(id);
            model.addAttribute("result", result);
            return "result";
        }catch (RestException e){
            model.addAttribute("error", e.getMessage());
            return "nasa/orbitalelement/index";
        }
    }
}
