package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.ProgramOperationJornal;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.FilesType;
import ua.pp.fairwind.internalDBSystem.dateTable.*;
import ua.pp.fairwind.internalDBSystem.services.JournalService;
import ua.pp.fairwind.internalDBSystem.services.repository.FileTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ������ on 21.07.2015.
 */
@Controller
@RequestMapping("/filetypes")
public class FileTypeController {
    @Autowired
    private FileTypeRepository filetypeservice;
    @Autowired
    private JournalService journal;
    @Autowired
    private MessageSource messageSource;

    @Secured("ROLE_GLOBAL_INFO_EDIT")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model) {
        return "filetypetable";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listedit", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<FilesType> getAllFileTypesSortSearch(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting,@RequestParam(required = false) String searchname) {
        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        PageRequest pager;
        Page<FilesType> page;
        if(sort!=null){
            pager=new PageRequest(jtStartIndex,jtPageSize,sort);
        } else {
            pager=new PageRequest(jtStartIndex, jtPageSize);
        }
        if(searchname!=null && !searchname.isEmpty()){
           page =  filetypeservice.findByFilesTypeNameLike(searchname,pager);
        } else {
            page = filetypeservice.findAll(pager);
        }
        return new JSTableExpenseListResp<FilesType>(page);
    }
    @Transactional(readOnly = true)
    @RequestMapping(value = "/lists", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<FilesType> getAllFileTypesSort(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting) {
        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        Page<FilesType> page;
        if(sort!=null){
            page=filetypeservice.findAll(new PageRequest(jtStartIndex, jtPageSize, sort));
        } else {
            page = filetypeservice.findAll(new PageRequest(jtStartIndex, jtPageSize));
        }
        return new JSTableExpenseListResp<FilesType>(page);
    }

    @Secured("ROLE_GLOBAL_INFO_EDIT")
    @Transactional(readOnly = false)
    /*CRUD operation - Add*/
    @RequestMapping(value = "/addfiletype", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<FilesType> insertGroup(@ModelAttribute FilesType filetype, BindingResult result,Locale currentLocale) {
        JSTableExpenseResp jsonJtableResponse;
        if (result.hasErrors()) {
            return new JSTableExpenseResp<>(messageSource.getMessage("label.forminvalid",null,"INVALIDE DATA FORM!", currentLocale));
        }
        try {
            filetypeservice.save(filetype);
            journal.log(ProgramOperationJornal.Operation.CREATE, "FILE_TYPE", "NAME:" + filetype.getFilesTypeName() + " 1CKEY_NEW:" + filetype.getKey1c());
            jsonJtableResponse = new JSTableExpenseResp<>(filetype);
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
    public JSTableExpenseResp<FilesType>  updateRole(@ModelAttribute FilesType filetype, BindingResult result,Locale currentLocale) {
        JSTableExpenseResp<FilesType>  jsonJtableResponse;
        if (result.hasErrors()) {
            jsonJtableResponse = new JSTableExpenseResp<>(messageSource.getMessage("label.forminvalid",null,"INVALIDE DATA FORM!", currentLocale));
            return jsonJtableResponse;
        }
        try {
            filetypeservice.save(filetype);
            journal.log(ProgramOperationJornal.Operation.UPDATE, "FILE_TYPE", "NAME:" + filetype.getFilesTypeName() + " 1CKEY_NEW:" + filetype.getKey1c());
            jsonJtableResponse = new JSTableExpenseResp<>(filetype);
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
    public JSTableExpenseResp<FilesType>  delete(@RequestParam String filesTypeId,Locale currentLocale) {
        JSTableExpenseResp<FilesType>  jsonJtableResponse;
        try {
            long id=new Long(filesTypeId);
            if(filetypeservice.getChildRecordCount(id)==0) {
            filetypeservice.delete(id);
                journal.log(ProgramOperationJornal.Operation.DELETE, "FILE_TYPE", filesTypeId);
            jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.OK,"OK");
            } else {
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

            jsonJtableResponse = new JSTableOptionsResponse<>(filetypeservice.getAllFileTypeOptions());
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
        Sort sort=new Sort(Sort.Direction.ASC,"filesTypeName");
        PageRequest pager=null;
        if(page_num!=null && per_page!=null) {
            pager = new PageRequest(page_num - 1, per_page, sort);
        }
        if(pager!=null) {
            Page<FilesType> page;
            if (qword != null && qword.length > 0) {
                page = filetypeservice.findByFilesTypeNameContains(qword[0], pager);
            } else {
                page = filetypeservice.findAll(pager);
            }
            return new JSComboExpenseResp<>(page);
        } else {
            if(pkey!=null && !pkey.isEmpty()){
                Long key=Long.valueOf(pkey);
                FilesType ft=null;
                if(key!=null) {
                    ft = filetypeservice.findOne(key);
                }
                return ft;
            } else {
                List<FilesType> page;
                if (qword != null && qword.length > 0) {
                    page = filetypeservice.findByFilesTypeNameContains(qword[0]);
                } else {
                    page = filetypeservice.findAll(sort);
                }
                return new JSComboExpenseResp<>(page);
            }
        }
    }

}
