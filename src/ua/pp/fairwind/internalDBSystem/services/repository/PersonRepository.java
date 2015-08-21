package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.pp.fairwind.internalDBSystem.datamodel.Person;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Roles;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Subdivision;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.User;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.PersonType;
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
}
