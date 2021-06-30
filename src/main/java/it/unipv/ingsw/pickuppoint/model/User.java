package it.unipv.ingsw.pickuppoint.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * 
 * Comportamento di default caricamento oggetti Entity relazionati OneToOne:
 * EAGER ManyToOne: EAGER OneToMany: LAZY ManyToMany: LAZY
 *
 */

@Entity
@Table(name = "User")
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(columnDefinition = "tinyint(1)")
	private boolean Enabled;
	
	private String registrationDate;

	@NotEmpty(message = "Email cannot be empty")
	@Email(message = "Email should be valid")
	private String email;

	@NotEmpty(message = "Password cannot be empty")
	private String password;

	@NotEmpty(message = "First name cannot be empty")
	private String firstName;
	
	@NotEmpty(message = "Surname cannot be empty")
	private String lastName;

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	/**
	 * Relazione 1:N con l'entità OrderDetails
	 */
	@OneToMany(mappedBy = "courier", cascade = CascadeType.ALL)
	private List<OrderDetails> orderDetailsCourier;

	/**
	 * Relazione 1:N con l'entità OrderDetails
	 */
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<OrderDetails> orderDetailsCustomer;

	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId=userId;
	}

	public boolean isEnabled() {
		return Enabled;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public void setEnabled(boolean enabled) {
		Enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<OrderDetails> getOrderDetailsCourier() {
		return orderDetailsCourier;
	}

	public void setOrderDetailsCourier(List<OrderDetails> orderDetailsCourier) {
		this.orderDetailsCourier = orderDetailsCourier;
	}

	public List<OrderDetails> getOrderDetailsCustomer() {
		return orderDetailsCustomer;
	}

	public void setOrderDetailsCustomer(List<OrderDetails> orderDetailsCustomer) {
		this.orderDetailsCustomer = orderDetailsCustomer;
	}
}