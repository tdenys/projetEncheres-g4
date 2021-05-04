<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"/>
<%! String erreur; %>
<% erreur =  (String)request.getAttribute("erreur"); %>
<body style="margin-left: 10px; margin-right: 10px;">

	<div class="row text-center">
		<div class="col">
			<h5>Liste des enchères</h5>
		</div>
	</div>
	
	<div class="row">
		<form method="POST" action="">
			<!-- FORMULAIRE -->
			<div class="col">
				<b>Filtres :</b>
				
				<!-- RECHERCHE NOM -->
				<input type="text" class="form-control" id="rechercheNom" name="rechercheNom" aria-describedby="Le nom de l'article contient" required>
			
				<!-- CATEGORIE -->
				<label for="rechercheNom">Catégorie : </label>
				<select class="form-control" id="rechercheNom">
			      <option value="1">Catégorie 1</option>
			    </select>
			    
			    <div class="row">
			    	<div class="col">
				    	<!-- RADIOS BUTTONS -->
				    	<div class="form-check">
					    	<input class="form-check-input" type="radio" name="type" id="radio1" value="achats" checked>
							<label class="form-check-label" for="radio1">Achats</label>
						</div>
						<!-- CHECKBOX -->
						<div class="form-check">
							<input class="form-check-input" type="checkbox" id="encheresOuvertes" name="encheresOuvertes" value="encheresOuvertes">
							<label class="form-check-label" for="encheresOuvertes">Enchères ouvertes</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox" id="mesEncheres" name="mesEncheres" value="mesEncheres">
							<label class="form-check-label" for="mesEncheres">Mes enchères</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox" id="mesEncheresRemportees" name="mesEncheresRemportees" value="mesEncheresRemportees">
							<label class="form-check-label" for="mesEncheresRemportees">Mes Encheres Remportees</label>
  						</div>
			    	</div>
			    	<div class="col">
				    	<!-- RADIOS BUTTONS -->
				    	<div class="form-check">
					    	<input class="form-check-input" type="radio" name="type" id="radio2" value="ventes" checked>
							<label class="form-check-label" for="radio2">Mes ventes</label>
						</div>
						<!-- CHECKBOX -->
						<div class="form-check">
							<input class="form-check-input" type="checkbox" id="mesVentesEnCours" name="mesVentesEnCours" value="mesVentesEnCours" disabled>
							<label class="form-check-label" for="mesVentesEnCours">Mes ventes en cours</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox" id="ventesNonDebutees" name="ventesNonDebutees" value="ventesNonDebutees" disabled>
							<label class="form-check-label" for="ventesNonDebutees">Ventes non débutées</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="checkbox" id="ventesTerminees" name="ventesTerminees" value="ventesTerminees" disabled>
							<label class="form-check-label" for="ventesTerminees">Ventes terminées</label>
			    		</div>
			    	</div>
			    </div>
			    
			
			</div>
			
			<!-- RECHERCHER -->
			<div class="col">
				<input type="submit" name="Rechercher" class="btn btn-lg btn-dark text-light">
			</div>
		</form>
	</div>
	
	<!-- RESULATATS DE RECHERCHE -->
	<div class="row">
		<div class="col">
			
			<div class="card" style="width: 18rem;">
			  <img class="card-img-top" src="https://img.icons8.com/ios/452/picture.png" />
			  <div class="card-body">
			    <h5 class="card-title">Titre du produit</h5>
			    <p class="card-text">
			    	Prix : 000€<br/>
			    	Fin de l'enchère : 00/00/0000<br/>
			    	<br/>
			    	Vendeur : <a href="">nom</a>
			    </p>
			  </div>
			</div>
			
		</div>
	</div>

</body>
</html>