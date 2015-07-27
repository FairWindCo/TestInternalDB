package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Roles;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.User;

import java.util.List;

/**
 * Created by Сергей on 17.07.2015.
 */
public interface RoleRepository extends JpaRepository<Roles,Long> {
    List<Roles> findByRoleName(String roleName);
    Page<Roles> findByRoleName(String roleName, Pageable pageRequest);
    List<Roles> findByRoleNameLike(String roleName);
    Page<Roles> findByRoleNameLike(String roleName, Pageable pageRequest);
}
