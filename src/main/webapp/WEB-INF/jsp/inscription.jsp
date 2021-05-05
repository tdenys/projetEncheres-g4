<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"/>
<body style="margin-left: 10px; margin-right: 10px;">
	
	<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>
		
		<!-- Formulaire d'inscription -->
		<form method="POST" action="${pageContext.request.contextPath}/inscription" style="margin-top: 10px;">
		
			<div class="row">
				<div class="col text-center">
					<h6>Mon profil</h6>
				</div>
			</div>
			
			<div class="row text-center">
				<div class="col">
				
					<!-- PSEUDO -->
					<label for="pseudo">Pseudo : </label>
					<input type="text" class="form-control" id="pseudo" maxlength="30" name="pseudo" required>
					
					<!-- PRENOM -->
					<label for="prenom">Prénom : </label>
					<input type="text" class="form-control" id="prenom" name="prenom" maxlength="30" required>
					
					<!-- TELEPHONE -->
					<label for="telephone">Téléphone : </label>
					<input type="tel" class="form-control" id="telephone" name="telephone" maxlength="15" required>
					
					<!-- CODE POSTAL -->
					<label for="codePostal">Code postal : </label>
					<input type="text" class="form-control" id="codePostal" name="codePostal" maxlength="10" required>
					
					<!-- MOT DE PASSE -->
					<label for="motDePasse">Mot de passe : </label>
					<input type="password" class="form-control" id="motDePasse" name="motDePasse" maxlength="30" required>
					 
				</div>
				
				<div class="col">
				
					<!-- NOM -->
					<label for="nom">Nom : </label>
					<input type="text" class="form-control" id="nom" name="nom" maxlength="30" required>
					
					<!-- EMAIL -->
					<label for="email">Email : </label>
					<input type="email" class="form-control" id="email" name="email" maxlength="50" required>
					
					<!-- RUE -->
					<label for="rue">Rue : </label>
					<input type="text" class="form-control" id="rue" name="rue" maxlength="30" required>
					
					<!-- VILLE -->
					<label for="ville">Ville : </label>
					<input type="text" class="form-control" id="ville" name="ville" maxlength="50" required>
					
					<!-- CONFIRAMATION MOT DE PASSE -->
					<label for="motDePasseConfirmation">Confirmation : </label>
					<input type="password" class="form-control" id="motDePasseConfirmation" name="motDePasseConfirmation" required>
					
				</div>
				
			</div>
			
			<div class="row text-center">
				<div class="col">
					<input type="submit" class="btn btn-lg btn-dark text-light" value="Créer" />
				</div>
				<div class="col">
					<a href="" class="btn btn-lg btn-dark text-light">Annuler</a>
				</div>
			</div>
				
		</form>
		
		<!-- Affichage du message d'erreur -->
		<c:if test="${!empty erreur}">
			<div class="row">
				<div class="col">
					<div style="margin-top: 10px;" class="alert alert-danger" role="alert">
					  ${ erreur }
					</div>
				</div>
			</div>
		</c:if>
		

</body>
</html>