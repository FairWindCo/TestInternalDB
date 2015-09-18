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
    List<ProgramImportStatistics> findByImportDateTimeBetween(Date start,Date end);

    /**
     * select count(DOSSIERS.DOSSIER_ID) as value,
     CATEGORIES.CATEGORY_NAME as label
     from DOSSIERS left join CATEGORIES on
     DOSSIERS.category_CATEGORY_ID=
     CATEGORIES.CATEGORY_ID
     group by DOSSIERS.category_CATEGORY_ID
     * @return
     */
    @Query("select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.DonutData(count(dos),dos.subdivision.name,dos.subdivision.subdivisionId) from Dosser dos group by dos.subdivision")
    List<DonutData> getSubdivisionStatistic();
    @Query("select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.DonutData(count(dos),dos.subdivision.name,dos.subdivision.subdivisionId) from Dosser dos where dos.category.categoryId=1 group by dos.subdivision")
    List<DonutData> getSubdivisionComplaintStatistic();
    @Query("select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.DonutData(count(dos),dos.category.name,dos.category.categoryId) from Dosser dos group by dos.category")
    List<DonutData> getCategoryStatistic();
    @Query("select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.DonutData(count(dos),dos.category.name,dos.category.categoryId) from Dosser dos where dos.subdivision.subdivisionId=?1 group by dos.category")
    List<DonutData> getCategoryStatistic(Long subdiviiosId);
    @Query("select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.DonutData(count(dos),dos.infotype.typeName,dos.infotype.typeId) from Dosser dos where dos.category.categoryId=?1 group by dos.infotype")
    List<DonutData> getInfoTypeStatistic(Long categoryId);
    @Query("select count(pers) from Person pers where pers.personType = :ptype")
    Long getPersonCountS(@Param("ptype")PersonType type);
    @Query("select count(dos) from Dosser dos")
    Long getDosserCount();
    @Query("select count(pers) from Person pers")
    Long getPersonCount();
    @Query("select count(dos) from Dosser dos where dos.category.categoryId=1")
    Long getDosserComplaintCount();


    @Query(value = "select  date_time,sum(total) as total,sum(complaint) as complaint\n" +
            " from(\n" +
            " (select DATE_FORMAT(FROM_UNIXTIME(creationTime/1000), '%d/%m/%y') as date_time,count(DOSSIER_ID) as total ,0 as complaint from DOSSIERS\n" +
            " where creationTime>=?1 and creationTime<=?2 \n"+
            " group by date_time)\n" +
            " union all \n" +
            " (select DATE_FORMAT(FROM_UNIXTIME(creationTime/1000), '%d/%m/%y') as date_time,0  as total,count(DOSSIER_ID) as complaint from DOSSIERS\n" +
            " where category_CATEGORY_ID=1 and creationTime>=?1 and creationTime<=?2\n" +
            " group by date_time)\n" +
            " ) t group by date_time",nativeQuery = true)
    List<Object[]> getDoosersDatestatistics(long startDate,long endDate);



}
