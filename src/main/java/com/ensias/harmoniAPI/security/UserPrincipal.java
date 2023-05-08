package com.ensias.harmoniAPI.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ensias.harmoniAPI.model.User;

public class UserPrincipal implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1582310928090644240L;
	private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }
    
    public String getId() {
    	return user.getId();
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.<GrantedAuthority>singletonList(new SimpleGrantedAuthority("User"));
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

    
}
