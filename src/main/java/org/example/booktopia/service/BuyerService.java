package org.example.booktopia.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.mapper.BuyerMapper;
import org.example.booktopia.mapper.CategoryMapper;
import org.example.booktopia.model.Buyer;
import org.example.booktopia.repository.BuyerRepository;
import org.example.booktopia.utils.RequestBuilderUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public Buyer findById(Long id) {
        return buyerRepository.findById(id)
                              .orElseThrow(() -> new RecordNotFoundException("Buyer", "ID", id.toString()));
    }

    @Transactional
    public BuyerDto save(HttpServletRequest request) {
        Buyer buyer = requestBuilderUtil.createBuyerFromRequest(request);
        buyer = buyerRepository.save(buyer);
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

}
