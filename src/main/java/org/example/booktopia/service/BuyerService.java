package org.example.booktopia.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.dtos.LoginDto;
import org.example.booktopia.dtos.SignupDto;
import org.example.booktopia.error.InvalidLoginCredentialsException;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.mapper.BuyerMapper;
import org.example.booktopia.model.Account;
import org.example.booktopia.model.Address;
import org.example.booktopia.model.Buyer;
import org.example.booktopia.repository.BuyerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuyerService {
    private final BuyerRepository buyerRepository;
    private final BuyerMapper buyerMapper;

    public Buyer findById(Long id) {
        return buyerRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Buyer", "ID", id.toString()));
    }

    @Transactional
    public void save(SignupDto signupDto) {
        Address address = Address.builder()
                .street(signupDto.street())
                .city(signupDto.city())
                .zipcode(signupDto.zipCode())
                .country(signupDto.country())
                .build();
        Account account = Account.builder()
                .name(signupDto.name())
                .birthday(signupDto.dob())
                .password(signupDto.password())
                .job(signupDto.job())
                .email(signupDto.email())
                .phoneNumber(signupDto.phoneNumber())
                .address(address)
                .build();
        Buyer buyer = new Buyer(account, signupDto.creditLimit(), signupDto.categories());
        buyerRepository.save(buyer);
    }

    public Page<Buyer> findAllByPage(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return buyerRepository.findAllByIsDeletedIsFalse(pageRequest);
    }

    public boolean login(LoginDto loginDto) {
        Buyer buyer = buyerRepository.findByAccount_EmailAndAccount_PasswordAndIsDeletedFalse(loginDto.email(), loginDto.password())
                .orElseThrow(InvalidLoginCredentialsException::new);

//        if (!passwordEncoder.matches(password, admin.getAccount().getPassword())) {
//            throw new InvalidLoginCredentialsException();
//        }
        return true;
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
}
