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
import lombok.Data;

@Data
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
}
