package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Category;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.InfoType;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean;

import java.util.List;
import java.util.Set;

/**
 * Created by ������ on 17.07.2015.
 */
public interface InfoTypeRepository extends JpaRepository<InfoType,Long> {
    List<InfoType> findByTypeName(String typeName);
    Page<InfoType> findByTypeName(String typeName, Pageable pageRequest);
    List<InfoType> findByTypeNameLike(String typeName);
    Page<InfoType> findByTypeNameLike(String typeName, Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(c.typeId,c.typeName) from InfoType c")
    List<JSTableExpenseOptionsBean> getAllInfoTypeOptions();

    Page<InfoType> findByCategorySubdivisionSubdivisionIdIn(Set<Long> trustedSubdivisions, Pageable pageRequest);
    Page<InfoType> findByTypeNameLikeAndCategorySubdivisionSubdivisionIdIn(String name,Set<Long> trustedSubdivisions, Pageable pageRequest);
    @Query("Select distinct categ.categoryId from InfoType inf join inf.category categ where inf.typeId = ?1")
    Set<Long> getCategoryIDForInfoTypeId(Long userID);


    @Query("Select cat from Category cat join cat.subdivision sub where sub.subdivisionId IN  ?1")
    List<Category> getAvaibleCategory(Set<Long> trustedSubdivisionsIds);
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(cat.categoryId,cat.name) from Category cat join cat.subdivision sub where sub.subdivisionId IN  ?1")
    List<JSTableExpenseOptionsBean> getAvaibleCategoryOpt(Set<Long> trustedSubdivisionsIds);
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(cat.categoryId,cat.name) from Category cat")
    List<JSTableExpenseOptionsBean> getAllCategoryes();

    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(inf.typeId,inf.typeName) from InfoType inf where inf.category.categoryId=?1")
    List<JSTableExpenseOptionsBean> getAllInfoTypesForCategory(long categoryId);



    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(inf.typeId,inf.typeName) from InfoType inf join inf.category cat where cat.categoryId IN  ?1 ")
    Set<JSTableExpenseOptionsBean> getInfoForCategory(Set<Long> categoryIDS);
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(inf.typeId,inf.typeName) from InfoType inf join inf.category cat join cat.subdivision sub where sub.subdivisionId IN  ?1 ")
    Set<JSTableExpenseOptionsBean> getInfoForCategorySubdiv(Set<Long> subdivsIDS);

    @Query("Select count(dos) from Dosser dos where dos.infotype is not null and dos.infotype.typeId=?1")
    Long getChildRecordCount(long id);

}
