package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.ProgramImportStatistics;

/**
 * Created by Сергей on 16.09.2015.
 */
public interface StatisticRepository extends JpaRepository<ProgramImportStatistics,Long> {
}
