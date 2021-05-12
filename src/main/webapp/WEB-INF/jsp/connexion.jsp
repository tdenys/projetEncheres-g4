<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<jsp:include page="/WEB-INF/fragments/head.jsp"/>
	<body>
		<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>
		<div style="margin-left: 25%; margin-right: 25%;">
			<div class="row text-center bg-light" style="margin-top: 100px;">
				<div class="col">
					<!-- Formulaire de connexion -->
					<form method="post" action="${pageContext.request.contextPath}/connexion">
						<!-- USER -->
						<label for="user">Identifiant</label>
						<input type="text" required class="form-control" id="user" name="user" value="${pseudo}">
						
						<!-- PASSWORD -->
						<label for="password">Mot de passe</label>
						<input type="password" class="form-control" id="password" name="password" value="${mdp}">
						<div class="form-check">
							<input class="form-check-input" type="checkbox" id="saveUserCookies" name="saveUserCookies" value="saveUserCookies" ${saveUserCookiesOK}>
							<label class="form-check-label" for="saveUserCookies">Enregistrer ces identifiants</label>
		  				</div>
			
						<!-- Affichage message d'erreur -->
						<c:if test="${!empty erreur}">
							<div style="margin-top: 10px;" class="alert alert-danger" role="alert">
							  ${ erreur }
							</div>
						</c:if>
						<c:if test="${!empty success}">
							<div class="row">
								<div class="col">
									<div style="margin-top: 10px;" class="alert alert-success" role="alert">
									  ${ success }
									</div>
								</div>
							</div>
						</c:if>
						
						<!-- SUBMIT -->
						<input type="submit" class="btn btn-dark text-light" value="Connexion"/><br/>
						<a href="${pageContext.request.contextPath}/resetMdp">Mot de passe oublié</a>	
					</form>
					
					<div class="row text-center">
						<div class="col">
							<!-- Bouton de création de compte -->
							<a href="${pageContext.request.contextPath}/inscription" class="btn btn-dark text-light">Créer un compte</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>