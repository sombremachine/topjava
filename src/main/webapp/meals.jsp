<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="/WEB-INF/functions" prefix="f" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table  border="1">
    <tr>
        <th>Date&Time</th>
        <th>Description</th>
        <th>Calories</th>
    <tr>
    <c:forEach items="${meals}" var="meal">
        <tr style="color: ${meal.isExceed() ? '#ff0000' : '#00ff00'};">
            <td><p>${f:formatLocalDateTime(meal.getDateTime(), 'dd.MM.yyyy HH:mm:ss')}</p></td>

            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>