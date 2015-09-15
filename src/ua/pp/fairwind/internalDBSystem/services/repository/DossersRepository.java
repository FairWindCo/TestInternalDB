package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.internalDBSystem.datamodel.Dosser;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.DosserType;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.PersonType;
import ua.pp.fairwind.internalDBSystem.datamodel.proxy.DosserProxy;

import java.util.List;
import java.util.Set;

/**
 * Created by ������ on 17.07.2015.
 */
public interface DossersRepository extends JpaRepository<Dosser,Long>,JpaSpecificationExecutor {
    Page<Dosser> findByPersonPersonIdAndPersonPersonTypeAndRecordStatus(Long personId,PersonType personType,DosserType recstat, Pageable pageRequest);
    Page<Dosser> findByPersonPersonIdAndPersonPersonTypeAndConfidentialAndRecordStatus(Long personId,PersonType personType,boolean Confidential,DosserType recstat,Pageable pageRequest);

    @Query("Select dos from Dosser dos join dos.subdivision sub where sub.subdivisionId IN  :sundivs and dos.person.personId=:personId and dos.person.personType=:personType and dos.recordStatus=:recstat")
    Page<Dosser> getAvaibleDossers(@Param("sundivs")Set<Long> trustedSubdivisionsIds,@Param("personType")PersonType personType,@Param("personId")Long personId,@Param("recstat")DosserType recstat, Pageable pageRequest);
    @Query("Select dos from Dosser dos join dos.subdivision sub where sub.subdivisionId IN  :sundivs and dos.person.personId=:personId and dos.person.personType=:personType and dos.confidential=:confidentional  and dos.recordStatus=:recstat")
    Page<Dosser> getAvaibleDossers(@Param("sundivs")Set<Long> trustedSubdivisionsIds,@Param("personType")PersonType personType,@Param("personId")Long personId,@Param("confidentional")boolean confidentional,@Param("recstat")DosserType recstat, Pageable pageRequest);


    List<Dosser> findByPersonPersonIdAndRecordStatus(Long personId,DosserType recstat);
    List<Dosser> findByPersonPersonIdAndConfidentialAndRecordStatus(Long personId,boolean Confidential,DosserType recstat);
    @Query("Select dos from Dosser dos join dos.subdivision sub where sub.subdivisionId IN  :sundivs and dos.person.personId=:personId and dos.recordStatus=:recstat")
    List<Dosser> getAvaibleDossers(@Param("sundivs")Set<Long> trustedSubdivisionsIds,@Param("personId")Long personId,@Param("recstat")DosserType recstat);
    @Query("Select dos from Dosser dos join dos.subdivision sub where sub.subdivisionId IN  :sundivs and dos.person.personId=:personId and dos.confidential=:confidentional  and dos.recordStatus=:recstat")
    List<Dosser> getAvaibleDossers(@Param("sundivs")Set<Long> trustedSubdivisionsIds,@Param("personId")Long personId,@Param("confidentional")boolean confidentional,@Param("recstat")DosserType recstat);

    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.DosserProxy(dos) from Dosser dos where dos.recordStatus=:recstat")
    Page<DosserProxy> findByRecordStatus(@Param("recstat")DosserType recstat, Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.DosserProxy(dos)  from Dosser dos where dos.confidential=:confidentional  and dos.recordStatus=:recstat")
    Page<DosserProxy> findByConfidentialAndRecordStatus(@Param("confidentional")boolean Confidential,@Param("recstat")DosserType recstat,Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.DosserProxy(dos) from Dosser dos join dos.subdivision sub where sub.subdivisionId IN  :sundivs and dos.recordStatus=:recstat")
    Page<DosserProxy> getAvaibleDossers(@Param("sundivs")Set<Long> trustedSubdivisionsIds,@Param("recstat")DosserType recstat, Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.DosserProxy(dos)  from Dosser dos join dos.subdivision sub where sub.subdivisionId IN  :sundivs and dos.confidential=:confidentional  and dos.recordStatus=:recstat")
    Page<DosserProxy> getAvaibleDossers(@Param("sundivs")Set<Long> trustedSubdivisionsIds,@Param("confidentional")boolean confidentional,@Param("recstat")DosserType recstat, Pageable pageRequest);

    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.DosserProxy(dos) from Dosser dos")
    Page<DosserProxy> findDossersProxy(Specification<DosserProxy> spec, Pageable pageRequest);
}
