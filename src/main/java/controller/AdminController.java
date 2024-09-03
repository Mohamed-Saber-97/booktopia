package controller;

import model.Admin;
import service.AdminService;

public class AdminController {
    private final AdminService adminService;

    public AdminController() {
        adminService = new AdminService();
    }

    public Admin update(Admin admin) {
        return adminService.update(admin);
    }
}
