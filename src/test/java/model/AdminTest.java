package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {
    @Test
    void testAdminConstructor() {
        assertDoesNotThrow(Admin::new);
    }
}