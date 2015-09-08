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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.pp.fairwind.internalDBSystem.datamodel.Dosser;
import ua.pp.fairwind.internalDBSystem.datamodel.Files;
import ua.pp.fairwind.internalDBSystem.datamodel.Person;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Category;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.InfoType;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Subdivision;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.DosserType;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.FilesType;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.PersonType;
import ua.pp.fairwind.internalDBSystem.dateTable.FormSort;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseListResp;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseResp;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseResult;
import ua.pp.fairwind.internalDBSystem.security.UserDetailsAdapter;
import ua.pp.fairwind.internalDBSystem.services.repository.*;

import javax.servlet.annotation.MultipartConfig;

/**
 * Created by Сергей on 31.08.2015.
 */
@Controller
@RequestMapping("/dossers")
public class DosserController {

    @Autowired
    DossersRepository dosserService;
    @Autowired
    PersonRepository personService;
    @Autowired
    FileRepository fileService;
    @Autowired
    SubdivisionRepository subdivService;
    @Autowired
    CategoryRepository categService;
    @Autowired
    InfoTypeRepository infoService;
    @Autowired
    FileTypeRepository filyTypeService;

    @Transactional(readOnly = true)
    @Secured({"ROLE_SUPERVIEW","ROLE_GROUP_VIEW", "ROLE_SUPER_VIEW","ROLE_MAIN_VIEW","ROLE_SUPER_EDIT","ROLE_GROUP_EDIT", "ROLE_SUPER_EDIT","ROLE_MAIN_EDIT"})
    @RequestMapping(value = "/listClientDossers", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JSTableExpenseListResp<Dosser> getClientsDossers(@RequestParam int jtStartIndex, @RequestParam int jtPageSize,@RequestParam(required = false) String jtSorting,@RequestParam long personId) {
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        PageRequest pager;
        Page<Dosser> page;
        if(sort!=null){
            pager = new PageRequest(jtStartIndex, jtPageSize, sort);

        } else {
            pager = new PageRequest(jtStartIndex, jtPageSize);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsAdapter user=(UserDetailsAdapter)auth.getPrincipal();
        if(user.hasRole("ROLE_CONFIDENTIAL")){
            if (user.hasRole("ROLE_SUPER_EDIT") || user.hasRole("ROLE_SUPER_VIEW")) {
                page = dosserService.findByPersonPersonIdAndPersonPersonType(personId, PersonType.CLIENT, pager);
            } else {
                page = dosserService.getAvaibleDossers(user.getTrustedSubvisionsId(), PersonType.CLIENT, personId, pager);
            }
        } else {
            if (user.hasRole("ROLE_SUPER_EDIT") || user.hasRole("ROLE_SUPER_VIEW")) {
                page = dosserService.findByPersonPersonIdAndPersonPersonTypeAndConfidential(personId, PersonType.CLIENT, false, pager);
            } else {
                page = dosserService.getAvaibleDossers(user.getTrustedSubvisionsId(), PersonType.CLIENT, personId,false, pager);
            }
        }
        return new JSTableExpenseListResp<>(page);
    }

    @Transactional(readOnly = true)
    @Secured({"ROLE_SUPERVIEW","ROLE_GROUP_VIEW", "ROLE_SUPER_VIEW","ROLE_MAIN_VIEW","ROLE_SUPER_EDIT","ROLE_GROUP_EDIT", "ROLE_SUPER_EDIT","ROLE_MAIN_EDIT"})
    @RequestMapping(value = "/listPersonalDossers", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JSTableExpenseListResp<Dosser> getPersonalDossers(@RequestParam int jtStartIndex, @RequestParam int jtPageSize,@RequestParam(required = false) String jtSorting, @RequestParam long personId) {

        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        PageRequest pager;
        Page<Dosser> page;
        if(sort!=null){
            pager = new PageRequest(jtStartIndex, jtPageSize, sort);

        } else {
            pager = new PageRequest(jtStartIndex, jtPageSize);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsAdapter user=(UserDetailsAdapter)auth.getPrincipal();
        if(user.hasRole("ROLE_CONFIDENTIAL")){
            if (user.hasRole("ROLE_SUPER_EDIT") || user.hasRole("ROLE_SUPER_VIEW")) {
                page = dosserService.findByPersonPersonIdAndPersonPersonType(personId, PersonType.WORKER, pager);
            } else {
                page = dosserService.getAvaibleDossers(user.getTrustedSubvisionsId(), PersonType.WORKER, personId, pager);
            }
        } else {
            if (user.hasRole("ROLE_SUPER_EDIT") || user.hasRole("ROLE_SUPER_VIEW")) {
                page = dosserService.findByPersonPersonIdAndPersonPersonTypeAndConfidential(personId, PersonType.WORKER, false,pager);
            } else {
                page = dosserService.getAvaibleDossers(user.getTrustedSubvisionsId(), PersonType.WORKER, personId,false, pager);
            }
        }
        return new JSTableExpenseListResp<>(page);
    }

    @Transactional(readOnly = false)
    @Secured({"ROLE_SUPER_EDIT","ROLE_GROUP_EDIT","ROLE_MAIN_EDIT"})
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ResponseBody
    public JSTableExpenseResp<Dosser> insert(@ModelAttribute Dosser dosser, BindingResult result,@RequestParam(value = "file",required = false) MultipartFile file, @RequestParam long personId,@RequestParam("subdivId") long subdivId,@RequestParam("categoryId") String categoryId,@RequestParam("infoTypeId") String infoTypeId,@RequestParam(value = "fileTypeId",required = false) Long fileTypeId) {
        JSTableExpenseResp jsonJtableResponse;
        if (result.hasErrors()) {
            return new JSTableExpenseResp<>("Form invalid");
        }
        if(categoryId==null || categoryId.isEmpty()||"----".equals(categoryId)){
            return new JSTableExpenseResp<>("No category select!");
        }
        if(infoTypeId==null || infoTypeId.isEmpty()||"----".equals(infoTypeId)){
            return new JSTableExpenseResp<>("No Info Type select!");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsAdapter user=(UserDetailsAdapter)auth.getPrincipal();
        try {
            Subdivision sub=subdivService.findOne(subdivId);
            Category cat=categService.findOne(Long.valueOf(categoryId));
            InfoType info=infoService.findOne(Long.valueOf(infoTypeId));
            Person person=personService.findOne(personId);
            FilesType ft=null;
            if(fileTypeId!=null){
                ft=filyTypeService.findOne(fileTypeId);
            }
            dosser.setCreationTime(System.currentTimeMillis());
            dosser.setPerson(person);
            dosser.setSubdivision(sub);
            dosser.setCategory(cat);
            dosser.setInfotype(info);
            dosser.setRecord_status(DosserType.ACTIVE);
            dosser.setCreateUser(user.getUserP());
            if(ft!=null && file!=null && !file.isEmpty()){
                Files fl=new Files();
                fl.setFileMimeType(file.getContentType());
                fl.setFilesType(ft);
                fl.setFileData(file.getBytes());
                dosser.setFileinfo(fl);
                fileService.saveAndFlush(fl);
            }
            dosserService.saveAndFlush(dosser);
            jsonJtableResponse = new JSTableExpenseResp<>(dosser);
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }

    @Transactional(readOnly = false)
    @Secured({"ROLE_SUPER_EDIT","ROLE_GROUP_EDIT","ROLE_MAIN_EDIT"})
    @RequestMapping(value = "/remove", method = {RequestMethod.POST})
    @ResponseBody
    public JSTableExpenseResp<Dosser>  remove(@ModelAttribute Dosser dosser, BindingResult result) {
        JSTableExpenseResp<Dosser>  jsonJtableResponse;
        try {
            dosserService.delete(dosser.getDossierId());
            jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.OK,"OK");
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }

    @Transactional(readOnly = false)
    @Secured({"ROLE_SUPER_EDIT","ROLE_GROUP_EDIT","ROLE_MAIN_EDIT"})
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    @ResponseBody
    public JSTableExpenseResp<Dosser>  delete(@ModelAttribute Dosser dosser, BindingResult result) {
        JSTableExpenseResp<Dosser>  jsonJtableResponse;
        try {
            Dosser old=dosserService.findOne(dosser.getDossierId());
            if(old!=null){
                old.setRecord_status(DosserType.DELETED);
                jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.OK,"OK");
            } else {
                jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.ERROR,"NOT FOUND!");
            }

        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }

    @Transactional(readOnly = false)
    @Secured({"ROLE_SUPER_EDIT","ROLE_GROUP_EDIT","ROLE_MAIN_EDIT"})
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ResponseBody
    public JSTableExpenseResp<Dosser> update(@RequestParam long dossierId,@RequestParam(value = "file",required = false) MultipartFile file, @RequestParam long personId,@RequestParam("subdivId") long subdivId,@RequestParam("categoryId") String categoryId,@RequestParam("infoTypeId") String infoTypeId,@RequestParam(value = "fileTypeId",required = false) Long fileTypeId) {
        JSTableExpenseResp jsonJtableResponse;
        Dosser old=null;
        try {
            old=dosserService.findOne(dossierId);
            if(old!=null){
                old.setRecord_status(DosserType.MODIFIED);
            }
        } catch (Exception e) {
            return new JSTableExpenseResp<>(e.getMessage());
        }
        if(categoryId==null || categoryId.isEmpty()||"----".equals(categoryId)){
            return new JSTableExpenseResp<>("No category select!");
        }
        if(infoTypeId==null || infoTypeId.isEmpty()||"----".equals(infoTypeId)){
            return new JSTableExpenseResp<>("No Info Type select!");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsAdapter user=(UserDetailsAdapter)auth.getPrincipal();
        try {
            Subdivision sub=subdivService.findOne(subdivId);
            Category cat=categService.findOne(Long.valueOf(categoryId));
            InfoType info=infoService.findOne(Long.valueOf(infoTypeId));
            Person person=personService.findOne(personId);
            FilesType ft=null;
            if(fileTypeId!=null){
                ft=filyTypeService.findOne(fileTypeId);
            }
            Dosser dosser=new Dosser();
            dosser.setParentDossierId(old);
            dosser.setCreationTime(System.currentTimeMillis());
            dosser.setPerson(person);
            dosser.setSubdivision(sub);
            dosser.setCategory(cat);
            dosser.setInfotype(info);
            dosser.setRecord_status(DosserType.ACTIVE);
            dosser.setCreateUser(user.getUserP());
            if(ft!=null && file!=null && !file.isEmpty()){
                Files fl=new Files();
                fl.setFileMimeType(file.getContentType());
                fl.setFilesType(ft);
                fl.setFileData(file.getBytes());
                dosser.setFileinfo(fl);
                fileService.saveAndFlush(fl);
            }
            dosserService.saveAndFlush(dosser);
            jsonJtableResponse = new JSTableExpenseResp<>(dosser);
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }


}
