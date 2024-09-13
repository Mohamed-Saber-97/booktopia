package dto;

public record OrderDto(
        Long id,
        String createdDate,
        String status,
        Long numberOfProducts
) {

}
