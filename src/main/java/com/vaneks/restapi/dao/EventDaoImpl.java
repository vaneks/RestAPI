package com.vaneks.restapi.dao;

import com.vaneks.restapi.model.Event;
import org.hibernate.query.Query;

import java.util.List;

public class EventDaoImpl extends AbstractDao<Event> {
    public EventDaoImpl(String className, Class<Event> eventClass) {
        super(className, eventClass);
    }

        public List<Event> getUserAll(long user_id) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Event E WHERE E.user.id =: user_id");
            query.setParameter("user_id", user_id);
            List<Event> entity = query.list();
            session.getTransaction().commit();
            return entity;
        }
}
