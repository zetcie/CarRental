package pl.lodz.p.it.carrental.model.users;

import org.springframework.security.core.GrantedAuthority;

public enum SystemRole implements GrantedAuthority {
    CLIENT, EMPLOYEE;

    public static final String hasRoleEmployee = "hasRole('ROLE_EMPLOYEE')";

    public static final String hasRoleClient = "hasRole('ROLE_CLIENT')";

    public String nameWithPrefix() {
        return "ROLE_" + this.name();
    }

    @Override
    public String getAuthority() {
        return this.nameWithPrefix();
    }
}
