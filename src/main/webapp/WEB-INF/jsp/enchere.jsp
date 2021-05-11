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
				<img src="https://source.unsplash.com/400x400/?${a.nom_article}" width="400px"/>
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
				
				<!-- SI JE SUIS LE CRETEUR DE LA VENTE ET QU'AUCUNE ENCHERE A ETE FAITE -->
				<c:if test="${a.utilisateur.pseudo == u.pseudo && a.prix_vente == a.prix_initial}">
					<form method="POST" action="${pageContext.request.contextPath}/enchere?id=${a.no_article}">
						<!-- NOM DE L'ARTICLE -->
						<label for="article">Article : </label>
						<input type="text" class="form-control" id="article" maxlength="30" name="article" value="${a.nom_article}" required>
						<!-- DESCRIPTION -->
						<label for="description">Description : </label>
    					<textarea class="form-control" id="description" maxlength="300" name="description" rows="3">${a.description}</textarea>
						<!-- CATEGORIE -->
						<label for="categorie">Catégorie : </label>
						<select class="form-control" name="categorie" id="categorie">
							<c:forEach var="c" items="${listeCategories}">
								<c:if test="${c.no_categorie == a.categorie.no_categorie}">
						     		<option value="${c.no_categorie}" selected>${c.libelle}</option>
						     	</c:if>
						     	<c:if test="${c.no_categorie != a.categorie.no_categorie}">
						     		<option value="${c.no_categorie}">${c.libelle}</option>
						     	</c:if>
						     </c:forEach>
					    </select>
						<!-- MISE A PRIX -->
						<label for="prix">Mise à prix : </label>
						<input type="number" class="form-control" id="prix" name="prix" value="${a.prix_initial}" required>
						<!-- DEBUT ENCHERE -->
						<label for="debutEnchere">Début de l'enchère : </label>
						<input type="date" class="form-control" id="debutEnchere" name="debutEnchere" value="${a.date_debut_encheres}" required>
						<!-- FIN ENCHERE -->
						<label for="finEnchere">Fin de l'enchère : </label>
						<input type="date" class="form-control" id="finEnchere" name="finEnchere" value="${a.date_fin_encheres}" required>
						<h5>---------- Retrait ----------</h5>	
						<!-- RUE -->
						<label for="rue">Rue : </label>
						<input type="text" class="form-control" id="rue" maxlength="30" name="rue" value="${r.rue}" required>
						<!-- CODE POSTAL -->
						<label for="codePostal">Code Postal : </label>
						<input type="text" class="form-control" id="codePostal" maxlength="10" name="codePostal"  value="${r.code_postal}" required>
						<!-- VILLE -->
						<label for="ville">Ville : </label>
						<input type="text" class="form-control" id="ville" maxlength="50" name="ville"  value="${r.ville}" required>
						<!-- SUBMIT -->
						<input type="submit" class="btn btn-dark text-light" value="Enregistrer" />
					</form>
				</c:if>
				<!-- SI JE SUIS UN ACHETEUR -->
				<c:if test="${a.utilisateur.pseudo != u.pseudo || a.prix_vente != a.prix_initial}">
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
				</c:if>
				
				<c:if test="${!termine && a.utilisateur.pseudo != u.pseudo && commence}">
					<!-- MA PROPOSITION -->
					<form method="POST" action="${pageContext.request.contextPath}/enchere?id=${a.no_article}">
						<!-- PRIX PROPOSE -->
						<label for="proposition">Ma proposition : </label>
						<input type="number" class="form-control" id="proposition" name="proposition" required>
						
						<input type="submit" class="btn btn-dark text-light" name="encherir" value="Enchérir" />
					</form>
				</c:if>
				<c:if test="${!commence}">
					<div class="row">
						<div class="col">
							<div style="margin-top: 10px;" class="alert alert-warning" role="alert">
							  La vente n'a pas encore débuté, un peu de patience :)
							</div>
						</div>
					</div>
				</c:if>
				<a href="" class="btn btn-dark text-light">Retour</a>    
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