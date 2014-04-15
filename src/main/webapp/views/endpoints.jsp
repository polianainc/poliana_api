<%@ include file="/WEB-INF/views/include.jsp" %>

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
    <c:forEach items="${endpoints}" var="endPoint">
        <tr>
            <th>path</th>
            <th>methods</th>
            <th>params</th>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
