package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.ContactType;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.FilesType;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean;

import java.util.List;

/**
 * Created by Сергей on 17.07.2015.
 */
public interface ContactTypeRepository extends JpaRepository<ContactType,Long> {
    List<ContactType> findByCobtactTypeName(String cobtactTypeName);
    Page<ContactType> findByCobtactTypeName(String cobtactTypeName, Pageable pageRequest);
    List<ContactType> findByCobtactTypeNameLike(String cobtactTypeName);
    Page<ContactType> findByCobtactTypeNameLike(String cobtactTypeName, Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(ct.id,ct.cobtactTypeName) from ContactType ct")
    List<JSTableExpenseOptionsBean> getAllFileTypeOptions();
}
