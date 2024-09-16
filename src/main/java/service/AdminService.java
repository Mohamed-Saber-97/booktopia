package service;

import model.Admin;
import repository.AdminRepository;
import utils.PasswordUtil;

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
//        if (existingAdmin.hashCode() == newAdmin.hashCode()) {
//            return existingAdmin;
//        }
        existingAdmin.getAccount().setAddress(newAdmin.getAccount().getAddress());
        existingAdmin.setAccount(newAdmin.getAccount());
        existingAdmin.getAccount().setPassword(PasswordUtil.hashPassword(newAdmin.getAccount().getPassword()));
        return adminRepository.update(existingAdmin);
    }

    public boolean checkValidLoginCredentials(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null) {
            return PasswordUtil.checkPassword(password, admin.getAccount().getPassword());
        }
        return false;
    }

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }
    public Admin findByPhoneNumber(String phoneNumber) {
        return adminRepository.findByPhoneNumber(phoneNumber);
    }
}