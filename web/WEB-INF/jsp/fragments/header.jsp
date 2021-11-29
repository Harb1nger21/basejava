<%--
  Created by IntelliJ IDEA.
  User: Victoriya
  Date: 13.11.2021
  Time: 20:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.UUID" %>
<header> <a href="resume">Управление резюме</a>
    <button type="submit" onclick="window.location.href = 'resume?uuid=<%=UUID.randomUUID().toString()%>&action=add'">
        Добавить
    </button>
</header>
