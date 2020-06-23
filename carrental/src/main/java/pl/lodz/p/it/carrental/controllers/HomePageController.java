package pl.lodz.p.it.carrental.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.lodz.p.it.carrental.model.cars.CarBrand;
import pl.lodz.p.it.carrental.services.CarService;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomePageController {

    private final CarService carService;

    public HomePageController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @ModelAttribute("brands")
    public List<CarBrand> possibleBrands() {
        return this.carService.getAllBrands();
    }

    @ModelAttribute("possibleSeatsCount")
    public List<Integer> possibleSeatsCount() {
        return Arrays.asList(
          2, 3, 4, 5, 6, 7
        );
    }
}
