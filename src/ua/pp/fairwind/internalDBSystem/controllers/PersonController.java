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
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Activities;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.PersonType;
import ua.pp.fairwind.internalDBSystem.dateTable.FormSort;
import ua.pp.fairwind.internalDBSystem.dateTable.JSComboExpenseResp;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseListResp;
import ua.pp.fairwind.internalDBSystem.security.UserDetailsAdapter;
import ua.pp.fairwind.internalDBSystem.services.repository.PersonRepository;

import java.util.List;
import java.util.logging.Level;
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

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listing", method = RequestMethod.GET)
    @ResponseBody
    public Object simpleList(Model model,@RequestParam(required = false) Integer page_num, @RequestParam(required = false) Integer per_page,@RequestParam(value = "pkey_val[]",required = false) String pkey,@RequestParam(value = "q_word[]",required = false) String[] qword) {

        logger.log(Level.INFO,"Received request to show "+per_page+" filetypes from"+page_num);

        // Retrieve all persons by delegating the call to PersonService
        //Sort sort= FormSort.formSortFromSortDescription(orderby);
        Sort sort=new Sort(Sort.Direction.ASC,"fio");
        PageRequest pager=null;
        if(page_num!=null && per_page!=null) {
            pager = new PageRequest(page_num - 1, per_page, sort);
        }
        if(pager!=null) {
            Page<Person> page;
            if (qword != null && qword.length > 0) {
                page = personService.findByFioContainsAndPersonType(qword[0], PersonType.CLIENT, pager);
            } else {
                page = personService.findAll(pager);
            }
            return new JSComboExpenseResp<>(page);
        } else {
            if(pkey!=null && !pkey.isEmpty()){
                Long key=Long.valueOf(pkey);
                Person ft=null;
                if(key!=null) {
                    ft = personService.findOne(key);
                }
                return ft;
            } else {
                List<Person> page;
                if (qword != null && qword.length > 0) {
                    page = personService.findByFioContainsAndPersonType(qword[0],PersonType.CLIENT);
                } else {
                    page = personService.findAll(sort);
                }
                return new JSComboExpenseResp<>(page);
            }
        }
    }

}
