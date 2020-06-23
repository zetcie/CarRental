package pl.lodz.p.it.carrental.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.lodz.p.it.carrental.misc.exceptions.EmailAlreadyTaken;
import pl.lodz.p.it.carrental.misc.exceptions.LoginAlreadyTaken;
import pl.lodz.p.it.carrental.model.users.SystemUser;

import java.util.List;

public interface SystemUserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException;

    void createStandardUser(SystemUser user) throws EmailAlreadyTaken, LoginAlreadyTaken;

    List<SystemUser> getAllUsers();

    void enableUser(Long id);

    void disableUser(Long id);
}
