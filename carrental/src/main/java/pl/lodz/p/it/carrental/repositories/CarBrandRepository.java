package pl.lodz.p.it.carrental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.carrental.model.cars.CarBrand;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, Long> {
}
