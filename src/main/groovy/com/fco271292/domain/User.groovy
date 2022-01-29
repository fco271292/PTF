package com.fco271292.domain

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Entity
class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id
	String username
	String password
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name="user_role", 
		joinColumns = @JoinColumn(name="user_id"), 
		inverseJoinColumns=@JoinColumn(name="role_id"))
	Set<Role> roles
	
}
