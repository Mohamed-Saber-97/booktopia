package repository;

import model.Admin;
import model.Buyer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AdminRepositoryTest {


    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("booktopia");
    private EntityManager em = emf.createEntityManager();


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