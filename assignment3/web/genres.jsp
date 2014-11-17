<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="genres" scope="request"
             type="java.util.List<java.lang.String>"/>
<!DOCTYPE html>
<html>
<head>
    <title>Innlevering 3</title>
    <link type="text/css" rel="stylesheet" href="<c:url value="./css/foundation.min.css" />"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="./css/normalize.css" />"/>
</head>
<body>
<section>
    <h1>Recommended music</h1>

    <h4>by vegeiv13</h4>
</section>
<div class="row">

    <c:choose>
        <c:when test="${genres.size() > 0}">
            <div class="medium-4 columns text-center">
                <h2>Choose genre</h2>
                <form action="${pageContext.request.contextPath}/music" method="POST">
                    <label>
                        Genre:
                        <select name="genre">
                            <c:forEach items="${genres}" var="genre">
                                <option value="${genre}">${genre}</option>
                            </c:forEach>
                        </select>
                    </label>
                    <input type="submit" value="Find albums">
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <div class="medium-4 columns text-center">
                Could not find any music in database.
                ${genres.size()}
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
