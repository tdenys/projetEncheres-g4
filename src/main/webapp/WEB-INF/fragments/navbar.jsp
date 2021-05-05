<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! HttpSession session; %>
<% session = request.getSession(); %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">ENI - Enchères</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
  	<%
		if(session.getAttribute("pseudo") != null)
		{
	%>
	<div>
		<form method="get" action="profil">
		  	<input type="hidden" name="p" value="<%= session.getAttribute("pseudo") %>">
		    <button class="btn" type="submit">Mon profil</a>
		</form>
	</div>
	
    <a class="btn" href="${pageContext.request.contextPath}/?dc=1">Se déconnecter</a>
    <%
		}
	%>
	<%
		if(session.getAttribute("pseudo") == null)
		{
	%>
  	<form method="post" action="index">
  		<input type="hidden" name="co" value="1">
    	<button class="btn" type="submit">Se connecter</a>
    </form>
    <%
		}
	%>
  </div>
</nav>