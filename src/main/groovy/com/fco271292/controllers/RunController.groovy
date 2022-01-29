package com.fco271292.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.fco271292.services.MovementService

@RestController
@RequestMapping(path="/")
class RunController {
	
	@Autowired
	MovementService movementService
	
	@GetMapping(path="/runTest")
	def runTest() {
		movementService.runTest()
	}
	
}
