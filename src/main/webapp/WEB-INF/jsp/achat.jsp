<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"/>
<body>
	<jsp:include page="/WEB-INF/fragments/navbar.jsp"/>
	<div class="text-center bg-light" style="margin: 10px;">
		<div style="margin:10px;">
			<h2 style="font-family: cursive; margin-top: 25px;">Achat de crédits</h2>
			<form method="POST" action="${pageContext.request.contextPath}/achat">
				<div class="row">
					<div class="col">
						<!-- NOMBRE DE CREDIT VOULU -->
						<label>Nombre de crédit : </label>
						<input type="number" id="credit" name="credit" required onkeyup="calculPrix()" style="width:100px;">
					</div>
					<div class="col">
						<div id="prixUNI">Prix unitaire : 0,09€</div>
					</div>
					<div class="col">
						<div id="prixTTC">Prix TTC : 0,00€</div>
					</div>
				</div>
				<div class="row">
					<!-- CARTE -->
					<div class="col">
						<div class="container bg-white text-left">
							<div style="margin: 10px;">
								<div class="row">
									<div class="col">
										<h3 class="text-center">Informations de paiement</h3>
									</div>
								</div>
								<div class="row">
									<div class="col">
										<!-- NUMERO DE CB -->
										<label for="ccn">Numéro de la carte :</label><br/>
										<input class="form-control" id="ccn" type="tel" name="ccn" inputmode="numeric" pattern="[0-9\s]{16}" maxlength="16" placeholder="xxxx xxxx xxxx xxxx">
									</div>
								</div>
								<div class="row">
									<div class="col">
										<!-- DATE EXPIRATION -->
										<label for="dateExp">Date d'exiration : </label><br/>
										<div class="row">
											<div class="col-5 text-right">
												<select class="form-control" name="monthExp" id="dateExp">
								                    <option value="01">Janvier</option>
								                    <option value="02">Février </option>
								                    <option value="03">Mars</option>
								                    <option value="04">Avril</option>
								                    <option value="05">Mai</option>
								                    <option value="06">Juin</option>
								                    <option value="07">Juillet</option>
								                    <option value="08">Août</option>
								                    <option value="09">Septembre</option>
								                    <option value="10">Octobre</option>
								                    <option value="11">Novembre</option>
								                    <option value="12">Decembre</option>
								                </select>
							                </div>
							                <div class="col-2 text-center">/</div>
							                <div class="col-5 text-left">
								                <select class="form-control" name="yearExp" id="dateExp">
								                    <option value="21"> 2021</option>
								                    <option value="22"> 2022</option>
								                    <option value="23"> 2023</option>
								                    <option value="24"> 2024</option>
								                    <option value="25"> 2025</option>
								                    <option value="26"> 2026</option>
								                </select>
							                </div>
						                </div>
									</div>
									<div class="col">
										<!-- CVV -->
										<label for="cvv">CVV :</label><br/>
										<input class="form-control" id="cvv" type="tel" name="cvv" inputmode="numeric" pattern="[0-9\s]{3}" maxlength="3">
									</div>
								</div>
								<div class="row">
									<div class="col">
										<!-- TITULAIRE -->
										<label for="titulaire">Titulaire :</label><br/>
										<input class="form-control" id="titulaire" type="text" name="titulaire">
									</div>
								</div>
							</div>
							<div class="row text-center" style="margin-bottom: 10px;">
								<div class="col">
									<input type="submit" class="btn btn-block btn-dark text-light text-center" value="Payer" />
								</div>
							</div>		
						</div>
					</div>
					<!-- JOLI IMAGE -->
					<div class="col">
						<img src="https://cdn.pixabay.com/photo/2018/08/31/02/28/credit-3643710__340.png" width="350px" style="margin-top: 30px;"/>
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
			<!-- Affichage du message de succes -->
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
	</div>
	
	<!-- JAVASCRIPT AFFICAHGE MONTANT -->
	<script>
		function calculPrix(){
			var prixTTC = document.getElementById("prixTTC");
			var nbCredit = document.getElementById("credit").value;
			var TVA = 0.2;
			if(nbCredit == null){
				nbCredit = 0;
			}
			var total = Math.round(((nbCredit*0.09*(1+TVA)) + Number.EPSILON) * 100) / 100;
			prixTTC.innerHTML = "Prix TTC : "+total+"€";
		}
	</script>

</body>
</html>