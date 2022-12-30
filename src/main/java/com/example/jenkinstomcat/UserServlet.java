package com.example.jenkinstomcat;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class UserServlet extends HttpServlet {
    private UsersDAO usersDAO;
    private static Map<String, ParametrSwitcher> parameterMap;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        usersDAO = new UsersDAO();
        parameterMap = new HashMap<>();

        parameterMap.put("delete", UserServlet::deleteUser);
        parameterMap.put("update", UserServlet::updateUser);
        parameterMap.put("create", UserServlet::addNewUser);
        parameterMap.put("all", UserServlet::showAllUsers);
        parameterMap.put(null, UserServlet::showAllUsers);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        executeCrudOperation(request, response, this);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
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

    public static void executeCrudOperation(HttpServletRequest req, HttpServletResponse resp, UserServlet servlet) throws ServletException, IOException {
        String action = req.getParameter("action");
        parameterMap.get(action).switchParameter(servlet, req, resp);

    }
}
