package base;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import utils.EMFactory;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T, ID> {

    private final Class<T> entityClass;

    protected EntityManagerFactory emf;

    protected BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
        emf = EMFactory.getEMF("booktopia");
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    protected void closeEntityManager(EntityManager entityManager) {
        entityManager.close();
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    public Optional<T> findById(ID id) {
        EntityManager entityManager = getEntityManager();
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.id = :id AND e.isDeleted = false";
        TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
        query.setParameter("id", id);
        try {
            Optional<T> result = Optional.of(query.getSingleResult());
            closeEntityManager(entityManager);
            return result;
        } catch (Exception e) {
            closeEntityManager(entityManager);
            return Optional.empty();
        }
    }

    public List<T> findAll() {
        EntityManager entityManager = getEntityManager();
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.isDeleted = false";
        TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
        List<T> result = query.getResultList();
        closeEntityManager(entityManager);
        return result;
    }

    public T save(T entity) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
            closeEntityManager(entityManager);
            return entity;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            closeEntityManager(entityManager);
            throw e;
        }
    }

    public T update(T entity) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            T updatedEntity = entityManager.merge(entity);
            transaction.commit();
            closeEntityManager(entityManager);
            return updatedEntity;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            closeEntityManager(entityManager);
            throw e;
        }
    }

    public long count() {
        EntityManager entityManager = getEntityManager();
        String jpql = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e WHERE e.isDeleted = false";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        long result = query.getSingleResult();
        closeEntityManager(entityManager);
        return result;
    }

    public List<T> findAllSorted(String sortField, boolean ascending) {
        EntityManager entityManager = getEntityManager();
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.isDeleted = false ORDER BY e." + sortField + (ascending ? " ASC" : " DESC");
        TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
        List<T> result = query.getResultList();
        closeEntityManager(entityManager);
        return result;
    }

    public List<T> findByField(String fieldName, Object value) {
        EntityManager entityManager = getEntityManager();
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e." + fieldName + " = :value AND e.isDeleted = false";
        TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
        query.setParameter("value", value);
        List<T> result = query.getResultList();
        closeEntityManager(entityManager);
        return result;
    }
}
