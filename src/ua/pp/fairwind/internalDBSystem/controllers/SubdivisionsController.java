package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.ProgramOperationJornal;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Subdivision;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Activities;
import ua.pp.fairwind.internalDBSystem.dateTable.*;
import ua.pp.fairwind.internalDBSystem.security.UserDetailsAdapter;
import ua.pp.fairwind.internalDBSystem.services.JournalService;
import ua.pp.fairwind.internalDBSystem.services.repository.SubdivisionRepository;

import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ������ on 21.07.2015.
 */
@Controller
@RequestMapping("/subdivisions")
public class SubdivisionsController {
    @Autowired
    private SubdivisionRepository subdivisionsservice;
    @Autowired
    private JournalService journal;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model) {
        return "subdivisionstable";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listedit", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<Subdivision> getAllFileTypesSortSearch(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting,@RequestParam(required = false) String searchname) {
        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        PageRequest pager;
        Page<Subdivision> page;
        if(sort!=null){
            pager=new PageRequest(jtStartIndex,jtPageSize,sort);
        } else {
            pager=new PageRequest(jtStartIndex, jtPageSize);
        }
        if(searchname!=null && !searchname.isEmpty()){
           page =  subdivisionsservice.findByNameLike(searchname, pager);
        } else {
            page = subdivisionsservice.findAll(pager);
        }
        return new JSTableExpenseListResp<>(page);
    }
    @Transactional(readOnly = true)
    @RequestMapping(value = "/lists", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<Subdivision> getAllFileTypesSort(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting) {
        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        Page<Subdivision> page;
        if(sort!=null){
            page= subdivisionsservice.findAll(new PageRequest(jtStartIndex, jtPageSize, sort));
        } else {
            page = subdivisionsservice.findAll(new PageRequest(jtStartIndex, jtPageSize));
        }
        return new JSTableExpenseListResp<>(page);
    }
    @Secured("ROLE_ADMIN")
    @Transactional(readOnly = false)
    /*CRUD operation - Add*/
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<Subdivision> insertGroup(@ModelAttribute Subdivision activities, BindingResult result,Locale currentLocale) {
        JSTableExpenseResp jsonJtableResponse;
        if (result.hasErrors()) {
            return new JSTableExpenseResp<>(messageSource.getMessage("label.forminvalid",null,"INVALIDE DATA FORM!", currentLocale));
        }
        try {
            subdivisionsservice.save(activities);
            journal.log(ProgramOperationJornal.Operation.CREATE, "SUBDIVISION", activities.getName());
            jsonJtableResponse = new JSTableExpenseResp<>(activities);
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }
    @Secured("ROLE_ADMIN")
    @Transactional(readOnly = false)
    /*CRUD operation - Update */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<Subdivision>  updateRole(@ModelAttribute Subdivision subdivision, BindingResult result,Locale currentLocale) {
        JSTableExpenseResp<Subdivision>  jsonJtableResponse;
        if (result.hasErrors()) {
            jsonJtableResponse = new JSTableExpenseResp<>(messageSource.getMessage("label.forminvalid",null,"INVALIDE DATA FORM!", currentLocale));
            return jsonJtableResponse;
        }
        try {
            Subdivision subdivisionDB=subdivisionsservice.findOne(subdivision.getSubdivisionId());
            if(subdivisionDB!=null && subdivisionDB.getVersionid()<=subdivision.getVersionid()) {
                subdivisionDB.setName(subdivision.getName());
                subdivisionDB.setKey1c(subdivision.getKey1c());
                subdivisionsservice.save(subdivisionDB);
                journal.log(ProgramOperationJornal.Operation.UPDATE, "SUBDIVISION", "OLD:" + subdivisionDB.getName() + " NEW:" + subdivision.getName() + " 1CKEY_NEW:" + subdivision.getKey1c() + " OLD:" + subdivisionDB.getKey1c());
                jsonJtableResponse = new JSTableExpenseResp<>(subdivision);
            } else {
                jsonJtableResponse = new JSTableExpenseResp<>("Subdivision VAS MODIFIED OR DELETE IN ANOTHER TRANSACTION!");
            }
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }
    @Secured("ROLE_ADMIN")
    @Transactional(readOnly = false)
    /*CRUD operation - Delete */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<Subdivision>  delete(@RequestParam String subdivisionId,Locale currentLocale) {
        JSTableExpenseResp<Subdivision>  jsonJtableResponse;
        try {
            long id=new Long(subdivisionId);
            if(subdivisionsservice.getChildRecordCount(id)==0 && subdivisionsservice.getChildUserCount(id)==0) {
                subdivisionsservice.delete(id);
                journal.log(ProgramOperationJornal.Operation.DELETE, "SUBDIVISION", subdivisionId);
                jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.OK, "OK");
            }else{
                jsonJtableResponse = new JSTableExpenseResp<>(messageSource.getMessage("label.forbidden",null,"DELETE FORBIDDEN!", currentLocale));
            }
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }

    @Transactional(readOnly = true)
    /*Options for CRUD operation*/
    @RequestMapping(value = "/options", method = RequestMethod.POST)
    @ResponseBody
    public JSTableOptionsResponse<JSTableExpenseOptionsBean> getOptions() {
        JSTableOptionsResponse<JSTableExpenseOptionsBean> jsonJtableResponse;
        try {

            jsonJtableResponse = new JSTableOptionsResponse<>(subdivisionsservice.getAllSubdivisionOptions());
        } catch (Exception e) {
            jsonJtableResponse = new JSTableOptionsResponse<>(e.getMessage());
        }
        return jsonJtableResponse;
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listing", method = RequestMethod.GET)
    @ResponseBody
    public Object simpleList(Model model,@RequestParam(required = false) Integer page_num, @RequestParam(required = false) Integer per_page,@RequestParam(value = "pkey_val[]",required = false) String pkey,@RequestParam(value = "q_word[]",required = false) String[] qword) {
        // Retrieve all persons by delegating the call to PersonService
        //Sort sort= FormSort.formSortFromSortDescription(orderby);
        Sort sort=new Sort(Sort.Direction.ASC,"name");
        PageRequest pager=null;
        if(page_num!=null && per_page!=null) {
            pager = new PageRequest(page_num - 1, per_page, sort);
        }
        if(pager!=null) {
            Page<Subdivision> page;
            if (qword != null && qword.length > 0) {
                page = subdivisionsservice.findByNameContains(qword[0], pager);
            } else {
                page = subdivisionsservice.findAll(pager);
            }
            return new JSComboExpenseResp<>(page);
        } else {
            if(pkey!=null && !pkey.isEmpty()){
                Long key=Long.valueOf(pkey);
                Subdivision ft=null;
                if(key!=null) {
                    ft = subdivisionsservice.findOne(key);
                }
                return ft;
            } else {
                List<Subdivision> page;
                if (qword != null && qword.length > 0) {
                    page = subdivisionsservice.findByNameContains(qword[0]);
                } else {
                    page = subdivisionsservice.findAll(sort);
                }
                return new JSComboExpenseResp<>(page);
            }
        }
    }


    @Transactional(readOnly = true)
    @RequestMapping(value = "/selecting", method = RequestMethod.GET)
    @ResponseBody
    public JSSelectExpenseResp<Subdivision> multiList(Model model,@RequestParam int page_num, @RequestParam int per_page) {
        // Retrieve all persons by delegating the call to PersonService
        Sort sort= null;//FormSort.formSortFromSortDescription(jtSorting);
        PageRequest pager;
        Page<Subdivision> page;

        if(sort!=null){
            pager=new PageRequest(page_num-1,per_page,sort);
        } else {
            pager=new PageRequest(page_num-1, per_page);
        }
        /*
        if(searchname!=null && !searchname.isEmpty()){
            page =  subdivisionsservice.findByNameLike(searchname, pager);
        } else {
            page = subdivisionsservice.findAll(pager);
        }*/
        page = subdivisionsservice.findAll(pager);
        return new JSSelectExpenseResp<>(page);
    }

    @Transactional(readOnly = true)
    @Secured({"ROLE_GROUP_EDIT", "ROLE_SUPER_EDIT","ROLE_MAIN_EDIT"})
    /*Options for CRUD operation*/
    @RequestMapping(value = "/optionsList", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public JSTableOptionsResponse<JSTableExpenseOptionsBean> getOptionsWithAccessControl() {
        JSTableOptionsResponse<JSTableExpenseOptionsBean> jsonJtableResponse;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsAdapter user=(UserDetailsAdapter)auth.getPrincipal();
            if (user.hasRole("ROLE_SUPER_EDIT")||user.hasRole("ROLE_SUPER_VIEW")) {
                jsonJtableResponse = new JSTableOptionsResponse<>(subdivisionsservice.getAllSubdivisionOptions());
            } else {
                jsonJtableResponse = new JSTableOptionsResponse<>(subdivisionsservice.getAllSubdivisionOptionsWithAccessControl(user.getTrustedSubvisionsId()));
            }

        } catch (Exception e) {
            jsonJtableResponse = new JSTableOptionsResponse<>(e.getMessage());
        }
        return jsonJtableResponse;
    }
}
