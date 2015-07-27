package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Subdivision;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.User;

import java.util.List;

/**
 * Created by ������ on 17.07.2015.
 */
public interface SubdivisionRepository extends JpaRepository<Subdivision,Long> {
    List<Subdivision> findByName(String name);
    Page<Subdivision> findByName(String name, Pageable pageRequest);
    List<Subdivision> findByNameLike(String name);
    Page<Subdivision> findByNameLike(String name, Pageable pageRequest);
}
