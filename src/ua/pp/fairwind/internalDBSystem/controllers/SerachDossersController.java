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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.pp.fairwind.internalDBSystem.datamodel.Dosser;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.DosserType;
import ua.pp.fairwind.internalDBSystem.datamodel.proxy.DosserProxy;
import ua.pp.fairwind.internalDBSystem.dateTable.FormSort;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseListResp;
import ua.pp.fairwind.internalDBSystem.security.UserDetailsAdapter;
import ua.pp.fairwind.internalDBSystem.services.JournalService;
import ua.pp.fairwind.internalDBSystem.services.repository.DossersRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Сергей on 15.09.2015.
 */
@Controller
@RequestMapping("/search")
public class SerachDossersController {
    @Secured({"ROLE_SUPERVIEW","ROLE_GROUP_VIEW", "ROLE_SUPER_VIEW","ROLE_MAIN_VIEW","ROLE_SUPER_EDIT","ROLE_GROUP_EDIT", "ROLE_SUPER_EDIT","ROLE_MAIN_EDIT"})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model) {
        return "search";
    }

    @Autowired
    DossersRepository dosserService;
    @Autowired
    EntityManager em;
    @Autowired
    private JournalService journal;

    @RequestMapping(value = "/search", method = {RequestMethod.GET,RequestMethod.POST})
    @Transactional(readOnly = true)
    @Secured({"ROLE_SUPERVIEW","ROLE_GROUP_VIEW", "ROLE_SUPER_VIEW","ROLE_MAIN_VIEW","ROLE_SUPER_EDIT","ROLE_GROUP_EDIT", "ROLE_SUPER_EDIT","ROLE_MAIN_EDIT"})
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

        boolean conf=user.hasRole("ROLE_CONFIDENTIAL");
        String fio=request.getParameter("searchname");
        String code=request.getParameter("searchcode");
        Set<Long> subdivisionsIds=FormSort.getIDsFromRequest(request,"subdivisions");
        if (subdivisionsIds==null && !(user.hasRole("ROLE_SUPER_EDIT") || user.hasRole("ROLE_SUPER_VIEW"))) subdivisionsIds=user.getTrustedSubvisionsId();
        Set<Long> categoryIds=FormSort.getIDsFromRequest(request,"categoryes");
        Set<Long> infoTypeIds=FormSort.getIDsFromRequest(request, "infotypes");

        Long count = em.createQuery(createCountRequest(DosserType.ACTIVE, conf, subdivisionsIds, categoryIds, infoTypeIds, fio, code)).getSingleResult();
        if(count==null){
            count=0L;
        }
        List<DosserProxy> lst=null;
        if(count>0) {
           lst = em.createQuery(createRequest(DosserType.ACTIVE, conf, subdivisionsIds, categoryIds, infoTypeIds, fio, code, jtSorting)).setFirstResult(jtStartIndex).setMaxResults(jtStartIndex + jtPageSize).getResultList();
        }
        return new JSTableExpenseListResp<>(lst,count.intValue());
    }

    public static String getURLWithContextPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @RequestMapping(value = "/print", method = {RequestMethod.GET,RequestMethod.POST})
    @Transactional(readOnly = true)
    @Secured({"ROLE_SUPERVIEW","ROLE_GROUP_VIEW", "ROLE_SUPER_VIEW","ROLE_MAIN_VIEW","ROLE_SUPER_EDIT","ROLE_GROUP_EDIT", "ROLE_SUPER_EDIT","ROLE_MAIN_EDIT"})
    public String printsearch(HttpServletRequest request,Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsAdapter user=(UserDetailsAdapter)auth.getPrincipal();
        boolean conf=user.hasRole("ROLE_CONFIDENTIAL");
        String fio=request.getParameter("searchname");
        String code=request.getParameter("searchcode");
        Set<Long> subdivisionsIds=FormSort.getIDsFromRequest(request,"subdivisions");
        if (subdivisionsIds==null && !(user.hasRole("ROLE_SUPER_EDIT") || user.hasRole("ROLE_SUPER_VIEW"))) subdivisionsIds=user.getTrustedSubvisionsId();
        Set<Long> categoryIds=FormSort.getIDsFromRequest(request,"categoryes");
        Set<Long> infoTypeIds=FormSort.getIDsFromRequest(request, "infotypes");
        List<DosserProxy> lst=em.createQuery(createRequest(DosserType.ACTIVE, conf, subdivisionsIds, categoryIds, infoTypeIds, fio, code, "creationTime DESC")).getResultList();
        model.addAttribute("url",getURLWithContextPath(request));
        model.addAttribute("dossers",lst);
        return "searchprint";
    }

    @RequestMapping(value = "/last", method = {RequestMethod.GET,RequestMethod.POST})
    @Transactional(readOnly = true)
    @Secured({"ROLE_SUPERVIEW","ROLE_GROUP_VIEW", "ROLE_SUPER_VIEW","ROLE_MAIN_VIEW","ROLE_SUPER_EDIT","ROLE_GROUP_EDIT", "ROLE_SUPER_EDIT","ROLE_MAIN_EDIT"})
    @ResponseBody
    public JSTableExpenseListResp<DosserProxy> lastRecord(@RequestParam(required = false) Integer maxRecordCount){
        if(maxRecordCount==null)maxRecordCount=25;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsAdapter user=(UserDetailsAdapter)auth.getPrincipal();

        //page=dosserService.findDossersProxy(createSimpleFind(DosserType.ACTIVE,false),pager);
        boolean conf=user.hasRole("ROLE_CONFIDENTIAL");
        Set<Long> subdivisionsIds=user.getTrustedSubvisionsId();
        List<DosserProxy> lst=em.createQuery(createRequest(DosserType.ACTIVE, conf, subdivisionsIds, null, null, null, null, "creationTime DESC")).setFirstResult(0).setMaxResults(maxRecordCount).getResultList();
        return new JSTableExpenseListResp<>(lst);
    }


    private Expression<Boolean> buildWhere(Root<Dosser> sroot,CriteriaBuilder criteriaBuilder,DosserType type,boolean hasConnfidentional,Set<Long> subdivisionsIds,Set<Long> categoryIds,Set<Long> infoTypeIds,String fio,String code){
        Expression<Boolean> exp=criteriaBuilder.equal(sroot.get("recordStatus"), type);
        if(!hasConnfidentional)
            exp=criteriaBuilder.and(exp,criteriaBuilder.equal(sroot.get("confidential"),false));
        if(subdivisionsIds!=null){
            exp=criteriaBuilder.and(exp,sroot.get("subdivision").isNotNull());
            exp=criteriaBuilder.and(exp, sroot.get("subdivision").get("subdivisionId").in(subdivisionsIds));
            //exp=criteriaBuilder.or(exp,sroot.get("subdivision").isNull());
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

    private String checkName(String fieldName){
        if(fieldName==null)return null;
        switch (fieldName){
            case "info":return "textinfo";
            case "fio":return "person.fio";
            case "code":return "person.code";
            case "bethday":return "person.dateberthdey";
            case "status":return "person.personStatus";
            case "fileType":return "fileinfo.filesType.filesTypeName";
            case "fileComments":return "fileinfo.fileNameComments";
            case "confidentional":return "confidential";
            default:return fieldName;
        }
    }

    private Path<?> formFieldExpresion(Root<Dosser> root,String fieldName){
        if(fieldName.contains(".")) {
            String[] nsmes = fieldName.split("\\.");
            Path<?> exp=null;
            for(String name:nsmes){
                if(exp==null){
                    exp=root.get(name);
                } else {
                    exp=exp.get(name);
                }
            }
            return exp;
        }else{
            return root.get(fieldName);
        }
    }

    private Order formOrder(CriteriaBuilder criteriaBuilder,Root<Dosser> root,String orderDeclaration){
        if(orderDeclaration==null || orderDeclaration.isEmpty()) return null;
        String[] delaration=orderDeclaration.trim().split(" ");
        if(delaration==null || delaration.length!=2){
            return null;
        } else {
            switch (delaration[1]){
                case "ASC" :return criteriaBuilder.asc(formFieldExpresion(root, checkName(delaration[0])));
                case "DESC":return criteriaBuilder.desc(formFieldExpresion(root, checkName(delaration[0])));
                default:
                    return  criteriaBuilder.asc(formFieldExpresion(root, delaration[0]));
            }
        }
    }

    private List<Order> buildOrder(CriteriaBuilder criteriaBuilder,Root<Dosser> root,String orders){
        List<Order> orderer=new ArrayList<>();

        if(orders!=null && !orders.isEmpty()){
            orders=orders.trim();
            if(orders.contains(",")) {
                String[] sortorders = orders.split(",");
                for(String sortorder:sortorders){
                    Order order=formOrder(criteriaBuilder,root,sortorder);
                    if(order!=null){
                        orderer.add(order);
                    }
                }
            } else {
                Order order=formOrder(criteriaBuilder,root,orders);
                if(order!=null){
                    orderer.add(order);
                }
            }

        }
        return orderer;
    }

    private CriteriaQuery<DosserProxy> createRequest(DosserType type,boolean hasConnfidentional,Set<Long> subdivisionsIds,Set<Long> categoryIds,Set<Long> infoTypeIds,String fio,String code,String order){
        CriteriaBuilder criteriaBuilder=em.getCriteriaBuilder();
        CriteriaQuery<DosserProxy> cq = criteriaBuilder.createQuery(DosserProxy.class);
        Root<Dosser> sroot=cq.from(Dosser.class);
        cq.select(criteriaBuilder.construct(DosserProxy.class, sroot));
        Expression<Boolean> exp=buildWhere(sroot,criteriaBuilder,type,hasConnfidentional,subdivisionsIds,categoryIds,infoTypeIds,fio,code);
        if(exp!=null)cq.where(exp);
        if(order!=null&&!order.isEmpty())cq.orderBy(buildOrder(criteriaBuilder,sroot,order));
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
