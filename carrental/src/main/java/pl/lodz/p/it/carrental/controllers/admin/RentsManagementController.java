package pl.lodz.p.it.carrental.controllers.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.carrental.model.rents.RentRequest;
import pl.lodz.p.it.carrental.model.users.SystemRole;
import pl.lodz.p.it.carrental.services.RentService;

import java.util.List;

@Controller
@RequestMapping("admin/rents")
@PreAuthorize(SystemRole.hasRoleEmployee)
public class RentsManagementController {

    private final RentService rentService;

    public RentsManagementController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping
    public String awaitingRequestsView() {
        return "admin/rentRequests";
    }

    @ModelAttribute("requests")
    public List<RentRequest> rentRequestList() {
        return this.rentService.getAllRentRequests();
    }

    @PostMapping("accept")
    @ResponseBody
    public ResponseEntity acceptRequest(@RequestParam Long id) {
        this.rentService.acceptRequest(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("reject")
    @ResponseBody
    public ResponseEntity rejectRequest(@RequestParam Long id) {
        this.rentService.rejectRequest(id);
        return ResponseEntity.ok().build();
    }

}
