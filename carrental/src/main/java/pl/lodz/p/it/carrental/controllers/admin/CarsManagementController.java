package pl.lodz.p.it.carrental.controllers.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.carrental.model.cars.Car;
import pl.lodz.p.it.carrental.model.cars.CarBrand;
import pl.lodz.p.it.carrental.model.rents.Rent;
import pl.lodz.p.it.carrental.model.users.SystemRole;
import pl.lodz.p.it.carrental.services.CarService;
import pl.lodz.p.it.carrental.services.RentService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("admin/cars")
@PreAuthorize(SystemRole.hasRoleEmployee)
public class CarsManagementController {

    private final CarService carService;
    private final RentService rentService;

    public CarsManagementController(CarService carService, RentService rentService) {
        this.carService = carService;
        this.rentService = rentService;
    }

    @GetMapping
    public String carsManagementHomepage() {
        return "admin/cars";
    }

    @GetMapping("new")
    public String addCarPage(Model model) {
        Car car = new Car();
        model.addAttribute("car", car);
        return "admin/newCar";
    }

    @PostMapping
    public String addCar(
            @Valid Car car,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/newCar";
        }
        car = this.carService.addCar(car);
        return "redirect:/admin/cars/all?added=" + car.getId();
    }

    @GetMapping("all")
    public String allCarsList(@RequestParam(required = false) Long added, Model model) {
        List<Car> allCars = this.carService.getAllCars();
        model.addAttribute("cars", allCars);
        model.addAttribute("addedCar", added);
        return "admin/carsList";
    }

    @GetMapping("{id:\\d+}")
    public String rentedForCar(@PathVariable Long id, Model model) {
        Car car = this.carService.getCarById(id);
        List<Rent> rents = this.rentService.getAllRentsForCar(id);
        model.addAttribute("car", car);
        model.addAttribute("rents", rents);
        return "admin/rentedForCar";
    }

    @PostMapping("enable")
    @ResponseBody
    public ResponseEntity enableCar(@RequestParam("id") Long id) {
        this.carService.enableCar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("disable")
    @ResponseBody
    public ResponseEntity disableCar(@RequestParam("id") Long id) {
        this.carService.disableCar(id);
        return ResponseEntity.ok().build();
    }

    @ModelAttribute("allBrands")
    public List<CarBrand> possibleBrands() {
        return this.carService.getAllBrands();
    }

    // Ewentualnie pod rozwój, przykładowo jakby te ilości mogły się zmieniać
    @ModelAttribute("possibleSeatsCount")
    public List<Integer> possibleSeatsCount() {
        return Arrays.asList(
                2, 3, 4, 5, 6, 7
        );
    }

}
