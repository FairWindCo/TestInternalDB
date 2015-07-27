package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Roles;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.User;
import ua.pp.fairwind.internalDBSystem.dateTable.FormSort;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseListResp;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseResp;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseResult;
import ua.pp.fairwind.internalDBSystem.services.repository.UserRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Сергей on 21.07.2015.
 */
@Controller
@RequestMapping("/users")
public class UserController {
    protected static Logger logger = Logger.getLogger("USER controller");

    @Autowired
    private UserRepository userservice;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model) {
        return "user";
    }
    @Transactional(readOnly = true)
    @RequestMapping(value = "/listedit", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<User> getAllFileTypesSortSearch(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting,@RequestParam(required = false) String searchname) {

        logger.log(Level.INFO,"Received request to show "+jtPageSize+" users from"+jtStartIndex);

        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        PageRequest pager;
        Page<User> page;
        if(sort!=null){
            pager=new PageRequest(jtStartIndex,jtPageSize,sort);
        } else {
            pager=new PageRequest(jtStartIndex, jtPageSize);
        }
        if(searchname!=null && !searchname.isEmpty()){
           page =  userservice.findByUserNameLike(searchname, pager);
        } else {
            page = userservice.findAll(pager);
        }
        return new JSTableExpenseListResp<User>(page);
    }
    @Transactional(readOnly = true)
    @RequestMapping(value = "/userroles", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<Roles> getUserRoles(Model model,@RequestParam(required = true) long userID,@RequestParam(required = false) Integer jtStartIndex, @RequestParam(required = false) Integer jtPageSize, @RequestParam(required = false) String jtSorting,@RequestParam(required = false) String searchname) {

        logger.log(Level.INFO,"Received request to show "+jtPageSize+" users from"+jtStartIndex);

        // Retrieve all persons by delegating the call to PersonService
        if(jtStartIndex!=null && jtPageSize!=null) {
            Sort sort= FormSort.formSortFromSortDescription(jtSorting);
            PageRequest pager;
            Page<Roles> page;
            if (sort != null) {
                pager = new PageRequest(jtStartIndex, jtPageSize, sort);
            } else {
                pager = new PageRequest(jtStartIndex, jtPageSize);
            }
            if (searchname != null && !searchname.isEmpty()) {
                page = userservice.findByUserID(userID, pager);
            } else {
                page = userservice.findByUserID(userID, pager);
            }
            return new JSTableExpenseListResp<Roles>(page);
        } else {
            List<Roles> roles=userservice.findByUserID(userID);
            return new JSTableExpenseListResp<Roles>(roles,roles.size());
        }

    }
    @Transactional(readOnly = true)
    @RequestMapping(value = "/lists", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<User> getAllFileTypesSort(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting) {

        logger.log(Level.INFO,"Received request to show "+jtPageSize+" users from"+jtStartIndex);

        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        Page<User> page;
        if(sort!=null){
            page= userservice.findAll(new PageRequest(jtStartIndex,jtPageSize,sort));
        } else {
            page = userservice.findAll(new PageRequest(jtStartIndex, jtPageSize));
        }
        return new JSTableExpenseListResp<User>(page);
    }
    @Transactional(readOnly = false)
    /*CRUD operation - Add*/
    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<User> insertGroup(@ModelAttribute User user, BindingResult result) {
        JSTableExpenseResp jsonJtableResponse;
        if (result.hasErrors()) {
            return new JSTableExpenseResp<User>("Form invalid");
        }
        try {
            userservice.save(user);
            jsonJtableResponse = new JSTableExpenseResp<>(user);
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }
    @Transactional(readOnly = false)
    /*CRUD operation - Update */
    @RequestMapping(value = "/updateuser", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<User>  updateRole(@ModelAttribute User user, BindingResult result) {
        JSTableExpenseResp<User>  jsonJtableResponse;
        if (result.hasErrors()) {
            jsonJtableResponse = new JSTableExpenseResp<>("Form invalid");
            return jsonJtableResponse;
        }
        try {
            userservice.save(user);
            jsonJtableResponse = new JSTableExpenseResp<>(user);
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }
    @Transactional(readOnly = false)
    /*CRUD operation - Delete */
    @RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<User>  delete(@RequestParam String filesTypeId) {
        JSTableExpenseResp<User>  jsonJtableResponse;
        try {

            userservice.delete(new Long(filesTypeId));
            jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.OK,"OK");
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }

}
