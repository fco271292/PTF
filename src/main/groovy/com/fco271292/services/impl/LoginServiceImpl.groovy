package com.fco271292.services.impl

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

import com.fasterxml.jackson.databind.ObjectMapper
import com.fco271292.dto.MeRSPDTO
import com.fco271292.dto.UserRQDTO
import com.fco271292.dto.UserRSPDTO
import com.fco271292.services.LoginService

import ch.qos.logback.classic.Logger

@Service
class LoginServiceImpl implements LoginService {
	
	Logger logger = LoggerFactory.getLogger(this.class)
	
	@Autowired
	Environment environment
	
	ObjectMapper objectMapper = new ObjectMapper()

	@Override
	def login() {
		
		def url = environment.getProperty("login.url")
		UserRQDTO userRQDTO = initLogin()
		UserRSPDTO userRSPDTO
		
		RestTemplate restTemplate = new RestTemplate()
		HttpEntity<UserRQDTO> request = new HttpEntity(userRQDTO)
		
		
		try {
			ResponseEntity<UserRSPDTO> response = restTemplate.exchange(url, HttpMethod.POST, request, UserRSPDTO)
			userRSPDTO = response.body
//			logger.info "${response.statusCodeValue}"
			logger.info "${'-' * 10}"
			logger.info "RQ: ${objectMapper.writeValueAsString(request?.body)}"
			logger.info "RQ: ${objectMapper.writeValueAsString(response?.body)}"
		} catch (Exception e) {
			logger.error "${e.message}"
//			throw new RuntimeException("No autorizado")
			return new ResponseEntity<>("No autorizado", HttpStatus.FORBIDDEN)
		}
		
		userRSPDTO
		
	}

	@Override
	def me() {
		
		def url = environment.getProperty("me.url")
		def userRSPDTO = login()
		
		String bearer = "${userRSPDTO?.token_type} ${userRSPDTO?.access_token}"
		def authorization = "Authorization"
		RestTemplate restTemplate = new RestTemplate()
		
		HttpHeaders httpHeaders = new HttpHeaders()
		httpHeaders.setContentType(MediaType.APPLICATION_JSON)
		httpHeaders.set(authorization, bearer)
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders)
		MeRSPDTO meRSPDTO
		try {
			ResponseEntity<MeRSPDTO> response = restTemplate.exchange(url, HttpMethod.GET, request, MeRSPDTO)
			if (response.statusCode == HttpStatus.FORBIDDEN) {
				return new ResponseEntity<>(response.statusCodeValue, HttpStatus.FORBIDDEN)
			}
			meRSPDTO = response.body
			logger.info "${'-' * 10}"
			logger.info "RQ: ${objectMapper.writeValueAsString(request?.body)}"
			logger.info "RQ: ${objectMapper.writeValueAsString(response?.body)}"
		} catch (Exception e){
			logger.error "${e.message}"
		}
		meRSPDTO
	}

	@Override
	def initLogin() {
		def username = environment.getProperty("login.username")
		def password = environment.getProperty("login.password")
		def userRQDTO = new UserRQDTO(username: username, password: password)
	}
}
