package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.FilesType;

import java.util.List;

/**
 * Created by Сергей on 17.07.2015.
 */
public interface FileTypeRepository extends JpaRepository<FilesType,Long> {
    List<FilesType> findByFilesTypeName(String filesTypeName);
    Page<FilesType> findByFilesTypeName(String filesTypeName,Pageable pageRequest);
    List<FilesType> findByFilesTypeNameLike(String filesTypeName);
    Page<FilesType> findByFilesTypeNameLike(String filesTypeName,Pageable pageRequest);
}
