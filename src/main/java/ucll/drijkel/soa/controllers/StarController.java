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
import ucll.drijkel.soa.model.Star;
import ucll.drijkel.soa.model.StarDetailed;
import ucll.drijkel.soa.services.StarService;
import javax.validation.Valid;

@Controller
public class StarController {
    private StarService starService;

    @Autowired
    public StarController(StarService starService) {
        this.starService = starService;
    }

    @GetMapping("/star")
    public String showStarIndex () {
        return "star/stars_index";
    }

    @GetMapping("/star/showgetstar")
    public String showGetStar() {
        return "star/get_star";
    }

    @GetMapping("/star/getstar")
    public String getStar(Model model, @RequestParam("ster_id") int id) {
        Star res = starService.getStar(id);
        if (res != null) {
            model.addAttribute("result", new JSONObject(res));
            return "result";
        } else {
            model.addAttribute("error", "Star not found");
            return "star/stars_index";
        }
    }

    @GetMapping("/star/showgetstardetailed")
    public String showGetStarDetailed() {
        return "star/get_star_detailed";
    }

    @GetMapping("/star/getstardetailed")
    public String getStardetailed(Model model, @RequestParam("ster_id") int id) {
        StarDetailed star = starService.getStarDetailed(id);
        if (star != null) {
            model.addAttribute("result", new JSONObject(star));
            return "result";
        } else {
            model.addAttribute("error", "Star not found");
            return "star/stars_index";
        }
    }

    @GetMapping("/star/getstars")
    public String getStars(Model model) {
        JSONObject result = starService.getStars();
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/star/getstarsdetailed")
    public String getStarsDetailed(Model model) {
        JSONObject result = starService.getStarsDetailed();
        model.addAttribute("result", result);
        return "result";
    }

    @GetMapping("/star/showaddstar")
    public String showAddStar(Model model) {
        StarDetailed star = new StarDetailed();
        model.addAttribute("star", star);
        return "star/add_star";
    }

    @PostMapping("/star/addstar")
    public String addStar(@ModelAttribute("star") @Valid StarDetailed star, BindingResult bindingResult, Model model, WebRequest webRequest) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("star", star);
            return "star/add_star";
        }
        try {
            JSONObject result = starService.addStar(star, webRequest.getParameter("api_key"));
            if (result != null) {
                model.addAttribute("result", result);
                return "result";
            } else {
                model.addAttribute("error", "An error occured");
                return "star/stars_index";
            }
        } catch (RestException e) {
            model.addAttribute("error", e.getMessage());
            return "star/stars_index";
        }
    }

    @GetMapping("/star/showdeletestar")
    public String showDeleteStar() {
        return "star/delete_star";
    }

    @PostMapping("/star/deletestar")
    public String deleteStar(WebRequest webRequest, Model model) {
        int id = Integer.parseInt(webRequest.getParameter("ster_id"));
        String api_key = webRequest.getParameter("api_key");
        try {
            starService.deleteStar(id, api_key);
            model.addAttribute("info", "Star deleted successfully");
            return "star/stars_index";
        } catch (RestException e) {
            model.addAttribute("error", e.getMessage());
            return "star/stars_index";
        }
    }

    @GetMapping("/star/showeditstar")
    public String showEditStar() {
        return "star/select_star";
    }

    @GetMapping("/star/editstar")
    public String showEditStar(Model model, @RequestParam("ster_id") int id) {
        StarDetailed star = starService.getStarDetailed(id);
        if (star == null) {
            model.addAttribute("error", "Star not found");
            return "star/stars_index";
        }
        model.addAttribute("star", star);
        return "star/edit_star";
    }

    @PostMapping("/star/editstar")
    public String editStar(@ModelAttribute("star") @Valid StarDetailed star, BindingResult bindingResult, Model model, WebRequest webRequest) {
        if (bindingResult.hasErrors()) {
            return "star/edit_star";
        }
        try {
            JSONObject result = starService.editStar(star, webRequest.getParameter("api_key"));
            if (result != null) {
                model.addAttribute("result", result);
                return "result";
            } else {
                model.addAttribute("error", "An error occured");
                return "star/stars_index";
            }
        } catch (RestException e) {
            model.addAttribute("error", e.getMessage());
            return "star/stars_index";
        }
    }

    @GetMapping("/star/showregister")
    public String showRegister() {
        return "star/register_star";
    }

    @PostMapping("/star/register")
    public String register(WebRequest webRequest, Model model) {
        String username = webRequest.getParameter("username");
        String password = webRequest.getParameter("password");
        try {
            starService.register(username, password);
            model.addAttribute("info", "Account created successfully, now you can go and request your api-key");
            return "star/stars_index";
        } catch(RestException e) {
            model.addAttribute("error", "Ann account with the given username already exists");
            return "star/stars_index";
        }
    }

    @GetMapping("/star/showgetkey")
    public String showGetKey() {
        return "star/request_key_star";
    }

    @PostMapping("/star/requestkey")
    public String requestKey(WebRequest webRequest, Model model) {
        String username = webRequest.getParameter("username");
        String password = webRequest.getParameter("password");
        JSONObject key = starService.getKey(username, password);
        if (key != null) {
            model.addAttribute("result", key);
            return "result";
        } else {
            model.addAttribute("error", "The provided credentials were not valid");
            return "star/stars_index";
        }
    }
}
