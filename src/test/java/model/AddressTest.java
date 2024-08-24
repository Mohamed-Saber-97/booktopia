package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    @Test
    void testAddressConstructor() {
        assertDoesNotThrow(Address::new);
    }
}