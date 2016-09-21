package enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority{
	ROLE_USER;

    @Override
    public String getAuthority() {        
        return name();
    }
}
