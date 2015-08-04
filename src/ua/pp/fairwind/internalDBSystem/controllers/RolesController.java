package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Roles;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.User;
import ua.pp.fairwind.internalDBSystem.dateTable.*;
import ua.pp.fairwind.internalDBSystem.services.repository.RoleRepository;
import ua.pp.fairwind.internalDBSystem.services.repository.UserRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Сергей on 21.07.2015.
 */
@Controller
@RequestMapping("/roles")
public class RolesController {
    protected static Logger logger = Logger.getLogger("USER controller");

    @Autowired
    private RoleRepository roleservice;

    @Secured("ROLE_ADMIN")
    @Transactional(readOnly = true)
    @RequestMapping(value = "/listoptions", method = RequestMethod.POST)
    @ResponseBody
    public JSTableOptionsResponse<Roles> getRoleOptions(Model model) {

        logger.log(Level.INFO,"Received request to show role options");

        // Retrieve all persons by delegating the call to PersonService

        List<Roles> roles=roleservice.findAll();
        if(roles==null) {
            return new JSTableOptionsResponse<Roles>("NO ROLES!");
        } else {

            return new JSTableOptionsResponse<Roles>(roles);
        }
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    @ResponseBody
    public List<Roles> getRoles(Model model) {

        logger.log(Level.INFO,"Received request to show role options");

        // Retrieve all persons by delegating the call to PersonService

        List<Roles> roles=roleservice.findAll();
        return roles;
    }
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<Roles> getAllFileTypesSortSearch(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting,@RequestParam(required = false) String searchname) {

        logger.log(Level.INFO,"Received request to show "+jtPageSize+" users from"+jtStartIndex);

        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        PageRequest pager;
        Page<Roles> page;
        if(sort!=null){
            pager=new PageRequest(jtStartIndex,jtPageSize,sort);
        } else {
            pager=new PageRequest(jtStartIndex, jtPageSize);
        }
        if(searchname!=null && !searchname.isEmpty()){
           page =  roleservice.findByRoleNameLike(searchname, pager);
        } else {
            page = roleservice.findAll(pager);
        }
        return new JSTableExpenseListResp<Roles>(page);
    }
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/lists", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<Roles> getAllFileTypesSort(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting) {

        logger.log(Level.INFO,"Received request to show "+jtPageSize+" users from"+jtStartIndex);

        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        Page<Roles> page;
        if(sort!=null){
            page= roleservice.findAll(new PageRequest(jtStartIndex,jtPageSize,sort));
        } else {
            page = roleservice.findAll(new PageRequest(jtStartIndex, jtPageSize));
        }
        return new JSTableExpenseListResp<Roles>(page);
    }



}
