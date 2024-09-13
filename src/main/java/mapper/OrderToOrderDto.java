package mapper;

import dto.OrderDto;
import model.Order;

public class OrderToOrderDto {
    public OrderDto convert(Order order) {
        return new OrderDto(
                order.getId(),
                order.getCreatedDate().toString(),
                order.getStatus().name(),
                order.getOrderProducts().stream().filter(orderProduct -> !orderProduct.getIsDeleted()).count()
        );
    }
}
