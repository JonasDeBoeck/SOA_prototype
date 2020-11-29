package ucll.drijkel.soa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    public IndexController() {}

    @GetMapping("/")
    public String showIndex() {
        return "index";
    }
}
