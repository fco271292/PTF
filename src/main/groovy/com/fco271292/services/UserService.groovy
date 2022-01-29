package com.fco271292.services

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

import com.fco271292.domain.Role
import com.fco271292.domain.User
import com.fco271292.repository.RoleRepository
import com.fco271292.repository.UserRepository

import ch.qos.logback.classic.Logger

@Service
class UserService {
	
	Logger logger = LoggerFactory.getLogger(this.class)
	
	@Autowired
	UserRepository userRepository
	
	@Autowired
	RoleRepository roleRepository
	
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder()
	
	User findByUsername(String username) {
		userRepository.findByUsername(username)
	}
	
	User save(User user) {
		def rsp
		try {
			rsp = userRepository.findByUsername(user.username)
			if (!rsp) {
				def passwordEncode = bCryptPasswordEncoder.encode(user.password)
				user = new User(username: user.username, password: passwordEncode)
				def role = new Role(authority: "ROLE_ADMIN")
				user.roles = []
				user.roles.add(role)
				userRepository.save(user)
			}
		} catch (Exception e) {
			logger.error "INIT ${e.message}"
		}
		rsp
	}
	
}
