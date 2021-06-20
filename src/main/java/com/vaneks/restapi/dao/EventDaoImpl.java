package com.vaneks.restapi.dao;

import com.vaneks.restapi.model.Event;

public class EventDaoImpl extends AbstractDao<Event> {
    public EventDaoImpl(String className, Class<Event> eventClass) {
        super(className, eventClass);

    }
}
