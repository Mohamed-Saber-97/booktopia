package org.example.booktopia.error;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String entity, String identifier, String idValue) {
        super("%s with %s: %s not found.".formatted(entity, identifier, idValue));
    }
}
