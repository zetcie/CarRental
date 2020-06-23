package pl.lodz.p.it.carrental.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.carrental.misc.exceptions.EmailAlreadyTaken;
import pl.lodz.p.it.carrental.misc.exceptions.LoginAlreadyTaken;
import pl.lodz.p.it.carrental.model.users.SystemRole;
import pl.lodz.p.it.carrental.model.users.SystemUser;
import pl.lodz.p.it.carrental.model.users.SystemUserPrincipal;
import pl.lodz.p.it.carrental.repositories.SystemUserRepository;

import java.util.List;

@Service
public class SystemUserServiceImpl implements SystemUserService {

    private final SystemUserRepository systemUserRepository;
    private final PasswordEncoder passwordEncoder;

    public SystemUserServiceImpl(
            SystemUserRepository systemUserRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.systemUserRepository = systemUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return new SystemUserPrincipal(
                this.systemUserRepository
                .findByLogin(userName)
                .orElseThrow(
                        () -> new UsernameNotFoundException("No user with given login.")
                )
        );
    }

    @Override
    public void createStandardUser(SystemUser user) throws EmailAlreadyTaken, LoginAlreadyTaken {
        user.setRole(SystemRole.CLIENT);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        if (this.systemUserRepository.existsByLogin(user.getLogin())) {
            throw new LoginAlreadyTaken();
        }
        if (this.systemUserRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyTaken();
        }
        this.systemUserRepository.save(user);
    }

    @Override
    public List<SystemUser> getAllUsers() {
        return this.systemUserRepository.findAll();
    }

    @Override
    public void enableUser(Long id) {
        this.systemUserRepository.findById(id).ifPresent(
                (car) -> {
                    car.setEnabled(true);
                    this.systemUserRepository.save(car);
                }
        );
    }

    @Override
    public void disableUser(Long id) {
        this.systemUserRepository.findById(id).ifPresent(
                (car) -> {
                    car.setEnabled(false);
                    this.systemUserRepository.save(car);
                }
        );
    }
}
