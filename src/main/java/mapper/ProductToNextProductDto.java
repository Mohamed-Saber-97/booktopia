package mapper;

import dto.NextProductDto;
import model.Product;

public class ProductToNextProductDto {
    public NextProductDto convert(Product product) {
        return new NextProductDto(
                product.getId(),
                product.getName(),
                product.getImagePath(),
                product.getPrice()
        );
    }
}
