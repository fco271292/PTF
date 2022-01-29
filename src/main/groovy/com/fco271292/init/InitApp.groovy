package com.fco271292.init

import javax.annotation.PostConstruct

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

import com.fco271292.domain.User
import com.fco271292.repository.RoleRepository
import com.fco271292.repository.UserRepository
import com.fco271292.services.UserService

import ch.qos.logback.classic.Logger

@Component
class InitApp {
	
	Logger logger = LoggerFactory.getLogger(this.class)
	
	@Autowired
	UserRepository userRepository
	
	@Autowired
	RoleRepository roleRepository
	
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder()
	
	@Autowired
	UserService userService
	
	@Autowired
	Environment environment
	
	@PostConstruct
	def init() {
		def username = environment.getProperty("user.username")
		def password = environment.getProperty("user.password")
//		logger.info "INIT ---> ${username} ${password}"
		try {
			if (userRepository.count()) {
				userRepository.deleteAllInBatch()
			}
			if (roleRepository.count()) {
				roleRepository.deleteAllInBatch()
			}
			
		} catch (Exception e) {
			logger.error "${e.message}"
		}
		def user = new User(username: username, password: password)
		userService.save(user)
		
	}
}
