package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.ContactType;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.Hobbies;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean;

import java.util.List;

/**
 * Created by Сергей on 17.07.2015.
 */
public interface HobbiesRepository extends JpaRepository<Hobbies,Long> {
    List<Hobbies> findByHobbieName(String hobbieName);
    Page<Hobbies> findByHobbieName(String hobbieName, Pageable pageRequest);
    List<Hobbies> findByHobbieNameLike(String hobbieName);
    Page<Hobbies> findByHobbieNameLike(String hobbieName, Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(h.hobbieId,h.hobbieName) from Hobbies h")
    List<JSTableExpenseOptionsBean> getAllHobbiesOptions();
}
