package pl.lodz.p.it.carrental.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.lodz.p.it.carrental.model.cars.Car;
import pl.lodz.p.it.carrental.model.cars.CarBrand;
import pl.lodz.p.it.carrental.model.cars.CarType;
import pl.lodz.p.it.carrental.model.cars.TransmissionType;
import pl.lodz.p.it.carrental.services.CarService;

import java.util.List;

@Controller
@RequestMapping("cars")
public class CarsController {

    private final CarService carService;

    public CarsController(
            CarService carService
    ) {
        this.carService = carService;
    }

    @GetMapping
    public String search(
            @RequestParam(required = false) Integer seatsCount,
            @RequestParam(required = false) Integer doorsCount,
            @RequestParam(required = false) Boolean hasCoolingSystem,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) CarType carType,
            @RequestParam(required = false) TransmissionType transmissionType,
            Model model
    ) {
        Car car = getCar(seatsCount, doorsCount, hasCoolingSystem, brandId, carType, transmissionType);
        List<Car> cars = this.carService.getAllCarsFiltered(car);
        model.addAttribute("cars", cars);
        return "cars/searchResult";
    }

    private Car getCar(
            Integer seatsCount,
            Integer doorsCount,
            Boolean hasCoolingSystem,
            Long brandId,
            CarType carType,
            TransmissionType transmissionType
    ) {
        Car car = new Car();
        car.setSeatsCount(seatsCount);
        car.setDoorsCount(doorsCount);
        car.setHasCoolingSystem(hasCoolingSystem);
        car.setTransmissionType(transmissionType);
        CarBrand carBrand = new CarBrand();
        carBrand.setId(brandId);
        car.setBrand(carBrand);
        car.setCarType(carType);
        return car;
    }

}
