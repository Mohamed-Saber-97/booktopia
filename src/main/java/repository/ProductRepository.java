package repository;

import base.BaseRepository;
import model.Product;

public class ProductRepository extends BaseRepository<Product, Long> {
    public ProductRepository() {
        super(Product.class);
    }
}
