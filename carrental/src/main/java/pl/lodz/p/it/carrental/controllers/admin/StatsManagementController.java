package pl.lodz.p.it.carrental.controllers.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.lodz.p.it.carrental.dto.BasicStatsDto;
import pl.lodz.p.it.carrental.model.users.SystemRole;
import pl.lodz.p.it.carrental.services.RentService;

@Controller
@RequestMapping("admin/stats")
@PreAuthorize(SystemRole.hasRoleEmployee)
public class StatsManagementController {

    private final RentService rentService;

    public StatsManagementController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping
    public String statsView() {
        return "admin/stats";
    }

    @GetMapping("rentRequests/{days:\\d+}")
    @ResponseBody
    public ResponseEntity<BasicStatsDto> rentRequestsStatsForLastWeek(@PathVariable int days) {
        BasicStatsDto stats = this.rentService.getRentRequestsStatsForLastDays(days);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("rents/{days:\\d+}")
    @ResponseBody
    public ResponseEntity<BasicStatsDto> rentStatsForLastWeek(@PathVariable int days) {
        BasicStatsDto stats = this.rentService.getRentStatsForLastDays(days);
        return ResponseEntity.ok(stats);
    }
}
