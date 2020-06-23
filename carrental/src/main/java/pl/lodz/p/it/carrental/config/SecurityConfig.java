package pl.lodz.p.it.carrental.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.lodz.p.it.carrental.model.users.SystemRole;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/client/**")
                    .hasAnyRole(SystemRole.CLIENT.name(), SystemRole.EMPLOYEE.name())
                .antMatchers("/admin/**")
                    .hasRole(SystemRole.EMPLOYEE.name())
                .and()
                .formLogin()
                    .loginPage("/login")
                        .failureUrl("/login?failed=true")
                        .permitAll()
                    .and()
                    .logout()
                        .logoutUrl("/logout")
                        .permitAll();

        // Tylko dla potrzeb testowania aby baza H2 działała
        http
                .csrf().disable()
                .headers().frameOptions().disable();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return this.userDetailsService;
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider =
                new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(this.passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }
}
