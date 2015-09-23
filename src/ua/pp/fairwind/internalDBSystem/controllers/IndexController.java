package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.PersonType;
import ua.pp.fairwind.internalDBSystem.services.repository.StatisticRepository;

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

    @Autowired
    StatisticRepository repository;

    @Secured("ROLE_USER")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String Index(Model model) {
        return "index";
    }

    @RequestMapping(value = "/notfound", method = RequestMethod.GET)
    public String eror404(Model model) {
        return "error404";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(Model model) {
        return "login";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String showDashboard(Model model) {
        long dosser_count=repository.getDosserCount();
        long dosser_complaint_count=repository.getDosserComplaintCount();
        long total_person_count=repository.getPersonCount();
        long total_client_count=repository.getPersonCountS(PersonType.CLIENT);
        long total_worker_count=repository.getPersonCountS(PersonType.WORKER);
        model.addAttribute("dosser_count",dosser_count);
        model.addAttribute("dosser_complaint_count",dosser_complaint_count);
        model.addAttribute("total_person_count",total_person_count);
        model.addAttribute("total_client_count",total_client_count);
        model.addAttribute("total_worker_count",total_worker_count);
        return "dashboard";
    }

    @RequestMapping(value = "/forbidden", method = RequestMethod.GET)
    public String showError403(Model model) {
        return "error403";
    }
}
