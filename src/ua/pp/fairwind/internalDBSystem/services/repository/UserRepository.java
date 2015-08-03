package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Roles;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.User;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.FilesType;
import ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean;
import ua.pp.fairwind.internalDBSystem.dateTable.JsonJTableExpenseOptionsBean;

import java.util.List;
import java.util.Set;

/**
 * Created by Сергей on 17.07.2015.
 */
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByUserName(String userName);
    Page<User> findByUserName(String userName, Pageable pageRequest);
    List<User> findByUserNameLike(String userName);
    Page<User> findByUserNameLike(String userName, Pageable pageRequest);
    Page<Roles> findByUserID(Long userID, Pageable pageRequest);
    Set<Roles> findByUserID(Long userID);
    @Query("Select distinct r.roleId from User u join u.userRoles r where u.userID = ?1")
    Set<Long> getRolesIDForUserId(Long userID);
    @Query("Select r from Roles r where r.roleId NOT IN  ?1")
    Page<Roles> findGetAvaibleRoles(Set<Long> userRolesIds, Pageable pageRequest);
    @Query("Select r from Roles r where r.roleId NOT IN  ?1")
    List<Roles> findGetAvaibleRoles(Set<Long> userRolesIds);
    @Query("Select new ua.pp.fairwind.internalDBSystem.dateTable.JSTableExpenseOptionsBean(r.roleId,r.roleDescription) from Roles r where r.roleId NOT IN  ?1")
    List<JSTableExpenseOptionsBean> findGetAvaibleRolesOptions(Set<Long> userRolesIds);
}
