package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.pp.fairwind.internalDBSystem.datamodel.Person;
import ua.pp.fairwind.internalDBSystem.services.PersonService;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Сергей on 16.07.2015.
 */
@Controller
@RequestMapping("/person")
public class PersonController {
    protected static Logger logger = Logger.getLogger("controller");

    @Resource(name="personService")
    private PersonService personService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getPersons(Model model) {

        logger.log(Level.INFO,"Received request to show all persons");

        // Retrieve all persons by delegating the call to PersonService
        List<Person> persons = personService.getAll();

        // Attach persons to the Model
        model.addAttribute("persons", persons);

        // This will resolve to /WEB-INF/jsp/personspage.jsp
        return "personspage";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<Person> getAllPersons(Model model) {

        logger.log(Level.INFO,"Received request to show all persons");

        // Retrieve all persons by delegating the call to PersonService
        List<Person> persons = personService.getAll();

        return persons;
    }

}
