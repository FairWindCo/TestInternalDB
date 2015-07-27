package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Category;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Subdivision;

import java.util.List;

/**
 * Created by Сергей on 17.07.2015.
 */
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByName(String name);
    Page<Category> findByName(String name, Pageable pageRequest);
    List<Category> findByNameLike(String name);
    Page<Category> findByNameLike(String name, Pageable pageRequest);
}
