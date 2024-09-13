package service;

import model.Address;
import model.Admin;
import model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.AdminRepository;

import java.time.LocalDate;
import java.util.Optional;
import model.Account;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testUpdateAdmin_Failure_AdminNotFound() {
        Admin newAdmin = new Admin();
        newAdmin.setId(1L);

        when(adminRepository.findById(1L)).thenReturn(Optional.empty());

        Admin updatedAdmin = adminService.update(newAdmin);

        assertNull(updatedAdmin);
    }



    @Test
    void testCheckValidLoginCredentials_Failure_InvalidPassword() {
        Admin admin = new Admin();
        admin.setAccount(new Account("John Doe", LocalDate.of(1990, 1, 1), "password123", "Developer", "john.doe@example.com", "0123456789", new Address()));

        when(adminRepository.findByEmail("john.doe@example.com")).thenReturn(admin);

        boolean isValid = adminService.checkValidLoginCredentials("john.doe@example.com", "wrongpassword");

        assertFalse(isValid);
    }

    @Test
    void testCheckValidLoginCredentials_Failure_AdminNotFound() {
        when(adminRepository.findByEmail("john.doe@example.com")).thenReturn(null);

        boolean isValid = adminService.checkValidLoginCredentials("john.doe@example.com", "password123");

        assertFalse(isValid);
    }



    @Test
    void testFindByEmail_Failure() {
        when(adminRepository.findByEmail("john.doe@example.com")).thenReturn(null);

        Admin foundAdmin = adminService.findByEmail("john.doe@example.com");

        assertNull(foundAdmin);
    }
}
