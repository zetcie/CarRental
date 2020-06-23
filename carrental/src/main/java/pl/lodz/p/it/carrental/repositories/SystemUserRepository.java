package pl.lodz.p.it.carrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.carrental.model.users.SystemUser;

import java.util.Optional;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {
    Optional<SystemUser> findByLogin(String login);
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
}
