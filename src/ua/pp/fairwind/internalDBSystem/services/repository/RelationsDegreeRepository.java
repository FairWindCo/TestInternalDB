package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.internalDBSystem.datamodel.RelationDegrees;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Relatives;

/**
 * Created by Сергей on 31.08.2015.
 */
public interface RelationsDegreeRepository extends JpaRepository<RelationDegrees,Long> {
}
