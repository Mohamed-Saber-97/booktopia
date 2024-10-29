package org.example.booktopia.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.dtos.OrderDto;
import org.example.booktopia.error.InsufficientFunds;
import org.example.booktopia.error.InsufficientStock;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.mapper.BuyerMapper;
import org.example.booktopia.mapper.CartItemMapperImpl;
import org.example.booktopia.mapper.CategoryMapper;
import org.example.booktopia.mapper.OrderMapper;
import org.example.booktopia.model.Buyer;
import org.example.booktopia.model.Order;
import org.example.booktopia.repository.BuyerRepository;
import org.example.booktopia.repository.OrderProductRepository;
import org.example.booktopia.repository.OrderRepository;
import org.example.booktopia.repository.ProductRepository;
import org.example.booktopia.utils.RequestBuilderUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static org.example.booktopia.utils.RequestAttributeUtil.USER;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuyerService implements UserDetailsService {
    private final BuyerRepository buyerRepository;
    private final BuyerMapper buyerMapper;
    private final PasswordEncoder passwordEncoder;
    private final CategoryMapper categoryMapper;
    private final RequestBuilderUtil requestBuilderUtil;
    private final OrderMapper orderMapper;
    private final CartItemMapperImpl cartItemMapperImpl;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    public Buyer findById(Long id) {
        return buyerRepository.findById(id)
                              .orElseThrow(() -> new RecordNotFoundException("Buyer", "ID", id.toString()));
    }

    @Transactional
    public BuyerDto save(Buyer buyer) {
        return buyerMapper.toDto(buyerRepository.save(buyer));
    }

    @Transactional
    public BuyerDto save(HttpServletRequest request) {
        Buyer buyer = requestBuilderUtil.createBuyerFromRequest(request);
        buyer = buyerRepository.save(buyer);
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_BUYER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                buyer.getUsername(),
                buyer.getPassword(),
                authorities
        );
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
        return buyerMapper.toDto(buyer);
    }

    public Page<Buyer> findAllByPage(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return buyerRepository.findAllByIsDeletedIsFalse(pageRequest);
    }

    public boolean existsByAccountEmail(String accountEmail) {
        return buyerRepository.existsByAccountEmail(accountEmail);
    }

    public boolean existsByAccountPhone(String accountPhone) {
        return buyerRepository.existsByAccountPhoneNumber(accountPhone);
    }

    public BuyerDto findByEmail(String email) {
        Buyer buyer = buyerRepository.findByAccountEmail(email)
                                     .orElseThrow(() -> new RecordNotFoundException("Buyer", "Email", email));
        return buyerMapper.toDto(buyer);
    }

    public BuyerDto findByPhoneNumber(String phoneNumber) {
        Buyer buyer = buyerRepository.findByAccountPhoneNumber(phoneNumber)
                                     .orElseThrow(() -> new RecordNotFoundException("Buyer",
                                                                                    "Phonenumber",
                                                                                    phoneNumber));
        return buyerMapper.toDto(buyer);
    }

    @Override
//    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Buyer buyer = buyerRepository.findByAccountEmailAndIsDeletedIsFalse(username)
                                     .orElseThrow(() -> new UsernameNotFoundException(username));
//        buyer.getAccount()
//             .setPassword(passwordEncoder.encode(username));
        return User.builder()
                   .username(buyer.getUsername())
                   .password(buyer.getPassword())
                   .roles("BUYER")
                   .build();
    }

    @Transactional
    public BuyerDto updateProfile(HttpServletRequest request) {
        Long id = ((BuyerDto) request.getSession()
                                     .getAttribute(USER)).id();
        Buyer currentBuyer = findById(id);
        Buyer requestBuyer = requestBuilderUtil.updateBuyerFromRequest(currentBuyer, request);
        buyerRepository.save(requestBuyer);
        return buyerMapper.toDto(requestBuyer);
    }

    public List<OrderDto> getOrdersByBuyerId(Long id) {
        Buyer buyer = this.findById(id);
        return buyer.getOrders()
                    .stream()
                    .sorted(Comparator.comparing(Order::getId)
                                      .reversed())
                    .map(orderMapper::toDto)
                    .toList();
    }

    public List<BuyerDto> getAllBuyers(Integer pageNumber, Integer pageSize) {
        return buyerRepository.findAll(PageRequest.of(pageNumber, pageSize))
                              .stream()
                              .map(buyerMapper::toDto)
                              .toList();
    }

}
