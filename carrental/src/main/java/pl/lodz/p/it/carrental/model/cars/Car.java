package pl.lodz.p.it.carrental.model.cars;

import pl.lodz.p.it.carrental.model.TimestampedAbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Car extends TimestampedAbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Integer seatsCount;

    @Column(nullable = false, updatable = false)
    private Integer doorsCount;

    @Column(nullable = false)
    private Boolean hasCoolingSystem;

    @NotNull
    @ManyToOne(optional = false)
    private CarBrand brand;

    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String model;

    @NotNull
    @Column(nullable = false)
    private TransmissionType transmissionType = TransmissionType.MANUAL;

    @NotNull
    @Column(nullable = false)
    private CarType carType;

    @NotNull
    @Column(nullable = false)
    private Boolean enabled = true;

    @NotNull
    @Column(nullable = false)
    @Min(0)
    private Double dailyCost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeatsCount() {
        return seatsCount;
    }

    public void setSeatsCount(Integer seatsCount) {
        this.seatsCount = seatsCount;
    }

    public Integer getDoorsCount() {
        return doorsCount;
    }

    public void setDoorsCount(Integer doorsCount) {
        this.doorsCount = doorsCount;
    }

    public Boolean getHasCoolingSystem() {
        return hasCoolingSystem;
    }

    public void setHasCoolingSystem(Boolean hasCoolingSystem) {
        this.hasCoolingSystem = hasCoolingSystem;
    }

    public CarBrand getBrand() {
        return brand;
    }

    public void setBrand(CarBrand brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Double getDailyCost() {
        return dailyCost;
    }

    public void setDailyCost(Double dailyCost) {
        this.dailyCost = dailyCost;
    }
}
