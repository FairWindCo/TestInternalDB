package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Сергей on 07.08.2015.
 */
@Controller
public class IndexController {
        @Secured("ROLE_USER")
        @RequestMapping(value = "/index", method = RequestMethod.GET)
        public String showIndex(Model model) {
            return "index";
        }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String Index(Model model) {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(Model model) {
        return "login";
    }

    @RequestMapping(value = "/forbidden", method = RequestMethod.GET)
    public String showError403(Model model) {
        return "error403";
    }
}
