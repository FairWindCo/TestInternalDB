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
 * Created by ������ on 17.07.2015.
 */
public interface ContactTypeRepository extends JpaRepository<ContactType,Long> {
    List<ContactType> findByCobtactTypeName(String cobtactTypeName);
    Page<ContactType> findByCobtactTypeName(String cobtactTypeName, Pageable pageRequest);
    List<ContactType> findByCobtactTypeNameLike(String cobtactTypeName);
    Page<ContactType> findByCobtactTypeNameLike(String cobtactTypeName, Pageable pageRequest);
    Page<ContactType> findByCobtactTypeNameContains(String cobtactTypeName, Pageable pageRequest);
    List<ContactType> findByCobtactTypeNameContains(String cobtactTypeName);
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(ct.id,ct.cobtactTypeName) from ContactType ct")
    List<JSTableExpenseOptionsBean> getAllFileTypeOptions();
    @Query("Select count(contact) from Contact contact where contact.contactType is not null and  contact.contactType.contactTypeId=?1")
    Long getChildRecordCount(long id);
}
