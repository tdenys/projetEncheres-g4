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
				<h2 style="font-family: cursive;">Détail vente</h2>
			</div>
		</div>
		
		<div class="row" style="margin: 10px;">
			<div class="col text-center" style="margin: 10px;">
				<img src="https://img.icons8.com/ios/452/picture.png" width="250px"/>
			</div>
			<div class="col text-left" style="margin: 10px;">
				
				<!-- NOM DE L'ARTICLE -->
				<p>${a.nom_article}</p>			
				<!-- DESCRIPTION -->
				<p class="text-justify">
					Description : 
					${a.description}
				</p>
   				<!-- CATEGORIE -->
				<p>Catégorie : ${a.categorie.libelle}</p>
				<!-- MEILLEUR OFFRE -->
				<p>Meilleur offre : ${prix_vente}</p>    
			    <!-- MISE A PRIX -->
				<p>Mise à prix : ${prix_initial}</p>				
				<!-- FIN ENCHERE -->
				<p>Fin de l'enchère : ${date_fin_encheres}</p>
				<!-- RETRAIT -->
				<p class="text-justify">
					Retrait :
					${r.rue}
					${r.code_postal}
					${r.ville}
				</p>
				<!-- VENDEUR -->
				<p>Vendeur : </p>
				<!-- MA PROPOSITION -->
				<form method="POST" action="${pageContext.request.contextPath}/enchere">
					<!-- PRIX PROPOSE -->
					<label for="proposition">Ma proposition : </label>
					<input type="number" class="form-control" id="proposition" name="proposition" required>
					
					<input type="submit" class="btn btn-dark text-light" name="encherir" value="Enchérir" />
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