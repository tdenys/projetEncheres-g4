<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.projetenchere.bo.Utilisateur" %>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"/>
<body style="margin-left: 10px; margin-right: 10px;" class="text-center">
	
	<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>
	
	<h6 style="margin-top: 25px;">Pseudo : ${ u.pseudo }</h6>
	<p>
		Nom : ${ u.nom }<br/>
		Prénom : ${ u.prenom }<br/>
		Email : ${ u.email }><br/>
		Téléphone : ${ u.telephone }<br/>
		Rue : ${ u.rue }><br/>
		Code Postal : ${ u.code_postal }<br/>
		Ville : ${ u.ville }<br/>
	</p>
	
	<!-- Si c'est mon compte j'affiche le bouton modifier -->
	<c:if test="${isProfil}">
		<a href="" class="btn btn-lg btn-dark btn-light">Modifier</a>
	</c:if>

</body>
</html>