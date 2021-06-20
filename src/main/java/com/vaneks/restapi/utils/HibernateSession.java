package com.vaneks.restapi.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSession {

        static Configuration config;
        static SessionFactory sf;
        static Session session;

        public static Session getSession() {

            if(session==null){
                config=new Configuration();
                sf=config.configure().buildSessionFactory();
                session = sf.openSession();
            }
            return session;
        }
    }