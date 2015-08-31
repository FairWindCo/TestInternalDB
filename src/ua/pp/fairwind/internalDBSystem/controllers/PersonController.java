package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.pp.fairwind.internalDBSystem.datamodel.Person;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.PersonType;
import ua.pp.fairwind.internalDBSystem.dateTable.FormSort;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseListResp;
import ua.pp.fairwind.internalDBSystem.security.UserDetailsAdapter;
import ua.pp.fairwind.internalDBSystem.services.repository.PersonRepository;
import java.util.logging.Logger;

/**
 * Created by FirWind on 16.07.2015.
 */
@Controller
@RequestMapping("/person")
public class PersonController {
    protected static Logger logger = Logger.getLogger("controller");

    @Autowired
    private PersonRepository personService;

    @Secured({"ROLE_SUPERVIEW","ROLE_GROUP_VIEW", "ROLE_SUPER_VIEW","ROLE_MAIN_VIEW"})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model) {
        return "personview";
    }

    @Secured({"ROLE_SUPEREDIT","ROLE_GROUP_EDIT", "ROLE_SUPER_EDIT","ROLE_MAIN_EDIT"})
    @RequestMapping(value = "/editview", method = RequestMethod.GET)
    public String showForEdit(Model model) {
        return "personedit";
    }

    @Transactional(readOnly = true)
    @Secured({"ROLE_SUPERVIEW","ROLE_GROUP_VIEW", "ROLE_SUPER_VIEW","ROLE_MAIN_VIEW","ROLE_SUPER_EDIT","ROLE_GROUP_EDIT", "ROLE_SUPER_EDIT","ROLE_MAIN_EDIT"})
    @RequestMapping(value = "/listClients", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JSTableExpenseListResp<Person> getClientPersons(@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting,@RequestParam(required = false) String searchname,@RequestParam(required = false) String codename) {
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        PageRequest pager;
        if(sort!=null){
            pager = new PageRequest(jtStartIndex, jtPageSize, sort);

        } else {
            pager = new PageRequest(jtStartIndex, jtPageSize);
        }
        Page<Person> page=getClientPersons(pager, searchname, codename,PersonType.CLIENT);
        return new JSTableExpenseListResp<Person>(page);
    }

    @Transactional(readOnly = true)
    @Secured({"ROLE_SUPERVIEW","ROLE_GROUP_VIEW", "ROLE_SUPER_VIEW","ROLE_MAIN_VIEW","ROLE_SUPER_EDIT","ROLE_GROUP_EDIT", "ROLE_SUPER_EDIT","ROLE_MAIN_EDIT"})
    @RequestMapping(value = "/listWorkers", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JSTableExpenseListResp<Person> getPersons(@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting,@RequestParam(required = false) String searchname,@RequestParam(required = false) String codename) {
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        PageRequest pager;
        if(sort!=null){
            pager = new PageRequest(jtStartIndex, jtPageSize, sort);

        } else {
            pager = new PageRequest(jtStartIndex, jtPageSize);
        }
        Page<Person> page=getClientPersons(pager, searchname, codename,PersonType.WORKER);
        return new JSTableExpenseListResp<Person>(page);
    }

    private Page<Person> getClientPersons(PageRequest pager,String searchname, String codename,PersonType personType){
        Page<Person> page;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsAdapter user=(UserDetailsAdapter)auth.getPrincipal();
        if(searchname!=null && !searchname.isEmpty()) {
                if (codename != null && !codename.isEmpty()) {
                    page=personService.findByFioContainsAndCodeContainsAndPersonType(searchname,codename,personType,pager);
                } else {
                    page=personService.findByFioContainsAndPersonType(searchname, personType, pager);
                }
            } else {
                if (codename != null && !codename.isEmpty()) {
                    page=personService.findByCodeContainsAndPersonType(codename, personType, pager);
                } else {
                    page=personService.findByPersonType(personType, pager);
                }
            }
        return page;
    }

}
