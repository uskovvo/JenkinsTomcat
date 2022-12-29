package com.example.jenkinstomcat;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {
    private UsersDAO usersDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        usersDAO = new UsersDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println(usersDAO.getAllUsers());
        String action = request.getParameter("action");
        switch (action == null ? "All" : action) {
            case "delete":
                deleteUser(request, response);
                break;
            case "create":
                addNewUser(request, response);
                break;
            case "update":
                updateUser(request, response);
                break;
            default:
                showAllUsers(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setAge(Integer.parseInt(request.getParameter("age")));
        if (!request.getParameter("id").equals("")) {
            user.setId(Long.valueOf(request.getParameter("id")));
            usersDAO.update(user);
        } else {
            usersDAO.save(user);
        }
        response.sendRedirect("user");

    }

    public void showAllUsers(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = usersDAO.getAllUsers();
        request.setAttribute("user", users);
        try {
            request.getRequestDispatcher("/user.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNewUser(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("user", new User());
        try {
            request.getRequestDispatcher("/new_user.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(
                "user",
                usersDAO.getUserById
                        (Long.parseLong(request.getParameter("id"))));
        try {
            request.getRequestDispatcher("/new_user.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(HttpServletRequest request, HttpServletResponse response) {
        usersDAO.delete(Long.parseLong(request.getParameter("id")));
        try {
            response.sendRedirect("user");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
