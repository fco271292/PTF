package com.fco271292.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import com.fco271292.services.LoginService
import com.fco271292.services.MovementService

@RestController
@RequestMapping(path="/ptf")
class PFT {
	
	@Autowired
	LoginService loginService
	
	@Autowired
	MovementService movementService
	
	@GetMapping(path="/login")
	def login() {
		loginService.login()
	}
	
	@GetMapping(path="/me")
	def me() {
		loginService.me()
	}
	
	@GetMapping(path="/movement")
	def movement(@RequestParam(name='offset') Integer offset,
		@RequestParam(name='max') Integer max) {
		movementService.movements(offset,max)
	}
	
	@GetMapping(path="/saveMovement")
	def saveMovement(@RequestParam(name='offset') Integer offset,
		@RequestParam(name='max') Integer max) {
		movementService.saveMovements(offset,max)
	}
	
	
}
