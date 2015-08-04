package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Activities;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Relatives;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean;

import java.util.List;

/**
 * Created by Сергей on 17.07.2015.
 */
public interface RelativesRepository extends JpaRepository<Relatives,Long> {
    List<Relatives> findByName(String name);
    Page<Relatives> findByName(String name, Pageable pageRequest);
    List<Relatives> findByNameLike(String name);
    Page<Relatives> findByNameLike(String name, Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(rel.id,rel.name) from Relatives rel")
    List<JSTableExpenseOptionsBean> getAllRelativesOptions();
}
