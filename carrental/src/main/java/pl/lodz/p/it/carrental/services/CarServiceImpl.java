package pl.lodz.p.it.carrental.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.carrental.model.cars.Car;
import pl.lodz.p.it.carrental.model.cars.CarBrand;
import pl.lodz.p.it.carrental.repositories.CarBrandRepository;
import pl.lodz.p.it.carrental.repositories.CarRepository;
import pl.lodz.p.it.carrental.repositories.RentRepository;

import java.util.List;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final CarBrandRepository carBrandRepository;
    private final CarRepository carRepository;
    private final RentRepository rentRepository;
    private final ModelMapper modelMapper;

    public CarServiceImpl(
            CarBrandRepository carBrandRepository,
            CarRepository carRepository,
            RentRepository rentRepository,
            ModelMapper modelMapper
    ) {
        this.carBrandRepository = carBrandRepository;
        this.carRepository = carRepository;
        this.rentRepository = rentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CarBrand> getAllBrands() {
        return this.carBrandRepository.findAll();
    }

    @Override
    public List<Car> getAllCarsFiltered(Car car) {
        car.setEnabled(true);
        return this.carRepository.findAll(
                Example.of(car)
        );

    }

    @Override
    public List<Car> getAllCars() {
        return this.carRepository.findAll();
    }

    @Override
    public Car addCar(Car car) {
        return this.carRepository.save(car);
    }

    @Override
    public CarBrand createBrand(CarBrand carBrand) {
        return this.carBrandRepository.save(carBrand);
    }

    @Override
    public void disableCar(Long id) {
        this.carRepository.findById(id).ifPresent(
                (car) -> {
                    car.setEnabled(false);
                    this.carRepository.save(car);
                }
        );
    }

    @Override
    public void enableCar(Long id) {
        this.carRepository.findById(id).ifPresent(
                (car) -> {
                    car.setEnabled(true);
                    this.carRepository.save(car);
                }
        );
    }

    @Override
    public Car getCarById(Long id) {
        return this.carRepository.findById(id).orElse(null);
    }
}
