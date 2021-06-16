package com.vaneks.restapi.controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vaneks.restapi.dao.AccountDaoImpl;
import com.vaneks.restapi.model.Account;
import com.vaneks.restapi.model.AccountStatus;
import com.vaneks.restapi.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AccountServlet", urlPatterns = {"/accounts/*"})
public class AccountServlet extends HttpServlet {
    private AccountDaoImpl accountDao;

    @Override
    public void init() {
        accountDao = new AccountDaoImpl(Account.class.getSimpleName(), Account.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if(pathInfo == null || pathInfo.equals("/")) {
            List<Account> listAccount = accountDao.getAll();
            sendJson(response, listAccount);
        }

        String[] splits = pathInfo.split("/");
        String id = splits[1];
        Account account = accountDao.getById(Long.parseLong(id));
        sendJson(response, account);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String[] splits = pathInfo.split("/");
        String id = splits[1];
        accountDao.deleteById(Long.parseLong(id));
        sendJson(response, "Deleted");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        int age = Integer.parseInt(request.getParameter("age"));
        accountDao.save(new Account(firstName, lastName, age, AccountStatus.ACTIVE));
        sendJson(response, "Added");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String[] splits = pathInfo.split("/");
        String id = splits[1];

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String age = request.getParameter("age");

        Account account = accountDao.getById(Long.parseLong(id));
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setAge(Integer.parseInt(age));
        accountDao.update(account);
        sendJson(response, "Updated");
    }

    private void sendJson(HttpServletResponse response, Object obj) throws IOException {
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                if (field.getDeclaringClass() == Account.class && field.getName().equals("user")) {
                    return true;
                }
                if (field.getDeclaringClass() == User.class && field.getName().equals("account")) {
                    return true;
                }
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(strategy)
                .create();
        String json = gson.toJson(obj);
        response.getWriter().write(json);
    }
}



