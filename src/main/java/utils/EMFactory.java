package utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EMFactory {
    private static EntityManagerFactory emf;

    private EMFactory() {
    }

    public static EntityManagerFactory getEMF(String persistenceUnitName) {
        if (emf == null) {
            synchronized (EMFactory.class) {
                if (emf == null) {
                    emf = Persistence.createEntityManagerFactory(persistenceUnitName);
                }
            }
        }
        return emf;
    }

    public static void close() {
        if (emf != null) {
            emf.close();
        }
    }
}