<%@ page import="ru.javawebinar.basejava.model.Resume" %>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Table of resumes</title>
</head>
<body>
<table border="3">
    <tr style="font-size: 20px">
        <td align="center">uuid</td>
        <td align="center">full name</td>
    </tr>
    <%
        List<Resume> resumes = (List<Resume>) request.getAttribute("resumes");
        for (Resume resume : resumes) {
            out.println("<tr>");
            out.println("<td align=\"right\">" + resume.getUuid() + "</td>");
            out.println("<td align=\"right\">" + resume.getFullName() + "</td>");
            out.println("</tr>");
        }
    %>
</table>
</body>
</html>