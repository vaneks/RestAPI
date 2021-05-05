package com.vaneks.restapi.dao;

import com.vaneks.restapi.model.User;

public class UserDaoImpl extends AbstractDao<User>{
    public UserDaoImpl(String className, Class<User> aClass) {
        super(className, aClass);
    }
}
