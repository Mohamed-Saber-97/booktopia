package mapper;

import dto.BuyerDto;
import model.Buyer;

public class BuyerToBuyerDto {
    public BuyerDto convert(Buyer buyer) {
        return new BuyerDto(
                buyer.getId(),
                buyer.getAccount().getName(),
                buyer.getAccount().getBirthday().toString(),
                buyer.getAccount().getJob(),
                buyer.getAccount().getEmail(),
                buyer.getCreditLimit(),
                buyer.getAccount().getPhoneNumber(),
                buyer.getAccount().getAddress().getCountry(),
                buyer.getAccount().getAddress().getCity(),
                buyer.getAccount().getAddress().getStreet(),
                buyer.getAccount().getAddress().getZipcode()
        );
    }
}
