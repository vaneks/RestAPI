package com.vaneks.restapi.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vaneks.restapi.dao.AccountDaoImpl;
import com.vaneks.restapi.dao.UserDaoImpl;
import com.vaneks.restapi.model.Account;
import com.vaneks.restapi.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = {"/users/*"})
public class UserServlet extends HttpServlet {
    private UserDaoImpl userDao;
    private AccountDaoImpl accountDao;

    @Override
    public void init() {
        userDao = new UserDaoImpl(User.class.getSimpleName(), User.class);
        accountDao = new AccountDaoImpl(Account.class.getSimpleName(), Account.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if(pathInfo == null || pathInfo.equals("/")) {
            List<User> listUser = userDao.getAll();
            sendJson(response, listUser);
        }

        String[] splits = pathInfo.split("/");
        String id = splits[1];

        User user = userDao.getById(Long.parseLong(id));
        sendJson(response, user);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String[] splits = pathInfo.split("/");
        String id = splits[1];

        userDao.deleteById(Long.parseLong(id));
        response.getWriter().write("Deleted");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        long account_id = Long.parseLong(request.getParameter("account_id"));
        Account account = accountDao.getById(account_id);
        userDao.save(new User(login, password, account));
        response.getWriter().write("Added");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String password = request.getParameter("password");

        String[] splits = pathInfo.split("/");
        String id = splits[1];

        User user = userDao.getById(Long.parseLong(id));
        user.setPassword(password);
        userDao.update(user);
        response.getWriter().write("Updated");
    }

    private void sendJson(HttpServletResponse response, Object obj) throws IOException {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(obj);
        response.getWriter().write(json);
    }
}
