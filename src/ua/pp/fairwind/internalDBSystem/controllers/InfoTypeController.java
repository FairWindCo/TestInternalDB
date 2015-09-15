package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.InfoType;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Subdivision;
import ua.pp.fairwind.internalDBSystem.dateTable.*;
import ua.pp.fairwind.internalDBSystem.security.UserDetailsAdapter;
import ua.pp.fairwind.internalDBSystem.services.repository.CategoryRepository;
import ua.pp.fairwind.internalDBSystem.services.repository.InfoTypeRepository;
import ua.pp.fairwind.internalDBSystem.services.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ������ on 21.07.2015.
 */
@Controller
@RequestMapping("/info")
public class InfoTypeController {
    protected static Logger logger = Logger.getLogger("USER controller");

    @Autowired
    private UserRepository userservice;
    @Autowired
    private CategoryRepository categoryservice;
    @Autowired
    private InfoTypeRepository infoservice;

    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model) {
        return "info";
    }

    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @RequestMapping(value = "/singe", method = RequestMethod.GET)
    public String showSingle(Model model) {
        return "single_page/info";
    }

    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @Transactional(readOnly = true)
    @RequestMapping(value = "/listedit", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<InfoType> getAllFileTypesSortSearch(Model model,@RequestParam int jtStartIndex, @RequestParam int jtPageSize, @RequestParam(required = false) String jtSorting,@RequestParam(required = false) String searchname) {

        logger.log(Level.INFO,"Received request to show "+jtPageSize+" users from"+jtStartIndex);

        // Retrieve all persons by delegating the call to PersonService
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        PageRequest pager;
        Page<InfoType> page;

        if(sort!=null){
            pager = new PageRequest(jtStartIndex, jtPageSize, sort);

        } else {
            pager = new PageRequest(jtStartIndex, jtPageSize);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsAdapter user=(UserDetailsAdapter)auth.getPrincipal();
        if(searchname!=null && !searchname.isEmpty()){
            if (user.hasRole("ROLE_SUPER_INF_EDIT")) {
                page = infoservice.findByTypeNameLike(searchname, pager);
            } else {
                page = infoservice.findByTypeNameLikeAndCategorySubdivisionSubdivisionIdIn(searchname, user.getTrustedSubvisionsId(), pager);
            }
        } else {
            if (user.hasRole("ROLE_SUPER_INF_EDIT")) {
                page = infoservice.findAll(pager);
            } else {
                page = infoservice.findByCategorySubdivisionSubdivisionIdIn(user.getTrustedSubvisionsId(),pager);
            }
        }
        return new JSTableExpenseListResp<InfoType>(page);
    }
    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @Transactional(readOnly = false)
    /*CRUD operation - Add*/
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseResp<InfoType> insert(@ModelAttribute InfoType infotype, BindingResult result,@RequestParam(value = "categoryId",required = false) Long categoryId) {
        JSTableExpenseResp jsonJtableResponse;
        if (result.hasErrors()) {
            return new JSTableExpenseResp<InfoType>("Form invalid");
        }
        try {
            if(categoryId!=null){
                Category category = categoryservice.findOne(categoryId);
                if(category!=null){
                    infotype.setCategory(category);
                }
            }
            infoservice.save(infotype);
            jsonJtableResponse = new JSTableExpenseResp<>(infotype);
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
    public JSTableExpenseResp<InfoType>  update(@ModelAttribute InfoType infotype, BindingResult result,@RequestParam(value = "categoryId",required = false) Long categoryId) {
        JSTableExpenseResp<InfoType>  jsonJtableResponse;
        if (result.hasErrors()) {
            jsonJtableResponse = new JSTableExpenseResp<>("Form invalid:"+result.toString());
            return jsonJtableResponse;
        }
        try {
            Long id= infotype.getTypeId();
            if(id!=null) {
                InfoType infoinDB = infoservice.getOne(id);
                if (infoinDB == null /**/|| infoinDB.getVersionid()!= infotype.getVersionid()/**/) {
                    jsonJtableResponse = new JSTableExpenseResp<>("Category VAS MODIFIED OR DELETE IN ANOTHER TRANSACTION!");
                } else {
                    infoinDB.setTypeName(infotype.getTypeName());
                    infoinDB.setVersionid(infotype.getVersionid());
                    if(categoryId!=null){
                        Category category = categoryservice.findOne(categoryId);
                        if(category!=null){
                            infoinDB.setCategory(category);
                        }
                    }
                    infoservice.save(infoinDB);
                    jsonJtableResponse = new JSTableExpenseResp<>(infoinDB);
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
    public JSTableExpenseResp<InfoType>  delete(@ModelAttribute InfoType infoType, BindingResult result) {
        JSTableExpenseResp<InfoType>  jsonJtableResponse;
        try {
            infoservice.delete(infoType.getTypeId());
            jsonJtableResponse = new JSTableExpenseResp<>(JSTableExpenseResult.OK,"OK");
        } catch (Exception e) {
            jsonJtableResponse = new JSTableExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }


    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/avaibleCategoryes", method = RequestMethod.POST)
    @ResponseBody
    public JSTableOptionsResponse<JSTableExpenseOptionsBean> getAvaibleCategorues() {
            return new JSTableOptionsResponse<>(getCategorys());
    }

    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT"})
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/avaibleInfoTypes", method = RequestMethod.POST)
    @ResponseBody
    public JSTableOptionsResponse<JSTableExpenseOptionsBean> getAvaibleSubdivisionsOpt(@RequestParam(required = true) long categoryId) {
        JSTableOptionsResponse<JSTableExpenseOptionsBean>  jsonJtableResponse;
        try {
            return new JSTableOptionsResponse<>(infoservice.getAllInfoTypesForCategory(categoryId));
        } catch (Exception e) {
            jsonJtableResponse = new JSTableOptionsResponse<>(e.getMessage());
        }
        return jsonJtableResponse;
    }


    private List<JSTableExpenseOptionsBean> getCategorys(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsAdapter user=(UserDetailsAdapter)auth.getPrincipal();
        if (user.hasRole("ROLE_SUPER_INF_EDIT")) {
                List<JSTableExpenseOptionsBean> avaibleRoles = infoservice.getAllCategoryes();
                return avaibleRoles;
        } else {
                List<JSTableExpenseOptionsBean> avaibleRoles = infoservice.getAvaibleCategoryOpt(user.getTrustedSubvisionsId());
            return avaibleRoles;
        }
    }

    @Secured({"ROLE_GROUP_INF_EDIT", "ROLE_SUPER_INF_EDIT","ROLE_MAIN_INF_EDIT","ROLE_GROUP_INF_VIEW", "ROLE_SUPER_INF_VIEW","ROLE_MAIN_INF_VIEW"})
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/subdivisionsCategory", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JSSelectExpenseResp<JSTableExpenseOptionsBean> getAvaibleSubdivisionsSelOpt(@RequestParam(required = false) String categoryes) {
        JSSelectExpenseResp<JSTableExpenseOptionsBean>  jsonJtableResponse;
        try {
            Set<Long> assignedSubdivID;
            if(categoryes==null || categoryes.isEmpty()){
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                UserDetailsAdapter user=(UserDetailsAdapter)auth.getPrincipal();
                assignedSubdivID=user.getTrustedSubvisionsId();
                return new JSSelectExpenseResp<>(infoservice.getInfoForCategorySubdiv(assignedSubdivID));
            } else {
                assignedSubdivID = FormSort.getIdFromString(categoryes);
                return new JSSelectExpenseResp<>(infoservice.getInfoForCategory(assignedSubdivID));
            }

        } catch (Exception e) {
            jsonJtableResponse = new JSSelectExpenseResp<>(e.getMessage());
        }
        return jsonJtableResponse;
    }


}
