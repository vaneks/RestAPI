package com.vaneks.restapi.dao;

import org.hibernate.Session;
import com.vaneks.restapi.utils.HibernateSession;

import java.util.List;

public class AbstractDao<T> implements EntityInterface<T>{
    private Session session = HibernateSession.getSession();
    private String className;
    private Class<T> tClass;

    public AbstractDao(String className, Class<T> tClass) {
        this.className = className;
        this.tClass = tClass;
    }

    @Override
    public List getAll() {
        session.beginTransaction();
        List<T> entity = session.createQuery("FROM " + className).list();
        session.getTransaction().commit();
        return entity;
    }

    @Override
    public T getById(long id) {
        session.beginTransaction();
        T entity = session.get(tClass, id);
        session.getTransaction().commit();
        return entity;
    }


    @Override
    public void save(T entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }

    @Override
    public void update(T entity) {
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
    }

    @Override
    public void deleteById(long id) {
        session.beginTransaction();
        T entity = session.get(tClass, id);
        session.delete(entity);
        session.getTransaction().commit();
    }
}
