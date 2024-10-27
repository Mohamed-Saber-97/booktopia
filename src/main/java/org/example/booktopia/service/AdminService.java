package org.example.booktopia.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.dtos.AdminDto;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.mapper.AdminMapper;
import org.example.booktopia.model.Admin;
import org.example.booktopia.repository.AdminRepository;
import org.example.booktopia.utils.RequestBuilderUtil;
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
public class AdminService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminMapper adminMapper;
    private final RequestBuilderUtil requestBuilderUtil;

    public AdminDto findByEmail(String email) {
        var admin = adminRepository.findByAccountEmailAndIsDeletedIsFalse(email)
                                   .orElseThrow(() -> new RecordNotFoundException("Admin", "Email", email));
        return adminMapper.toDto(admin);
    }

    public Admin findById(Long id) {
        return adminRepository.findById(id)
                              .orElseThrow(() -> new RecordNotFoundException("Admin", "Id", id.toString()));
    }

    public Admin findByPhonenumber(String phoneNumber) {
        return adminRepository.findByAccountPhoneNumberAndIsDeletedIsFalse(phoneNumber)
                              .orElseThrow(() -> new RecordNotFoundException("Admin", "Phonenumber", phoneNumber));
    }

    @Transactional
    public AdminDto updateProfile(HttpServletRequest request) {
        Long id = ((AdminDto) request.getSession()
                                     .getAttribute(USER)).id();
        Admin currentAdmin = findById(id);
        Admin requestAdmin = requestBuilderUtil.updateAdminFromRequest(currentAdmin, request);
        adminRepository.save(requestAdmin);
        return adminMapper.toDto(requestAdmin);
    }
//
//    @Transactional
//    public AdminDto updateProfile(Long id, AdminDto adminDto) {
//        Admin currentAdmin = adminRepository.findById(id)
//                                            .orElseThrow(() -> new RecordNotFoundException("Admin", "Id", id
//                                                    .toString()));
//        Address address = Address.builder()
//                                 .country(adminDto.country())
//                                 .zipcode(adminDto.zipCode())
//                                 .street(adminDto.street())
//                                 .city(adminDto.city())
//                                 .build();
//        String encodedPassword = passwordEncoder.encode(adminDto.password());
//        Account account = Account.builder()
//                                 .name(adminDto.name())
//                                 .address(address)
//                                 .password(encodedPassword)
//                                 .birthday(adminDto.dob())
//                                 .job(adminDto.job())
//                                 .phoneNumber(adminDto.phoneNumber())
//                                 .email(adminDto.email())
//                                 .build();
//        currentAdmin.setAccount(account);
//        currentAdmin = adminRepository.save(currentAdmin);
//        return adminMapper.toDto(currentAdmin);
//    }

    @Override
//    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Admin admin = adminRepository.findByAccountEmailAndIsDeletedIsFalse(username)
//                                     .orElseThrow(() -> new UsernameNotFoundException(username));
////        admin.getAccount()
////             .setPassword(passwordEncoder.encode(username));
//        return User.builder()
//                   .username(admin.getUsername())
//                   .password(admin.getPassword())
//                   .roles("ADMIN")
//                   .build();

        try {
            Admin admin = adminRepository.findByAccountEmailAndIsDeletedIsFalse(username)
                                         .orElseThrow(() -> new UsernameNotFoundException(
                                                 "Admin not found with email: " + username));

            return User.builder()
                       .username(admin.getAccount()
                                      .getEmail())
                       .password(admin.getAccount()
                                      .getPassword())
                       .roles("ADMIN")
                       .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Error loading admin: " + username, e);
        }
    }
}
