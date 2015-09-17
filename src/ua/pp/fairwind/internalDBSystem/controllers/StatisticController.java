package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.ProgramImportStatistics;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.PersonType;
import ua.pp.fairwind.internalDBSystem.datamodel.proxy.DonutData;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseListResp;
import ua.pp.fairwind.internalDBSystem.services.repository.StatisticRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Сергей on 16.09.2015.
 */
@Controller
@RequestMapping("/stats")
public class StatisticController {
    final SimpleDateFormat formater=new SimpleDateFormat("MM/dd/yyyy");
    @Autowired
    StatisticRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model) {
        return "stat";
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public String showBuble(Model model) {
        long dosser_count=repository.getDosserCount();
        long dosser_complaint_count=repository.getDosserComplaintCount();
        long total_person_count=repository.getPersonCount();
        long total_client_count=repository.getPersonCountS(PersonType.CLIENT);
        long total_worker_count=repository.getPersonCountS(PersonType.WORKER);
        model.addAttribute("dosser_count",dosser_count);
        model.addAttribute("dosser_complaint_count",dosser_complaint_count);
        model.addAttribute("total_person_count",total_person_count);
        model.addAttribute("total_client_count",total_client_count);
        model.addAttribute("total_worker_count",total_worker_count);
        return "statdonut";
    }

    @RequestMapping(value = "/plot", method = RequestMethod.GET)
    public String showDiagram(Model model) {
        long dosser_count=repository.getDosserCount();
        long dosser_complaint_count=repository.getDosserComplaintCount();
        long total_person_count=repository.getPersonCount();
        long total_client_count=repository.getPersonCountS(PersonType.CLIENT);
        long total_worker_count=repository.getPersonCountS(PersonType.WORKER);
        model.addAttribute("dosser_count",dosser_count);
        model.addAttribute("dosser_complaint_count",dosser_complaint_count);
        model.addAttribute("total_person_count",total_person_count);
        model.addAttribute("total_client_count",total_client_count);
        model.addAttribute("total_worker_count",total_worker_count);
        return "statplot";
    }


    @Transactional(readOnly = true)
    @RequestMapping(value = "/subdivisions", method = RequestMethod.POST)
    @ResponseBody
    public List<DonutData> getSubdivisionsStat(){
        return repository.getSubdivisionStatistic();
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    @ResponseBody
    public List<DonutData> getCategoryStat(){
        return repository.getCategoryStatistic();
    }


    @Transactional(readOnly = true)
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<ProgramImportStatistics> getImportStat(@RequestParam int jtStartIndex, @RequestParam int jtPageSize,@RequestParam(required = false)String startDate,@RequestParam(required = false)String endDate){
        Sort sort=new Sort(Sort.Direction.DESC,"importDateTime");
        PageRequest pager;
        Page<ProgramImportStatistics> page;
        pager=new PageRequest(jtStartIndex, jtPageSize,sort);
        if(startDate==null||endDate==null){
            return new JSTableExpenseListResp<>(repository.findAll(pager));
        } else {
            try {
                Date sdate=formater.parse(startDate);
                Date edate=formater.parse(endDate);
                return new JSTableExpenseListResp<>(repository.findByImportDateTimeBetween(sdate,edate,pager));
            } catch (ParseException e) {
                return new JSTableExpenseListResp<>(repository.findAll(pager));
            }
        }
    }
}
