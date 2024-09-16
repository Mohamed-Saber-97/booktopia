package mapper;

import dto.OrderDto;
import model.Order;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class OrderToOrderDto {
    public OrderDto convert(Order order) {
        return new OrderDto(
                order.getId(),
                order.getCreatedDate().atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                order.getStatus().name(),
                order.getOrderProducts().stream().filter(orderProduct -> !orderProduct.getIsDeleted()).count()
        );
    }
}
