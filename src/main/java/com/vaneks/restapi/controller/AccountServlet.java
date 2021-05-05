package com.vaneks.restapi.controller;

import com.google.gson.Gson;
import com.vaneks.restapi.dao.AccountDaoImpl;
import com.vaneks.restapi.model.Account;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "AccountServlet", urlPatterns = "/listAccount")
public class AccountServlet extends HttpServlet {
    private AccountDaoImpl accountDao;

    @Override
    public void init() {
        accountDao = new AccountDaoImpl(Account.class.getSimpleName(), Account.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/listAccount":
                    listAccount(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Account> listAccount = accountDao.getAll();
//        Gson gson = new Gson();
//        String json = gson.toJson(listAccount);
        response.setContentType("text/html");
        PrintWriter messageWriter = response.getWriter();
        for(Account account:listAccount) {
            messageWriter.println(account.getId());
            messageWriter.println( account.getFirstName());
            messageWriter.println( account.getLastName());
            messageWriter.println(account.getAge());
            messageWriter.println(account.getAccountStatus());
        }

    }
}



