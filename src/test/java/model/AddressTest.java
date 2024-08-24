package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    @Test
    void testApplicationConstructor() {
        assertDoesNotThrow(Address::new);
    }

}