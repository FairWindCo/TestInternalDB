package ua.pp.fairwind.internalDBSystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.pp.fairwind.internalDBSystem.datamodel.administrative.User;
import ua.pp.fairwind.internalDBSystem.services.repository.UserRepository;

import java.util.logging.Logger;

/**
 * Created by Сергей on 04.08.2015.
 */
@Service("myUserDetailsService")
public class MyUserDetailService implements UserDetailsService {
    protected static Logger logger = Logger.getLogger("userDetailService");
    @Autowired
    UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Try find user: "+username);
        User account= users.findByUserName(username);

        if(account==null){
            logger.info("NO user: "+username);
            throw new UsernameNotFoundException("No such user: " + username);
        } else {
            if(account.getUserRoles().isEmpty()){
                logger.info("NO roles for: "+username);
                throw new UsernameNotFoundException("User " + username + " has no authorities");
            } else {
                logger.info("user find: "+username + " passowd:"+account.getPasswordHash() );
                return new UserDetailsAdapter(account);
            }
        }
    }
}
