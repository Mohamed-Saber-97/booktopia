package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    void testAccountConstructor() {
        assertDoesNotThrow(Account::new);
    }
}