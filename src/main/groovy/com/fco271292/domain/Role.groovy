package com.fco271292.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id
	String authority
}
