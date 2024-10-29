package org.example.booktopia.config;

import lombok.RequiredArgsConstructor;
import org.example.booktopia.model.CartItem;
import org.example.booktopia.service.CartItemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableAspectJAutoProxy
@EnableJpaAuditing(auditorAwareRef = "AuditAwareImplementation")
@RequiredArgsConstructor
public class WebConfig {

}