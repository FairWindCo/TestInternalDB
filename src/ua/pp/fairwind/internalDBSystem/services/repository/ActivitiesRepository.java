package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Activities;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Hobbies;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean;

import java.util.List;

/**
 * Created by Сергей on 17.07.2015.
 */
public interface ActivitiesRepository extends JpaRepository<Activities,Long> {
    List<Activities> findByActivitiesTypeName(String activitiesTypeName);
    Page<Activities> findByActivitiesTypeName(String activitiesTypeName, Pageable pageRequest);
    List<Activities> findByActivitiesTypeNameLike(String activitiesTypeName);
    Page<Activities> findByActivitiesTypeNameLike(String activitiesTypeName, Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(a.activitiesTypeId,a.activitiesTypeName) from Activities a")
    List<JSTableExpenseOptionsBean> getAllActivitiesOptions();
}
