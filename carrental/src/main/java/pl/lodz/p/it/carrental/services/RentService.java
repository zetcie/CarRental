package pl.lodz.p.it.carrental.services;

import pl.lodz.p.it.carrental.dto.BasicStatsDto;
import pl.lodz.p.it.carrental.misc.exceptions.CarNotFoundException;
import pl.lodz.p.it.carrental.misc.exceptions.RentExistsForTimespan;
import pl.lodz.p.it.carrental.model.rents.Rent;
import pl.lodz.p.it.carrental.model.rents.RentRequest;

import java.time.LocalDate;
import java.util.List;

public interface RentService {
    List<LocalDate> getUnavailableDays(Long id);

    RentRequest createRentRequest(RentRequest rentRequest) throws RentExistsForTimespan, CarNotFoundException;

    List<RentRequest> getAllRentRequests();

    List<Rent> getAllRentsForCar(Long id);

    void acceptRequest(Long id);

    void rejectRequest(Long id);

    List<RentRequest> getAllRentRequestsForUser(Long userId);

    List<Rent> getAllRentsForUser(Long userId);

    BasicStatsDto getRentRequestsStatsForLastDays(int days);

    BasicStatsDto getRentStatsForLastDays(int days);
}
