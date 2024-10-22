package org.example.booktopia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.repository.BuyerInterestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuyerInterestService {
    private final BuyerInterestRepository buyerInterestRepository;

    public List<Long> findCategoryIdsByBuyerId(Long buyerId) {
        return buyerInterestRepository.findCategoryIdsByBuyerId(buyerId);
    }
}
