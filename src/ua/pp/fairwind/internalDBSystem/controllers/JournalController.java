package ua.pp.fairwind.internalDBSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.ProgramImportStatistics;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.ProgramOperationJornal;
import ua.pp.fairwind.internalDBSystem.datamodel.proxy.DonutData;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseListResp;
import ua.pp.fairwind.internalDBSystem.services.JournalService;
import ua.pp.fairwind.internalDBSystem.services.repository.StatisticRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Сергей on 16.09.2015.
 */
@Controller
@RequestMapping("/journal")
public class JournalController {
    final SimpleDateFormat formater=new SimpleDateFormat("MM/dd/yyyy");
    @Autowired
    JournalService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model) {
        return "journal";
    }


    @Transactional(readOnly = true)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public JSTableExpenseListResp<ProgramOperationJornal> getImportStat(@RequestParam int jtStartIndex, @RequestParam int jtPageSize,@RequestParam(required = false)String name,@RequestParam(required = false)String startDate,@RequestParam(required = false)String endDate){
        return service.viewLog(jtStartIndex,jtPageSize,null,name,startDate,endDate);
    }
}
