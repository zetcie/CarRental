package pl.lodz.p.it.carrental.dto;

import pl.lodz.p.it.carrental.misc.PasswordAndRePasswordProvider;
import pl.lodz.p.it.carrental.misc.PasswordsMatching;

import javax.validation.constraints.NotBlank;

@PasswordsMatching
public class UserRegistrationDto implements PasswordAndRePasswordProvider {

    @NotBlank(message = "{validation.login.not_blank}")
    private String login;

    @NotBlank(message = "{validation.password.not_blank}")
    private String password;

    @NotBlank(message = "{validation.rePassword.not_blank}")
    private String rePassword;

    @NotBlank(message = "{validation.email.not_blank}")
    private String email;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
