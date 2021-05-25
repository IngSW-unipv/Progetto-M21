package it.unipv.ingsw.pickuppoint.model.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.unipv.ingsw.pickuppoint.model.User;

@Entity
@Table(name = "roles")
public class Role {

	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	/**
	 * Relazione 1:N con l'entita User
	 */
	@OneToMany(mappedBy = "role")
	private Set<User> user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}
}
