package org.example.booktopia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.repository.BuyerInterestRepository;
import org.example.booktopia.repository.BuyerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuyerInterestService {
    private final BuyerInterestRepository buyerInterestRepository;
    private final BuyerRepository buyerRepository;

    public List<Long> findCategoryIdsByBuyerId(Long buyerId) {
        buyerRepository.findById(buyerId).orElseThrow(() -> new RecordNotFoundException("Buyer", "ID", buyerId.toString()));
        return buyerInterestRepository.findCategoryIdsByBuyerId(buyerId);
    }
}
