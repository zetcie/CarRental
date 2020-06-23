package pl.lodz.p.it.carrental.controllers.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.carrental.model.users.SystemRole;
import pl.lodz.p.it.carrental.model.users.SystemUserPrincipal;
import pl.lodz.p.it.carrental.services.SystemUserService;

@Controller
@RequestMapping("admin/users")
@PreAuthorize(SystemRole.hasRoleEmployee)
public class UsersManagementController {

    private final SystemUserService usersService;

    public UsersManagementController(SystemUserService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public String usersListView(Model model, Authentication authentication) {
        model.addAttribute("users", this.usersService.getAllUsers());
        model.addAttribute(
                "currentUser",
                ((SystemUserPrincipal) authentication.getPrincipal()).getUser().getId()
        );
        return "admin/users";
    }

    @PostMapping("enable")
    @ResponseBody
    public ResponseEntity enableCar(@RequestParam("id") Long id) {
        this.usersService.enableUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("disable")
    @ResponseBody
    public ResponseEntity disableCar(@RequestParam("id") Long id) {
        this.usersService.disableUser(id);
        return ResponseEntity.ok().build();
    }

}
