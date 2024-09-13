package utils;

import com.google.gson.Gson;
import dto.BuyerDto;
import dto.NextProductDto;
import dto.OrderDto;
import mapper.BuyerToBuyerDto;
import mapper.OrderToOrderDto;
import mapper.ProductToNextProductDto;
import model.Buyer;
import model.Order;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public class Jsonify {
    private Jsonify() {
    }

    public static String jsonifyOrders(List<Order> orders) {
        List<OrderDto> orderDtos = new ArrayList<>();
        OrderToOrderDto orderToOrderDto = new OrderToOrderDto();
        for (int i = 0; i < orders.size(); i++) {
            orderDtos.add(orderToOrderDto.convert(orders.get(i)));
        }
        return new Gson().toJson(orderDtos);
    }

    public static String jsonifyBuyers(List<Buyer> buyers) {
        List<BuyerDto> buyerDtos = new ArrayList<>();
        BuyerToBuyerDto buyerToBuyerDto = new BuyerToBuyerDto();
        for (int i = 0; i < buyers.size(); i++) {
            buyerDtos.add(buyerToBuyerDto.convert(buyers.get(i)));
        }
        return new Gson().toJson(buyerDtos);
    }

    public static String jsonifyProducts(List<Product> products) {
        List<NextProductDto> nextProductDtos = new ArrayList<>();
        ProductToNextProductDto productToNextProductDto = new ProductToNextProductDto();
        for (int i = 0; i < products.size(); i++) {
            nextProductDtos.add(productToNextProductDto.convert(products.get(i)));
        }
        return new Gson().toJson(nextProductDtos);
    }
}
