<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.util.HtmlUtil" %>
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
            <c:set var="section" value="${sections.value}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>
    <h3>${key.title}</h3>
    <c:choose>
        <c:when test="${key == SectionType.PERSONAL || key == SectionType.OBJECTIVE}">
            <%=sections.getValue()%>
        </c:when>
        <c:when test="${key == SectionType.ACHIEVEMENT || key == SectionType.QUALIFICATIONS}">
            <ul>
                <c:forEach var="item" items="<%=((ListSection)section).getItems()%>">
                    <li><c:out value="${item}"/></li>
                </c:forEach>
            </ul>
        </c:when>
        <c:when test="${key == SectionType.EXPERIENCE || key == SectionType.EDUCATION}">
            <ul>
                <c:forEach var="organization" items="<%=((OrganizationSection)section).getOrganizations()%>">
                    <h4><a href="${organization.homePage.url}">${organization.homePage.name}</a></h4>
                    <c:forEach var="position" items="${organization.positions}">
                        <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                        <%=HtmlUtil.formatDates(position)%><br/>
                        <c:if test="${key == SectionType.EXPERIENCE}">
                            <strong><%=position.getTitle()%>
                            </strong><br/>
                        </c:if>
                        <%=position.getDescription()%><br/>
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
