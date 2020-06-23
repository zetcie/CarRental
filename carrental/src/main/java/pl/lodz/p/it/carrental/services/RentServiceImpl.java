package pl.lodz.p.it.carrental.services;

import org.springframework.stereotype.Service;
import pl.lodz.p.it.carrental.dto.BasicStatsDto;
import pl.lodz.p.it.carrental.misc.exceptions.CarNotFoundException;
import pl.lodz.p.it.carrental.misc.exceptions.RentExistsForTimespan;
import pl.lodz.p.it.carrental.model.TraceLog;
import pl.lodz.p.it.carrental.model.TraceLogType;
import pl.lodz.p.it.carrental.model.cars.Car;
import pl.lodz.p.it.carrental.model.rents.Rent;
import pl.lodz.p.it.carrental.model.rents.RentRequest;
import pl.lodz.p.it.carrental.repositories.CarRepository;
import pl.lodz.p.it.carrental.repositories.RentRepository;
import pl.lodz.p.it.carrental.repositories.RentRequestRepository;
import pl.lodz.p.it.carrental.repositories.TraceLogRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class RentServiceImpl implements RentService {

    private final RentRepository rentRepository;
    private final RentRequestRepository rentRequestRepository;
    private final TraceLogRepository traceLogRepository;
    private final CarRepository carRepository;

    public RentServiceImpl(
            RentRepository rentRepository,
            RentRequestRepository rentRequestRepository,
            TraceLogRepository traceLogRepository,
            CarRepository carRepository
    ) {
        this.rentRepository = rentRepository;
        this.rentRequestRepository = rentRequestRepository;
        this.traceLogRepository = traceLogRepository;
        this.carRepository = carRepository;
    }

    @Override
    public List<LocalDate> getUnavailableDays(Long id) {
        List<Rent> allByCarIdAndStartDateAfter = this.rentRepository
                .findAllByCarIdAndStartDateAfter(id, LocalDate.now().minusDays(1));

        List<LocalDate> unavailableDays = new LinkedList<>();
        for (Rent rent : allByCarIdAndStartDateAfter) {
            unavailableDays.addAll(this.extractDatesBetween(rent.getStartDate(), rent.getEndDate()));
        }
        return unavailableDays;
    }

    private List<LocalDate> extractDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new LinkedList<>();
        dates.add(startDate);
        LocalDate dateIter = startDate.plusDays(1);
        while (dateIter.isBefore(endDate)) {
            dates.add(dateIter);
            dateIter = dateIter.plusDays(1);
        }
        dates.add(endDate);
        return dates;
    }

    @Override
    public RentRequest createRentRequest(RentRequest rentRequest) throws RentExistsForTimespan, CarNotFoundException {
        boolean anyRentBetweenExists = this.rentRepository.existsRentsByCarIdAndEndDateIsBetween(
                rentRequest.getCar().getId(),
                rentRequest.getStartDate(),
                rentRequest.getEndDate()
        );
        if (anyRentBetweenExists) {
            throw new RentExistsForTimespan();
        }
        Car car = this.carRepository.findById(rentRequest.getCar().getId()).orElseThrow(CarNotFoundException::new);
        rentRequest.setTotalCost(
                ChronoUnit.DAYS.between(rentRequest.getStartDate(), rentRequest.getEndDate()) * car.getDailyCost()
        );
        rentRequest = this.rentRequestRepository.save(rentRequest);
        this.traceLogRepository.save(
                new TraceLog(
                        String.format(
                                "User %s made a rental request for a car with id %s",
                                rentRequest.getSystemUser().getLogin(),
                                rentRequest.getCar().getId()
                        ),
                        rentRequest.getSystemUser(),
                        TraceLogType.CAR_RENTAL_REQUEST
                )
        );
        return rentRequest;
    }

    @Override
    public List<RentRequest> getAllRentRequests() {
        return this.rentRequestRepository.findAll();
    }

    @Override
    public List<Rent> getAllRentsForCar(Long id) {
        return this.rentRepository.findAllByCarId(id);
    }

    @Override
    public void acceptRequest(Long id) {
        Optional<RentRequest> rentRequestOptional = this.rentRequestRepository.findById(id);
        rentRequestOptional.ifPresent(
                (rentReq) -> {
                    Rent rent = new Rent(rentReq);
                    rent = this.rentRepository.save(rent);
                    this.rentRequestRepository.delete(rentReq);
                    this.traceLogRepository.save(
                            new TraceLog(
                                    String.format(
                                            "User %s rented a car with id %s",
                                            rent.getSystemUser().getLogin(),
                                            rent.getCar().getId()
                                    ),
                                    rent.getSystemUser(),
                                    TraceLogType.CAR_RENTAL
                            )
                    );
                }
        );

    }

    @Override
    public void rejectRequest(Long id) {
        Optional<RentRequest> rentRequestOptional = this.rentRequestRepository.findById(id);
        rentRequestOptional.ifPresent(
                this.rentRequestRepository::delete
        );
    }

    @Override
    public List<RentRequest> getAllRentRequestsForUser(Long userId) {
        return this.rentRequestRepository.findAllBySystemUserId(userId);
    }

    @Override
    public List<Rent> getAllRentsForUser(Long userId) {
        return this.rentRepository.findAllBySystemUserId(userId);
    }

    @Override
    public BasicStatsDto getRentRequestsStatsForLastDays(int days) {
        return this.getCountStatusForTraceLog(TraceLogType.CAR_RENTAL_REQUEST, days);
    }

    @Override
    public BasicStatsDto getRentStatsForLastDays(int days) {
        return this.getCountStatusForTraceLog(TraceLogType.CAR_RENTAL, days);
    }

    private BasicStatsDto getCountStatusForTraceLog(
            TraceLogType traceLogType,
            int daysInThePast
    ) {
        List<TraceLog> objs = this.traceLogRepository.findAllByCreatedDateGreaterThanEqualAndTypeEquals(
                LocalDateTime.now().minusDays(daysInThePast),
                traceLogType
        );
        Map<String, List<Object>> map = new Hashtable<>();
        for (TraceLog obj : objs) {
            String identifier = this.formatCreatedDate(obj.getCreatedDate());
            if (map.containsKey(identifier)) {
                List<Object> listOfObjects = map.get(identifier);
                listOfObjects.add(obj);
            } else {
                List<Object> newList = new ArrayList<>();
                newList.add(obj);
                map.put(identifier, newList);
            }
        }
        List<String> labels = new ArrayList<>();
        List<Object> data = new ArrayList<>();
        for (Map.Entry<String, List<Object>> mapping : map.entrySet()) {
            labels.add(mapping.getKey());
            data.add(mapping.getValue().size());
        }
        return new BasicStatsDto(data, labels);
    }

    private String formatCreatedDate(LocalDateTime createdDate) {
        return LocalDate.of(
                createdDate.getYear(),
                createdDate.getMonth(),
                createdDate.getDayOfMonth()
        ).toString();
    }
}
