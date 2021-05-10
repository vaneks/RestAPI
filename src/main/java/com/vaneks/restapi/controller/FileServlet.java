package com.vaneks.restapi.controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vaneks.restapi.dao.FileDaoImpl;
import com.vaneks.restapi.model.File;
import com.vaneks.restapi.model.FileStatus;
import com.vaneks.restapi.model.User;
import lombok.SneakyThrows;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "FileServlet", urlPatterns = {"/files/*"})
public class FileServlet extends HttpServlet {
    private FileDaoImpl fileDao;

    @Override
    public void init() {
        fileDao = new FileDaoImpl(File.class.getSimpleName(), File.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String[] splits = pathInfo.split("/");
        String id = splits[1];
        fileDao.deleteById(Long.parseLong(id));
        response.getWriter().write("Deleted");
    }

    @SneakyThrows
    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String fileName = request.getParameter("fileName");
//        Date date = new Date();
//        UserDaoImpl userDao =  new UserDaoImpl(User.class.getSimpleName(), User.class);
//        User user = userDao.getById(1);
//        fileDao.save(new File(fileName, date, FileStatus.ACTIVE, user));
//        response.getWriter().write("Added");
//    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filePath  = "C:\\uploads";
        java.io.File file;
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setRepository(new java.io.File(filePath));
        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);

        try {
            List fileItems = upload.parseRequest(request);
            Iterator iterator = fileItems.iterator();

            while (iterator.hasNext()) {
                FileItem fileItem = (FileItem) iterator.next();
                if (!fileItem.isFormField()) {

                    String fileName = fileItem.getName();
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new java.io.File(filePath +
                                fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new java.io.File(filePath +
                                fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fileItem.write(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String fileName = request.getParameter("fileName");
        String fileStatusString = request.getParameter("fileStatus");
        FileStatus fileStatus = FileStatus.valueOf(fileStatusString);
        Date date = new Date();;
        File file= fileDao.getById(Long.parseLong(id));
        file.setFileName(fileName);
        file.setDate(date);
        file.setFileStatus(fileStatus);
        response.getWriter().write("Updated");
    }

    private void sendJson(HttpServletResponse response, Object obj) throws IOException {
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                if (field.getDeclaringClass() == File.class && field.getName().equals("user")) {
                    return true;
                }
                if (field.getDeclaringClass() == User.class && field.getName().equals("files")) {
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



