package org.example.booktopia.repository;

import org.example.booktopia.model.Buyer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    boolean existsByAccountEmail(String email);

    boolean existsByAccountPhoneNumber(String phoneNumber);

    Optional<Buyer> findByAccountPhoneNumber(String phoneNumber);

    Optional<Buyer> findByAccountEmail(String accountEmail);

    Page<Buyer> findAllByIsDeletedIsFalse(Pageable pageable);

    Optional<Buyer> findByAccount_EmailAndAccount_PasswordAndIsDeletedFalse(@NonNull String email,
                                                                            @NonNull String password);
}
