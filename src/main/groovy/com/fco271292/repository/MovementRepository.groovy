package com.fco271292.repository

import org.springframework.data.jpa.repository.JpaRepository

import com.fco271292.domain.Movement

interface MovementRepository extends JpaRepository<Movement,Long>{
	Movement findById(String id)
}
