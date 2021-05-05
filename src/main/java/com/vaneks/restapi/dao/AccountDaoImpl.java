package com.vaneks.restapi.dao;

import com.vaneks.restapi.model.Account;

public class AccountDaoImpl extends  AbstractDao<Account> {
    public AccountDaoImpl(String className, Class<Account> accountClass) {
        super(className, accountClass);
    }
}
