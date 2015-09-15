package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.pp.fairwind.internalDBSystem.datamodel.Dosser;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.DosserType;
import ua.pp.fairwind.internalDBSystem.datamodel.proxy.DosserProxy;
import ua.pp.fairwind.internalDBSystem.dateTable.FormSort;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseListResp;
import ua.pp.fairwind.internalDBSystem.security.UserDetailsAdapter;
import ua.pp.fairwind.internalDBSystem.services.repository.DossersRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * Created by Сергей on 15.09.2015.
 */
@Controller
@RequestMapping("/search")
public class SerachDossersController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model) {
        return "search";
    }

    @Autowired
    DossersRepository dosserService;
    @Autowired
    EntityManager em;

    @RequestMapping(value = "/search", method = {RequestMethod.GET,RequestMethod.POST})
    @Transactional(readOnly = true)
    //@Secured({"ROLE_SUPERVIEW","ROLE_GROUP_VIEW", "ROLE_SUPER_VIEW","ROLE_MAIN_VIEW","ROLE_SUPER_EDIT","ROLE_GROUP_EDIT", "ROLE_SUPER_EDIT","ROLE_MAIN_EDIT"})
    @ResponseBody
    public JSTableExpenseListResp<DosserProxy> search(HttpServletRequest request,@RequestParam int jtStartIndex, @RequestParam int jtPageSize,@RequestParam(required = false) String jtSorting){
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        PageRequest pager;
        Page<DosserProxy> page;
        if(sort!=null){
            pager = new PageRequest(jtStartIndex, jtPageSize, sort);

        } else {
            pager = new PageRequest(jtStartIndex, jtPageSize);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsAdapter user=(UserDetailsAdapter)auth.getPrincipal();

        /*if(user.hasRole("ROLE_CONFIDENTIAL")){
            if (user.hasRole("ROLE_SUPER_EDIT") || user.hasRole("ROLE_SUPER_VIEW")) {
                page = dosserService.findByRecordStatus(DosserType.ACTIVE, pager);
            } else {
                page = dosserService.getAvaibleDossers(user.getTrustedSubvisionsId(), DosserType.ACTIVE, pager);
            }
        } else {
            if (user.hasRole("ROLE_SUPER_EDIT") || user.hasRole("ROLE_SUPER_VIEW")) {
                page = dosserService.findByConfidentialAndRecordStatus(false, DosserType.ACTIVE, pager);
            } else {
                page = dosserService.getAvaibleDossers(user.getTrustedSubvisionsId(), false,DosserType.ACTIVE, pager);
            }
        }*/
        //page=dosserService.findDossersProxy(createSimpleFind(DosserType.ACTIVE,false),pager);
        boolean conf=user.hasRole("ROLE_CONFIDENTIAL");
        String fio=request.getParameter("searchname");
        String code=request.getParameter("searchcode");
        String subdiv=request.getParameter("subdivisions")==null?request.getParameter("subdivisions[]"):request.getParameter("subdivisions");
        Set<Long> subdivisionsIds=FormSort.getIdFromString(subdiv);
        if(subdivisionsIds==null && (user.hasRole("ROLE_SUPER_EDIT") || user.hasRole("ROLE_SUPER_VIEW"))) subdivisionsIds=user.getTrustedSubvisionsId();
        String cat=request.getParameter("categoryes")==null?request.getParameter("categoryes[]"):request.getParameter("categoryes");
        Set<Long> categoryIds=FormSort.getIdFromString(cat);
        String in=request.getParameter("infotypes")==null?request.getParameter("infotypes[]"):request.getParameter("infotypes");
        Set<Long> infoTypeIds=FormSort.getIdFromString(in);
        Long count=em.createQuery(createCountRequest(DosserType.ACTIVE,conf,subdivisionsIds,categoryIds,infoTypeIds,fio, code)).getSingleResult();
        if(count==null){
            count=0L;
        }
        List<DosserProxy> lst=null;
        if(count>0) {
           lst = em.createQuery(createRequest(DosserType.ACTIVE, conf, subdivisionsIds, categoryIds, infoTypeIds, fio, code, sort)).setFirstResult(jtStartIndex).setMaxResults(jtStartIndex + jtPageSize).getResultList();
        }
        return new JSTableExpenseListResp<>(lst,count.intValue());
    }



    private Expression<Boolean> buildWhere(Root<Dosser> sroot,CriteriaBuilder criteriaBuilder,DosserType type,boolean hasConnfidentional,Set<Long> subdivisionsIds,Set<Long> categoryIds,Set<Long> infoTypeIds,String fio,String code){
        Expression<Boolean> exp=criteriaBuilder.equal(sroot.get("recordStatus"), type);
        if(hasConnfidentional)
            exp=criteriaBuilder.and(exp,criteriaBuilder.equal(sroot.get("confidential"),false));
        if(subdivisionsIds!=null){
            exp=criteriaBuilder.and(exp,sroot.get("subdivision").isNotNull());
            exp=criteriaBuilder.and(exp,sroot.get("subdivision").get("subdivisionId").in(subdivisionsIds));
        }
        if(categoryIds!=null){
            exp=criteriaBuilder.and(exp,sroot.get("category").isNotNull());
            exp=criteriaBuilder.and(exp,sroot.get("category").get("categoryId").in(categoryIds));
        }
        if(infoTypeIds!=null){
            exp=criteriaBuilder.and(exp,sroot.get("infotype").isNotNull());
            exp=criteriaBuilder.and(exp,sroot.get("infotype").get("typeId").in(infoTypeIds));
        }
        if(fio!=null){
            exp=criteriaBuilder.and(exp,sroot.get("person").isNotNull());
            exp=criteriaBuilder.and(exp,criteriaBuilder.like(sroot.get("person").get("fio"), "%" + fio + "%"));
        }
        if(code!=null){
            exp=criteriaBuilder.and(exp,sroot.get("person").isNotNull());
            exp=criteriaBuilder.and(exp,criteriaBuilder.like(sroot.get("person").get("code"),"%"+code+"%"));
        }
        return exp;
    }

    private CriteriaQuery<DosserProxy> createRequest(DosserType type,boolean hasConnfidentional,Set<Long> subdivisionsIds,Set<Long> categoryIds,Set<Long> infoTypeIds,String fio,String code,Sort order){
        CriteriaBuilder criteriaBuilder=em.getCriteriaBuilder();
        CriteriaQuery<DosserProxy> cq = criteriaBuilder.createQuery(DosserProxy.class);
        Root<Dosser> sroot=cq.from(Dosser.class);
        cq.select(criteriaBuilder.construct(DosserProxy.class, sroot));
        Expression<Boolean> exp=buildWhere(sroot,criteriaBuilder,type,hasConnfidentional,subdivisionsIds,categoryIds,infoTypeIds,fio,code);
        if(exp!=null)cq.where(exp);
        if(order!=null)cq.orderBy();
        return cq;
    }

    private CriteriaQuery<Long> createCountRequest(DosserType type,boolean hasConnfidentional,Set<Long> subdivisionsIds,Set<Long> categoryIds,Set<Long> infoTypeIds,String fio,String code){
        CriteriaBuilder criteriaBuilder=em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        Root<Dosser> sroot=cq.from(Dosser.class);
        cq.select(criteriaBuilder.count(sroot));
        Expression<Boolean> exp=buildWhere(sroot,criteriaBuilder,type,hasConnfidentional,subdivisionsIds,categoryIds,infoTypeIds,fio,code);
        if(exp!=null)cq.where(exp);
        return cq;
    }
}
