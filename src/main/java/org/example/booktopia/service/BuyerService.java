package org.example.booktopia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.repository.BuyerRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuyerService {
    private final BuyerRepository buyerRepository;

}
