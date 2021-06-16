package com.vaneks.restapi.controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vaneks.restapi.dao.EventDaoImpl;
import com.vaneks.restapi.dao.FileDaoImpl;
import com.vaneks.restapi.dao.UserDaoImpl;
import com.vaneks.restapi.model.Event;
import com.vaneks.restapi.model.File;
import com.vaneks.restapi.model.FileStatus;
import com.vaneks.restapi.model.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import utils.HibernateSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "FileServlet", urlPatterns = {"/files/*"})

public class FileServlet extends HttpServlet {

    private FileDaoImpl fileDao;
    private EventDaoImpl eventDao;
    private UserDaoImpl userDao;
    static String filePath = "D:/upload/";
    @Override
    public void init() {
        fileDao = new FileDaoImpl(File.class.getSimpleName(), File.class);
        eventDao = new EventDaoImpl(Event.class.getSimpleName(), Event.class);
        userDao = new UserDaoImpl(User.class.getSimpleName(), User.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String pathInfo = request.getPathInfo();
        if(pathInfo == null || pathInfo.equals("/")) {
            List<File> listAccount = fileDao.getAll();
            sendJson(response, listAccount);
        }
        String[] splits = pathInfo.split("/");
        String id = splits[1];
        File file = fileDao.getById(Long.parseLong(id));
        sendJson(response, file);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String pathInfo = request.getPathInfo();
        String[] splits = pathInfo.split("/");
        String id = splits[1];
        String fileName = fileDao.getById(Long.parseLong(id)).getFileName();
        fileDao.deleteById(Long.parseLong(id));
        String path = filePath + fileName;
        java.io.File deleteFile = new java.io.File(path);
        if( deleteFile.exists() )
            deleteFile.delete() ;
        sendJson(response, "Deleted");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        final int fileMaxSize = 100 * 1024;
        final int memMaxSize = 100 * 1024;

        Date date = new Date();
        User user = userDao.getById(1L);

        String uuidFile = UUID.randomUUID().toString();

        java.io.File file;


        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setRepository(new java.io.File(filePath));
        diskFileItemFactory.setSizeThreshold(memMaxSize);

        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        upload.setSizeMax(fileMaxSize);

        try {
            List fileItems = upload.parseRequest(request);

            Iterator iterator = fileItems.iterator();

            while (iterator.hasNext()) {
                FileItem fileItem = (FileItem) iterator.next();
                if (!fileItem.isFormField()) {

                    String fileName = uuidFile + "_" + fileItem.getName();
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new java.io.File(filePath +
                                fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new java.io.File(filePath +
                                fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }

                    fileItem.write(file);
                    File fileSave = new File(fileName, date, FileStatus.ACTIVE);
                    fileDao.save(fileSave);
                    eventDao.save(new Event(fileSave, date, user));
                    sendJson(response, fileName + " is uploaded");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id = request.getParameter("id");
        String fileName = request.getParameter("fileName");
        String fileStatusString = request.getParameter("fileStatus");

        FileStatus fileStatus = FileStatus.valueOf(fileStatusString);
        Date date = new Date();
        File file= fileDao.getById(Long.parseLong(id));
        String name = file.getFileName();

        file.setFileName(fileName);
        file.setDate(date);
        file.setFileStatus(fileStatus);

        java.io.File oldFileName=  new java.io.File(filePath + name);
        java.io.File newFileName = new java.io.File(filePath + fileName);
        if(oldFileName.renameTo(newFileName)){
            fileDao.update(file);
            sendJson(response, "Updated");
        }

    }

    private void sendJson(HttpServletResponse response, Object obj) throws IOException {
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes field) {
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



