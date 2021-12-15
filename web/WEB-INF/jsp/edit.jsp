<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
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
                <c:when test="${type.name() == SectionType.PERSONAL || type.name() == SectionType.OBJECTIVE ||
                   type.name() == SectionType.ACHIEVEMENT || type.name() == SectionType.QUALIFICATIONS}">
                    <c:choose>
                        <c:when test="${resume.getSection(type) == null}">
                            <textarea id="tx" cols="100" name="${type.name()}" rows="2"></textarea>
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
<c:forEach var="item" items="${content.items}">${item}
</c:forEach>
                            </textarea>
                                </c:when>
                            </c:choose>
                        </c:when>
                    </c:choose>
                </c:when>
                <c:when test="${type.name() == SectionType.EXPERIENCE || type.name() == SectionType.EDUCATION}">
                    <h5>Добавить новую организацию</h5>
                    <dl>
                        <dt>Название оргазинации</dt>
                        <dd><input type="text" name="${type.name()}_orgName" size="30"  value=""></dd>
                        <br/>
                        <dt>Сайт оргазинации</dt>
                        <dd><input type="text" name="${type.name()}_siteName" size="30" value=""/></dd>
                    </dl>
                    <dl id="nullPos">
                        <dt>Дата начала</dt>
                        <dd><input type="text" name="${type.name()}_0_startDate" placeholder="Пример: 2000-01" size="30" value=""></dd>
                        <dt>Дата окончания</dt>
                        <dd><input type="text" name="${type.name()}_0_endDate" placeholder="Пример: 2000-01" size="30" value=""></dd>
                        <br/>
                        <dt>Позиция/Должность</dt>
                        <dd><input type="text" name="${type.name()}_0_title" size="30" value=""></dd>
                        <br/>
                        <dt>Обяанности</dt>
                        <dd><textarea cols="100" name="${type.name()}_0_description" rows="5"></textarea></dd>
                    </dl>
                    <hr/>
                    <c:if test="${resume.getSection(type) != null}">
                        <c:set var="organizations" value="${resume.getSection(type)}"/>
                        <jsp:useBean id="organizations" type="ru.javawebinar.basejava.model.OrganizationSection"/>
                        <h5>Текущие организации</h5>
                        <c:forEach var="organization" items="${organizations.organizations}" varStatus="counter">
                            <dl>
                                <dt>Название оргазинации</dt>
                                <dd><input type="text" name="${type.name()}_orgName"
                                           size="${organization != null ? organization.homePage.name.length() : 30}"
                                           value="${organization != null ? organization.homePage.name : ""}"></dd>
                                <br/>
                                <dt>Сайт оргазинации</dt>
                                <dd><input type="text" name="${type.name()}_siteName" size="30"
                                           value="${organization != null ? organization.homePage.url : ""}"></dd>
                            </dl>
                            <h5>Добавить новую позицию</h5>
                            <dl>
                                <dt>Дата начала</dt>
                                <dd><input type="text" name="${type.name()}_${counter.count}_startDate"
                                           size="30" placeholder="Пример: 2000-01" value=""></dd>
                                <dt>Дата окончания</dt>
                                <dd><input type="text" name="${type.name()}_${counter.count}_endDate"
                                           size="30" placeholder="Пример: 2000-01" value="">
                                </dd>
                                <br/>
                                <dt>Позиция/Должность</dt>
                                <dd><input type="text" name="${type.name()}_${counter.count}_title"
                                           size="30" value=""></dd>
                                <br/>
                                <dt>Обяанности</dt>
                                <dd><textarea cols="100"
                                              name="${type.name()}_${counter.count}_description"
                                              rows="5"></textarea>
                                </dd>
                            </dl>
                            <h5>Текущие позиции</h5>
                            <c:forEach var="orgItem" items="${organization.positions}">
                                <dl>
                                    <dt>Дата начала</dt>
                                    <fmt:parseDate value="${orgItem.startDate}" type="date" pattern="yyyy-MM-dd"
                                                   var="parsedStart"/>
                                    <fmt:formatDate value="${parsedStart}" type="date" pattern="yyyy-MM"
                                                    var="start"/>
                                    <dd><input type="text"
                                               name="${type.name()}_${counter.count}_startDate"
                                               size="30"
                                               value="${orgItem.startDate != null ? start : ""}"></dd>
                                    <dt>Дата окончания</dt>
                                    <fmt:parseDate value="${orgItem.endDate}" type="date" pattern="yyyy-MM-dd"
                                                   var="parsedEnd"/>
                                    <fmt:formatDate value="${parsedEnd}" type="date" pattern="yyyy-MM" var="end"/>
                                    <dd><input type="text"
                                               name="${type.name()}_${counter.count}_endDate" size="30"
                                               value="${orgItem.endDate.year > 2500 ? 'настоящее время' : orgItem.endDate != null ?  end : ""}">
                                    </dd>
                                    <br/>
                                    <dt>Позиция/Должность</dt>
                                    <dd><input type="text" name="${type.name()}_${counter.count}_title"
                                               size="30"
                                               value="${orgItem != null ? orgItem.title : ""}"></dd>
                                    <br/>
                                    <dt>Обяанности</dt>
                                    <dd><textarea cols="100"
                                                  name="${type.name()}_${counter.count}_description"
                                                  rows="5">${orgItem != null ? orgItem.description : ""}</textarea>
                                    </dd>
                                </dl>
                            </c:forEach>
                            <c:set var="orgItem" value="${null}"/>
                            <hr/>
                        </c:forEach>
                        <c:set var="organization" value="${null}"/>
                    </c:if>
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