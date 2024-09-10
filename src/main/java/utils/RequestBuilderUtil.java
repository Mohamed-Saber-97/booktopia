package utils;

import jakarta.servlet.ServletRequest;
import model.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static utils.RequestAttributeUtil.PRODUCT;
import static utils.RequestAttributeUtil.USER;
import static utils.RequestParameterUtil.*;

public class RequestBuilderUtil {

    private RequestBuilderUtil() {

    }

    public static Buyer createBuyerFromRequest(ServletRequest request) {
        Buyer buyer = new Buyer();
        Account account = buildAccountFromRequest(request);
        buyer.setAccount(account);
        buyer.setCreditLimit(BigDecimal.valueOf(Double.parseDouble(request.getParameter(CREDIT_LIMIT))));
        return buyer;
    }

    public static Buyer updateBuyerFromRequest(ServletRequest request) {
        Buyer buyer = (Buyer) request.getAttribute(USER);
        Account account = buildAccountFromRequest(request);
        buyer.setAccount(account);
        buyer.setCreditLimit(BigDecimal.valueOf(Double.parseDouble(request.getParameter(CREDIT_LIMIT))));
        return buyer;
    }

    public static Admin updateAdminFromRequest(ServletRequest request) {
        Admin admin = (Admin) request.getAttribute(USER);
        Account account = buildAccountFromRequest(request);
        admin.setAccount(account);
        return admin;
    }

    private static Account buildAccountFromRequest(ServletRequest request) {
        Account account = new Account();
        Address address = new Address();

        account.setName(request.getParameter(NAME));
        account.setBirthday(LocalDate.parse(request.getParameter(BIRTHDAY)));
        account.setJob(request.getParameter(JOB));
        account.setEmail(request.getParameter(EMAIL));
        account.setPassword(request.getParameter(PASSWORD));
        account.setPhoneNumber(request.getParameter(PHONE_NUMBER));

        address.setCountry(request.getParameter(COUNTRY));
        address.setCity(request.getParameter(CITY));
        address.setStreet(request.getParameter(STREET));
        address.setZipcode(request.getParameter(ZIPCODE));

        account.setAddress(address);
        return account;
    }

    private static void setCommonProductAttributes(ServletRequest request, Product product) {
        product.setPrice(BigDecimal.valueOf(Double.parseDouble(request.getParameter(PRICE))));
        product.setQuantity(Integer.parseInt(request.getParameter(QUANTITY)));
        product.setReleaseDate(LocalDate.parse(request.getParameter(RELEASE_DATE)));
        product.setIsbn(request.getParameter(ISBN));
        product.setAuthor(request.getParameter(AUTHOR));
        product.setName(request.getParameter(NAME));
        product.setDescription(request.getParameter(DESCRIPTION));
    }

    public static Product createProductFromRequest(ServletRequest request) {
        Product product = new Product();
        setCommonProductAttributes(request, product);
        return product;
    }

    public static Product updateProductFromRequest(ServletRequest request) {
        Product product = (Product) request.getAttribute(PRODUCT);
        setCommonProductAttributes(request, product);
        return product;
    }

    private static void setCommonCategoryAttributes(ServletRequest request, Category category) {
        category.setName(request.getParameter(NAME));
    }

    public static Category createCategoryFromRequest(ServletRequest request) {
        Category category = new Category();
        setCommonCategoryAttributes(request, category);
        return category;
    }

    public static Category updateCategoryFromRequest(ServletRequest request) {
        Category category = (Category) request.getAttribute(CATEGORY);
        setCommonCategoryAttributes(request, category);
        return category;
    }
}
