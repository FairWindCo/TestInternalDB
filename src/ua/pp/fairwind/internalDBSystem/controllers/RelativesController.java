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
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Hobbies;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Relatives;
import ua.pp.fairwind.internalDBSystem.dateTable.*;
import ua.pp.fairwind.internalDBSystem.services.repository.RelativesRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ������ on 21.07.2015.
 */
@Controller
@RequestMapping("/relatives")
public class RelativesController {
    protected static Logger logger = Logger.getLogger("controller");

    @Autowired
    private RelativesRepository relativesservice;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model) {
        return "relativestable";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listedit", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<Relatives> getAllFileTypesSortSearch(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting,@RequestParam(required = false) String searchname) {

        logger.log(Level.INFO,"Received request to show "+jtPageSize+" filetypes from"+jtStartIndex);

        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        PageRequest pager;
        Page<Relatives> page;
        if(sort!=null){
            pager=new PageRequest(jtStartIndex,jtPageSize,sort);
        } else {
            pager=new PageRequest(jtStartIndex, jtPageSize);
        }
        if(searchname!=null && !searchname.isEmpty()){
           page =  relativesservice.findByNameLike(searchname, pager);
        } else {
            page = relativesservice.findAll(pager);
        }
        return new JSTableExpenseListResp<>(page);
    }
    @Transactional(readOnly = true)
    @RequestMapping(value = "/lists", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<Relatives> getAllFileTypesSort(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting) {

        logger.log(Level.INFO,"Received request to show "+jtPageSize+" filetypes from"+jtStartIndex);

        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        Page<Relatives> page;
        if(sort!=null){
            page= relativesservice.findAll(new PageRequest(jtStartIndex, jtPageSize, sort));
        } else {
            page = relativesservice.findAll(new PageRequest(jtStartIndex, jtPageSize));
        }
        return new JSTableExpenseListResp<>(page);
    }
    @Secured("ROLE_GLOBAL_INFO_EDIT")
    @Transactional(readOnly = false)
    /*CRUD operation - Add*/
    @RequestMapping(value = "/addfiletype", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<Relatives> insertGroup(@ModelAttribute Relatives hobbies, BindingResult result) {
        JSTableExpenseResp jsonJtableResponse;
        if (result.hasErrors()) {
            return new JSTableExpenseResp<>("Form invalid");
        }
        try {
            relativesservice.save(hobbies);
            jsonJtableResponse = new JSTableExpenseResp<>(hobbies);
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }
    @Secured("ROLE_GLOBAL_INFO_EDIT")
    @Transactional(readOnly = false)
    /*CRUD operation - Update */
    @RequestMapping(value = "/updatefiletype", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<Relatives>  updateRole(@ModelAttribute Relatives hobbies, BindingResult result) {
        JSTableExpenseResp<Relatives>  jsonJtableResponse;
        if (result.hasErrors()) {
            jsonJtableResponse = new JSTableExpenseResp<>("Form invalid");
            return jsonJtableResponse;
        }
        try {
            relativesservice.save(hobbies);
            jsonJtableResponse = new JSTableExpenseResp<>(hobbies);
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }
    @Secured("ROLE_GLOBAL_INFO_EDIT")
    @Transactional(readOnly = false)
    /*CRUD operation - Delete */
    @RequestMapping(value = "/deletefiletype", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<Relatives>  delete(@RequestParam String filesTypeId) {
        JSTableExpenseResp<Relatives>  jsonJtableResponse;
        try {

            relativesservice.delete(new Long(filesTypeId));
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

            jsonJtableResponse = new JSTableOptionsResponse<>(relativesservice.getAllRelativesOptions());
        } catch (Exception e) {
            jsonJtableResponse = new JSTableOptionsResponse<>(e.getMessage());
        }
        return jsonJtableResponse;
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listing", method = RequestMethod.GET)
    @ResponseBody
    public Object simpleList(Model model,@RequestParam(required = false) Integer page_num, @RequestParam(required = false) Integer per_page,@RequestParam(value = "pkey_val[]",required = false) String pkey,@RequestParam(value = "q_word[]",required = false) String[] qword) {

        logger.log(Level.INFO,"Received request to show "+per_page+" filetypes from"+page_num);

        // Retrieve all persons by delegating the call to PersonService
        //Sort sort= FormSort.formSortFromSortDescription(orderby);
        Sort sort=new Sort(Sort.Direction.ASC,"name");
        PageRequest pager=null;
        if(page_num!=null && per_page!=null) {
            pager = new PageRequest(page_num - 1, per_page, sort);
        }
        if(pager!=null) {
            Page<Relatives> page;
            if (qword != null && qword.length > 0) {
                page = relativesservice.findByNameContains(qword[0], pager);
            } else {
                page = relativesservice.findAll(pager);
            }
            return new JSComboExpenseResp<>(page);
        } else {
            if(pkey!=null && !pkey.isEmpty()){
                Long key=Long.valueOf(pkey);
                Relatives ft=null;
                if(key!=null) {
                    ft = relativesservice.findOne(key);
                }
                return ft;
            } else {
                List<Relatives> page;
                if (qword != null && qword.length > 0) {
                    page = relativesservice.findByNameContains(qword[0]);
                } else {
                    page = relativesservice.findAll(sort);
                }
                return new JSComboExpenseResp<>(page);
            }
        }
    }

}
