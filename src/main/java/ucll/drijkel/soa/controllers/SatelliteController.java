package ucll.drijkel.soa.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import ucll.drijkel.soa.model.RestException;
import ucll.drijkel.soa.model.Satellite;
import ucll.drijkel.soa.services.SatelliteService;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

@Controller
public class SatelliteController {
    final private SatelliteService satelliteService;

    @Autowired
    public SatelliteController(SatelliteService satelliteService) {
        this.satelliteService = satelliteService;
    }

    @GetMapping("/satellite")
    public String showSatelliteIndex () {
        return "satellite/satellite_index";
    }

    @GetMapping("/satellite/showGetSatellite")
    public String showGetSatellite() {
        return "satellite/get_satellite";
    }

    @GetMapping("/satellite/getSatellite")
    public String getSatellite(Model model, @RequestParam("satellite_id") int id) {
        Satellite s = satelliteService.getSatellite(id);
        if (s != null) {
            model.addAttribute("result", new JSONObject(s));
            return "result";
        } else {
            model.addAttribute("error", "Satellite not found");
            return "satellite/satellite_index";
        }
    }

    @GetMapping("/satellite/getSatellites")
    public String getSatellites(Model model) {
        JSONObject result = satelliteService.getSatellites();
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/satellite/showAddSatellite")
    public String showAddSatellite(Model model) {
        Satellite satellite = new Satellite();
        model.addAttribute("satellite", satellite);
        return "satellite/add_satellite";
    }

    @PostMapping("/satellite/addSatellite")
    public String addSatellite(@ModelAttribute("satellite") @Valid Satellite satellite, BindingResult bindingResult, Model model, WebRequest webRequest) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("satellite", satellite);
            return "satellite/add_satellite";
        }
        try {
            JSONObject result = satelliteService.addSatellite(satellite);
            if (result != null) {
                model.addAttribute("result", result);
                return "result";
            } else {
                model.addAttribute("error", "An error occured");
                return "satellite/satellite_index";
            }
        } catch (RestException e) {
            model.addAttribute("error", e.getMessage());
            return "satellite/satellite_index";
        }
    }

    @GetMapping("/satellite/showDeleteSatellite")
    public String showDeleteSatellite() {
        return "satellite/delete_satellite";
    }

    @PostMapping("/satellite/deleteSatellite")
    public String deleteSatellite(WebRequest webRequest, Model model) {
        int id = Integer.parseInt(Objects.requireNonNull(webRequest.getParameter("satellite_id")));
        try {
            satelliteService.deleteSatellite(id);
            model.addAttribute("info", "Satellite deleted successfully");
            return "satellite/satellite_index";
        } catch (RestException e) {
            model.addAttribute("error", e.getMessage());
            return "satellite/satellite_index";
        }
    }

    @GetMapping("/satellite/showEditSatellite")
    public String showEditSatellite() {
        return "satellite/select_satellite";
    }

    @GetMapping("/satellite/editSatellite")
    public String showEditSatellite(Model model, @RequestParam("satellite_id") int id) {
        Satellite satellite = satelliteService.getSatellite(id);
        if (satellite == null) {
            model.addAttribute("error", "Satellite not found");
            return "satellite/satellite_index";
        }
        model.addAttribute("satellite", satellite);
        return "satellite/edit_satellite";
    }

    @PostMapping("/satellite/editSatellite")
    public String editSatellite(@ModelAttribute("satellite") @Valid Satellite satellite, BindingResult bindingResult, Model model, WebRequest webRequest) {
        if (bindingResult.hasErrors()) {
            return "satellite/edit_satellite";
        }
        try {
            JSONObject result = satelliteService.editSatellite(satellite);
            if (result != null) {
                model.addAttribute("result", result);
                return "result";
            } else {
                model.addAttribute("error", "An error occured");
                return "satellite/satellite_index";
            }
        } catch (RestException e) {
            model.addAttribute("error", e.getMessage());
            return "satellite/satellite_index";
        }
    }

}
