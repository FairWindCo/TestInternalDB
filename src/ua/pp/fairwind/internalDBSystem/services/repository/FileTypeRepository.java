package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.FilesType;

import java.util.List;

/**
 * Created by Сергей on 17.07.2015.
 */
public interface FileTypeRepository extends JpaRepository<FilesType,Long> {
    List<FilesType> findByFilesTypeNam(String name);
}
