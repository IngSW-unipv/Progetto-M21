package it.unipv.ingsw.pickuppoint.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import it.unipv.ingsw.pickuppoint.model.entity.Role;
import lombok.Data;


//@Data
//@Entity
//@Table(name = "User")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="Role",discriminatorType=DiscriminatorType.STRING)
//@DiscriminatorValue(value="User")  

@Data
@Entity
@Table(name = "User")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;

	@Column(columnDefinition = "tinyint(1)")
	private boolean Enabled;
	
	@NotEmpty(message="Email cannot be empty")
	@Email(message = "Email should be valid")
	private String email;
	
	@NotEmpty(message="Password cannot be empty")
	private String password;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id", nullable=false)
	private Role role;

}