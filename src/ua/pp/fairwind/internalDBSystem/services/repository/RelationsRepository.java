package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.pp.fairwind.internalDBSystem.datamodel.Files;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Relatives;

/**
 * Created by Сергей on 31.08.2015.
 */
public interface RelationsRepository extends JpaRepository<Relatives,Long> {
    @Query("Select count(relation) from RelationDegrees relation where relation.relatives is not null and relation.relatives.relativiesId=?1")
    Long getChildRecordCount(long id);
}
