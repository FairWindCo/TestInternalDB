package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Relatives;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Segments;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean;

import java.util.List;

/**
 * Created by ������ on 17.07.2015.
 */
public interface SegmentsRepository extends JpaRepository<Segments,Long> {
    List<Segments> findByName(String name);
    Page<Segments> findByName(String name, Pageable pageRequest);
    List<Segments> findByNameLike(String name);
    Page<Segments> findByNameLike(String name, Pageable pageRequest);
    Page<Segments> findByNameContains(String name, Pageable pageRequest);
    List<Segments> findByNameContains(String name);
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(seg.id,seg.name) from Segments seg")
    List<JSTableExpenseOptionsBean> getAllSegmentsOptions();
}
