<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"/>
<body>
	<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>
		<div style="margin-left: 10px; margin-right: 10px;" class="bg-light">

		<div class="row text-center" style="margin-top: 10px;">
			<div class="col">
				<h2 style="font-family: cursive;">Liste des enchères</h2>
			</div>
		</div>
		
		<form method="POST" action="${pageContext.request.contextPath}/" style="margin: 10px;">
			<div class="row">
				<!-- FORMULAIRE -->
				<div class="col-lg-8">
				
					<b>Filtres :</b>
					
					<!-- RECHERCHE NOM -->
					<input type="text" class="form-control" id="rechercheNom" name="rechercheNom" value="${nom}" placeholder="Le nom de l'article contient">
					<br/>
					
					<!-- CATEGORIE -->
					<label for="categorie">Catégorie : </label>
					<select class="form-control" name="categorie" id="categorie">
						<option value="0">Tous</option>
						<c:forEach var="c" items="${listeCategories}">
							<c:if test="${c.no_categorie == cat}">
					     		<option value="${c.no_categorie}" selected>${c.libelle}</option>
					     	</c:if>
					     	<c:if test="${c.no_categorie != cat}">
					     		<option value="${c.no_categorie}">${c.libelle}</option>
					     	</c:if>
					     </c:forEach>
				    </select>
				    <br/>
				    
				    <c:if test="${!empty u}">
					    <div class="row">
					    	<div class="col">
						    	<!-- RADIOS BUTTONS -->
						    	<div class="form-check">
						    		<c:if test="${affType1 != 'disabled'}">
							    		<input class="form-check-input" type="radio" name="type" id="radio1" value="achats" checked>
									</c:if>
									<c:if test="${affType1 == 'disabled'}">
							    		<input class="form-check-input" type="radio" name="type" id="radio1" value="achats">
									</c:if>
									<label class="form-check-label" for="radio1">Achats</label>
								</div>
								<br/>
								<!-- CHECKBOX -->
								<div class="form-check">
									<input class="form-check-input" type="checkbox" id="encheresOuvertes" name="encheresOuvertes" value="encheresOuvertes" ${affParam1} ${affType1}>
									<label class="form-check-label" for="encheresOuvertes">Enchères ouvertes</label>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="checkbox" id="mesEncheres" name="mesEncheres" value="mesEncheres" ${affParam2} ${affType1}>
									<label class="form-check-label" for="mesEncheres">Mes enchères</label>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="checkbox" id="mesEncheresRemportees" name="mesEncheresRemportees" value="mesEncheresRemportees" ${affParam3} ${affType1}>
									<label class="form-check-label" for="mesEncheresRemportees">Mes Encheres Remportees</label>
		  						</div>
					    	</div>
					    	<div class="col">
						    	<!-- RADIOS BUTTONS -->
						    	<div class="form-check">
							    	<c:if test="${affType2 != 'disabled'}">
								    	<input class="form-check-input" type="radio" name="type" id="radio2" value="ventes" checked>
									</c:if>
									<c:if test="${affType2 == 'disabled'}">
								    	<input class="form-check-input" type="radio" name="type" id="radio2" value="ventes">
									</c:if>		
									<label class="form-check-label" for="radio2">Mes ventes</label>
								</div>
								<br/>
								<!-- CHECKBOX -->
								<div class="form-check">
									<input class="form-check-input" type="checkbox" id="mesVentesEnCours" name="mesVentesEnCours" value="mesVentesEnCours" ${affParam4} ${affType2}>
									<label class="form-check-label" for="mesVentesEnCours">Mes ventes en cours</label>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="checkbox" id="ventesNonDebutees" name="ventesNonDebutees" value="ventesNonDebutees" ${affParam5} ${affType2}>
									<label class="form-check-label" for="ventesNonDebutees">Ventes non débutées</label>
								</div>
								<div class="form-check">
									<input class="form-check-input" type="checkbox" id="ventesTerminees" name="ventesTerminees" value="ventesTerminees" ${affParam6} ${affType2}>
									<label class="form-check-label" for="ventesTerminees">Ventes terminées</label>
					    		</div>
					    	</div>
					    </div>
				    </c:if>
				    
				
				</div>
				
				<!-- RECHERCHER -->
				<div class="col-lg-4 text-center">
					<input type="submit" name="Rechercher" class="btn btn-lg btn-dark text-light" value="Rechercher" style="margin-top: 50px;">
				</div>
			</div>
		</form>
		
		<hr/>
		
		<!-- RESULATATS DE RECHERCHE --> 
		<div class="row" style="margin: 10px;">
			<c:forEach var="a" items="${listeArticles}">
				<div class="col-sm-4">
					
					<div class="card" style="width: 18rem;">
					  <img class="card-img-top" src="https://source.unsplash.com/250x200/?${a.nom_article}" width="18rem" /> 
					  <div class="card-body">
					    <h5 class="card-title"><a href="${pageContext.request.contextPath}/enchere?id=${a.no_article}">${a.nom_article}</a></h5>
					    <p class="card-text">
					    	Prix : ${a.prix_vente}<br/>
					    	Fin de l'enchère : ${a.date_fin_encheres}<br/>
					    	<br/>
					    	Vendeur : <a href="${pageContext.request.contextPath}/profil?p=${a.utilisateur.pseudo}">${a.utilisateur.pseudo}</a>
					    </p>
					  </div>
					</div>
				
				</div>
			</c:forEach>
			<c:if test="${empty listeArticles}">
				<div style="margin-top: 10px;" class="alert alert-warning" role="alert">
				  Aucune enchère ne correspond à la recherche
				</div>
			</c:if>
		</div>
	</div>
	
	<!-- Affichage du message d'erreur -->
	<c:if test="${!empty erreur}">
		<div class="row text-center">
			<div class="col">
				<div style="margin-top: 10px;" class="alert alert-danger" role="alert">
				  ${ erreur }
				</div>
			</div>
		</div>
	</c:if>

</body>
<script>
if(document.querySelector('input#radio1')){
	document.querySelector('input#radio1').addEventListener('click', function(){
		if(document.querySelector('input#radio1').checked){
			document.querySelector('input#encheresOuvertes').disabled = false;
			document.querySelector('input#mesEncheres').disabled = false;
			document.querySelector('input#mesEncheresRemportees').disabled = false;
			document.querySelector('input#mesVentesEnCours').disabled = true;
			document.querySelector('input#ventesNonDebutees').disabled = true;
			document.querySelector('input#ventesTerminees').disabled = true;
			document.querySelector('input#mesVentesEnCours').checked = false;
			document.querySelector('input#ventesNonDebutees').checked = false;
			document.querySelector('input#ventesTerminees').checked = false;
		}
	});
}
if(document.querySelector('input#radio2')){
	document.querySelector('input#radio2').addEventListener('click', function(){
		if(document.querySelector('input#radio2').checked){
			document.querySelector('input#encheresOuvertes').disabled = true;
			document.querySelector('input#mesEncheres').disabled = true;
			document.querySelector('input#mesEncheresRemportees').disabled = true;
			document.querySelector('input#mesVentesEnCours').disabled = false;
			document.querySelector('input#ventesNonDebutees').disabled = false;
			document.querySelector('input#ventesTerminees').disabled = false;
			document.querySelector('input#encheresOuvertes').checked = false;
			document.querySelector('input#mesEncheres').checked = false;
			document.querySelector('input#mesEncheresRemportees').checked = false;
		}
	});
}
</script>
</html>