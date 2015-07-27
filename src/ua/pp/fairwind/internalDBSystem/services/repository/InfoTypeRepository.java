package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Category;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.InfoType;

import java.util.List;

/**
 * Created by ������ on 17.07.2015.
 */
public interface InfoTypeRepository extends JpaRepository<InfoType,Long> {
    List<InfoType> findByTypeName(String typeName);
    Page<InfoType> findByTypeName(String typeName, Pageable pageRequest);
    List<InfoType> findByTypeNameLike(String typeName);
    Page<InfoType> findByTypeNameLike(String typeName, Pageable pageRequest);
}