<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<jsp:include page="/WEB-INF/fragments/head.jsp"/>
	<body style="background-image: url('https://i0.hippopx.com/photos/164/964/28/background-board-brown-carpentry-preview.jpg');">
		<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>
		<div style="margin-left: 25%; margin-right: 25%;">
			<div class="row text-center bg-light" style="margin-top: 100px;">
				<div class="col">
					<!-- Formulaire de connexion -->
					<form method="post" action="${pageContext.request.contextPath}/connexion">
						<!-- USER -->
						<label for="user">Identifiant</label>
						<input type="text" required class="form-control" id="user" name="user">
						
						<!-- PASSWORD -->
						<label for="password">Mot de passe</label>
						<input type="password" required class="form-control" id="password" name="password">
						
						<!-- Affichage message d'erreur -->
						<c:if test="${!empty erreur}">
							<div style="margin-top: 10px;" class="alert alert-danger" role="alert">
							  ${ erreur }
							</div>
						</c:if>
						
						<!-- SUBMIT -->
						<input type="submit" class="btn btn-dark text-light" value="Connexion"/>	
					</form>
					
				</div>
			</div>
			
			<div class="row text-center">
				<div class="col">
					<!-- Bouton de création de compte -->
					<a href="${pageContext.request.contextPath}/inscription" class="btn btn-lg btn-dark text-light">Créer un compte</a>
				</div>
			</div>
		</div>
		
	</body>
</html>