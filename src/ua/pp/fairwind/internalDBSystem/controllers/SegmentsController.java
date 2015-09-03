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
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Segments;
import ua.pp.fairwind.internalDBSystem.dateTable.*;
import ua.pp.fairwind.internalDBSystem.services.repository.SegmentsRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ������ on 21.07.2015.
 */
@Controller
@RequestMapping("/segments")
public class SegmentsController {
    protected static Logger logger = Logger.getLogger("controller");

    @Autowired
    private SegmentsRepository segmentsservice;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model) {
        return "segmentstable";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listedit", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<Segments> getAllFileTypesSortSearch(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting,@RequestParam(required = false) String searchname) {

        logger.log(Level.INFO,"Received request to show "+jtPageSize+" filetypes from"+jtStartIndex);

        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        PageRequest pager;
        Page<Segments> page;
        if(sort!=null){
            pager=new PageRequest(jtStartIndex,jtPageSize,sort);
        } else {
            pager=new PageRequest(jtStartIndex, jtPageSize);
        }
        if(searchname!=null && !searchname.isEmpty()){
           page =  segmentsservice.findByNameLike(searchname, pager);
        } else {
            page = segmentsservice.findAll(pager);
        }
        return new JSTableExpenseListResp<>(page);
    }
    @Transactional(readOnly = true)
    @RequestMapping(value = "/lists", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<Segments> getAllFileTypesSort(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting) {

        logger.log(Level.INFO,"Received request to show "+jtPageSize+" filetypes from"+jtStartIndex);

        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        Page<Segments> page;
        if(sort!=null){
            page= segmentsservice.findAll(new PageRequest(jtStartIndex, jtPageSize, sort));
        } else {
            page = segmentsservice.findAll(new PageRequest(jtStartIndex, jtPageSize));
        }
        return new JSTableExpenseListResp<>(page);
    }
    @Secured("ROLE_GLOBAL_INFO_EDIT")
    @Transactional(readOnly = false)
    /*CRUD operation - Add*/
    @RequestMapping(value = "/addfiletype", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<Segments> insertGroup(@ModelAttribute Segments segments, BindingResult result) {
        JSTableExpenseResp jsonJtableResponse;
        if (result.hasErrors()) {
            return new JSTableExpenseResp<>("Form invalid");
        }
        try {
            segmentsservice.save(segments);
            jsonJtableResponse = new JSTableExpenseResp<>(segments);
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
    public JSTableExpenseResp<Segments>  updateRole(@ModelAttribute Segments segments, BindingResult result) {
        JSTableExpenseResp<Segments>  jsonJtableResponse;
        if (result.hasErrors()) {
            jsonJtableResponse = new JSTableExpenseResp<>("Form invalid");
            return jsonJtableResponse;
        }
        try {
            segmentsservice.save(segments);
            jsonJtableResponse = new JSTableExpenseResp<>(segments);
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
    public JSTableExpenseResp<Segments>  delete(@RequestParam String filesTypeId) {
        JSTableExpenseResp<Segments>  jsonJtableResponse;
        try {

            segmentsservice.delete(new Long(filesTypeId));
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

            jsonJtableResponse = new JSTableOptionsResponse<>(segmentsservice.getAllSegmentsOptions());
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
                Page<Segments> page;
                if (qword != null && qword.length > 0) {
                    page = segmentsservice.findByNameContains(qword[0], pager);
                } else {
                    page = segmentsservice.findAll(pager);
                }
                return new JSComboExpenseResp<>(page);
            } else {
                if(pkey!=null && !pkey.isEmpty()){
                    Long key=Long.valueOf(pkey);
                    Segments ft=null;
                    if(key!=null) {
                        ft = segmentsservice.findOne(key);
                    }
                    return ft;
                } else {
                    List<Segments> page;
                    if (qword != null && qword.length > 0) {
                        page = segmentsservice.findByNameContains(qword[0]);
                    } else {
                        page = segmentsservice.findAll(sort);
                    }
                    return new JSComboExpenseResp<>(page);
                }
            }
        }

}
