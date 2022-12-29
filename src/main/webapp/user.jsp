<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.example.jenkinstomcat.User" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<section>
    <h2>Users</h2>
    <button><a href="${pageContext.request.contextPath}/user?action=create">Add User</a></button>
<%--    <a href="user?action=create">Add User</a>--%>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Age</th>
            <th></th>
            <th></th>

        </tr>
        </thead>

        <c:forEach var="user" items="${user}">

            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.age}</td>
                <td><a href="user?action=update&id=${user.id}">Update</a></td>
                <td><a href="user?action=delete&id=${user.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>