<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<table align="center">
    <tr>
        <th colspan="4">List of Accounts</th>
    </tr>
    <tr>
        <th class="text-center h5">ID</th>
        <th class="text-center h5">firstName</th>
        <th class="text-center h5">lastName</th>
        <th class="text-center h5">Age</th>
        <th class="text-center h5">accountStatus</th>
    </tr>
    <c:forEach var="account" items="${listAccount}">
        <tr>
            <td><c:out value="${account.id}" /></td>
            <td class="text-center"><c:out value="${account.firstName}" /></td>
            <td class="text-center"><c:out value="${account.lastName}" /></td>
            <td class="text-center"><c:out value="${account.age}" /></td>
            <td class="text-center"><c:out value="${account.accountStatus}" /></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
