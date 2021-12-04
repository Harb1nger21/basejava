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
        <c:choose>
            <c:when test="${param.get('action').equals('add')}">
                <input type="hidden" name="action" value="add">
            </c:when>
            <c:when test="${param.get('action').equals('edit')}">
                <input type="hidden" name="action" value="edit">
            </c:when>
        </c:choose>
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
                <c:when test="${resume.getSection(type) == null &&
                 (type.name() == SectionType.PERSONAL ||
                  type.name() == SectionType.OBJECTIVE ||
                   type.name() == SectionType.ACHIEVEMENT ||
                    type.name() == SectionType.QUALIFICATIONS)}">
                    <textarea id="tx" cols="100" name="${type.name()}" rows="2"></textarea>
                </c:when>
                <c:when test="${resume.getSection(type) == null &&
                (type.name() == SectionType.EXPERIENCE ||
                type.name() == SectionType.EDUCATION)}">
                    <c:import url="fragments/organization.jsp"/>
                </c:when>
                <c:when test="${resume.getSection(type) != null}">
                    <c:choose>
                        <c:when test="${type.name() == SectionType.PERSONAL || type.name() == SectionType.OBJECTIVE}">
                                <textarea cols="100" name="${type.name()}"
                                          rows="2">${resume.getSection(type)}</textarea>
                        </c:when>
                        <c:when test="${type.name() == SectionType.ACHIEVEMENT || type.name() == SectionType.QUALIFICATIONS}">
                            <c:set var="content" value="${resume.getSection(type)}"/>
                            <jsp:useBean id="content" type="ru.javawebinar.basejava.model.ListSection"/>
                            <textarea cols="100" name="${type.name()}" rows="${content.items.size()}">
<c:forEach var="item" items="${content.items}">${item.trim()}
</c:forEach>
                            </textarea>
                        </c:when>
                        <c:when test="${type.name() == SectionType.EXPERIENCE || type.name() == SectionType.EDUCATION}">
                            <c:import url="fragments/organization.jsp"/>
                            <c:set var="organizations" value="${resume.getSection(type)}"/>
                            <jsp:useBean id="organizations" type="ru.javawebinar.basejava.model.OrganizationSection"/>
                            <c:set var="orgCount" value="1" scope="request"/>
                            <c:set var="posCount" value="1" scope="request"/>
                            <c:forEach var="organization" items="${organizations.organizations}" varStatus="orgCount">
                                <c:set var="organization" value="${organization}" scope="request"/>
                                <c:set var="orgCount" value="${orgCount}" scope="request"/>
                                <c:import url="fragments/organization.jsp"/>
                            </c:forEach>
                            <c:set var="organization" value="${null}"/>
                        </c:when>
                    </c:choose>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
