package com.fco271292.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Movement {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long idMovement
	
	@Column(name="id_movement")
	String id
	Double amount
	Double balance
	String customDate
	String customDescription
	Date date
	Date dateCreated
	Boolean deleted
	String description
	Boolean duplicated
	Boolean hasConcepts
	Boolean inResume
	Date lastUpdated
	String type
	
}
