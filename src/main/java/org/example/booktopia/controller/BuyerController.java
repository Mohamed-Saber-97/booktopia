package org.example.booktopia.controller;

import lombok.RequiredArgsConstructor;
import org.example.booktopia.service.BuyerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buyers")
public class BuyerController {
    private final BuyerService buyerService;

//    @GetMapping
//    public List<Buyer> getAllBuyers() {
//        return buyerService.();
//    }
//
//    @GetMapping("/{id}")
//    public Buyer getBuyerById(@PathVariable Long id) {
//        return buyerService.findById(id);
//    }
}
