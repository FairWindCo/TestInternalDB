package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.ProgramImportStatistics;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.PersonType;
import ua.pp.fairwind.internalDBSystem.datamodel.proxy.DonutData;

import java.util.Date;
import java.util.List;

/**
 * Created by Сергей on 16.09.2015.
 */
public interface StatisticRepository extends JpaRepository<ProgramImportStatistics,Long> {
    Page<ProgramImportStatistics> findByImportDateTimeBetween(Date start,Date end,Pageable pager);

    /**
     * select count(DOSSIERS.DOSSIER_ID) as value,
     CATEGORIES.CATEGORY_NAME as label
     from DOSSIERS left join CATEGORIES on
     DOSSIERS.category_CATEGORY_ID=
     CATEGORIES.CATEGORY_ID
     group by DOSSIERS.category_CATEGORY_ID
     * @return
     */
    @Query("select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.DonutData(count(dos),dos.subdivision.name) from Dosser dos group by dos.subdivision")
    List<DonutData> getSubdivisionStatistic();
    @Query("select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.DonutData(count(dos),dos.category.name) from Dosser dos group by dos.category")
    List<DonutData> getCategoryStatistic();
    @Query("select count(pers) from Person pers where pers.personType = :ptype")
    Long getPersonCountS(@Param("ptype")PersonType type);
    @Query("select count(dos) from Dosser dos")
    Long getDosserCount();
    @Query("select count(pers) from Person pers")
    Long getPersonCount();
    @Query("select count(dos) from Dosser dos where dos.category.categoryId=1")
    Long getDosserComplaintCount();



}
