package repository;

import base.BaseRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.Admin;
import model.Buyer;

import static utils.RequestParameterUtil.EMAIL;
import static utils.RequestParameterUtil.PHONE_NUMBER;

public class AdminRepository extends BaseRepository<Admin, Long> {
    public AdminRepository() {
        super(Admin.class);
    }

    public Admin findByEmail(String email) {
        String jpql = "SELECT admin FROM Admin admin WHERE admin.account.email = :email AND admin.isDeleted = false";
        TypedQuery<Admin> query = entityManager.createQuery(jpql, Admin.class);
        query.setParameter(EMAIL, email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public Admin findByPhoneNumber(String phoneNumber) {
        String jpql = "SELECT a FROM Admin a WHERE a.account.phoneNumber = :phoneNumber and a.isDeleted = false";
        TypedQuery<Admin> query = entityManager.createQuery(jpql, Admin.class);
        Admin result = null;
        try {
            result = query.setParameter(PHONE_NUMBER, phoneNumber).getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return result;
    }
}