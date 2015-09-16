package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.FilesType;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean;

import java.util.List;

/**
 * Created by ������ on 17.07.2015.
 */
public interface FileTypeRepository extends JpaRepository<FilesType,Long> {
    List<FilesType> findByFilesTypeName(String filesTypeName);
    Page<FilesType> findByFilesTypeName(String filesTypeName,Pageable pageRequest);
    List<FilesType> findByFilesTypeNameLike(String filesTypeName);
    Page<FilesType> findByFilesTypeNameLike(String filesTypeName,Pageable pageRequest);
    Page<FilesType> findByFilesTypeNameContains(String filesTypeName,Pageable pageRequest);
    List<FilesType> findByFilesTypeNameContains(String filesTypeName);
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(ft.filesTypeId,ft.filesTypeName) from FilesType ft")
    List<JSTableExpenseOptionsBean> getAllFileTypeOptions();
    @Query("Select count(file) from Files file where file.filesType is not null and  file.filesType.filesTypeId=?1")
    Long getChildRecordCount(long id);
}
