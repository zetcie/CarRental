package pl.lodz.p.it.carrental.services;

import pl.lodz.p.it.carrental.model.cars.Car;
import pl.lodz.p.it.carrental.model.cars.CarBrand;

import java.util.List;

public interface CarService {
    List<CarBrand> getAllBrands();

    List<Car> getAllCarsFiltered(Car car);

    List<Car> getAllCars();

    Car addCar(Car car);

    CarBrand createBrand(CarBrand carBrand);

    void disableCar(Long id);

    void enableCar(Long id);

    Car getCarById(Long id);
}
