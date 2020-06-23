package pl.lodz.p.it.carrental.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.lodz.p.it.carrental.dto.UserRegistrationDto;
import pl.lodz.p.it.carrental.misc.exceptions.EmailAlreadyTaken;
import pl.lodz.p.it.carrental.misc.exceptions.LoginAlreadyTaken;
import pl.lodz.p.it.carrental.model.users.SystemUser;
import pl.lodz.p.it.carrental.services.SystemUserService;

import javax.validation.Valid;

@Controller
@PreAuthorize("isAnonymous()")
public class AuthenticationController {

    private final SystemUserService systemUserService;
    private final ModelMapper modelMapper;

    public AuthenticationController(
            SystemUserService systemUserService,
            ModelMapper modelMapper
    ) {
        this.systemUserService = systemUserService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(required = false, name = "failed") Boolean failed,
            Model model
    ) {
        model.addAttribute("failed", failed);
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        model.addAttribute("user", userRegistrationDto);
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("user") UserRegistrationDto user,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        try {
            this.systemUserService.createStandardUser(
                    this.modelMapper.map(user, SystemUser.class)
            );
        } catch (LoginAlreadyTaken e) {
            model.addAttribute("error", "Ten login jest już zajęty.");
            model.addAttribute("user", user);
            return "register";
        } catch (EmailAlreadyTaken e) {
            model.addAttribute("error", "Ten e-mail jest już zajęty.");
            model.addAttribute("user", user);
            return "register";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            return "register";
        }
        return "redirect:/registerSuccess?user=" + user.getLogin();
    }

    @GetMapping("/registerSuccess")
    public String registeredSuccessfully(
            @RequestParam(required = false, name = "user") String user,
            Model model
    ) {
        model.addAttribute("user", user);
        return "registerSuccess";
    }
}
