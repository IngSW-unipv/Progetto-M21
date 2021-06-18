package it.unipv.ingsw.pickuppoint.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unipv.ingsw.pickuppoint.model.Recipient;

@Repository
public interface RecipientRepo extends JpaRepository<Recipient, Long> {

}
