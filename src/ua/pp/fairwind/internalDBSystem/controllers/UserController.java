package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Roles;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Subdivision;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.User;
import ua.pp.fairwind.internalDBSystem.dateTable.*;
import ua.pp.fairwind.internalDBSystem.services.repository.RoleRepository;
import ua.pp.fairwind.internalDBSystem.services.repository.SubdivisionRepository;
import ua.pp.fairwind.internalDBSystem.services.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ������ on 21.07.2015.
 */
@Controller
@RequestMapping("/users")
public class UserController {
    protected static Logger logger = Logger.getLogger("USER controller");

    @Autowired
    private UserRepository userservice;
    @Autowired
    private RoleRepository roleservice;
    @Autowired
    private SubdivisionRepository subdivservice;

    @Secured("ROLE_ADMIN")
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
    @Secured("ROLE_ADMIN")
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
            Set<Roles> roles=userservice.findByUserID(userID);
            return new JSTableExpenseListResp<Roles>(roles,roles.size());
        }

    }
    @Secured("ROLE_ADMIN")
    @Transactional(readOnly = true)
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<Roles> getRolesByUserId(Model model,@RequestParam(required = true) long userID,@RequestParam(required = false) Integer jtStartIndex, @RequestParam(required = false) Integer jtPageSize, @RequestParam(required = false) String jtSorting,@RequestParam(required = false) String searchname) {

        logger.log(Level.INFO,"Received request to show "+jtPageSize+" users from"+jtStartIndex);

        User user=userservice.findOne(userID);
        if(user==null){
            return new JSTableExpenseListResp<Roles>(JSTableExpenseResult.ERROR,"NO RULES");
        } else {
            Set<Roles> roles=user.getUserRoles();
            if(roles==null){
                return new JSTableExpenseListResp<Roles>(JSTableExpenseResult.ERROR,"NO RULES");
            } else {
                return new JSTableExpenseListResp<Roles>(roles,roles.size());
            }
        }
    }
    @Secured("ROLE_ADMIN")
    @Transactional(readOnly = true)
    @RequestMapping(value = "/lists", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<User> getAllFileTypesSort(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting) {

        logger.log(Level.INFO,"Received request to show "+jtPageSize+" users from"+jtStartIndex);

        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        Page<User> page;
        if(sort!=null){
            page= userservice.findAll(new PageRequest(jtStartIndex, jtPageSize, sort));
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
    @Secured("ROLE_ADMIN")
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
    @Secured("ROLE_ADMIN")
    @Transactional(readOnly = false)
    /*CRUD operation - Delete */
    @RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<User>  delete(@ModelAttribute User user, BindingResult result) {
        JSTableExpenseResp<User>  jsonJtableResponse;
        try {
            userservice.delete(user.getUserID());
            jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.OK,"OK");
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }

    @Secured("ROLE_ADMIN")
    @Transactional(readOnly = true)
    @RequestMapping(value = "/avaibleRoles", method = RequestMethod.GET)
    @ResponseBody
    public JSTableExpenseListResp<Roles> getAvaibleRoles(@RequestParam(required = true) long userID) {
        JSTableExpenseListResp<Roles>  jsonJtableResponse;
        try {

            Set<Long> assignedRolesID=userservice.getRolesIDForUserId(userID);
            if(assignedRolesID==null || assignedRolesID.isEmpty()){
                List<Roles> avaibleRoles = userservice.getAllRoles();
                return new JSTableExpenseListResp<Roles>(avaibleRoles);
            } else {
                List<Roles> avaibleRoles = userservice.findGetAvaibleRoles(assignedRolesID);
                return new JSTableExpenseListResp<Roles>(avaibleRoles);
            }
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseListResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }
    @Secured("ROLE_ADMIN")
    @Transactional(readOnly = true)
    @RequestMapping(value = "/avaibleRolesOpt", method = RequestMethod.POST)
    @ResponseBody
    public JSTableOptionsResponse<JSTableExpenseOptionsBean> getAvaibleRolesOpt(@RequestParam(required = true) long userID) {
        JSTableOptionsResponse<JSTableExpenseOptionsBean>  jsonJtableResponse;
        try {

            Set<Long> assignedRolesID=userservice.getRolesIDForUserId(userID);
            if(assignedRolesID==null || assignedRolesID.isEmpty()){
                List<JSTableExpenseOptionsBean> avaibleRoles = userservice.findAllRolesOptions();
                return new JSTableOptionsResponse<JSTableExpenseOptionsBean>(avaibleRoles);
            } else {
                List<JSTableExpenseOptionsBean> avaibleRoles = userservice.findGetAvaibleRolesOptions(assignedRolesID);
                return new JSTableOptionsResponse<JSTableExpenseOptionsBean>(avaibleRoles);
            }
        } catch (Exception e) {
            jsonJtableResponse = new JSTableOptionsResponse<>(e.getMessage());
        }
        return jsonJtableResponse;
    }
    @Secured("ROLE_ADMIN")
    @Transactional(readOnly = false)
    @RequestMapping(value = "/adduserrole", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<Roles> setupNewRole(@RequestParam(required = true) long newRoleID,@RequestParam(required = true) long userId) {
        JSTableExpenseResp<Roles>  jsonJtableResponse;
        try {
            Roles role=roleservice.findOne(newRoleID);
            User user=userservice.findOne(userId);
            if(user!=null && role!=null) {
                user.addUserRoles(role);
                jsonJtableResponse = new JSTableExpenseResp<>(role);
            } else {
                jsonJtableResponse = new JSTableExpenseResp<>("NO RECORD FOUND!");
            }
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }
    @Secured("ROLE_ADMIN")
    @Transactional(readOnly = false)
    @RequestMapping(value = "/removeuserrole", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<Roles> removeRole(@RequestParam(required = true) long roleId,@RequestParam(required = true) long userId) {
        JSTableExpenseResp<Roles>  jsonJtableResponse;
        try {
            Roles role=roleservice.findOne(roleId);
            User user=userservice.findOne(userId);
            if(user!=null && role!=null) {
                user.removeUserRoles(role);
                jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.OK,"OK");
            } else {
                jsonJtableResponse = new JSTableExpenseResp<>("NO RECORD FOUND!");
            }
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }


    @Secured("ROLE_ADMIN")
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/avaibleGrantedSubdiv", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<Subdivision> getAvaibleGruntedSubdivisions(@RequestParam(required = true) long userID) {
        User user=userservice.findOne(userID);
        if(user==null){
            return new JSTableExpenseListResp<>(JSTableExpenseResult.ERROR,"NO USER WITH  ID "+userID);
        } else {
            Set<Subdivision> roles=user.getGrantedSubdivisions();
            if(roles==null){
                return new JSTableExpenseListResp<>(JSTableExpenseResult.ERROR,"NO SUBDIVISIONS");
            } else {
                return new JSTableExpenseListResp<>(roles,roles.size());
            }
        }
    }

    @Secured("ROLE_ADMIN")
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/avaibleGrantedSubdivOpt", method = RequestMethod.POST)
    @ResponseBody
    public JSTableOptionsResponse<JSTableExpenseOptionsBean> getAvaibleGruntedSubdivisionsOpt(@RequestParam(required = true) long userID) {
        JSTableOptionsResponse<JSTableExpenseOptionsBean>  jsonJtableResponse;
        try {

            Set<Long> assignedRolesID=userservice.getGrantedSubdivisionsIDForUserId(userID);
            if(assignedRolesID==null || assignedRolesID.isEmpty()){
                List<JSTableExpenseOptionsBean> avaibleRoles = userservice.getAllSubdivisionsOptions();
                return new JSTableOptionsResponse<>(avaibleRoles);
            } else {
                List<JSTableExpenseOptionsBean> avaibleRoles = userservice.getAvaibleSubdivisionsOptions(assignedRolesID);
                return new JSTableOptionsResponse<>(avaibleRoles);
            }
        } catch (Exception e) {
            jsonJtableResponse = new JSTableOptionsResponse<>(e.getMessage());
        }
        return jsonJtableResponse;
    }
    @Secured("ROLE_ADMIN")
    @Transactional(readOnly = false)
    @RequestMapping(value = "/addgrantedsubdivision", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<Subdivision> setupNewGrantedSubdivision(@RequestParam(required = true) long subdivId,@RequestParam(required = true) long userId) {
        JSTableExpenseResp<Subdivision>  jsonJtableResponse;
        try {
            Subdivision role=subdivservice.findOne(subdivId);
            User user=userservice.findOne(userId);
            if(user!=null && role!=null) {
                user.addGrantedSubdivisions(role);
                jsonJtableResponse = new JSTableExpenseResp<>(role);
            } else {
                jsonJtableResponse = new JSTableExpenseResp<>("NO RECORD FOUND!");
            }
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }
    @Secured("ROLE_ADMIN")
    @Transactional(readOnly = false)
    @RequestMapping(value = "/removegrantedsubdivision", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<Subdivision> removeGrantedSubdivision(@RequestParam(required = true) long subdivisionId,@RequestParam(required = true) long userId) {
        JSTableExpenseResp<Subdivision>  jsonJtableResponse;
        try {
            Subdivision role=subdivservice.findOne(subdivisionId);
            User user=userservice.findOne(userId);
            if(user!=null && role!=null) {
                user.removeGrantedSubdivisions(role);
                jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.OK,"OK");
            } else {
                jsonJtableResponse = new JSTableExpenseResp<>("NO RECORD FOUND!");
            }
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }

}
