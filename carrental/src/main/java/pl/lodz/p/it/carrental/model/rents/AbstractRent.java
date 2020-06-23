package pl.lodz.p.it.carrental.model.rents;

import pl.lodz.p.it.carrental.model.TimestampedAbstractEntity;
import pl.lodz.p.it.carrental.model.cars.Car;
import pl.lodz.p.it.carrental.model.users.SystemUser;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@MappedSuperclass
public abstract class AbstractRent extends TimestampedAbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private Car car;

    @NotNull
    @ManyToOne
    private SystemUser systemUser;

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    @NotNull
    @FutureOrPresent
    private LocalDate endDate;

    @NotNull
    @Min(0)
    private Double totalCost;

    public AbstractRent() {

    }

    public AbstractRent(AbstractRent rent) {
        this.car = rent.getCar();
        this.endDate = rent.getEndDate();
        this.startDate = rent.getStartDate();
        this.systemUser = rent.getSystemUser();
        this.totalCost = rent.getTotalCost();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
}
