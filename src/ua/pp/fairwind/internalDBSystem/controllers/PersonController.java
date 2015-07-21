package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.pp.fairwind.internalDBSystem.datamodel.Person;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.FilesType;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseListResp;
import ua.pp.fairwind.internalDBSystem.services.PersonService;
import ua.pp.fairwind.internalDBSystem.services.repository.FileTypeRepository;

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

    @Autowired
    private FileTypeRepository filetypeservice;

    @Resource(name="personService")
    private PersonService personService;

    @Transactional(readOnly = false)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getPersons(Model model) {

        logger.log(Level.INFO, "Received request to show all persons");

        // Retrieve all persons by delegating the call to PersonService
        List<Person> persons = personService.getAll();

        // Attach persons to the Model
        model.addAttribute("persons", persons);
        FilesType typ=new FilesType();
        typ.setFilesTypeName("test");
        filetypeservice.saveAndFlush(typ);
        // This will resolve to /WEB-INF/jsp/personspage.jsp
        return "personspage";
    }

    public FileTypeRepository getFiletypeservice() {
        return filetypeservice;
    }

    public void setFiletypeservice(FileTypeRepository filetypeservice) {
        this.filetypeservice = filetypeservice;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<Person> getAllPersons(Model model) {

        logger.log(Level.INFO,"Received request to show all persons");

        // Retrieve all persons by delegating the call to PersonService
        List<Person> persons = personService.getAll();

        return persons;
    }

    @RequestMapping(value = "/list2", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<Person> getAllPersonsJS(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize) {

        logger.log(Level.INFO,"Received request to show "+jtPageSize+" persons from"+jtStartIndex);

        // Retrieve all persons by delegating the call to PersonService


        List<Person> persons = personService.getAll();

        return new JSTableExpenseListResp<Person>(persons,persons.size());
    }

}
