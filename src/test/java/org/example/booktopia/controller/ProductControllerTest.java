package org.example.booktopia.controller;

import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        List<ProductDto> productDtos = List.of(new ProductDto(1L, "Product1", "Description1", "Author1", "ISBN1", LocalDate.now(), 1L, BigDecimal.TEN, 5, "/image1.jpg"));

        when(productService.findAll()).thenReturn(productDtos);

        ResponseEntity<List<ProductDto>> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDtos, response.getBody());
    }

    @Test
    void testGetAllAvailableProducts() {
        List<ProductDto> productDtos = List.of(new ProductDto(2L, "Product2", "Description2", "Author2", "ISBN2", LocalDate.now(), 2L, BigDecimal.TEN, 3, "/image2.jpg"));

        when(productService.findAllAvailable()).thenReturn(productDtos);

        ResponseEntity<List<ProductDto>> response = productController.getAllAvailableProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDtos, response.getBody());
    }

    @Test
    void testGetFirstProducts() {
        List<ProductDto> productDtos = List.of(new ProductDto(3L, "Product3", "Description3", "Author3", "ISBN3", LocalDate.now(), 3L, BigDecimal.TEN, 7, "/image3.jpg"));

        when(productService.findFirst(3)).thenReturn(productDtos);

        ResponseEntity<List<ProductDto>> response = productController.getFirstProducts(3);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDtos, response.getBody());
    }

    @Test
    void testGetProductByID() {
        Long productId = 4L;
        ProductDto productDto = new ProductDto(productId, "Product4", "Description4", "Author4", "ISBN4", LocalDate.now(), 4L, BigDecimal.TEN, 8, "/image4.jpg");

        when(productService.findProductById(productId)).thenReturn(productDto);

        ResponseEntity<ProductDto> response = productController.getProductByID(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDto, response.getBody());
    }

    @Test
    void testGetProductByName() {
        String name = "Product5";
        ProductDto productDto = new ProductDto(5L, name, "Description5", "Author5", "ISBN5", LocalDate.now(), 5L, BigDecimal.TEN, 9, "/image5.jpg");

        when(productService.findByName(name)).thenReturn(productDto);

        ResponseEntity<ProductDto> response = productController.getProductByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDto, response.getBody());
    }

    @Test
    void testGetProductByIsbn() {
        String isbn = "ISBN6";
        ProductDto productDto = new ProductDto(6L, "Product6", "Description6", "Author6", isbn, LocalDate.now(), 6L, BigDecimal.TEN, 10, "/image6.jpg");

        when(productService.findByIsbn(isbn)).thenReturn(productDto);

        ResponseEntity<ProductDto> response = productController.getProductByIsbn(isbn);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDto, response.getBody());
    }

    @Test
    void testSaveProduct() {
        ProductDto productDto = new ProductDto(null, "Product7", "Description7", "Author7", "ISBN7", LocalDate.now(), 7L, BigDecimal.TEN, 11, "/image7.jpg");
        ProductDto savedProductDto = new ProductDto(7L, "Product7", "Description7", "Author7", "ISBN7", LocalDate.now(), 7L, BigDecimal.TEN, 11, "/image7.jpg");

        when(productService.save(productDto)).thenReturn(savedProductDto);

        ResponseEntity<ProductDto> response = productController.saveProduct(productDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedProductDto, response.getBody());
    }

    @Test
    void testUpdateProduct() {
        Long productId = 8L;
        ProductDto productDto = new ProductDto(productId, "Updated Product8", "Updated Description8", "Updated Author8", "ISBN8", LocalDate.now(), 8L, BigDecimal.TEN, 12, "/image8.jpg");

        when(productService.update(productId, productDto)).thenReturn(productDto);

        ResponseEntity<ProductDto> response = productController.updateProduct(productId, productDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDto, response.getBody());
    }

    @Test
    void testDeleteProduct() {
        Long productId = 9L;

        doNothing().when(productService).deleteById(productId);

        ResponseEntity<ProductDto> response = productController.deleteProduct(productId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deleteById(productId);
    }
}
