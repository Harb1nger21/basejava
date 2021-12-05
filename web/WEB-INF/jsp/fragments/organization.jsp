<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <dl>
        <dt>Название оргазинации</dt>
        <dd><input type="text" name="orgName"
                   size="${organization != null ? organization.homePage.name.length() : 30}"
                   value="${organization != null ? organization.homePage.name : ""}"></dd>
        <br/>
        <dt>Сайт оргазинации</dt>
        <dd><input type="text" name="siteName" size="30"
                   value="${organization != null ? organization.homePage.url : ""}"></dd>
    </dl>
    <c:import url="fragments/position.jsp"/>
    <c:forEach var="orgItem" items="${organization.positions}">
        <c:set var="orgItem" value="${orgItem}" scope="request"/>
        <c:import url="fragments/position.jsp"/>
    </c:forEach>
    <c:set var="orgItem" value="${null}"/>
</body>
</html>
