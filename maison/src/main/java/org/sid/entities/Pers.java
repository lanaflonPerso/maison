package org.sid.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Pers implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min=2, max=50)
	private String nom;
	
	@Size(max=50)
	private String prenom;
	
	@NotNull
	@Min(10)
	private int age;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="date_naissance")
	private Date dateNaissance;
	
	@Email
	private String email;
	
	private String photo;
	
	public Pers(Long id, String nom, String prenom, int age, Date dateNaissance, String email, String photo) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.dateNaissance = dateNaissance;
		this.email = email;
		this.photo = photo;
	}

	
	/*
	 * public Pers(Long id, @NotNull @Size(min = 2, max = 50) String nom, @Size(max
	 * = 50) String prenom,
	 * 
	 * @NotNull @Min(10) int age, Date dateNaissance, String email, String photo) {
	 * super(); this.id = id; this.nom = nom; this.prenom = prenom; this.age = age;
	 * this.dateNaissance = dateNaissance; this.email = email; this.photo = photo; }
	 */
	
	public Pers() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	
}
