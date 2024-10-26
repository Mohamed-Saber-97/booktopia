package org.example.booktopia.controller;

import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.mapper.BuyerMapper;
import org.example.booktopia.model.Buyer;
import org.example.booktopia.service.BuyerService;
import org.example.booktopia.error.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyList;

class BuyerControllerTest {

    @InjectMocks
    private BuyerController buyerController;

    @Mock
    private BuyerService buyerService;

    @Mock
    private BuyerMapper buyerMapper;

    private Buyer buyer;
    private BuyerDto buyerDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data using constructor
        buyerDto = new BuyerDto(1L, "John Doe", "john.doe@example.com", LocalDate.now(), "Developer", "1234567890", "USA", "New York", "5th Avenue", "10001");

        // Use constructor to create a Buyer instance
        buyer = new Buyer(
                null, // account (you would need to create a valid Account instance here)
                BigDecimal.valueOf(1000),
                Collections.emptySet() // interests (set it to an empty set or create valid Category instances)
        );
    }

    @Test
    void getBuyerById_shouldReturnBuyerDto() {
        when(buyerService.findById(anyLong())).thenReturn(buyer);
        when(buyerMapper.toDto(buyer)).thenReturn(buyerDto);

        ResponseEntity<BuyerDto> response = buyerController.getBuyerById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(buyerDto, response.getBody());
    }



    @Test
    void getAllBuyers_shouldReturnBuyerDtos() {
        Buyer buyer2 = new Buyer(
                null, // account
                BigDecimal.valueOf(2000),
                Collections.emptySet() // interests
        );
        BuyerDto buyerDto2 = new BuyerDto(2L, "Jane Doe", "jane.doe@example.com", LocalDate.now(), "Designer", "0987654321", "USA", "Los Angeles", "Sunset Blvd", "90001");

        // Mock the pagination response
        Page<Buyer> buyerPage = new PageImpl<>(Collections.singletonList(buyer)); // single buyer for this test
        when(buyerService.findAllByPage(anyInt(), anyInt())).thenReturn(buyerPage);

        // Use anyList() to match a List<Buyer>
        when(buyerMapper.toDto(anyList())).thenReturn(Collections.singletonList(buyerDto));

        ResponseEntity<Iterable<BuyerDto>> response = buyerController.getAllBuyers(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, ((Iterable<BuyerDto>) response.getBody()).spliterator().estimateSize());
    }



    @Test
    void getBuyerById_whenBuyerNotFound_shouldReturnNotFound() {

        when(buyerService.findById(anyLong())).thenThrow(new RecordNotFoundException("Buyer", "ID", "1"));

        assertThrows(RecordNotFoundException.class, () -> {
            buyerController.getBuyerById(1L);
        });
    }

    @Test
    void getAllBuyers_whenNoBuyers_shouldReturnEmptyList() {
        // Mock an empty page
        Page<Buyer> emptyBuyerPage = new PageImpl<>(Collections.emptyList());
        when(buyerService.findAllByPage(anyInt(), anyInt())).thenReturn(emptyBuyerPage);
        when(buyerMapper.toDto(anyList())).thenReturn(Collections.emptyList());

        ResponseEntity<Iterable<BuyerDto>> response = buyerController.getAllBuyers(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, ((Iterable<BuyerDto>) response.getBody()).spliterator().estimateSize());
    }

    @Test
    void getBuyerById_whenBuyerNotFound_shouldThrowRecordNotFoundException() {
        when(buyerService.findById(anyLong())).thenThrow(new RecordNotFoundException("Buyer", "ID", "1"));

        assertThrows(RecordNotFoundException.class, () -> {
            buyerController.getBuyerById(1L);
        });
    }



}
