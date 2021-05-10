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
				<img src="http://placeimg.com/400/400/${a.nom_article}" width="400px"/>
			</div>
			<div class="col text-left" style="margin: 10px;">
			
				<!-- SI ENCHERE TERMINEE ON AFFICHE LE GAGNANT -->
				<c:if test="${termine}">
					<!-- SI J'AI GAGNE -->
					<c:if test="${win.pseudo == u.pseudo}">
						<b>Vous avez remporté la vente</b>
					</c:if>
					<!-- SI JE N'AI PAS GAGNE -->
					<c:if test="${win.pseudo != u.pseudo}">
						<b>${win.pseudo} a remporté la vente</b>
					</c:if>
				</c:if>
				
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
				<p>Meilleur offre : ${a.prix_vente}</p>    
			    <!-- MISE A PRIX -->
				<p>Mise à prix : ${a.prix_initial}</p>				
				<!-- FIN ENCHERE -->
				<p>Fin de l'enchère : ${a.date_fin_encheres}</p>
				<!-- RETRAIT -->
				<p class="text-justify">
					Retrait :<br/>
					${r.rue}<br/>
					${r.code_postal} 
					${r.ville}
				</p>
				<!-- VENDEUR -->
				<p>Vendeur : ${a.utilisateur.pseudo}</p>
				
				<c:if test="${!termine && a.utilisateur.pseudo != u.pseudo}">
					<!-- MA PROPOSITION -->
					<form method="POST" action="${pageContext.request.contextPath}/enchere?id=${a.no_article}">
						<!-- PRIX PROPOSE -->
						<label for="proposition">Ma proposition : </label>
						<input type="number" class="form-control" id="proposition" name="proposition" required>
						
						<input type="submit" class="btn btn-dark text-light" name="encherir" value="Enchérir" />
					</form>
				</c:if>
				<c:if test="${termine}">
					<a href="" class="btn btn-dark text-light">Retour</a> 
				</c:if>
				    
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