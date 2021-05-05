package com.vaneks.restapi.dao;

import com.vaneks.restapi.model.File;

public class FileDaoImpl extends AbstractDao<File> {
    public FileDaoImpl(String className, Class<File> fileClass) {
        super(className, fileClass);
    }
}
