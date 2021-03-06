<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="${pageContext.request.contextPath}/">ENI - Enchères <i class="fas fa-gavel fa-lg"></i></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
  
  	<ul class="navbar-nav mr-auto"></ul>

	<c:if test="${!empty utilisateur}">
		<a class="text-dark">Crédits : ${utilisateur.credit} pts </a>
		<a style="margin-left: 50px;" class="text-dark" href="${pageContext.request.contextPath}/achat">Acheter des crédits <i class="fas fa-credit-card"></i></a>
		<a style="margin-left: 30px;" class="text-dark" href="${pageContext.request.contextPath}/vente">Vendre un article <i class="fas fa-shopping-cart"></i></a>
		<a style="margin-left: 30px;" class="text-dark" href="${pageContext.request.contextPath}/profil?p=${utilisateur.pseudo}">Profil <i class="fas fa-user-cog"></i></a>
    	<a style="margin-left: 30px;" class="text-dark" href="${pageContext.request.contextPath}/?dc=1">Se déconnecter <i class="fas fa-sign-out-alt"></i></a>
    </c:if>
    
	<c:if test="${empty utilisateur}">
	  	<a class="text-dark" href="${pageContext.request.contextPath}/?co=1">Se connecter <i class="fas fa-sign-in-alt"></i></a>
    </c:if>
    
  </div>
</nav>