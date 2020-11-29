package ucll.drijkel.soa.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ucll.drijkel.soa.model.RestException;
import ucll.drijkel.soa.services.NASALibraryService;

@Controller
public class NASALibraryController {
    private NASALibraryService service;

    @Autowired
    public NASALibraryController(NASALibraryService nasaLibraryService) {
        this.service = nasaLibraryService;
    }

    @GetMapping("/nasa/library")
    public String showLibraryHome() {
        return "/nasa/library/index";
    }

    @GetMapping("/nasa/library/showSearch")
    public String showSearch() {
        return "/nasa/library/search";
    }

    @GetMapping("/nasa/library/getoperation")
    public String getOperation(@RequestParam("operation") String operation, Model model) {
        JSONObject result = service.getOperation(operation);
        if (result == null) {
            model.addAttribute("error", "The given operation wasn't found");
            return "/nasa/library/index";
        }
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/nasa/library/showAssetSearch")
    public String showAssetSearch() {
        return "/nasa/library/asset";
    }

    @GetMapping("/nasa/library/getasset")
    public String getAsset(@RequestParam("asset") String asset, Model model) {
        JSONObject result = service.getAsset(asset);
        if (result == null) {
            model.addAttribute("error", "The given NASA ID wasn't found");
            return "/nasa/library/index";
        }
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/nasa/library/showMetadataSearch")
    public String showMetadataSearch() {
        return "/nasa/library/metadata";
    }

    @GetMapping("/nasa/library/getmetadata")
    public String getMetadata(@RequestParam("asset") String asset, Model model) {
        JSONObject result = service.getMetadata(asset);
        if (result == null) {
            model.addAttribute("error", "The given NASA ID wasn't found");
            return "/nasa/library/index";
        }
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/nasa/library/showCaptionSearch")
    public String showCaptionSearch() {
        return "/nasa/library/caption";
    }

    @GetMapping("/nasa/library/getcaptions")
    public String getCaptions(@RequestParam("asset") String asset, Model model) {
        try {
            JSONObject result = service.getCaptions(asset);
            if (result == null) {
                model.addAttribute("error", "The given NASA ID wasn't found");
                return "/nasa/library/index";
            }
            model.addAttribute("result", result);
            return "result";
        } catch (RestException e) {
            model.addAttribute("error", e.getMessage());
            return "/nasa/library/index";
        }
    }

    @GetMapping("/nasa/library/showNasaId")
    public String showNasaId() {
        return "/nasa/library/nasaid";
    }

    @GetMapping("/nasa/library/getnasaid")
    public String getNasaId(@RequestParam("operation") String operation, Model model) {
        JSONObject result = service.getNasaId(operation);
        if (result == null) {
            model.addAttribute("error", "The given operation wasn't found");
            return "/nasa/library/index";
        }
        model.addAttribute("result", result);
        return "result";
    }
}
