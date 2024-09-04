package utils;

import jakarta.servlet.http.HttpServletRequest;
import model.Account;
import model.Address;
import model.Admin;
import model.Buyer;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RequestBuilderUtil {

    private RequestBuilderUtil() {

    }

    public static Buyer buildBuyerFromRequest(HttpServletRequest request) {
        Buyer buyer = new Buyer();
        Account account = buildAccountFromRequest(request);
        buyer.setAccount(account);
        buyer.setCreditLimit(BigDecimal.valueOf(Double.parseDouble(request.getParameter("creditLimit"))));
        return buyer;
    }

    public static Admin buildAdminFromRequest(HttpServletRequest request) {
        Admin admin = new Admin();
        Account account = buildAccountFromRequest(request);
        admin.setAccount(account);
        return admin;
    }

    private static Account buildAccountFromRequest(HttpServletRequest request) {
        Account account = new Account();
        Address address = new Address();

        account.setName(request.getParameter("name"));
        account.setBirthday(LocalDate.parse(request.getParameter("birthday")));
        account.setJob(request.getParameter("job"));
        account.setEmail(request.getParameter("email"));
        account.setPassword(request.getParameter("password"));
        account.setPhoneNumber(request.getParameter("phoneNumber"));

        address.setCountry(request.getParameter("country"));
        address.setCity(request.getParameter("city"));
        address.setStreet(request.getParameter("street"));
        address.setZipcode(request.getParameter("zipcode"));

        account.setAddress(address);
        return account;
    }
}
