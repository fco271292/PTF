package com.fco271292.services.impl

import org.codehaus.groovy.runtime.InvokerHelper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

import com.fasterxml.jackson.databind.ObjectMapper
import com.fco271292.domain.Movement
import com.fco271292.dto.MeRSPDTO
import com.fco271292.dto.MovementDTO
import com.fco271292.dto.MovementRSPDTO
import com.fco271292.dto.UserRSPDTO
import com.fco271292.repository.MovementRepository
import com.fco271292.services.LoginService
import com.fco271292.services.MovementService

import ch.qos.logback.classic.Logger

@Service
class MovementImpl implements MovementService {

	Logger logger = LoggerFactory.getLogger(this.class)
	
	@Autowired
	Environment environment
	
	@Autowired
	LoginService loginService
	
	@Autowired
	MovementRepository movementRepository
	
	ObjectMapper objectMapper = new ObjectMapper()
	
	@Override
	def movements(Integer offset, Integer max) {
		
		def url = environment.getProperty("movement.url")
		MeRSPDTO meRSPDTO = loginService.me()
		url += "${meRSPDTO?.id}/movements?deep=true&offset=${offset}&max=${max}&includeCharges=true&includeDeposits=true&includeDuplicates=true"
		
		UserRSPDTO userRSPDTO = loginService.login()
		String bearer = "${userRSPDTO.token_type} ${userRSPDTO.access_token}"
		def authorization = "Authorization"
		RestTemplate restTemplate = new RestTemplate()
		
		HttpHeaders httpHeaders = new HttpHeaders()
		httpHeaders.setContentType(MediaType.APPLICATION_JSON)
		httpHeaders.set(authorization, bearer)
		HttpEntity<String> request = new HttpEntity<String>(httpHeaders)
		MovementRSPDTO movementRSPDTO
		try {
			ResponseEntity<MovementRSPDTO> response = restTemplate.exchange(url, HttpMethod.GET, request, MovementRSPDTO)
			movementRSPDTO = response.body
			logger.info "${'-' * 10}"
			logger.info "RQ: ${objectMapper.writeValueAsString(request?.body)}"
			logger.info "RQ: ${objectMapper.writeValueAsString(response?.body)}"
			
		} catch (Exception e){
			logger.error "${e.message}"
		}
		movementRSPDTO
		
	}
	
	
	@Override
	def saveMovements(MovementRSPDTO movementRSPDTO) {
		def movements = []
		def movement
		movementRSPDTO.data.each { MovementDTO movementDTO->
			movement = movementRepository.findById(movementDTO?.id)
			if (!movement) {
				movement = new Movement(id: movementDTO.id, amount: movementDTO.amount,
					balance: movementDTO.balance, customDate: movementDTO.customDate,
					customDescription: movementDTO.customDescription, date: movementDTO.date,
					dateCreated: movementDTO.dateCreated, deleted: movementDTO.deleted,
					description: movementDTO.description, duplicated: movementDTO.duplicated,
					hasConcepts: movementDTO.hasConcepts, inResume: movementDTO.inResume,
					lastUpdated: movementDTO.lastUpdated, type: movementDTO.type)
			} else {
				try {
					InvokerHelper.setProperties(movement, movementDTO?.properties)
				} catch (Exception e){
					logger.error "${e.message}"
				}
				
			}
			try {
				movements << movementRepository.save(movement)
			} catch (Exception e) {
				logger.error "${e.message}"
			}
		}
		movements
	}

	@Override
	def saveMovements(Integer offset, Integer max) {
		MovementRSPDTO movementRSPDTO = movements(offset,max)
		def movements = []
		movements = saveMovements(movementRSPDTO)
	}


	@Override
	def runTest() {
		def offset = 0
		def max = 10
		def movements = []
		(2).times { time ->
			logger.info "# ${time}"
			movements.addAll(saveMovements(offset,max)) 
			offset += max
			max += max
		}
		movements
	}
}
