package service;

import model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_found() {
        Product product = createSampleProduct(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product foundProduct = productService.findById(1L);

        assertNotNull(foundProduct);
        assertEquals(1L, foundProduct.getId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void findById_notFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Product foundProduct = productService.findById(1L);

        assertNull(foundProduct);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void findFirstX_productsFound() {
        List<Product> productList = Arrays.asList(createSampleProduct(1L), createSampleProduct(2L), createSampleProduct(3L));
        when(productRepository.findAllAvailable()).thenReturn(productList);

        List<Product> result = productService.findFirstX(2);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(productRepository, times(1)).findAllAvailable();
    }

    @Test
    void findFirstX_noProducts() {
        when(productRepository.findAllAvailable()).thenReturn(Collections.emptyList());

        List<Product> result = productService.findFirstX(2);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(productRepository, times(1)).findAllAvailable();
    }

    @Test
    void search_withQueryParameters() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "Sample Product");
        List<Product> productList = Collections.singletonList(createSampleProduct(1L));

        when(productRepository.search(queryParams, 1, 10)).thenReturn(productList);

        List<Product> result = productService.search(queryParams, 1, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).search(queryParams, 1, 10);
    }

    @Test
    void findAllAvailable() {
        List<Product> productList = Arrays.asList(createSampleProduct(1L), createSampleProduct(2L));
        when(productRepository.findAllAvailable()).thenReturn(productList);

        List<Product> result = productService.findAllAvailable();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAllAvailable();
    }

    @Test
    void saveProduct() {
        Product product = createSampleProduct(1L);
        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.save(product);

        assertNotNull(savedProduct);
        assertEquals(1L, savedProduct.getId());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void updateProduct() {
        Product product = createSampleProduct(1L);
        when(productRepository.update(product)).thenReturn(product);

        Product updatedProduct = productService.update(product);

        assertNotNull(updatedProduct);
        assertEquals(1L, updatedProduct.getId());
        verify(productRepository, times(1)).update(product);
    }

    @Test
    void existsByIsbn() {
        String isbn = "9785767562077";
        when(productRepository.existsByIsbn(isbn)).thenReturn(true);

        boolean exists = productService.existsByIsbn(isbn);

        assertTrue(exists);
        verify(productRepository, times(1)).existsByIsbn(isbn);
    }

    private Product createSampleProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("Sample Product");
        product.setAuthor("Sample Author");
        product.setReleaseDate(LocalDate.of(2023, 12, 15));
        product.setIsbn("9785767562077");
        product.setDescription("Sample Description");
        product.setPrice(new BigDecimal("29.99"));
        product.setQuantity(10);
        product.setImagePath("images/sample.jpg");
        return product;
    }
}
