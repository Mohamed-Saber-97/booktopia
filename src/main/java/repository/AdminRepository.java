package repository;

import base.BaseRepository;
import model.Admin;

import java.util.List;

import static utils.RequestParameterUtil.EMAIL;
import static utils.RequestParameterUtil.PHONE_NUMBER;

public class AdminRepository extends BaseRepository<Admin, Long> {
    public AdminRepository() {
        super(Admin.class);
    }

    public Admin findByEmail(String email) {
        List<Admin> result = findByField(EMAIL, email);
        return result.isEmpty() ? null : result.getFirst();
    }

    public Admin findByPhoneNumber(String phoneNumber) {
        List<Admin> result = findByField("phone_number", phoneNumber);
        return result.isEmpty() ? null : result.getFirst();
    }
}
