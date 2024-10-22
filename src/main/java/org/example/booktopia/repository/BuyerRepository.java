package org.example.booktopia.repository;

import org.example.booktopia.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    boolean existsByAccountEmail(String email);

    boolean existsByAccountPhoneNumber(String phoneNumber);

    Optional<Buyer> findByAccountPhoneNumber(String phoneNumber);

    Optional<Buyer> findByAccountEmail(String accountEmail);

}
