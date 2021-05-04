<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<jsp:include page="/WEB-INF/fragments/head.jsp"/>
	<%! String erreur; %>
	<% erreur =  (String)request.getAttribute("erreur"); %>
	<body style="margin: 25px;">
	
		<div class="row text-left">
			<div class="col">
				<h1>ENI - Enchères</h1>
			</div>
		</div>
	
		<div class="row text-center">
			<div class="col">
				<!-- Formulaire de connexion -->
				<form method="post" action="Connexion">
					<!-- USER -->
					<label for="user">Identifiant</label>
					<input type="text" class="form-control" id="user" name="user">
					
					<!-- PASSWORD -->
					<label for="password">Mot de passe</label>
					<input type="password" class="form-control" id="password" name="password">
					
					<!-- SUBMIT -->
					<input type="submit" class="btn btn-dark text-light" value="Connexion"/>	
				</form>
				
				<!-- Affichage message d'erreur -->
				<%
					if(erreur != null)
					{
				%>
				<div class="alert alert-danger" role="alert">
				  <%= erreur %>
				</div>
				<%
					}
				%>
				
			</div>
		</div>
		
		<div class="row text-center">
			<div class="col">
				<!-- Bouton création de compte -->
				<a href="${pageContext.request.contextPath}/signin" class="btn btn-lg btn-dark text-light">Créer un compte</a>
			</div>
		</div>
		
	</body>
</html>