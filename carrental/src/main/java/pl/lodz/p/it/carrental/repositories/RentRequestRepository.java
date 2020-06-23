package pl.lodz.p.it.carrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.carrental.model.rents.RentRequest;

import java.util.List;

public interface RentRequestRepository extends JpaRepository<RentRequest, Long> {
    List<RentRequest> findAllBySystemUserId(Long userId);
}
