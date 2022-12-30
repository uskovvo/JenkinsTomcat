package com.example.jenkinstomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@FunctionalInterface
public interface ParametrSwitcher {
    void switchParameter(UserServlet servlet, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

}
