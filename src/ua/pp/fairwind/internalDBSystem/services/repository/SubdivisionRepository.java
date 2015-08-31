package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Subdivision;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.User;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean;

import java.util.List;
import java.util.Set;

/**
 * Created by ������ on 17.07.2015.
 */
public interface SubdivisionRepository extends JpaRepository<Subdivision,Long> {
    List<Subdivision> findByName(String name);
    Page<Subdivision> findByName(String name, Pageable pageRequest);
    List<Subdivision> findByNameLike(String name);
    Page<Subdivision> findByNameLike(String name, Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(s.subdivisionId,s.name) from Subdivision s")
    List<JSTableExpenseOptionsBean> getAllSubdivisionOptions();
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(s.subdivisionId,s.name) from Subdivision s where s.subdivisionId in ?1")
    List<JSTableExpenseOptionsBean> getAllSubdivisionOptionsWithAccessControl(Set<Long> trustedSubdivisions);

    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(cat.categoryId,cat.name) from Subdivision s join s.categories cat where s.subdivisionId = ?1")
    List<JSTableExpenseOptionsBean> getAllCategoryForSubdivisionOptions(Long subdivisionsId);
}
