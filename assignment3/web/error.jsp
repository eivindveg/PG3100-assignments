<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="<c:url value="./css/foundation.min.css" />" />
    <link type="text/css" rel="stylesheet" href="<c:url value="./css/normalize.css" />" />
</head>
<body>
<div>
    <p>
        The following error was received:<br>
        ${requestScope.error}
    </p>
</div>
</body>
</html>