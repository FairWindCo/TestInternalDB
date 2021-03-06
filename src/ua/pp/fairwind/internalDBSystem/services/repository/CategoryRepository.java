package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Category;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Subdivision;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean;

import java.util.List;
import java.util.Set;

/**
 * Created by ������ on 17.07.2015.
 */
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByName(String name);
    Page<Category> findByName(String name, Pageable pageRequest);
    List<Category> findByNameLike(String name);
    Page<Category> findByNameLike(String name, Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(c.categoryId,c.name) from Category c")
    List<JSTableExpenseOptionsBean> getAllCategoryOptions();

    @Query("Select c from Category c join c.subdivision s where c.name like :name and s.subdivisionId in :trusted")
    Page<Category> getAllElementsByName(@Param("name") String name,@Param("trusted") Set<Long> trustedSubdivisions, Pageable pageRequest);

    Page<Category> findBySubdivisionSubdivisionIdIn(Set<Long> trustedSubdivisions, Pageable pageRequest);
    Page<Category> findByNameLikeAndSubdivisionSubdivisionIdIn(String name,Set<Long> trustedSubdivisions, Pageable pageRequest);
    @Query("Select distinct gSub.subdivisionId from Category c join c.subdivision gSub where c.categoryId = ?1")
    Set<Long> getSubdivisionsIDForCategoryId(Long userID);
    @Query("Select sub from Subdivision sub where sub.subdivisionId NOT IN  ?1")
    List<Subdivision> getAvaibleSubdivisions(Set<Long> userSubdivisionsIds);
    @Query("Select sub from Subdivision sub")
    List<Subdivision> getAllSubdivisions();
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(sub.subdivisionId,sub.name) from Subdivision sub where sub.subdivisionId NOT IN  ?1")
    List<JSTableExpenseOptionsBean> getAvaibleSubdivisionsOptions(Set<Long> categorySubdivisionsIds);

    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(sub.subdivisionId,sub.name) from Subdivision sub where sub.subdivisionId NOT IN  ?1 and sub IN ?2")
    List<JSTableExpenseOptionsBean> getAvaibleSubdivisionsOptionsSecurety(Set<Long> categorySubdivisionsIds,Set<Subdivision> trustedSubdivisions);

    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(sub.subdivisionId,sub.name) from Subdivision sub ")
    List<JSTableExpenseOptionsBean> getAllSubdivisionsOptions();
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(sub.subdivisionId,sub.name) from Subdivision sub where sub IN ?1")
    List<JSTableExpenseOptionsBean> getAllSubdivisionsOptionsSecurity(Set<Subdivision> trustedSubdivisions);


    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(c.categoryId,c.name) from Category c join c.subdivision sub where sub.subdivisionId IN  ?1 ")
    Set<JSTableExpenseOptionsBean> getCategoryForSubdivisions(Set<Long> sunbdivisionsIDS);

    @Query("Select count(dos) from Dosser dos where dos.category is not null and dos.category.categoryId=?1")
    Long getChildRecordCount(long id);
}
