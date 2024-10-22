package org.example.booktopia.repository;

import org.example.booktopia.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByAccountEmailAndIsDeletedIsFalse(String email);

    Optional<Admin> findByAccountPhoneNumberAndIsDeletedIsFalse(String phoneNumber);
}
