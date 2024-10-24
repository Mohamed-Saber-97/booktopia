package org.example.booktopia.controller;

import lombok.RequiredArgsConstructor;
import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.mapper.BuyerMapper;
import org.example.booktopia.model.Buyer;
import org.example.booktopia.service.BuyerService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("RestBuyerController")
@RequiredArgsConstructor
@RequestMapping("/api/buyers")
public class BuyerController {
    private final BuyerService buyerService;
    private final BuyerMapper buyerMapper;

    @GetMapping("/{id}")
    public ResponseEntity<BuyerDto> getBuyerById(@PathVariable Long id) {
        Buyer buyer = buyerService.findById(id);
        BuyerDto buyerDto = buyerMapper.toDto(buyer);
        return ResponseEntity.ok(buyerDto);
    }

    @GetMapping("/all/{pageNumber}/{pageSize}")
    public ResponseEntity<Iterable<BuyerDto>> getAllBuyers(@PathVariable int pageNumber, @PathVariable int pageSize) {
        Page<Buyer> buyers = buyerService.findAllByPage(pageNumber, pageSize);
        Iterable<BuyerDto> buyerDtos = buyerMapper.toDto(buyers.getContent());
        return ResponseEntity.ok(buyerDtos);
    }
}
