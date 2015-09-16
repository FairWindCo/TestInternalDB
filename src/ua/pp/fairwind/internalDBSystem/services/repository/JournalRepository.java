package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.ProgramOperationJornal;
import java.util.Date;

/**
 * Created by Сергей on 16.09.2015.
 */
public interface JournalRepository extends JpaRepository<ProgramOperationJornal,Long> {
    Page<ProgramOperationJornal> findByUserContains(String user,Pageable page);
    Page<ProgramOperationJornal> findByUserContainsAndJournalDateBetween(String user,Date stardate,Date enddate,Pageable page);
    Page<ProgramOperationJornal> findByJournalDateBetween(Date stardate,Date enddate,Pageable page);

}
