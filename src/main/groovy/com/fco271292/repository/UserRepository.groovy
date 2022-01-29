package com.fco271292.repository

import org.springframework.data.jpa.repository.JpaRepository

import com.fco271292.domain.User

interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username)
}
