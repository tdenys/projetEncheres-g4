<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"/>
<body>
	<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>
	<div class="text-center bg-light" style="margin-left: 10px; margin-right: 10px;">
		<div style="margin: 10px;">
			<h2 style="font-family: cursive; margin-top: 25px;">Réinitialiser le mot de passe</h2>
			<form method="POST" action="${pageContext.request.contextPath}/resetMdp?pseudo=${pseudo}&token=${mdpCrypt}">
				<c:if test="${pseudo != null}">
					<!-- MOT DE PASSE -->
					<label for="nouveauMotDePasse">Nouveau mot de passe : </label>
					<input type="password" class="form-control" id="nouveauMotDePasse" name="nouveauMotDePasse" maxlength="30">
					<!-- CONFIRAMATION MOT DE PASSE -->
					<label for="motDePasseConfirmation">Confirmation : </label>
					<input type="password" class="form-control" id="motDePasseConfirmation" name="motDePasseConfirmation">
				</c:if>
				<c:if test="${pseudo == null}">
					<!-- MOT DE PASSE -->
					<label for="pseudoSaisi">Pseudo : </label>
					<input type="text" class="form-control" id="pseudoSaisi" name="pseudoSaisi" maxlength="30">
				</c:if>
				<!-- CONFIRM -->
				<input type="submit" class="btn btn-dark text-light" value="Confirmer" />
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
			
			<c:if test="${!empty mail}">
				<div class="row">
					<div class="col">
						<div style="margin-top: 10px;" class="alert alert-success" role="alert">
						  	<a href="${ mail }" class="text-success"><b>${ mail }</b></a>
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</div>

	<jsp:include page="/WEB-INF/fragments/footer.jsp"/>
</body>
</html>