<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<jsp:include page="/WEB-INF/fragments/head.jsp"/>
	<%! String erreur; %>
	<% erreur =  (String)request.getAttribute("erreur"); %>
	<body style="margin-left: 10px; margin-right: 10px;">
	
		<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>
	
		<div class="row text-center" style="margin-top: 10px;">
			<div class="col">
				<!-- Formulaire de connexion -->
				<form method="post" action="connexion">
					<!-- USER -->
					<label for="user">Identifiant</label>
					<input type="text" required class="form-control" id="user" name="user">
					
					<!-- PASSWORD -->
					<label for="password">Mot de passe</label>
					<input type="password" required class="form-control" id="password" name="password">
					
					<!-- Affichage message d'erreur -->
					<%
						if(erreur != null)
						{
					%>
					<div style="margin-top: 10px;" class="alert alert-danger" role="alert">
					  <%= erreur %>
					</div>
					<%
						}
					%>
					
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
		
	</body>
</html>