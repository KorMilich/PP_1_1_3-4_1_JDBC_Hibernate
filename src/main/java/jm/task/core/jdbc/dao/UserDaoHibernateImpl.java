package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = Util.getUtil().getSessionFactory();

    public UserDaoHibernateImpl() {
    }
@Override
    public void createUsersTable() {

        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users"
                    + "(Id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(45) NOT NULL,"
                    + "lastName VARCHAR(45) NOT NULL, age TINYINT NOT NULL)").executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        try {
           session.beginTransaction();
           session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
           session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            users = session.createQuery("FROM User").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        try  {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }
}