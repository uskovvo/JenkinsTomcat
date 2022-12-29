<jsp:useBean id="user" scope="request" type="com.example.jenkinstomcat.User"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.example.jenkinstomcat.User" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Add user</title>
</head>
<body>
<div>
    <form method="post" action="user">
        <h2>${param.action == 'create' ? 'Добавить нового пользовотеля' : 'Редактировать пользователя'}</h2>

        <input type="hidden" name="id" value="${user.id}">
        <br>
        <label>Имя</label>
        <br>
        <input type="text" name="name" value="${user.name}">
        <br>
        <label>Фамилия</label>
        <br>
        <input type="text" name="surname" value="${user.surname}">
        <br>
        <label>Возраст</label>
        <br>
        <input type="number" name="age" value="${user.age}">
        <br>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()" type="button">Отмена</button>
    </form>
</div>
</body>
</html>
