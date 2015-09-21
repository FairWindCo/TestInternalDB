package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.pp.fairwind.internalDBSystem.datamodel.Person;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Roles;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Subdivision;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.User;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.PersonType;
import ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean;

import java.util.List;
import java.util.Set;

/**
 * Created by ������ on 17.07.2015.
 */
public interface PersonRepository extends JpaRepository<Person,Long> {
    List<Person> findByPersonType(PersonType personType);
    Page<Person> findByPersonType(PersonType personType, Pageable pageRequest);
    List<Person> findByFioContainsAndPersonType(String fio,PersonType personType);
    Page<Person> findByFioContainsAndPersonType(String fio,PersonType personType, Pageable pageRequest);
    List<Person> findByCodeContainsAndPersonType(String code,PersonType personType);
    Page<Person> findByCodeContainsAndPersonType(String code,PersonType personType, Pageable pageRequest);
    List<Person> findByFioContainsAndCodeContainsAndPersonType(String fio,String code,PersonType personType);
    Page<Person> findByFioContainsAndCodeContainsAndPersonType(String fio,String code,PersonType personType, Pageable pageRequest);

    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.personType=:ptype")
    List<PersonProxy> findProxyByPersonType(@Param("ptype")PersonType personType,Sort sort);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.personType=:ptype")
    Page<PersonProxy> findProxyByPersonType(@Param("ptype")PersonType personType,Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.personType=:ptype and p.fio like :fio")
    List<PersonProxy> findProxyByFioPersonType(@Param("fio")String fio,@Param("ptype")PersonType personType);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.personType=:ptype and p.fio like :fio")
    Page<PersonProxy> findProxyByFioPersonType(@Param("fio")String fio,@Param("ptype")PersonType personType,Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.personType=:ptype and p.code like :code")
    List<PersonProxy> findProxyByCodePersonType(@Param("code")String code,@Param("ptype")PersonType personType);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.personType=:ptype and p.code like :code")
    Page<PersonProxy> findProxyByCodePersonType(@Param("code")String code,@Param("ptype")PersonType personType,Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.personType=:ptype and p.code like :code and p.fio like :fio")
    List<PersonProxy> findProxyByFioAndCodePersonType(@Param("fio")String fio,@Param("code")String code,@Param("ptype")PersonType personType);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.personType=:ptype and p.code like :code and p.fio like :fio")
    Page<PersonProxy> findProxyByFioAndCodePersonType(@Param("fio")String fio,@Param("code")String code,@Param("ptype")PersonType personType,Pageable pageRequest);

    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.personType=:ptype and p.code like :text or p.fio like :text")
    List<PersonProxy> findProxyByFioOrCodePersonType(@Param("text")String text,@Param("ptype")PersonType personType);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.personType=:ptype and p.code like :text or p.fio like :text")
    Page<PersonProxy> findProxyByFioORCodePersonType(@Param("text")String text,@Param("ptype")PersonType personType,Pageable pageRequest);


    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p")
    List<PersonProxy> findProxy(Sort sort);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p")
    Page<PersonProxy> findProxy(Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.fio like :fio")
    List<PersonProxy> findProxyByFio(@Param("fio")String fio);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.fio like :fio")
    Page<PersonProxy> findProxyByFio(@Param("fio")String fio,Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.code like :code")
    List<PersonProxy> findProxyByCode(@Param("code")String code);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.code like :code")
    Page<PersonProxy> findProxyByCode(@Param("code")String code,Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.code like :code and p.fio like :fio")
    List<PersonProxy> findProxyByFioAndCode(@Param("fio")String fio,@Param("code")String code);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.code like :code and p.fio like :fio")
    Page<PersonProxy> findProxyByFioAndCode(@Param("fio")String fio,@Param("code")String code,Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.code like :text or p.fio like :text")
    List<PersonProxy> findProxyByFioOrCode(@Param("text")String text);
    @Query("Select new ua.pp.fairwind.internalDBSystem.datamodel.proxy.PersonProxy(p.personId,p.fio,p.code,p.dateberthdey) from Person p where p.code like :text or p.fio like :text")
    Page<PersonProxy> findProxyByFioOrCode(@Param("text")String text,Pageable pageRequest);
    @Query("select person from Person person where person.key1c=?1")
    Person findByKey1C(String key);
    @Query("select person from Person person where person.fio=?1")
    Person findByFio(String fio);
}
