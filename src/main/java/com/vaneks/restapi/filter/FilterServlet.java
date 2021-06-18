package com.vaneks.restapi.filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vaneks.restapi.dao.UserDaoImpl;
import com.vaneks.restapi.model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import static java.util.Objects.nonNull;

@WebFilter(urlPatterns = { "/*" })
public class FilterServlet implements Filter {
    UserDaoImpl userDao;
    @Override
    public void init(FilterConfig filterConfig) {
        userDao = new UserDaoImpl(User.class.getSimpleName(), User.class);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        User user;
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        if (nonNull(session) && nonNull(session.getAttribute("login")) && nonNull(session.getAttribute("password"))) {
            chain.doFilter(request,response);
        } else if (nonNull(user = userIsExist(login, password))){

            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("login", login);
            request.getSession().setAttribute("password", password);
            chain.doFilter(request,response);
        } else {
            sendJson(response, "Incorrect Login or Password");
        }
    }

    public User userIsExist(String login, String password) {
        List<User> users = userDao.getAll();
        User user = users.stream().filter(s->(s.getLogin().equals(login)) && (s.getPassword().equals(password))).findFirst().orElse(null);
        return user;
    }

    private void sendJson(HttpServletResponse response, Object obj) throws IOException {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(obj);
        response.getWriter().write(json);
    }
}
