package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ua.pp.fairwind.internalDBSystem.datamodel.*;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.*;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseListResp;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseResp;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseResult;
import ua.pp.fairwind.internalDBSystem.services.repository.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 02.09.2015.
 */
@Controller
@RequestMapping("/additional")
public class AdditionalInfoController {

    @Autowired
    PersonRepository personService;
    @Autowired
    ContactRepository contactService;
    @Autowired
    ContactTypeRepository contactTypeService;
    @Autowired
    RelationsRepository relationsService;
    @Autowired
    AdditionalInfoRepository additionalService;
    @Autowired
    SegmentsRepository segmentService;
    @Autowired
    ActivitiesRepository activityService;
    @Autowired
    HobbiesRepository hobbyService;
    @Autowired
    FileRepository fileService;
    @Autowired
    FileTypeRepository filyTypeService;


    @RequestMapping(value = "/view", method = {RequestMethod.POST,RequestMethod.GET})
    public String doView(@RequestParam Long personID,ModelMap model){
        if(personID!=null) {
            Person person=personService.findOne(personID);
            if(person!=null){
                model.addAttribute("person",person);
                return "addtitional_view";
            }
        }
        return "no_person";
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.POST,RequestMethod.GET})
    public String doEdit(@RequestParam Long personID,ModelMap model){
        if(personID!=null) {
            Person person=personService.findOne(personID);
            if(person!=null){
                model.addAttribute("person",person);
                return "addtitional_edit";
            }
        }
        return "no_person";
    }

    @RequestMapping(value = "/contactsList", method = {RequestMethod.POST,RequestMethod.GET})
    @Transactional(readOnly = true)
    @ResponseBody
    public JSTableExpenseListResp<Contact> contacts(@RequestParam Long personId){
        JSTableExpenseListResp<Contact>  jsonJtableResponse;
        if(personId!=null) {
            Person person=personService.findOne(personId);
            if(person!=null){
                if(person.getContacts()!=null) {
                    jsonJtableResponse = new JSTableExpenseListResp<>(person.getContacts());
                } else {
                    List<Contact> contactList=new ArrayList<>();
                    jsonJtableResponse = new JSTableExpenseListResp<>(contactList);
                }
            } else {
                jsonJtableResponse = new JSTableExpenseListResp<>("NO PERSON FOUND ID="+personId);
            }
        } else {
            jsonJtableResponse = new JSTableExpenseListResp<>("NO PERSON FOUND ID="+personId);
        }
        return jsonJtableResponse;
    }
    @Transactional(readOnly = true)
    @ResponseBody
    @RequestMapping(value = "/fileList", method = {RequestMethod.POST,RequestMethod.GET})
    public JSTableExpenseListResp<Files> files(@RequestParam Long personId,ModelMap model){
        JSTableExpenseListResp<Files>  jsonJtableResponse;
        if(personId!=null) {
            Person person=personService.findOne(personId);
            if(person!=null){
                if(person.getFiles()!=null) {
                    jsonJtableResponse = new JSTableExpenseListResp<>(person.getFiles());
                } else {
                    List<Files> contactList=new ArrayList<>();
                    jsonJtableResponse = new JSTableExpenseListResp<>(contactList);
                }
            } else {
                jsonJtableResponse = new JSTableExpenseListResp<>("NO PERSON FOUND ID="+personId);
            }
        } else {
            jsonJtableResponse = new JSTableExpenseListResp<>("NO PERSON FOUND ID="+personId);
        }
        return jsonJtableResponse;
    }
    @Transactional(readOnly = true)
    @ResponseBody
    @RequestMapping(value = "/relationList", method = {RequestMethod.POST,RequestMethod.GET})
    public JSTableExpenseListResp<RelationDegrees> relations(@RequestParam Long personId){
        JSTableExpenseListResp<RelationDegrees>  jsonJtableResponse;
        if(personId!=null) {
            Person person=personService.findOne(personId);
            if(person!=null){
                if(person.getAdditionalInfo()!=null && person.getAdditionalInfo().getRelationDegrees()!=null) {
                    jsonJtableResponse = new JSTableExpenseListResp<>(person.getAdditionalInfo().getRelationDegrees());
                } else {
                    List<RelationDegrees> contactList=new ArrayList<>();
                    jsonJtableResponse = new JSTableExpenseListResp<>(contactList);
                }
            } else {
                jsonJtableResponse = new JSTableExpenseListResp<>("NO PERSON FOUND ID="+personId);
            }
        } else {
            jsonJtableResponse = new JSTableExpenseListResp<>("NO PERSON FOUND ID="+personId);
        }
        return jsonJtableResponse;
    }
    @Transactional(readOnly = false)
    @ResponseBody
    @RequestMapping(value = "/addContact", method = {RequestMethod.POST,RequestMethod.GET})
    public JSTableExpenseResp<Contact> addContact(@RequestParam Long personId,@RequestParam String contactinfo,@RequestParam Long contactTypeId){
        JSTableExpenseResp<Contact>  jsonJtableResponse;
        if(personId!=null) {
            Person person=personService.findOne(personId);
            if(person!=null){
                Contact news=new Contact();
                news.setContactinfo(contactinfo);
                ContactType cType=contactTypeService.findOne(contactTypeId);
                news.setContactType(cType);
                contactService.save(news);
                try {
                    person.getContacts().add(news);
                    personService.save(person);
                    jsonJtableResponse = new JSTableExpenseResp<>(news);
                }catch (Exception e){
                    jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
                }
            } else {
                jsonJtableResponse = new JSTableExpenseResp<>("NO PERSON FOUND ID="+personId);
            }
        } else {
            jsonJtableResponse = new JSTableExpenseResp<>("NO PERSON FOUND ID="+personId);
        }
        return jsonJtableResponse;
    }
    @Transactional(readOnly = false)
    @ResponseBody
    @RequestMapping(value = "/removeContact", method = {RequestMethod.POST,RequestMethod.GET})
    public JSTableExpenseResp<Contact> removeContact(@RequestParam Long personId,@RequestParam Long contactId){
        JSTableExpenseResp<Contact>  jsonJtableResponse;
        if(personId!=null) {
            try {
                Person person = personService.findOne(personId);
                if (person != null) {
                        Contact news = contactService.findOne(contactId);
                        if(news!=null) {

                            if(person.getContacts().contains(news)) {
                                person.getContacts().remove(news);
                                contactService.delete(news);
                                personService.save(person);
                                jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.OK,"OK");
                            } else {
                                jsonJtableResponse = new JSTableExpenseResp<>("NO CONTACT  ID=" + contactId+" IN PERSOND ID="+personId+" FOUND!");
                            }
                        } else {
                            jsonJtableResponse = new JSTableExpenseResp<>("NO CONTACT FOUND ID=" + contactId);
                        }
                } else {
                    jsonJtableResponse = new JSTableExpenseResp<>("NO PERSON FOUND ID=" + personId);
                }
            } catch (Exception e) {
                jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
            }
        } else {
            jsonJtableResponse = new JSTableExpenseResp<>("NO PERSON FOUND ID="+personId);
        }
        return jsonJtableResponse;
    }
    @Transactional(readOnly = false)
    @ResponseBody
    @RequestMapping(value = "/updateContact", method = {RequestMethod.POST,RequestMethod.GET})
    public JSTableExpenseResp<Contact> updateContact(@RequestParam Long personId,@RequestParam Long contactId,@RequestParam String contactinfo,@RequestParam Long contactTypeId){
        JSTableExpenseResp<Contact>  jsonJtableResponse;
        if(personId!=null) {
            try {
                Person person = personService.findOne(personId);
                if (person != null) {
                    Contact news = contactService.findOne(contactId);
                    if(news!=null) {
                        if(person.getContacts().contains(news)) {
                            news.setContactinfo(contactinfo);
                            ContactType cType=contactTypeService.findOne(contactTypeId);
                            news.setContactType(cType);
                            contactService.save(news);
                            jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.OK,"OK");
                        } else {
                            jsonJtableResponse = new JSTableExpenseResp<>("NO CONTACT  ID=" + contactId+" IN PERSOND ID="+personId+" FOUND!");
                        }
                    } else {
                        jsonJtableResponse = new JSTableExpenseResp<>("NO CONTACT FOUND ID=" + contactId);
                    }
                } else {
                    jsonJtableResponse = new JSTableExpenseResp<>("NO PERSON FOUND ID=" + personId);
                }
            } catch (Exception e) {
                jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
            }
        } else {
            jsonJtableResponse = new JSTableExpenseResp<>("NO PERSON FOUND ID="+personId);
        }
        return jsonJtableResponse;
    }


    @Transactional(readOnly = false)
    @ResponseBody
    @RequestMapping(value = "/addFile", method = {RequestMethod.POST,RequestMethod.GET})
    public JSTableExpenseResp<Files> addFile(@RequestParam Long personId,@RequestParam(required = false) String fileNameComments,@RequestParam(required = false) Long filesTypeId,@RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        JSTableExpenseResp<Files>  jsonJtableResponse;
        if(personId!=null) {
            Person person=personService.findOne(personId);
            if(person!=null){
                try {
                    if(file!=null && !file.isEmpty()){
                        Files newfile=new Files();
                        newfile.setFileMimeType(file.getContentType());
                        if(fileNameComments==null||fileNameComments.isEmpty()){
                            newfile.setFileNameComments(file.getOriginalFilename());
                        }
                        newfile.setFileMimeType(file.getContentType());
                        FilesType ft=null;
                        if(filesTypeId!=null){
                            ft=filyTypeService.findOne(filesTypeId);
                        }
                        newfile.setFilesType(ft);
                        newfile.setFileData(file.getBytes());
                        fileService.saveAndFlush(newfile);
                        person.getFiles().add(newfile);
                        personService.save(person);
                        jsonJtableResponse = new JSTableExpenseResp<>(newfile);
                    } else {
                        jsonJtableResponse = new JSTableExpenseResp<>("EMPTY FILE!!!");
                    }
                }catch (Exception e){
                    jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
                }
            } else {
                jsonJtableResponse = new JSTableExpenseResp<>("NO PERSON FOUND ID="+personId);
            }
        } else {
            jsonJtableResponse = new JSTableExpenseResp<>("NO PERSON FOUND ID="+personId);
        }
        return jsonJtableResponse;
    }
    @Transactional(readOnly = false)
    @ResponseBody
    @RequestMapping(value = "/removeFile", method = {RequestMethod.POST,RequestMethod.GET})
    public JSTableExpenseResp<Files> removeFile(@RequestParam Long personId,@RequestParam Long fileId){
        JSTableExpenseResp<Files>  jsonJtableResponse;
        if(personId!=null) {
            try {
                Person person = personService.findOne(personId);
                if (person != null) {
                    Files file = fileService.findOne(fileId);
                    if(file!=null) {
                        if(person.getFiles().contains(file)) {
                            person.getContacts().remove(file);
                            fileService.delete(file);
                            personService.save(person);
                            jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.OK,"OK");
                        } else {
                            jsonJtableResponse = new JSTableExpenseResp<>("NO FILE  ID=" + fileId+" IN PERSOND ID="+personId+" FOUND!");
                        }
                    } else {
                        jsonJtableResponse = new JSTableExpenseResp<>("NO FILE FOUND ID=" + fileId);
                    }
                } else {
                    jsonJtableResponse = new JSTableExpenseResp<>("NO PERSON FOUND ID=" + personId);
                }
            } catch (Exception e) {
                jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
            }
        } else {
            jsonJtableResponse = new JSTableExpenseResp<>("NO PERSON FOUND ID="+personId);
        }
        return jsonJtableResponse;
    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public String doSave(HttpServletRequest request,ModelMap model,@RequestParam Long personId,@RequestParam(required = false) String fileNameComments,@RequestParam(required = false) Long filesTypeId,@RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        if(personId!=null) {
            Person person = personService.findOne(personId);
            if (person != null) {
                ClientAdditionalInfo additional=person.getAdditionalInfo();
                if(additional==null)additional=new ClientAdditionalInfo();
                Long sergments_id = Long.valueOf(request.getParameter("sergments_id"));
                Long activities_id = Long.valueOf(request.getParameter("activities_id"));
                Long hobbie_id = Long.valueOf(request.getParameter("hobbie_id"));
                Long filetype_id = Long.valueOf(request.getParameter("filetype_id"));
                Segments seg=null;
                Activities act=null;
                Hobbies hob=null;
                FilesType ft=null;
                if(sergments_id!=null){
                    seg=segmentService.findOne(sergments_id);
                }
                if(activities_id!=null){
                    act=activityService.findOne(hobbie_id);
                }
                if(hobbie_id!=null){
                    hob=hobbyService.findOne(hobbie_id);
                }
                if(filetype_id!=null){
                    ft=filyTypeService.findOne(filesTypeId);
                }

                person.setPassportInfo(request.getParameter("passportInfo"));
                person.setHobbie(hob);
                person.setHobbiesComments(request.getParameter("hobbiesComments"));
                person.setActivities(act);
                person.setActivitiesComments(request.getParameter("activitiesComments"));
                additional.setClientColorCODE(request.getParameter("clientColorCode"));
                additional.setClientColorComments(request.getParameter("clientColorComments"));
                additional.setClientSegment(seg);
                Files old=null;
                if(file!=null && !file.isEmpty()){
                    Files newFile=new Files();
                    newFile.setFilesType(ft);
                    newFile.setFileMimeType(file.getContentType());
                    if(fileNameComments==null||fileNameComments.isEmpty()){
                        newFile.setFileNameComments(file.getOriginalFilename());
                    }
                    newFile.setFileMimeType(file.getContentType());
                    newFile.setFileData(file.getBytes());
                    fileService.save(newFile);
                    old=person.getPhoto();
                    person.setPhoto(newFile);
                }
                if(old!=null){
                    fileService.delete(old);
                }
                additionalService.save(additional);
                person.setAdditionalInfo(additional);
                personService.save(person);
                model.addAttribute("person",person);
                return "addtitional_view";
            }
        }
        return "no_person";
    }


}
