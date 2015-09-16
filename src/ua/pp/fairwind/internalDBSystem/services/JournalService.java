package ua.pp.fairwind.internalDBSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.ProgramOperationJornal;
import ua.pp.fairwind.internalDBSystem.datamodel.proxy.DosserProxy;
import ua.pp.fairwind.internalDBSystem.dateTable.FormSort;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseListResp;
import ua.pp.fairwind.internalDBSystem.security.UserDetailsAdapter;
import ua.pp.fairwind.internalDBSystem.services.repository.JournalRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Сергей on 16.09.2015.
 */
@Service("JOURNAL_SERVICE")
@Transactional
public class JournalService {
    final SimpleDateFormat formater=new SimpleDateFormat("MM/dd/yyyy");
    protected static Logger logger = Logger.getLogger("service");

    @Autowired
    JournalRepository repository;

    public void log(ProgramOperationJornal.Operation operation,Object object,String info){
        ProgramOperationJornal record=new ProgramOperationJornal();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null) {
            //UserDetailsAdapter user = (UserDetailsAdapter) auth.getPrincipal();
            record.setUser(auth.getName());
        }
        record.setOperation(operation);
        if(object!=null)record.setObject(object.getClass().getCanonicalName());
        record.setInfo(info);
        try{
        repository.save(record);
        }catch (Exception e){
            if(logger!=null)logger.log(Level.WARNING,"WRITE LOG ERROR:"+e.getLocalizedMessage());
        }
        if(logger!=null)logger.log(Level.INFO,operation+" "+object+" "+info);
        }

    public void log(ProgramOperationJornal.Operation operation,String object,String info){
        ProgramOperationJornal record=new ProgramOperationJornal();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null) {
            //UserDetailsAdapter user = (UserDetailsAdapter) auth.getPrincipal();
            record.setUser(auth.getName());
        }
        record.setOperation(operation);
        record.setObject(object);
        record.setInfo(info);
        try{
        repository.save(record);
        }catch (Exception e){
            if(logger!=null)logger.log(Level.WARNING,"WRITE LOG ERROR:"+e.getLocalizedMessage());
        }
        if(logger!=null)logger.log(Level.INFO,operation+" "+object+" "+info);
    }

    public JSTableExpenseListResp<ProgramOperationJornal> viewLog(int jtStartIndex, int jtPageSize,String jtSorting,String name,String startDate,String endDate){
        Sort sort= FormSort.formSortFromSortDescription(jtSorting);
        PageRequest pager;
        Page<ProgramOperationJornal> page;
        if(sort!=null){
            pager = new PageRequest(jtStartIndex, jtPageSize, sort);

        } else {
            pager = new PageRequest(jtStartIndex, jtPageSize);
        }
        Date sdate=null;
        Date edate=null;
        if(startDate!=null){
            try {
                sdate=formater.parse(startDate);
            } catch (ParseException e) {
                //do nothing
            }
        }
        if(endDate!=null){
            try {
                edate=formater.parse(endDate);
            } catch (ParseException e) {
                //do nothing
            }
        }
        if(name==null&&sdate==null&&edate==null){
            page=repository.findAll(pager);
        } else {
            if(name==null){
                if(sdate!=null&&edate!=null){
                    page=repository.findByJournalDateBetween(sdate, edate, pager);

                }
                page=repository.findAll(pager);
            } else {
                if(sdate!=null&&edate!=null){
                    page=repository.findByUserContainsAndJournalDateBetween(name,sdate,edate,pager);
                }
                page=repository.findByUserContains(name,pager);
            }
        }
        return new JSTableExpenseListResp<ProgramOperationJornal>(page);
    }
}
