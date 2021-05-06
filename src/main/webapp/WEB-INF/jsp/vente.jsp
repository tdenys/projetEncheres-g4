<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.projetenchere.bo.Utilisateur" %>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"/>
<body>
	<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>
	<div class="text-center bg-light" style="margin: 10px;">
	
		<div class="row">
			<div class="col text-center">
				<h2 style="font-family: cursive;">Nouvelle vente</h2>
			</div>
		</div>
		
		<div class="row" style="margin: 10px;">
			<div class="col text-center" style="margin: 10px;">
				<img src="https://img.icons8.com/ios/452/picture.png" width="250px"/>
			</div>
			<div class="col text-left" style="margin: 10px;">
				<form method="POST" action="${pageContext.request.contextPath}/vente">
				
					<!-- NOM DE L'ARTICLE -->
					<label for="article">Article : </label>
					<input type="text" class="form-control" id="article" maxlength="30" name="article" required>
					
					<!-- DESCRIPTION -->
					<label for="description">Description : </label>
    				<textarea class="form-control" id="description" maxlength="300" name="description" rows="3"></textarea>
    				
    				<!-- CATEGORIE -->
					<label for="categorie">Catégorie : </label>
					<select class="form-control" id="categorie">
						<c:forEach var="c" items="${listeCategories}">
					     	<option value="${c.no_categorie}">${c.libelle}</option>
					     </c:forEach>
				    </select>
				    
				    <!-- PHOTO (TODO) -->
				    
				    <!-- MISE A PRIX -->
					<label for="prix">Mise à prix : </label>
					<input type="number" class="form-control" id="prix" name="prix" required>
					
					<!-- DEBUT ENCHERE -->
					<label for="debutEnchere">Début de l'enchère : </label>
					<input type="date" class="form-control" id="debutEnchere" name="debutEnchere" required>
					
					<!-- FIN ENCHERE -->
					<label for="finEnchere">Fin de l'enchère : </label>
					<input type="date" class="form-control" id="finEnchere" name="finEnchere" required>
					
					<h5>---------- Retrait ----------</h5>
					
					<!-- RUE -->
					<label for="rue">Rue : </label>
					<input type="text" class="form-control" id="rue" maxlength="30" name="rue" value="${u.rue }" required>
					
					<!-- CODE POSTAL -->
					<label for="codePostal">Rue : </label>
					<input type="text" class="form-control" id="codePostal" maxlength="10" name="codePostal"  value="${u.code_postal }" required>
					
					<!-- VILLE -->
					<label for="ville">Rue : </label>
					<input type="text" class="form-control" id="ville" maxlength="50" name="ville"  value="${u.ville }" required>
					
					<div class="row">
						<div class="col">
							<input type="submit" class="btn btn-dark text-light" name="enregistrer" value="Enregistrer" />
						</div>
						<div class="col">
							<a href="" class="btn btn-dark text-light">Annuler</a>
						</div>
					</div>
				    
				</form>
			</div>
		</div>
		
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
		
		<!-- Affichage du message de success -->
		<c:if test="${!empty success}">
			<div class="row">
				<div class="col">
					<div style="margin-top: 10px;" class="alert alert-success" role="alert">
					  ${ success }
					</div>
				</div>
			</div>
		</c:if>
		
	</div>

</body>
</html>