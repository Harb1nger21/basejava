<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt> ${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <h3>${type.title}</h3>
            <c:choose>
                <c:when test="${resume.getSection(type) != null}">
                    <c:choose>
                        <c:when test="${type.name() == SectionType.PERSONAL || type.name() == SectionType.OBJECTIVE}">
                                <textarea cols="100" name="${type.name()}"
                                          rows="2">${resume.getSection(type)}</textarea>
                        </c:when>
                        <c:when test="${type.name() == SectionType.ACHIEVEMENT || type.name() == SectionType.QUALIFICATIONS}">
                            <c:set var="content" value="${resume.getSection(type)}"/>
                            <jsp:useBean id="content" type="ru.javawebinar.basejava.model.ListSection"/>
                            <c:forEach var="item" items="${content.items}">
                                <textarea cols="100" name="${type.name()}" rows="4">${item}</textarea>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </c:when>
                <c:when test="${resume.getSection(type) == null}">
                    <textarea id="tx" cols="100" name="${type.name()}" rows="2"></textarea>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
