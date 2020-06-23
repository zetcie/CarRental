package pl.lodz.p.it.carrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.lodz.p.it.carrental.model.rents.Rent;

import java.time.LocalDate;
import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {
    List<Rent> findAllByCarId(Long carId);
    List<Rent> findAllByCarIdAndStartDateAfter(Long carId, LocalDate startDate);
    boolean existsRentsByCarIdAndEndDateIsBetween(Long carId, LocalDate start, LocalDate end);
    List<Rent> findAllBySystemUserId(Long userId);
    @Query("select case when count(e) > 0 then true else false end from Rent e where e.startDate <= :date and e.endDate >= :date")
    boolean existsRentDuringGivenDate(LocalDate date);
}
