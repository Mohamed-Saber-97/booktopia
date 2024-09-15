package service;

import model.Admin;
import repository.AdminRepository;

public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService() {
        this.adminRepository = new AdminRepository();
    }

    public Admin update(Admin newAdmin) {
        Admin existingAdmin = adminRepository.findById(newAdmin.getId()).orElse(null);
        if (existingAdmin == null) {
            return null;
        }
        if (existingAdmin.hashCode() == newAdmin.hashCode()) {
            return existingAdmin;
        }
        existingAdmin.getAccount().setAddress(newAdmin.getAccount().getAddress());
        existingAdmin.setAccount(newAdmin.getAccount());
        return adminRepository.update(existingAdmin);
    }

    public boolean checkValidLoginCredentials(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);
        if (admin == null) {
            return false;
        }
        return admin.getAccount().getPassword().equals(password);
    }

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }
    public Admin findByPhoneNumber(String phoneNumber) {
        return adminRepository.findByPhoneNumber(phoneNumber);
    }
}