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
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Subdivision;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Activities;
import ua.pp.fairwind.internalDBSystem.dateTable.*;
import ua.pp.fairwind.internalDBSystem.services.repository.SubdivisionRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ������ on 21.07.2015.
 */
@Controller
@RequestMapping("/subdivisions")
public class SubdivisionsController {
    protected static Logger logger = Logger.getLogger("controller");

    @Autowired
    private SubdivisionRepository subdivisionsservice;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model) {
        return "subdivisionstable";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listedit", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<Subdivision> getAllFileTypesSortSearch(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting,@RequestParam(required = false) String searchname) {

        logger.log(Level.INFO,"Received request to show "+jtPageSize+" filetypes from"+jtStartIndex);

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

        logger.log(Level.INFO,"Received request to show "+jtPageSize+" filetypes from"+jtStartIndex);

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
    public JSTableExpenseResp<Subdivision> insertGroup(@ModelAttribute Subdivision activities, BindingResult result) {
        JSTableExpenseResp jsonJtableResponse;
        if (result.hasErrors()) {
            return new JSTableExpenseResp<>("Form invalid");
        }
        try {
            subdivisionsservice.save(activities);
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
    public JSTableExpenseResp<Subdivision>  updateRole(@ModelAttribute Subdivision subdivision, BindingResult result) {
        JSTableExpenseResp<Subdivision>  jsonJtableResponse;
        if (result.hasErrors()) {
            jsonJtableResponse = new JSTableExpenseResp<>("Form invalid");
            return jsonJtableResponse;
        }
        try {
            Subdivision subdivisionDB=subdivisionsservice.findOne(subdivision.getSubdivisionId());
            if(subdivisionDB!=null && subdivisionDB.getVersionid()<=subdivision.getVersionid()) {
                subdivisionDB.setName(subdivision.getName());
                subdivisionsservice.save(subdivisionDB);
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
    public JSTableExpenseResp<Subdivision>  delete(@RequestParam String subdivisionId) {
        JSTableExpenseResp<Subdivision>  jsonJtableResponse;
        try {

            subdivisionsservice.delete(new Long(subdivisionId));
            jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.OK,"OK");
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
    public JSComboExpenseResp<Subdivision> simpleList(Model model,@RequestParam int page_num, @RequestParam int per_page) {

        logger.log(Level.INFO,"Received request to show "+per_page+" filetypes from"+page_num);

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
        return new JSComboExpenseResp<>(page);
    }


    @Transactional(readOnly = true)
    @RequestMapping(value = "/selecting", method = RequestMethod.GET)
    @ResponseBody
    public JSSelectExpenseResp<Subdivision> multiList(Model model,@RequestParam int page_num, @RequestParam int per_page) {

        logger.log(Level.INFO,"Received request to show "+per_page+" filetypes from"+page_num);

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
}
