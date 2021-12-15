<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
        <c:forEach var="sections" items="${resume.sections}">
            <jsp:useBean id="sections"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.AbstractSection>"/>
            <c:set var="key" value="${sections.key}"/>
    <h3>${key.title}</h3>
    <c:choose>
        <c:when test="${key == SectionType.PERSONAL || key == SectionType.OBJECTIVE}">
            <%=sections.getValue()%>
        </c:when>
        <c:when test="${key == SectionType.ACHIEVEMENT || key == SectionType.QUALIFICATIONS}">
            <c:set var="content" value="${sections.value}"/>
            <jsp:useBean id="content" type="ru.javawebinar.basejava.model.ListSection"/>
            <ul>
                <c:forEach var="item" items="${content.items}">
                    <li><c:out value="${item}"/></li>
                </c:forEach>
            </ul>
        </c:when>
        <c:when test="${key == SectionType.EXPERIENCE || key == SectionType.EDUCATION}">
            <c:set var="orgContent" value="${sections.value}"/>
            <jsp:useBean id="orgContent" type="ru.javawebinar.basejava.model.OrganizationSection"/>
            <ul>
                <c:forEach var="organization" items="${orgContent.organizations}">
                    <h4><a href="${organization.homePage.url}">${organization.homePage.name}</a></h4>
                    <c:forEach var="position" items="${organization.positions}">
                        <fmt:parseDate value="${position.startDate}" type="date" pattern="yyyy-MM-dd" var="parsedStart"/>
                        <fmt:formatDate value="${parsedStart}" type="date" pattern="yyyy.MM" var="start"/>
                        <fmt:parseDate value="${position.endDate}" type="date" pattern="yyyy-MM-dd" var="parsedEnd"/>
                        <fmt:formatDate value="${parsedEnd}" type="date" pattern="yyyy.MM" var="end"/>
                        <c:out value="${start} - ${position.endDate.year > 2500 ? 'настояшее время' : end}"/><br/>
                        <c:if test="${key == SectionType.EXPERIENCE}">
                            <h5>${position.title}</h5>
                        </c:if>
                        <c:out value="${position.description}"/><br/>
                    </c:forEach>
                </c:forEach>
            </ul>
        </c:when>
    </c:choose>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
