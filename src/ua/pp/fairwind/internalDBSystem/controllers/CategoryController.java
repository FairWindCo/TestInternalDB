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
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Category;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.ProgramOperationJornal;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Subdivision;
import ua.pp.fairwind.internalDBSystem.dateTable.*;
import ua.pp.fairwind.internalDBSystem.security.UserDetailsAdapter;
import ua.pp.fairwind.internalDBSystem.services.JournalService;
import ua.pp.fairwind.internalDBSystem.services.repository.CategoryRepository;
import ua.pp.fairwind.internalDBSystem.services.repository.SubdivisionRepository;
import ua.pp.fairwind.internalDBSystem.services.repository.UserRepository;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ������ on 21.07.2015.
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private UserRepository userservice;
    @Autowired
    private CategoryRepository categoryservice;
    @Autowired
    private SubdivisionRepository subdivservice;
    @Autowired
    private JournalService journal;
    @Autowired
    private MessageSource messageSource;

    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model) {
        return "category";
    }

    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @RequestMapping(value = "/singe", method = RequestMethod.GET)
    public String showSingle(Model model) {
        return "single_page/user";
    }

    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @Transactional(readOnly = true)
    @RequestMapping(value = "/listedit", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<Category> getAllFileTypesSortSearch(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting,@RequestParam(required = false) String searchname) {
        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        PageRequest pager;
        Page<Category> page;
        if(sort!=null){
            pager=new PageRequest(jtStartIndex,jtPageSize,sort);
        } else {
            pager=new PageRequest(jtStartIndex, jtPageSize);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsAdapter user=(UserDetailsAdapter)auth.getPrincipal();
        if(searchname!=null && !searchname.isEmpty()){
            if (user.hasRole("ROLE_SUPER_INF_EDIT")) {
                page =  categoryservice.findByNameLike(searchname, pager);
            } else {
                page = categoryservice.findByNameLikeAndSubdivisionSubdivisionIdIn(searchname, user.getTrustedSubvisionsId(), pager);
                //page =  categoryservice.findByNameLikeSec(searchname,user.getTrustedSubvisionsId(), pager);
            }
        } else {
            //page = categoryservice.getAllSec(user.getTrustedSubvisionsId(),pager);
            if (user.hasRole("ROLE_SUPER_INF_EDIT")) {
                page = categoryservice.findAll(pager);
            } else {
                page = categoryservice.findBySubdivisionSubdivisionIdIn(user.getTrustedSubvisionsId(), pager);
                //return new JSTableExpenseListResp<Category>(categoryservice.getAllSec(user.getTrustedSubvisionsId()));
            }
        }
        return new JSTableExpenseListResp<Category>(page);
    }
    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @Transactional(readOnly = false)
    /*CRUD operation - Add*/
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<Category> insert(@ModelAttribute Category category, BindingResult result,@RequestParam(value = "subdivsId2[]",required = false) long[] subdivsid,Locale currentLocale) {
        JSTableExpenseResp jsonJtableResponse;
        if (result.hasErrors()) {
            return new JSTableExpenseResp<>(messageSource.getMessage("label.forminvalid",null,"INVALIDE DATA FORM!", currentLocale));
        }
        try {
            if(subdivsid!=null && subdivsid.length>0){
                for(long subdivid:subdivsid) {
                    Subdivision subdivision =subdivservice.findOne(subdivid);
                    if(subdivision!=null){
                        category.getSubdivision().add(subdivision);
                    }
                }
            }
            categoryservice.save(category);
            journal.log(ProgramOperationJornal.Operation.CREATE, "Category", category.getName());
            jsonJtableResponse = new JSTableExpenseResp<>(category);
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }
    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @Transactional(readOnly = false)
    /*CRUD operation - Update */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<Category>  update(@ModelAttribute Category category, BindingResult result,Locale currentLocale) {
        JSTableExpenseResp<Category>  jsonJtableResponse;
        if (result.hasErrors()) {
            jsonJtableResponse = new JSTableExpenseResp<>("Form invalid:"+result.toString());
            return jsonJtableResponse;
        }
        try {
            Long id=category.getCategoryId();
            if(id!=null) {
                Category categoryinDB = categoryservice.getOne(id);
                if (categoryinDB == null /**/|| categoryinDB.getVersionid()!=category.getVersionid()/**/) {
                    jsonJtableResponse = new JSTableExpenseResp<>("Category VAS MODIFIED OR DELETE IN ANOTHER TRANSACTION!");
                } else {
                    categoryinDB.setName(category.getName());
                    categoryinDB.setKey1c(category.getKey1c());
                    categoryinDB.setVersionid(category.getVersionid());
                    categoryservice.save(categoryinDB);
                    journal.log(ProgramOperationJornal.Operation.UPDATE, "Category","OLD:"+categoryinDB.getName()+" NEW:"+category.getName()+" 1CKEY_NEW:"+category.getKey1c()+" OLD:"+categoryinDB.getKey1c());
                    jsonJtableResponse = new JSTableExpenseResp<>(categoryinDB);
                }
            }else {
                jsonJtableResponse = new JSTableExpenseResp<>("USER NOT FOUND!");
            }
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }
    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @Transactional(readOnly = false)
    /*CRUD operation - Delete */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<Category>  delete(@ModelAttribute Category category, BindingResult result,Locale currentLocale) {
        JSTableExpenseResp<Category>  jsonJtableResponse;
        try {
            if(category.getCategoryId()==1){
                return new JSTableExpenseResp<>("DELETE FORBIDDEN!");
            }
            if(categoryservice.getChildRecordCount(category.getCategoryId())==0) {
                categoryservice.delete(category.getCategoryId());
                journal.log(ProgramOperationJornal.Operation.DELETE, "Category", "ID:"+category.getCategoryId()+"Name:"+category.getName());
                jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.OK, "OK");
            } else {
                jsonJtableResponse = new JSTableExpenseResp<>(messageSource.getMessage("label.forbidden",null,"DELETE FORBIDDEN!", currentLocale));
            }
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }


    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/avaibleSubdiv", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<Subdivision> getAvaibleGruntedSubdivisions(@RequestParam(required = true) long categoryId) {
        Category category=categoryservice.findOne(categoryId);
        if(category==null){
            return new JSTableExpenseListResp<>(JSTableExpenseResult.ERROR,"NO USER WITH  ID "+categoryId);
        } else {
            Set<Subdivision> roles=category.getSubdivision();
            if(roles==null){
                return new JSTableExpenseListResp<>(JSTableExpenseResult.ERROR,"NO SUBDIVISIONS");
            } else {
                return new JSTableExpenseListResp<>(roles,roles.size());
            }
        }
    }

    @Secured({"ROLE_GROUP_EDIT", "ROLE_SUPER_EDIT","ROLE_MAIN_EDIT"})
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/subdivisionsCategories", method = RequestMethod.POST)
    @ResponseBody
    public JSTableOptionsResponse<JSTableExpenseOptionsBean> getCategoryForSubdivision(@RequestParam(required = true) long subdivisionId) {
        List<JSTableExpenseOptionsBean> categoryes=subdivservice.getAllCategoryForSubdivisionOptions(subdivisionId);
        if(categoryes==null){
            return new JSTableOptionsResponse<>("NO SUBDIVISION WITH  ID "+subdivisionId);
        } else {
            return new JSTableOptionsResponse<>(categoryes);
        }
    }

    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/avaibleSubdivOpt", method = RequestMethod.POST)
    @ResponseBody
    public JSTableOptionsResponse<JSTableExpenseOptionsBean> getAvaibleSubdivisionsOpt(@RequestParam(required = true) long categoryId) {
        JSTableOptionsResponse<JSTableExpenseOptionsBean>  jsonJtableResponse;
        try {

            Set<Long> assignedSubdivID=categoryservice.getSubdivisionsIDForCategoryId(categoryId);
            return new JSTableOptionsResponse<>(getCategorySubdivisions(assignedSubdivID));
        } catch (Exception e) {
            jsonJtableResponse = new JSTableOptionsResponse<>(e.getMessage());
        }
        return jsonJtableResponse;
    }

    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/avaibleSubdivListOpt", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JSSelectExpenseResp<JSTableExpenseOptionsBean> getAvaibleSubdivisionsSelOpt(@RequestParam(required = true) long categoryId) {
        JSSelectExpenseResp<JSTableExpenseOptionsBean>  jsonJtableResponse;
        try {

            Set<Long> assignedSubdivID=categoryservice.getSubdivisionsIDForCategoryId(categoryId);
            return new JSSelectExpenseResp<>(getCategorySubdivisions(assignedSubdivID));
        } catch (Exception e) {
            jsonJtableResponse = new JSSelectExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }

    private List<JSTableExpenseOptionsBean> getCategorySubdivisions(Set<Long> assignedSubdivID){
        JSTableOptionsResponse<JSTableExpenseOptionsBean>  jsonJtableResponse;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsAdapter user=(UserDetailsAdapter)auth.getPrincipal();
        if(assignedSubdivID==null || assignedSubdivID.isEmpty()) {
            if (user.hasRole("ROLE_SUPER_INF_EDIT")) {
                List<JSTableExpenseOptionsBean> avaibleRoles = categoryservice.getAllSubdivisionsOptions();
                return avaibleRoles;
            } else {
                List<JSTableExpenseOptionsBean> avaibleRoles = categoryservice.getAllSubdivisionsOptionsSecurity(user.getTrustedSubvisions());
                return avaibleRoles;
            }
        } else {
            if (user.hasRole("ROLE_SUPER_INF_EDIT")) {
                List<JSTableExpenseOptionsBean> avaibleRoles = categoryservice.getAvaibleSubdivisionsOptions(assignedSubdivID);
                return avaibleRoles;
            } else {
                List<JSTableExpenseOptionsBean> avaibleRoles = categoryservice.getAvaibleSubdivisionsOptionsSecurety(assignedSubdivID,user.getTrustedSubvisions());
                return avaibleRoles;
            }
        }

    }

    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @Transactional(readOnly = true)
    @RequestMapping(value = "/subdivOpt", method = {RequestMethod.POST})
    @ResponseBody
    public JSTableOptionsResponse<JSTableExpenseOptionsBean> getSubdivisionsOpt() {
        JSTableOptionsResponse<JSTableExpenseOptionsBean>  jsonJtableResponse;
        try {
            return new JSTableOptionsResponse<>(getCategorySubdivisions(null));
        } catch (Exception e) {
            jsonJtableResponse = new JSTableOptionsResponse<>(e.getMessage());
        }
        return jsonJtableResponse;
    }

    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @Transactional(readOnly = true)
    @RequestMapping(value = "/subdivListOpt", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JSSelectExpenseResp<JSTableExpenseOptionsBean> getSubdivisionsListOpt() {
        JSSelectExpenseResp<JSTableExpenseOptionsBean>  jsonJtableResponse;
        try {
            return new JSSelectExpenseResp<>(getCategorySubdivisions(null));
        } catch (Exception e) {
            jsonJtableResponse = new JSSelectExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }

    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @Transactional(readOnly = true)
    @RequestMapping(value = "/subdivs", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public List<JSTableExpenseOptionsBean> getSubdivisionsList() {
        try {
            return getCategorySubdivisions(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @Transactional(readOnly = false)
    @RequestMapping(value = "/addsubdivision", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<Subdivision> setupNewSubdivision(@RequestParam(required = true) long subdivId,@RequestParam(required = true) long categoryId) {
        JSTableExpenseResp<Subdivision>  jsonJtableResponse;
        try {
            Subdivision role=subdivservice.findOne(subdivId);
            Category category=categoryservice.findOne(categoryId);
            if(category!=null && role!=null) {
                category.addSubdivision(role);
                jsonJtableResponse = new JSTableExpenseResp<>(role);
            } else {
                jsonJtableResponse = new JSTableExpenseResp<>("NO RECORD FOUND!");
            }
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }

    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @Transactional(readOnly = false)
    @RequestMapping(value = "/removesubdivision", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<Subdivision> removeSubdivision(@RequestParam(required = true) long subdivisionId,@RequestParam(required = true) long categoryId) {
        JSTableExpenseResp<Subdivision>  jsonJtableResponse;
        try {
            Subdivision role=subdivservice.findOne(subdivisionId);
            Category category=categoryservice.findOne(categoryId);
            if(category!=null && role!=null) {
                category.removeSubdivision(role);
                jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.OK,"OK");
            } else {
                jsonJtableResponse = new JSTableExpenseResp<>("NO RECORD FOUND!");
            }
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }

    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT","ROLE_GROUP_INF_VIEW", "ROLE_SUPER_INF_VIEW","ROLE_MAIN_INF_VIEW"})
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/subdivisionsCategory", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JSSelectExpenseResp<JSTableExpenseOptionsBean> getAvaibleSubdivisionsSelOpt(@RequestParam(required = false) String subdivisions) {
        JSSelectExpenseResp<JSTableExpenseOptionsBean>  jsonJtableResponse;
        try {
            Set<Long> assignedSubdivID;
            if(subdivisions==null || subdivisions.isEmpty()){
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                UserDetailsAdapter user=(UserDetailsAdapter)auth.getPrincipal();
                assignedSubdivID=user.getTrustedSubvisionsId();
            } else {
                assignedSubdivID = FormSort.getIdFromString(subdivisions);
            }
            return new JSSelectExpenseResp<>(categoryservice.getCategoryForSubdivisions(assignedSubdivID));
        } catch (Exception e) {
            jsonJtableResponse = new JSSelectExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }

}
