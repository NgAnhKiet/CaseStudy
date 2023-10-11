<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 10/10/2023
  Time: 9:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<%
    boolean isLogeedIn = false;
    String userName = request.getParameter("userName");
    String password = request.getParameter("password");

    if (userName != null && password != null) {
        if (userName.equals("admin") && password.equals("password")) {
            isLogeedIn = true;
        }
    }
%>>
<h1>Đăng nhập</h1>
<label>Username:</label>
<input type="text" name="username"><br>
<label>Password:</label>
<input type="password" name="password"><br>
<input type="submit" value="Login">
</body>
</html>
