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
import ua.pp.fairwind.internalDBSystem.datamodel.directories.ContactType;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Hobbies;
import ua.pp.fairwind.internalDBSystem.dateTable.*;
import ua.pp.fairwind.internalDBSystem.services.JournalService;
import ua.pp.fairwind.internalDBSystem.services.repository.ContactTypeRepository;

import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ������ on 21.07.2015.
 */
@Controller
@RequestMapping("/contacttypes")
public class ContactTypeController {
    @Autowired
    private ContactTypeRepository contacttypeservice;
    @Autowired
    private JournalService journal;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @Secured("ROLE_GLOBAL_INFO_EDIT")
    public String show(Model model) {
        return "contacttypetable";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listedit", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<ContactType> getAllFileTypesSortSearch(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting,@RequestParam(required = false) String searchname) {
        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        PageRequest pager;
        Page<ContactType> page;
        if(sort!=null){
            pager=new PageRequest(jtStartIndex,jtPageSize,sort);
        } else {
            pager=new PageRequest(jtStartIndex, jtPageSize);
        }
        if(searchname!=null && !searchname.isEmpty()){
           page =  contacttypeservice.findByCobtactTypeNameLike(searchname, pager);
        } else {
            page = contacttypeservice.findAll(pager);
        }
        return new JSTableExpenseListResp<>(page);
    }
    @Transactional(readOnly = true)
    @RequestMapping(value = "/lists", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<ContactType> getAllFileTypesSort(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting) {
        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        Page<ContactType> page;
        if(sort!=null){
            page= contacttypeservice.findAll(new PageRequest(jtStartIndex, jtPageSize, sort));
        } else {
            page = contacttypeservice.findAll(new PageRequest(jtStartIndex, jtPageSize));
        }
        return new JSTableExpenseListResp<>(page);
    }
    @Secured("ROLE_GLOBAL_INFO_EDIT")
    @Transactional(readOnly = false)
    /*CRUD operation - Add*/
    @RequestMapping(value = "/addfiletype", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<ContactType> insertGroup(@ModelAttribute ContactType contactType, BindingResult result,Locale currentLocale) {
        JSTableExpenseResp jsonJtableResponse;
        if (result.hasErrors()) {
            return new JSTableExpenseResp<>(messageSource.getMessage("label.forminvalid",null,"INVALIDE DATA FORM!", currentLocale));
        }
        try {
            journal.log(ProgramOperationJornal.Operation.CREATE, "CONTACT_TYPE", "NEW:" + contactType.getCobtactTypeName() + " 1CKEY_NEW:" + contactType.getKey1c());
            contacttypeservice.save(contactType);
            jsonJtableResponse = new JSTableExpenseResp<>(contactType);
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
    public JSTableExpenseResp<ContactType>  updateRole(@ModelAttribute ContactType contactType, BindingResult result,Locale currentLocale) {
        JSTableExpenseResp<ContactType>  jsonJtableResponse;
        if (result.hasErrors()) {
            jsonJtableResponse = new JSTableExpenseResp<>(messageSource.getMessage("label.forminvalid",null,"INVALIDE DATA FORM!", currentLocale));
            return jsonJtableResponse;
        }
        try {
            contacttypeservice.save(contactType);
            journal.log(ProgramOperationJornal.Operation.UPDATE, "CONTACT_TYPE", "NEW:" + contactType.getCobtactTypeName() + " 1CKEY_NEW:" + contactType.getKey1c());
            jsonJtableResponse = new JSTableExpenseResp<>(contactType);
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
    public JSTableExpenseResp<ContactType>  delete(@RequestParam String filesTypeId,Locale currentLocale) {
        JSTableExpenseResp<ContactType>  jsonJtableResponse;
        try {
            long id=new Long(filesTypeId);
            if(contacttypeservice.getChildRecordCount(id)==0) {
                contacttypeservice.delete(id);
                journal.log(ProgramOperationJornal.Operation.DELETE, "CONTACT_TYPE", "ID:" + id);
                jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.OK, "OK");
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

            jsonJtableResponse = new JSTableOptionsResponse<>(contacttypeservice.getAllFileTypeOptions());
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
        Sort sort=new Sort(Sort.Direction.ASC,"cobtactTypeName");
        PageRequest pager=null;
        if(page_num!=null && per_page!=null) {
            pager = new PageRequest(page_num - 1, per_page, sort);
        }
        if(pager!=null) {
            Page<ContactType> page;
            if (qword != null && qword.length > 0) {
                page = contacttypeservice.findByCobtactTypeNameContains(qword[0], pager);
            } else {
                page = contacttypeservice.findAll(pager);
            }
            return new JSComboExpenseResp<>(page);
        } else {
            if(pkey!=null && !pkey.isEmpty()){
                Long key=Long.valueOf(pkey);
                ContactType ft=null;
                if(key!=null) {
                    ft = contacttypeservice.findOne(key);
                }
                return ft;
            } else {
                List<ContactType> page;
                if (qword != null && qword.length > 0) {
                    page = contacttypeservice.findByCobtactTypeNameContains(qword[0]);
                } else {
                    page = contacttypeservice.findAll(sort);
                }
                return new JSComboExpenseResp<>(page);
            }
        }
    }

}
