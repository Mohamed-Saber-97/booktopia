package org.example.booktopia.config;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(value = "AuditAwareImplementation")
@Primary
public class AuditAwareImplementation implements AuditorAware<String> {
    @Override

    public Optional<String> getCurrentAuditor() {
        //Username from spring security should be used here
        return Optional.empty();
    }
}
