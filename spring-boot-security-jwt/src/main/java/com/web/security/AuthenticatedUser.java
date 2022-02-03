package com.web.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.web.mode.User;



public class AuthenticatedUser extends org.springframework.security.core.userdetails.User {
	private static Logger logger = LogManager.getLogger(AuthenticatedUser.class);
	private static final long serialVersionUID = 1L;
	private User user;

	public AuthenticatedUser(User user) {
		super(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user));
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		Set<String> roleAndPermissions = new HashSet<>();
		/*List<Role> roles = user.getRole();
		for (Role role : roles) {
			logger.info("role::" + role.getName());
			roleAndPermissions.add(role.getName());
		}*/
		roleAndPermissions.add(user.getRoles().stream().findFirst().get().getName().toString());
		String[] roleNames = new String[roleAndPermissions.size()];
		Collection<GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList(roleAndPermissions.toArray(roleNames));
		return authorities;
	}
}
