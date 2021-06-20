package com.vaneks.restapi.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vaneks.restapi.dao.EventDaoImpl;
import com.vaneks.restapi.model.Event;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EventServlet", urlPatterns = {"/events/*"})
public class EventServlet extends HttpServlet {

    private EventDaoImpl eventDao;

    @Override
    public void init() {
        eventDao = new EventDaoImpl(Event.class.getSimpleName(), Event.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if(pathInfo == null || pathInfo.equals("/")) {
            List<Event> eventList = eventDao.getAll();
            sendJson(resp, eventList);
        }

        String[] splits = pathInfo.split("/");
        String id = splits[1];
        Event event = eventDao.getById(Long.parseLong(id));
        sendJson(resp, event);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    private void sendJson(HttpServletResponse response, Object obj) throws IOException {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(obj);
        response.getWriter().write(json);
    }
}
