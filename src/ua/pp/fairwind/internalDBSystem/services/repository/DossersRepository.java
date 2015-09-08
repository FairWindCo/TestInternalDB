package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.internalDBSystem.datamodel.Dosser;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Category;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.InfoType;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.PersonType;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean;

import java.util.List;
import java.util.Set;

/**
 * Created by ������ on 17.07.2015.
 */
public interface DossersRepository extends JpaRepository<Dosser,Long> {
    Page<Dosser> findByPersonPersonIdAndPersonPersonType(Long personId,PersonType personType, Pageable pageRequest);
    Page<Dosser> findByPersonPersonIdAndPersonPersonTypeAndConfidential(Long personId,PersonType personType,boolean Confidential,Pageable pageRequest);

    @Query("Select dos from Dosser dos join dos.subdivision sub where sub.subdivisionId IN  :sundivs and dos.person.personId=:personId and dos.person.personType=:personType")
    Page<Dosser> getAvaibleDossers(@Param("sundivs")Set<Long> trustedSubdivisionsIds,@Param("personType")PersonType personType,@Param("personId")Long personId, Pageable pageRequest);
    @Query("Select dos from Dosser dos join dos.subdivision sub where sub.subdivisionId IN  :sundivs and dos.person.personId=:personId and dos.person.personType=:personType and dos.confidential=:confidentional")
    Page<Dosser> getAvaibleDossers(@Param("sundivs")Set<Long> trustedSubdivisionsIds,@Param("personType")PersonType personType,@Param("personId")Long personId,@Param("confidentional")boolean confidentional, Pageable pageRequest);
}
