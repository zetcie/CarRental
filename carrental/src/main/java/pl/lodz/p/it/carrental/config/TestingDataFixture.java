package pl.lodz.p.it.carrental.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.carrental.model.cars.Car;
import pl.lodz.p.it.carrental.model.cars.CarBrand;
import pl.lodz.p.it.carrental.model.cars.CarType;
import pl.lodz.p.it.carrental.model.cars.TransmissionType;
import pl.lodz.p.it.carrental.model.users.SystemRole;
import pl.lodz.p.it.carrental.model.users.SystemUser;
import pl.lodz.p.it.carrental.repositories.CarBrandRepository;
import pl.lodz.p.it.carrental.repositories.CarRepository;
import pl.lodz.p.it.carrental.repositories.SystemUserRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
public class TestingDataFixture {

    private final SystemUserRepository userRepository;
    private final CarBrandRepository carBrandRepository;
    private final CarRepository carRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TestingDataFixture(
            SystemUserRepository userRepository,
            CarBrandRepository carBrandRepository,
            CarRepository carRepository,
            PasswordEncoder passwordEncoder
    ){
        this.userRepository = userRepository;
        this.carBrandRepository = carBrandRepository;
        this.carRepository = carRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createSampleUsers() {

        SystemUser client = new SystemUser();
        client.setPassword(this.passwordEncoder.encode("client"));
        client.setEmail("client@mail.com");
        client.setLogin("client");
        client.setRole(SystemRole.CLIENT);

        SystemUser employee = new SystemUser();
        employee.setPassword(this.passwordEncoder.encode("employee"));
        employee.setEmail("employee@mail.com");
        employee.setLogin("employee");
        employee.setRole(SystemRole.EMPLOYEE);

        CarBrand carBrand1 = new CarBrand();
        carBrand1.setName("Marka #1");

        CarBrand carBrand2 = new CarBrand();
        carBrand2.setName("Marka #2");

        carBrand1 = this.carBrandRepository.save(carBrand1);
        carBrand2 = this.carBrandRepository.save(carBrand2);

        Car car1 = new Car();
        car1.setCarType(CarType.FAMILY);
        car1.setBrand(carBrand1);
        car1.setTransmissionType(TransmissionType.MANUAL);
        car1.setHasCoolingSystem(true);
        car1.setDoorsCount(2);
        car1.setSeatsCount(2);
        car1.setEnabled(true);
        car1.setDailyCost(100D);
        car1.setModel("Dobre auto");

        Car car2 = new Car();
        car2.setCarType(CarType.KOMBI);
        car2.setBrand(carBrand2);
        car2.setTransmissionType(TransmissionType.AUTOMATIC);
        car2.setHasCoolingSystem(true);
        car2.setDoorsCount(4);
        car2.setSeatsCount(4);
        car2.setEnabled(false);
        car2.setDailyCost(150D);
        car2.setModel("Gorsze auto");

        this.carRepository.save(car1);
        this.carRepository.save(car2);

        this.userRepository.saveAll(Arrays.asList(client, employee));
    }
}
