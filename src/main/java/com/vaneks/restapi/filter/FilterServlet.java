package com.vaneks.restapi.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = { "/*" })
public class FilterServlet implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();
        String sessionId = session.getId();
        System.out.println(sessionId);
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        String cookieName = "sessionId";
        if (cookies != null) {
            for(Cookie cookie: cookies) {
                if(cookieName.equals(cookie.getName()) && sessionId.equals(cookie.getValue())) {
                    chain.doFilter(request, response);
                } else {
                    
                }
            }
            chain.doFilter(request, response);
        }
    }


    @Override
    public void destroy() {

    }
}
