package org.example.booktopia.repository;

import org.example.booktopia.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByAccountEmailAndIsDeletedIsFalse(String email);

    Optional<Admin> findByAccountPhoneNumberAndIsDeletedIsFalse(String phoneNumber);

    Optional<Admin> findByAccount_EmailAndAccount_PasswordAndIsDeletedFalse(@NonNull String email,
                                                                            @NonNull String password);
}

