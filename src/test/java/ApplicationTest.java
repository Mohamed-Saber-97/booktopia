import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ApplicationTest {
    @Test
    void testApplicationConstructor() {
        assertDoesNotThrow(Application::new);
    }

    @Test
    void testMainMethod() {
        assertDoesNotThrow(() -> Application.main(new String[]{}));
    }
}