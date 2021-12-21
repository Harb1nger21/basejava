<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
    <form id="all" method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="action" value="${param.get('action').equals('add')?'add':'edit'}">
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
        <c:forEach var="sectionsMap" items="${resume.sections}">
            <jsp:useBean id="sectionsMap"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.AbstractSection>"/>
            <c:set var="type" value="${sectionsMap.key}"/>
            <c:set var="section" value="${sectionsMap.value}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>
            <h3>${type.title}</h3>
            <c:choose>
                <c:when test="${type == 'PERSONAL' || type == 'OBJECTIVE'}">
                    <textarea cols="100" name="${type}" rows="2">${section}</textarea>
                </c:when>
                <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                    <textarea cols="100" name="${type.name()}"
                              rows="<%=((ListSection)section).getItems().size()%>"><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                </c:when>
                <c:when test="${type == SectionType.EXPERIENCE || type == SectionType.EDUCATION}">
                    <c:forEach var="organization" items="<%=((OrganizationSection)section).getOrganizations()%>"
                               varStatus="counter">
                        <dl>
                            <dt>Название оргазинации</dt>
                            <dd><input type="text" name="${type}_orgName"
                                       size="30"
                                       value="${organization != null ? organization.homePage.name : ""}"></dd>
                            <br/>
                            <dt>Сайт оргазинации</dt>
                            <dd><input type="text" name="${type}_siteName" size="30"
                                       value="${organization != null ? organization.homePage.url : ""}"></dd>
                        </dl>
                        <div style="margin-left: 30px">
                            <c:forEach var="orgItem" items="${organization.positions}">
                                <jsp:useBean id="orgItem" type="ru.javawebinar.basejava.model.Organization.Position"/>
                                <dl>
                                    <dt>Дата начала</dt>
                                    <dd><input type="text" name="${type}_${counter.index}_startDate" size="30"
                                               placeholder="Пример: 2000-01"
                                               value="<%=orgItem.getStartDate() != null ? DateUtil.format(orgItem.getStartDate()) : ""%>">
                                    </dd>
                                    <dt>Дата окончания</dt>
                                    <dd><input type="text" name="${type}_${counter.index}_endDate" size="30"
                                               placeholder="Пример: 2000-01"
                                               value="<%=DateUtil.format(orgItem.getEndDate())%>">
                                    </dd>
                                    <br/>
                                    <dt>Позиция/Должность</dt>
                                    <dd><input type="text" name="${type}_${counter.index}_title" size="30"
                                               value="${orgItem.title}"></dd>
                                    <br/>
                                    <dt>Обяанности</dt>
                                    <dd><textarea cols="100" name="${type}_${counter.index}_description"
                                                  rows="5">${orgItem.description}</textarea>
                                    </dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
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