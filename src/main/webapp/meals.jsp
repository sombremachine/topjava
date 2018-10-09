<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="/WEB-INF/functions" prefix="f" %>
<html>
<head>
    <meta charset="UTF-8"/>
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
        <th>Edit</th>
        <th>Delete</th>
    <tr>
    <c:forEach items="${meals}" var="meal">
        <tr style="color: ${meal.isExceed() ? '#ff0000' : '#00ff00'};">
            <td><p>${f:formatLocalDateTime(meal.getDateTime(), 'dd.MM.yyyy HH:mm:ss')}</p></td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>

            <td><a href="meals?action=update&id=<c:out value="${meal.getId()}"/>">Edit</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${meal.getId()}"/>">Delete</a></td>
        </tr>
    </c:forEach>

    <form method="POST" action='meals'>
    <tr>
        <td><input type="datetime-local" name="datetime" value = "${editMeal == null?"":editMeal.getDateTime()}"></td>
        <td><input type="text" name="description" value = "${editMeal == null?"":editMeal.getDescription()}"></td>
        <td><input type="number" name="calories" value = "${editMeal == null?"":editMeal.getCalories()}"></td>
        <td><input type="submit" value="${editMeal == null?"Add":"Update"}" /></td>
        <td>
            <input type = "hidden" name = "action" value = "${editMeal == null?"create":"update"}">
            <input type = "hidden" name = "id" value = "${editMeal == null?'':editMeal.getId()}">
        </td>
    </tr>
    </form>
</table>
</body>
</html>