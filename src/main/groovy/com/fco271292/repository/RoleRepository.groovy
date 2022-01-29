package com.fco271292.repository

import org.springframework.data.jpa.repository.JpaRepository

import com.fco271292.domain.Role

interface RoleRepository extends JpaRepository<Role, Long>{
}
