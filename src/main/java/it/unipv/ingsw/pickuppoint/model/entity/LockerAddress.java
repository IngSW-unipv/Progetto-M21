package it.unipv.ingsw.pickuppoint.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "LockerAddress")
public class LockerAddress {

	@Id
	@Column(name = "locker_id")
	private Long lockerId;

	private String city;
	private String address;
	
	@Column(nullable = true)
	private int postalCode;

	/**
	 * Associazione 1:1 con l'entita Locker, chiave primaria condivisa
	 */
	@OneToOne
	@MapsId
	@JoinColumn(name = "locker_id")
	private Locker locker;

}
