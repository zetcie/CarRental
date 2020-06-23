package pl.lodz.p.it.carrental.controllers.client;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.carrental.dto.RentDto;
import pl.lodz.p.it.carrental.misc.exceptions.CarNotFoundException;
import pl.lodz.p.it.carrental.misc.exceptions.RentExistsForTimespan;
import pl.lodz.p.it.carrental.model.rents.Rent;
import pl.lodz.p.it.carrental.model.rents.RentRequest;
import pl.lodz.p.it.carrental.model.users.SystemUserPrincipal;
import pl.lodz.p.it.carrental.services.CarService;
import pl.lodz.p.it.carrental.services.RentService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/client/rent")
public class RentController {

    private final CarService carService;
    private final RentService rentService;
    private final ModelMapper modelMapper;

    public RentController(CarService carService,
                          RentService rentService,
                          ModelMapper modelMapper
    ) {
        this.carService = carService;
        this.rentService = rentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("{id:\\d+}")
    public String rentCarView(
            @PathVariable("id") Long id,
            Model model
    ) {
        RentDto rentDto = new RentDto();
        rentDto.setCarId(id);
        model.addAttribute("rent", rentDto);
        model.addAttribute("car", this.carService.getCarById(id));
        List<LocalDateTime> takenDays =
                this.rentService.getUnavailableDays(id)
                .stream()
                .map(r -> LocalDateTime.of(r, LocalTime.MIDNIGHT))
                .collect(Collectors.toList());
        model.addAttribute(
                "takenDays",
                takenDays
        );
        return "client/rentCar";
    }

    @GetMapping("days/{id:\\d+}")
    @ResponseBody
    public ResponseEntity<List<LocalDate>> takenDays(@PathVariable("id") Long id) {
        List<LocalDate> takenDays =
                this.rentService.getUnavailableDays(id);
        return ResponseEntity.ok().body(takenDays);
    }

    @GetMapping("rentedCar")
    public String rentedCarView() {
        return "client/rentedCar";
    }

    @GetMapping("my")
    public String myRentsView(Model model, Authentication authentication) {
        Long userId = ((SystemUserPrincipal) authentication.getPrincipal()).getUser().getId();
        List<RentRequest> rentRequests =
                this.rentService.getAllRentRequestsForUser(userId);
        List<Rent> rents =
                this.rentService.getAllRentsForUser(userId);
        model.addAttribute("requests", rentRequests);
        model.addAttribute("rents", rents);
        return "client/myRents";
    }

    @PostMapping
    public String rentCar(
            @Valid RentDto rentDto,
            Model model,
            Authentication authentication
    ) {
        model.addAttribute("rent", rentDto);
        RentRequest rentRequest = this.modelMapper.map(rentDto, RentRequest.class);
        rentRequest.setSystemUser(
                ((SystemUserPrincipal)authentication.getPrincipal()).getUser()
        );
        try {
            this.rentService.createRentRequest(rentRequest);
        } catch (RentExistsForTimespan rentExistsForTimespan) {
            rentExistsForTimespan.printStackTrace();
            model.addAttribute("error", "Już istnieje wynajęcia w tym terminie.");
            return this.rentCarView(rentDto.getCarId(), model);
        } catch (CarNotFoundException e) {
            e.printStackTrace();
            model.addAttribute("error", "Nie znaleziono podanego samochodu.");
            return this.rentCarView(rentDto.getCarId(), model);
        }
        return "redirect:/client/rent/rentedCar";
    }
}
