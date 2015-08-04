package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Category;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Subdivision;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean;

import java.util.List;

/**
 * Created by Сергей on 17.07.2015.
 */
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByName(String name);
    Page<Category> findByName(String name, Pageable pageRequest);
    List<Category> findByNameLike(String name);
    Page<Category> findByNameLike(String name, Pageable pageRequest);
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(c.categoryId,c.name) from Category c")
    List<JSTableExpenseOptionsBean> getAllCategoryOptions();
}
