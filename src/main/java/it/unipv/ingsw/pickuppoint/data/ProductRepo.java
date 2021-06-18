package it.unipv.ingsw.pickuppoint.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unipv.ingsw.pickuppoint.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

}
