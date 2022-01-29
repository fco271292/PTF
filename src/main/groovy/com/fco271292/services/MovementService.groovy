package com.fco271292.services

import com.fco271292.dto.MovementRSPDTO

interface MovementService {
	
	def movements(Integer offset, Integer max)
	def saveMovements(Integer offset, Integer max)
	def saveMovements(MovementRSPDTO movementRSPDTO)
	def runTest()
	
}
