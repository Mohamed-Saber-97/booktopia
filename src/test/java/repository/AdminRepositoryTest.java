package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Admin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class AdminRepositoryTest {


    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("booktopia");
    private final EntityManager em = emf.createEntityManager();


    @Test
    void shouldReturnNullWhenEmailDoesNotExist() {
        // Arrange
        AdminRepository adminRepository = new AdminRepository();
        String nonExistingEmail = "non_existing_email@example.com";

        // Act
        Admin admin = adminRepository.findByEmail(nonExistingEmail);

        // Assert
        assertNull(admin);
    }

}