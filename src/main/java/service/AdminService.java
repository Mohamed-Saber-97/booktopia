package service;

import model.Admin;
import repository.AdminRepository;

public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService() {
        this.adminRepository = new AdminRepository();
    }

    public Admin update(Admin admin) {
        Admin savedAdmin = findByEmail(admin.getAccount().getEmail());
        savedAdmin.getAccount().setAddress(admin.getAccount().getAddress());
        savedAdmin.setAccount(admin.getAccount());
        return adminRepository.update(savedAdmin);
    }

    Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }
}