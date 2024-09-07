package repository;

import base.BaseRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.Admin;

import static utils.RequestParameterUtil.EMAIL;

public class AdminRepository extends BaseRepository<Admin, Long> {
    public AdminRepository() {
        super(Admin.class);
    }

    public Admin findByEmail(String email) {
        String jpql = "SELECT admin FROM Admin admin WHERE admin.account.email = :email";
        TypedQuery<Admin> query = entityManager.createQuery(jpql, Admin.class);
        query.setParameter(EMAIL, email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}