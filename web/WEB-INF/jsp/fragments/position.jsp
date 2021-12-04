<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<dl>
    <dt>Дата начала</dt>
    <fmt:parseDate value="${orgItem.startDate}" type="date" pattern="yyyy-MM-dd"
                   var="parsedStart"/>
    <fmt:formatDate value="${parsedStart}" type="date" pattern="yyyy-MM" var="start"/>
    <dd><input type="text" name="startDate${OrgName}" size="30"
               value="${orgItem.startDate != null ? start : ""}"></dd>
    <dt>Дата окончания</dt>
    <fmt:parseDate value="${orgItem.endDate}" type="date" pattern="yyyy-MM-dd"
                   var="parsedEnd"/>
    <fmt:formatDate value="${parsedEnd}" type="date" pattern="yyyy-MM" var="end"/>
    <dd><input type="text" name="endDate${itemCount.count}" size="30"
               value="${orgItem.endDate.year > 2500 ? 'настояшее время' : orgItem.endDate != null ?  end : ""}">
    </dd>
    <br/>
    <dt>Позиция/Должность</dt>
    <dd><input type="text" name="title${itemCount.count}" size="30"
               value="${orgItem != null ? orgItem.title : ""}"></dd>
    <br/>
    <dt>Обяанности</dt>
    <dd><textarea cols="100" name="description${itemCount.count}"
                  rows="5">${orgItem != null ? orgItem.description : ""}</textarea></dd>
</dl>
</body>
</html>
