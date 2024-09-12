package controller;

import model.Admin;
import service.AdminService;

public class AdminController {
    private final AdminService adminService;

    public AdminController() {
        adminService = new AdminService();
    }

    public boolean login(String email, String password) {
        return adminService.checkValidLoginCredentials(email, password);
    }

    public Admin findByEmail(String email) {
        return adminService.findByEmail(email);
    }

    public Admin update(Admin admin) {
        return adminService.update(admin);
    }
}
