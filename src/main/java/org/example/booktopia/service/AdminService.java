package org.example.booktopia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.dtos.LoginDto;
import org.example.booktopia.error.InvalidLoginCredentialsException;
import org.example.booktopia.model.Admin;
import org.example.booktopia.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public boolean login(LoginDto loginDto) {
        Admin admin = adminRepository.findByAccount_EmailAndAccount_PasswordAndIsDeletedFalse(loginDto.email(),
                                                                                              loginDto.password())
                                     .orElseThrow(InvalidLoginCredentialsException::new);

//        if (!passwordEncoder.matches(password, admin.getAccount().getPassword())) {
//            throw new InvalidLoginCredentialsException();
//        }
        return true;
    }
}
