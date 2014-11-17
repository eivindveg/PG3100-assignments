<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="albums" scope="request"
             type="java.util.List<no.westerdals.student.vegeiv13.innlevering3.models.Album>"/>
<jsp:useBean id="genre" scope="request"
             type="java.lang.String"/>
<html>
<head>
    <title>Innlevering3</title>
</head>
<body>
<section>
    <h1>Recommended music</h1>

    <h2>by vegeiv13</h2>
</section>
<section>
    <div>
        <p>
            You've chosen ${genre}; perhaps you should try one of these albums?
        </p>
        <ul>
            <c:forEach items="${albums}" var="album">
            <li>${album}</li>
            </c:forEach>
        </ul>
    </div>

    <div>
        <a href="${pageContext.request.contextPath}/music">Nytt søk</a>
    </div>
</section>

</body>
</html>
