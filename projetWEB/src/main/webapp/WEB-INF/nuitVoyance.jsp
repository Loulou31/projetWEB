<%-- 
    Document   : nuitVoyance
    Created on : Apr 4, 2017, 4:38:20 PM
    Author     : nicolasl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Voyance</title>
    </head>
    <body>
        <h1>Choisissez le joueur dont vous voulez connaître le rôle et le pouvoir.</h1>
        <SELECT name="choixVoyance" size="1">
                    <c:forEach items="${vivants}" var="vivants"> 
                        <OPTION><c:out value="${vivants.pseudo}" />
                    </c:forEach>
         </SELECT>
        <input type="submit" value="Valider la décision" />
        <input type="hidden" name="action" value="addChoixVoyant"/>
        <input type="hidden" name="voyant" value="voyant"/>
    </body>
</html>
