<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:template>
    <jsp:body>

    <h1><c:out value="${message}"></c:out></h1>

    <table>
        <thead>
        <tr>
            <th>path</th>
            <th>methods</th>
            <th>params</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${endPoints}" var="endPoint">
            <tr>
                <td>${endPoint.patternsCondition}</td>
                <td>${endPoint.methodsCondition}</td>
                <td>${endPoint.paramsCondition}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    </jsp:body>
</tags:template>
