package com.fco271292.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User as UserUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

import com.fco271292.domain.Role
import com.fco271292.domain.User

@Service
class UserDetailService implements UserDetailsService{
	
	@Autowired
	UserService userService

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username)
		def authorities = getUserAuthority(user.roles)
		buildUserForAuthentication(user, authorities)
	}
	
	def getUserAuthority(Set<Role> userRoles) {
		def roles = new HashSet<GrantedAuthority>()
		userRoles.each { userRole ->
			roles << new SimpleGrantedAuthority(userRole.authority)
		}
		def grantedAuthorities = new ArrayList(roles)
	}
	
	UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		new UserUserDetails(user.username,user.password,true,true,true,true,authorities)
	}
}
