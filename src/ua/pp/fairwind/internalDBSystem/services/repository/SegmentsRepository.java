package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.FilesType;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Segments;

import java.util.List;

/**
 * Created by Сергей on 17.07.2015.
 */
public interface SegmentsRepository extends JpaRepository<Segments,Long> {
    List<Segments> findByName(String name);
}
