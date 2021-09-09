package com.employee.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class DatabaseUnitTest {
    protected static EntityManager em;

    @BeforeAll
    public static void startupDatabase() {
        try {
            if (null == em) {
                Map<String, String> properties = new HashMap<>();
                properties.put("hibernate.show_sql", "false");
                properties.put("hibernate.connection.provider_class", "org.hibernate.connection.DriverManagerConnectionProvider");
                properties.put("hibernate.connection.url", "jdbc:hsqldb:mem:unit;sql.syntax_mys=true");
                properties.put("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
                properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
                properties.put("hibernate.connection.username", "sa");
                properties.put("hibernate.connection.password", "");
                properties.put("hibernate.cache.use_second_level_cache", "false");
                properties.put("hibernate.cache.use_query_cache", "false");
                properties.put("hibernate.hbm2ddl.auto", "create-drop");
                properties.put("javax.persistence.schema-generation.database.action", "drop-and-create");
                EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence_demo", properties);
                em = entityManagerFactory.createEntityManager();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void beginTransaction() {
        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE SCHEMA public AND COMMIT ").executeUpdate();
    }

    @AfterEach
    public void rollbackTransaction() {
        em.getTransaction().rollback();
    }

    protected void commitTransaction() {
        em.getTransaction().commit();
    }

    protected void commitTransaction(boolean clearContext) {
        commitTransaction();
        if (clearContext) {
            em.clear();
        }
    }

    @Test
    public void test() {
    }
}
