package ua.pp.fairwind.internalDBSystem.services.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.Roles;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.User;
import ua.pp.fairwind.internalDBSystem.datamodel.directories.FilesType;

import java.util.List;

/**
 * Created by Сергей on 17.07.2015.
 */
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByUserName(String userName);
    Page<User> findByUserName(String userName, Pageable pageRequest);
    List<User> findByUserNameLike(String userName);
    Page<User> findByUserNameLike(String userName, Pageable pageRequest);
    Page<Roles> findByUserID(Long userID, Pageable pageRequest);
    List<Roles> findByUserID(Long userID);
}
