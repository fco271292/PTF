package com.fco271292.dto

class MovementDTO {
	
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
	AccountDTO account
	List<ConceptDTO> concepts
	
}
