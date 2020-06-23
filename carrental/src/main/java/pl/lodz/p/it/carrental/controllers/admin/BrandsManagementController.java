package pl.lodz.p.it.carrental.controllers.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.lodz.p.it.carrental.model.cars.CarBrand;
import pl.lodz.p.it.carrental.model.users.SystemRole;
import pl.lodz.p.it.carrental.services.CarService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("admin/brands")
@PreAuthorize(SystemRole.hasRoleEmployee)
public class BrandsManagementController {

    private final CarService carService;

    public BrandsManagementController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public String allBrandsView(
            @RequestParam(required = false) Long added,
            Model model
    ) {
        model.addAttribute("newBrand", added);
        List<CarBrand> allBrands = this.carService.getAllBrands();
        model.addAttribute("brands", allBrands);
        return "admin/brandsList";
    }

    @GetMapping("new")
    public String newBrandView(Model model) {
        CarBrand carBrand = new CarBrand();
        model.addAttribute("brand", carBrand);
        return "admin/newBrand";
    }


    @PostMapping
    public String newBrandCreate(
            @Valid CarBrand carBrand,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/newBrand";
        }
        CarBrand brand = this.carService.createBrand(carBrand);
        return "redirect:/admin/brands?added=" + brand.getId();
    }

}
