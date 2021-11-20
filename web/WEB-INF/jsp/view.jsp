<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                <% SectionType key = sections.getKey(); %>
    <h3><%=key.getTitle()%>
    </h3>
    <%
        if (key == SectionType.PERSONAL || key == SectionType.OBJECTIVE) {
            out.println(((TextSection) sections.getValue()).getContent());
        }
        out.println();
        if (key == SectionType.ACHIEVEMENT || key == SectionType.QUALIFICATIONS) {
            StringBuilder all = new StringBuilder();
            for (String string : ((ListSection) sections.getValue()).getItems()) {
                all.append("<li>").append(string).append("</li>");
            }
            out.println(all);
        }
    %>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
