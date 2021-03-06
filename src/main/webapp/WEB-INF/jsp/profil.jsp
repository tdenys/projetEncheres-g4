<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.projetenchere.bo.Utilisateur" %>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"/>
<body>
	<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>
	<div class="text-center bg-light" style="margin-left: 10px; margin-right: 10px;">
	
		<h2 style="font-family: cursive; margin-top: 25px;">Pseudo : ${ u.pseudo }</h2>
		<p class="text-justify" style="margin-left: 25%; margin-right: 25%;">
			Nom : ${ u.nom }<br/>
			Prénom : ${ u.prenom }<br/>
			Email : ${ u.email }<br/>
			Téléphone : ${ u.telephone }<br/>
			Rue : ${ u.rue }<br/>
			Code Postal : ${ u.code_postal }<br/>
			Ville : ${ u.ville }<br/>
		</p>
		
		<!-- Si c'est mon compte j'affiche le bouton modifier -->
		<c:if test="${isProfil}">
			<a href="${pageContext.request.contextPath}/profil/modifier" class="btn btn-lg btn-dark text-light">Modifier</a>
		</c:if>
		
	</div>

	<jsp:include page="/WEB-INF/fragments/footer.jsp"/>
</body>
</html>